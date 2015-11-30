using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ABB.SrcML;
using ABB.SrcML.Data;
using DBScribeHibernate.DBScribeHibernate.CallGraphExtractor;
using DBScribeHibernate.DBScribeHibernate.Stereotype;
using DBScribeHibernate.DBScribeHibernate;
using DBScribeHibernate.DBScribeHibernate.ConfigParser;
using DBScribeHibernate.DBScribeHibernate.Util;
using DBScribeHibernate.DBScribeHibernate.DescriptionTemplates;

namespace DBScribeHibernate
{
    class DBScribeH
    {
        public string TargetProjPath;
        public string ProjName;

        /// <summary> Hibernate Configure File Related Variables</summary>
        public MappingParser mappingParser;

        /// <summary> POJO DB Classes that are registered in the mapping file</summary>
        public Dictionary<string, string> registeredClassFullNameToTableName;
        /// <summary> POJO DB Class's property --> table attributes </summary>
        public Dictionary<string, string> classPropertyToTableColumn;
        public Dictionary<string, List<string>> tableNameToTableConstraints;

        /// <summary> POJO DB Classes that are registered in the mapping file, as well as their parent classes</summary>
        public Dictionary<string, string> allDBClassToTableName;
        /// <summary> Class properties from POJO DB Classes that are registered in the mapping file, as well as their parent classes</summary>
        public Dictionary<string, string> allDBClassPropToTableAttr;


        /// <summary> Call Graph Related Variables</summary>
        public NamespaceDefinition globalNamespace;
        public IEnumerable<MethodDefinition> methods;
        public CGManager cgm;
        public int num_of_methods;
        public List<MethodDefinition> bottomUpSortedMethods;
        //public HashSet<MethodDefinition> methods_LocalSQLMethods;  // methods call transaction and sessions
        //public HashSet<MethodDefinition> methods_SQLOperatingMethods;

        /// <summary> All classes in the project and their parent classes</summary>
        public Dictionary<string, List<string>> allClassToParentClasses;

        /// <summary>
        /// There are three kinds of methods:
        /// (1) SQL Operating methods: defined in POJO classes
        /// (2) Local SQL methods: call (1) SQL operating methods and Hibernate session built-in functions(i.e. save/update/delete)
        /// (3) Delegated SQL methods: call (2)
        /// </summary>
        //public List<MethodDefinition> SortedSqlOperatingMethods;
        //public List<MethodDefinition> SortedLocalSqlMethods;
        //public List<MethodDefinition> SortedDelegatedSqlMethods;


        public DBScribeH(string targetProjPath, string projName)
        {
            this.TargetProjPath = targetProjPath;
            this.ProjName = projName;
        }


        public void run()
        {
            Step1_1_ConfigParser(); // Analyze Hibernate Configuration files
            Console.WriteLine("\n\n");

            Step2_1_GenerateCallGraph();

            Step1_2_ConfigParser(); // allDBClass --> table name; all DB class properties --> table column

            Tuple<int, string> results = Step3_1_BottomUpTraverseMethods();
            int num_methods = results.Item1;
            string output = results.Item2;

            Utility.CreateDirectoryIfNotExist(Constants.ResultPath);
            string projName = Utility.GetProjectName(Constants.ProjName);
            StreamWriter writetext = new StreamWriter(Constants.ResultPath + "DBScribe_" + projName + ".txt");
            writetext.WriteLine("DBScribe Report for Project " + projName);
            writetext.WriteLine("There are " + num_methods + " database-related methods.\n");
            writetext.WriteLine("");
            writetext.WriteLine(Constants.Divider);
            writetext.WriteLine("");

            writetext.Write(output);
            writetext.Close();

            Console.Write(output);

        }


        public void Step1_1_ConfigParser()
        {
            Console.WriteLine("Project Name: " + ProjName);
            ConfigParser configParser = new ConfigParser(TargetProjPath + "\\" + ProjName, Constants.CfgFileName);
            if (configParser.configFilePath == null)
            {
                Console.Error.WriteLine("[Error] Hibernate configuration file " + Constants.CfgFileName + " not found!");
                Console.ReadKey();
                System.Environment.Exit(-1);
            }

            Console.WriteLine("Hibernate DTD: " + configParser.HibernateDTD);
            Console.WriteLine("Mapping File Type: " + configParser.MappingFileType);
            Console.WriteLine("");

            if (configParser.MappingFileType == Constants.MappingFileType.XMLMapping)
            {
                mappingParser = new XMLMappingParser(TargetProjPath + "\\" + ProjName, Constants.CfgFileName);
                if (Constants.ShowLog)
                {
                    Console.WriteLine("Mapping Parser Type: " + mappingParser.GetMappingParserType());
                }

                Console.WriteLine("\n<1> Class Full Name <--> Table Name(s)");
                registeredClassFullNameToTableName = mappingParser.GetClassFullNameToTableName();
                //Utility.PrintDictionary(registeredClassFullNameToTableName);

                Console.WriteLine("\n<2> Class Property <--> Table Attribute");
                classPropertyToTableColumn = mappingParser.GetClassPropertyToTableColumn();
                //Utility.PrintDictionary(classPropertyToTableColumn);

                Console.WriteLine("\n<3> Table Name --> Table Constraints");
                tableNameToTableConstraints = mappingParser.GetTableNameToTableConstraints();
                //Utility.PrintTableConstraints(tableNameToTableConstraints);

            }
            else if (configParser.MappingFileType == Constants.MappingFileType.AnnotationMapping)
            {
                mappingParser = new AnnotationMappingParser(TargetProjPath + "\\" + ProjName, Constants.CfgFileName);
                if (Constants.ShowLog)
                {
                    Console.WriteLine("Mapping Parser Type: " + mappingParser.GetMappingParserType());
                }

                Console.WriteLine("\n<1> Class Full Name <--> Table Name(s)");
                registeredClassFullNameToTableName = mappingParser.GetClassFullNameToTableName();
                Utility.PrintDictionary(registeredClassFullNameToTableName);

                Console.WriteLine("\n<2> Class Property <--> Table Attribute");
                classPropertyToTableColumn = mappingParser.GetClassPropertyToTableColumn();
                Utility.PrintDictionary(classPropertyToTableColumn);

                Console.WriteLine("\n<3> Table Name --> Table Constraints");
                tableNameToTableConstraints = mappingParser.GetTableNameToTableConstraints();
                Utility.PrintTableConstraints(tableNameToTableConstraints);

                //Console.ReadKey();
                //Environment.Exit(0);
            }
            else
            {
                Console.WriteLine("Unkonw mapping type.");
                Console.ReadKey();
                System.Environment.Exit(1);
            }
        }

        public void Step2_1_GenerateCallGraph()
        {
            // Get methods from SrcML.net
            //Console.Out.WriteLine("Invoke call graph generator ");
            string dataDir = @"TESTNAIVE_1.0";
            string localProj = TargetProjPath + "\\" + ProjName;
            using (var project = new DataProject<CompleteWorkingSet>(dataDir, localProj, Constants.SrcmlLoc))
            {
                string unknownLogPath = Path.Combine(project.StoragePath, "unknown.log");
                DateTime start = DateTime.Now, end;
                using (var unknownLog = new StreamWriter(unknownLogPath))
                {
                    project.UnknownLog = unknownLog;
                    project.UpdateAsync().Wait();
                }
                end = DateTime.Now;

                project.WorkingSet.TryObtainReadLock(5000, out globalNamespace);
                try
                {
                    // return IEnumerable<MethodDefinition> type
                    methods = globalNamespace.GetDescendants<MethodDefinition>();
                    num_of_methods = globalNamespace.GetDescendants<MethodDefinition>().Count();
                    Console.WriteLine("# of methods = " + num_of_methods);

                    cgm = new CGManager();
                    cgm.BuildCallGraph(methods);

                    bottomUpSortedMethods = InvokeCGManager.GetBottomUpSortedMethodsFromCallGraph(methods, cgm);
                    //Console.WriteLine("\nBottom-up sorted methods:");
                    //foreach (MethodDefinition m in bottomUpSortedMethods)
                    //{
                    //    Console.WriteLine(m.GetFullName());
                    //    //if (m.GetFullName().Contains("BaseGradePK"))
                    //    //{
                    //    //    Console.WriteLine(m.GetFullName());
                    //    //}
                    //}

                    ////methods calling "beginTransaction" --> LocalSQLMethods -- not accurate!!!
                    //methods_LocalSQLMethods = InvokeCGManager.GetMethodesWithCertainMethodCall(HibernateKeywords.beginTransaction, methods);
                    //Console.WriteLine("\nLocal SQL Methods (invoking session/transaction): ");
                    //foreach (MethodDefinition m in methods_LocalSQLMethods)
                    //{
                    //    Console.WriteLine(m.GetFullName());
                    //}


                    // methods in POJO class: i.e. get/set
                    //Console.WriteLine("\nSQL Operating Methods (in POJO Class): ");
                    //methods_SQLOperatingMethods = InvokeCGManager.GetSQLOperatingMethods(methods, classFullNameToTableName);
                    //foreach (MethodDefinition m in methods_SQLOperatingMethods)
                    //{
                    //    Console.WriteLine(m.GetFullName());
                    //}


                    //cgm.getMethodByFullName();

                    // Step 2.   Testing
                    //Console.WriteLine("\nAnalyze methods:");
                    //InvokeCGManager.TestHowToAnalyzeMethods(bottomUpSortedMethods, "2_analyze_methods_using_srcml.net.txt");
                    //InvokeCGManager.TestHowToUseMethodAnalyzer(bottomUpSortedMethods, "3_analyze_methods_using_MethodAnalyzer.txt");
                    //IEnumerable<TypeDefinition> classes = globalNamespace.GetDescendants<TypeDefinition>();
                    //InvokeCGManager.TestHowToAnalyzeClasses(classes, "1_analyze_classes_using_srcml.net.txt");

                }
                finally
                {
                    project.WorkingSet.ReleaseReadLock();
                }
            }
        }

        public void Step1_2_ConfigParser()
        {
            // all DB class full name <--> table name
            _SetAllDBClassToTableName();
            //Console.WriteLine("\nAll DB Class Names --> Table Names");
            //Utility.PrintDictionary(allDBClassToTableName);

            // get all the classes in the project and their parent classes
            _SetAllClassToParentClasses();
            //Console.WriteLine("\nAll Project's Classes --> Their Parent Classes");
            //Utility.PrintDictionary(allClassToParentClasses, false);

            // all DB class property <--> table attribute
            _SetAllDBClassPropToTableAttr();
            //Console.WriteLine("\nAll DB Class Property --> Table Attribute");
            //Utility.PrintDictionary(allDBClassPropToTableAttr);
        }

        private void _SetAllDBClassToTableName()
        {
            allDBClassToTableName = new Dictionary<string, string>();
            foreach (MethodDefinition m in methods)
            {
                TypeDefinition curClass = MethodUtil.GetDeclaringClass(m);
                if (curClass == null)
                {
                    //Console.WriteLine("[Error] Cannot method's declaring class!");
                    continue;
                }

                string curClassName = MethodUtil.GetDeclaringClass(m).GetFullName();
                if (registeredClassFullNameToTableName.ContainsKey(curClassName))
                {
                    if (allDBClassToTableName.ContainsKey(curClassName))
                    {
                        continue;  // already handled one method from this class, so moving on
                    }
                    else
                    {
                        string curTableName = registeredClassFullNameToTableName[curClassName];
                        allDBClassToTableName.Add(curClassName, curTableName);
                        // add its parent class(es)
                        foreach (TypeDefinition pc in MethodUtil.GetParentClasses(curClass))
                        {
                            string pcFullName = pc.GetFullName();
                            allDBClassToTableName.Add(pcFullName, curTableName);
                        }
                    }
                }
            }
        }

        private void _SetAllClassToParentClasses()
        {
            allClassToParentClasses = new Dictionary<string, List<string>>();
            foreach (MethodDefinition m in methods)
            {
                TypeDefinition curClass = MethodUtil.GetDeclaringClass(m);
                if (curClass == null)
                {
                    //Console.WriteLine(Console.WriteLine("[Error] Cannot method's declaring class!");
                    continue;
                }
                string curClassName = curClass.GetFullName();
                if (allClassToParentClasses.ContainsKey(curClassName))
                {
                    continue;  // already handled one method from this class, so moving on
                }
                else
                {
                    // add its parent class(es)
                    List<string> pcList = new List<string>();
                    foreach (TypeDefinition pc in MethodUtil.GetParentClasses(curClass))
                    {
                        pcList.Add(pc.GetFullName());
                    }
                    allClassToParentClasses.Add(curClassName, pcList);
                }
            }
        }

        private void _SetAllDBClassPropToTableAttr()
        {
            allDBClassPropToTableAttr = new Dictionary<string, string>();
            foreach (KeyValuePair<string, string> item in classPropertyToTableColumn)
            {
                string curClassProp = item.Key;
                string tableAttr = item.Value;
                allDBClassPropToTableAttr.Add(curClassProp, tableAttr);

                string[] segs = curClassProp.Split('.');
                string curClass = segs[0];
                for (int i = 1; i < segs.Length - 1; i++)
                {
                    curClass += "." + segs[i];
                }
                string prop = segs[segs.Length - 1];

                List<string> parentClasses = allClassToParentClasses[curClass];
                foreach (string pc in parentClasses)
                {
                    allDBClassPropToTableAttr.Add(pc + "." + prop, tableAttr);
                }
            }
        }

        public Tuple<int, string> Step3_1_BottomUpTraverseMethods()
        {
            StringBuilder outputBuilder = new StringBuilder();

            Dictionary<string, List<string>> DBMethodToOpList = new Dictionary<string, List<string>>();
            Dictionary<string, List<string>> DBMethodToConstraitList = new Dictionary<string, List<string>>();
            Dictionary<string, List<string>> DBMethodToTableAttrNames = new Dictionary<string, List<string>>();

            int idx_m = 0;
            bool isDBMethod = true;
            foreach (MethodDefinition method in bottomUpSortedMethods)
            {
                isDBMethod = true;

                //var dc = MethodUtil.GetDeclaringClass(method);
                //if (dc != null)
                //{
                //    var name = dc.GetFullName();
                //    if (name != "com.jspdev.biyesheji.Course")
                //    {
                //        continue;
                //    }
                //}
                //else
                //{
                //    continue;
                //}

                // (1) Check if POJO Class's Get/Set Function
                BasicMethod basicMethod = MethodUtil.CheckIfGetSetMethodsInPOJOClass(method, allDBClassToTableName, allDBClassPropToTableAttr);
                if (basicMethod != null)
                {
                    
                    string methodHeader = MethodDescriptionUtil.BuildMethodHeader(method);
                    outputBuilder.Append("[M-" + ++idx_m + "] " + methodHeader);
                    MethodDescription curMD = MethodDescriptionUtil.DescribeBasicMethod(basicMethod, tableNameToTableConstraints);
                    outputBuilder.AppendLine(curMD.MethodDescriptionStr);
                    if (!DBMethodToOpList.ContainsKey(methodHeader))
                    {
                        DBMethodToOpList.Add(methodHeader, curMD.MethodOperationList);
                        DBMethodToConstraitList.Add(methodHeader, curMD.ConstraintList);
                        DBMethodToTableAttrNames.Add(methodHeader, curMD.DBTableAttrList);
                    }
                }
                else
                {
                    // (2) Check if call session built-in function
                    List<SessionBuiltInFunction> sessionBuiltInFuncList = MethodUtil.CheckIfCallSessionBuiltInFunction(method, allDBClassToTableName);
                    if (sessionBuiltInFuncList != null && sessionBuiltInFuncList.Count() != 0)
                    {
                        string methodHeader = MethodDescriptionUtil.BuildMethodHeader(method);
                        outputBuilder.Append("[M-" + ++idx_m + "] " + methodHeader);

                        HashSet<string> _DBRelatedMethods = new HashSet<string>(DBMethodToOpList.Keys);
                        IEnumerable<string> invokedDBMethodNameHeaders = MethodUtil.GetInvokedMethodNameHeaderInTheMethod(method).Intersect(_DBRelatedMethods);
                        List<List<MethodDefinition>> calleeList = cgm.findCalleeList(method);
                        MethodDescription curMD = MethodDescriptionUtil.DescribeSessionMethod(method, methodHeader, calleeList, sessionBuiltInFuncList,
                            DBMethodToOpList, DBMethodToConstraitList, DBMethodToTableAttrNames, allDBClassToTableName, allDBClassPropToTableAttr);
                        outputBuilder.AppendLine(curMD.MethodDescriptionStr);
                        DBMethodToOpList.Add(methodHeader, curMD.MethodOperationList);
                        DBMethodToConstraitList.Add(methodHeader, curMD.ConstraintList);
                        DBMethodToTableAttrNames.Add(methodHeader, curMD.DBTableAttrList);

                        //foreach (SessionBuiltInFunction item in sessionBuiltInFuncList)
                        //{
                        //    Console.WriteLine(item);
                        //}

                        //IEnumerable<string> invokedDBMethodNames = MethodUtil.GetInvokedMethodNameInTheMethod(method).Intersect(DBRelatedMethods);
                        //if (invokedDBMethodNames.Count() != 0)
                        //{
                        //    string callChainStr = CallGraphUtil.GetCalleeListStr(cgm.findCalleeList(method));
                        //    Console.WriteLine(callChainStr);
                        //}

                        
                    }
                    else
                    {
                        // (3) check if call db related functions (POJO get/set/constructor or session built-in functions)
                        // Otherwise, discard it!
                        HashSet<string> _DBRelatedMethods = new HashSet<string>(DBMethodToOpList.Keys);
                        IEnumerable<string> invokedDBMethodNameHeaders = MethodUtil.GetInvokedMethodNameHeaderInTheMethod(method).Intersect(_DBRelatedMethods);
                        if (invokedDBMethodNameHeaders.Count() != 0)
                        {
                            string methodHeader = MethodDescriptionUtil.BuildMethodHeader(method);
                            outputBuilder.Append("[M-" + ++idx_m + "] " + methodHeader);

                            //DBRelatedMethods.Add(method.GetFullName());
                            //string callChainStr = CallGraphUtil.GetCalleeListStr(cgm.findCalleeList(method));
                            //Console.WriteLine(callChainStr);
                            List<List<MethodDefinition>> calleeList = cgm.findCalleeList(method);
                            MethodDescription curMD = MethodDescriptionUtil.DescribeDelegatedMethod(methodHeader, calleeList, 
                                DBMethodToOpList, DBMethodToConstraitList);
                            outputBuilder.AppendLine(curMD.MethodDescriptionStr);
                            if (!DBMethodToOpList.ContainsKey(methodHeader))
                            {
                                DBMethodToOpList.Add(methodHeader, curMD.MethodOperationList);
                                DBMethodToConstraitList.Add(methodHeader, curMD.ConstraintList);
                                DBMethodToTableAttrNames.Add(methodHeader, curMD.DBTableAttrList);
                            }
                        }
                        else
                        {
                            isDBMethod = false;
                            //string methodHeader = MethodDescriptionUtil.BuildMethodHeader(method);
                            //outputBuilder.Append("[M-" + ++idx_m + "] " + methodHeader);

                            //outputBuilder.AppendLine("\tNot DB-related method!");
                        }
                    }
                }

                if (isDBMethod == true)
                {
                    outputBuilder.AppendLine(Constants.Divider);
                    outputBuilder.AppendLine("");
                }
            }

            return Tuple.Create(idx_m, outputBuilder.ToString());
        }

        //public void Step3_1_DivideAllMethodsIntoThreeCategories_stale()
        //{
        //    StringBuilder outputBuilder = new StringBuilder();

        //    HashSet<string> allPojoClasses = new HashSet<string>(allDBClassToTableName.Keys);

        //    HashSet<string> _sqlOperatingMethodNames = new HashSet<string>();
        //    // get all POJO classes and those SQL operating methods
        //    foreach (TypeDefinition curClass in globalNamespace.GetDescendants<TypeDefinition>())
        //    {
        //        if (allPojoClasses.Contains(curClass.GetFullName())) // if this is a POJO class
        //        {
        //            //Console.WriteLine(curClass.GetFullName());
        //            foreach (MethodDefinition md in curClass.GetDescendants<MethodDefinition>()) // get current class's locally defined methods
        //            {
        //                _sqlOperatingMethodNames.Add(md.GetFullName());
        //            }
        //        }
        //    }

        //    // get sorted sql operating methods
        //    outputBuilder.AppendLine("==== SQL Operating Methods: ");
        //    SortedSqlOperatingMethods = new List<MethodDefinition>();
        //    foreach (MethodDefinition md in bottomUpSortedMethods)
        //    {
        //        if (_sqlOperatingMethodNames.Contains(md.GetFullName()))
        //        {
        //            outputBuilder.AppendLine(md.GetFullName());
        //            SortedSqlOperatingMethods.Add(md);
        //        }
        //    }

        //    outputBuilder.AppendLine("==== Local SQL Methods: ");
        //    // get sorted local sql methods
        //    SortedLocalSqlMethods = new List<MethodDefinition>();
        //    HashSet<string> _localSqlMethodNames = new HashSet<string>();
        //    foreach (MethodDefinition md in bottomUpSortedMethods)
        //    {
        //        if (!_sqlOperatingMethodNames.Contains(md.GetFullName()))
        //        {
        //            HashSet<string> invokedMethodNames = MethodUtil.GetInvokedMethodNameInTheMethod(md); // both locally and externally
        //            if (invokedMethodNames.Overlaps(_sqlOperatingMethodNames))
        //            {
        //                outputBuilder.AppendLine(md.GetFullName());
        //                SortedLocalSqlMethods.Add(md);
        //                _localSqlMethodNames.Add(md.GetFullName());
        //            }
        //        }
        //    }

        //    outputBuilder.AppendLine("==== Delegated SQL Methods");
        //    SortedDelegatedSqlMethods = new List<MethodDefinition>();
        //    HashSet<string> _delegatedSqlMethodNames = new HashSet<string>();
        //    foreach (MethodDefinition md in bottomUpSortedMethods)
        //    {
        //        if (!_sqlOperatingMethodNames.Contains(md.GetFullName()) && !_localSqlMethodNames.Contains(md.GetFullName()))
        //        {
        //            HashSet<string> invokedMethodNames = MethodUtil.GetInvokedMethodNameInTheMethod(md); // both locally and externally
        //            if (invokedMethodNames.Overlaps(_sqlOperatingMethodNames) || invokedMethodNames.Overlaps(_localSqlMethodNames))
        //            {
        //                outputBuilder.AppendLine(md.GetFullName());
        //                SortedDelegatedSqlMethods.Add(md);
        //                _delegatedSqlMethodNames.Add(md.GetFullName());
        //            }
        //        }
        //    }

        //    outputBuilder.AppendLine("==== Non DB-Related Methods: ");
        //    List<MethodDefinition> NonDBMethods = new List<MethodDefinition>();
        //    foreach (MethodDefinition md in bottomUpSortedMethods)
        //    {
        //        if (!_sqlOperatingMethodNames.Contains(md.GetFullName()) && !_localSqlMethodNames.Contains(md.GetFullName())
        //            && !_delegatedSqlMethodNames.Contains(md.GetFullName()))
        //        {
        //            //var stmts = md.GetDescendantsAndSelf();
        //            //if (stmts.Count() < 2)
        //            //{
        //            //    continue;
        //            //}
        //            //MethodUtil.CheckIfCallSessionBuiltInFunction(md);
        //            NonDBMethods.Add(md);
        //            outputBuilder.AppendLine(md.GetFullName());
        //        }
        //    }

        //    foreach (MethodDefinition md in bottomUpSortedMethods)
        //    {
        //        //var dc = MethodUtil.GetDeclaringClass(md);
        //        //if (dc != null && dc.GetFullName() == "com.jspdev.biyesheji.Student")
        //        //if (md.GetFullName() == "com.jspdev.biyesheji.Student.deleteStudent")
        //        if (true)
        //        {
        //            List<SessionBuiltInFunction> sessionFunctionList = MethodUtil.CheckIfCallSessionBuiltInFunction(md, allDBClassToTableName);
        //            if (sessionFunctionList.Count() == 0)
        //            {
        //                continue;
        //            }
        //            Console.WriteLine("=== " + md.GetFullName());
        //            for (int i = 0; i < sessionFunctionList.Count(); i++)
        //            {
        //                var item = sessionFunctionList[i];
        //                Console.WriteLine("\tsession." + item.FunctionName + "(" + item.TargetClassName + ")");
        //            }
        //        }
        //    }

        //    /*
        //    // Step 2.   Testing
        //    string filePath = Constants.LogPath + "\\" + Utility.GetProjectName(Constants.ProjName);
        //    Utility.CreateDirectoryIfNotExist(filePath);
        //    StreamWriter writetext = new StreamWriter(filePath + "\\1_divide_methods_in_3_categories.txt");
        //    writetext.WriteLine("# of total methods = " + bottomUpSortedMethods.Count());
        //    writetext.WriteLine("# of sql operating methods = " + SortedSqlOperatingMethods.Count());
        //    writetext.WriteLine("# of local sql methods = " + SortedLocalSqlMethods.Count());
        //    writetext.WriteLine("# of delegated sql methods = " + SortedDelegatedSqlMethods.Count());
        //    writetext.WriteLine("# of non-database operating methods = " + NonDBMethods.Count());
        //    writetext.WriteLine("");
        //    writetext.Write(outputBuilder.ToString());
        //    writetext.Close();
        //    Console.WriteLine("Writing to file finished!");
            
        //    InvokeCGManager.TestHowToUseMethodAnalyzer(SortedSqlOperatingMethods, "2_analyze_sql_operating_methods_using_MethodAnalyzer.txt");
        //    InvokeCGManager.TestHowToUseMethodAnalyzer(SortedLocalSqlMethods, "3_analyze_local_sql_methods_using_MethodAnalyzer.txt");
        //    InvokeCGManager.TestHowToUseMethodAnalyzer(SortedDelegatedSqlMethods, "4_analyze_delegated_sql_methods_using_MethodAnalyzer.txt");
        //    InvokeCGManager.TestHowToUseMethodAnalyzer(NonDBMethods, "5_analyze_non_db_methods_using_MethodAnalyzer.txt");
        //     **/
        //}

    }
}
