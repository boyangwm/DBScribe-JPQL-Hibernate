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
using DBScribeHibernate.DBScribeHibernate.ReportGenerator;

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
        public int num_of_methods; // total method number
        public Dictionary<string, int> method_count; // each type of DB method count
        public List<MethodDefinition> bottomUpSortedMethods;

        /// <summary> All classes in the project and their parent classes</summary>
        public Dictionary<string, List<string>> allClassToParentClasses;


        public List<string> AllMethodHeaders;
        public Dictionary<string, string> AllMethodFullDescriptions;


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

            HomeGenerator homeGenerator = new HomeGenerator(projName, num_methods, num_of_methods, 
                method_count[Constants.SQLMethodCategory.SQLOperatingMethod.ToString()],
                method_count[Constants.SQLMethodCategory.LocalSQLMethod.ToString()],
                method_count[Constants.SQLMethodCategory.DelegatedSQLMethod.ToString()],
                AllMethodHeaders, AllMethodFullDescriptions);
            homeGenerator.Generate(Constants.ResultPath + "DBScribe_" + projName + ".html");
        }


        public void Step1_1_ConfigParser()
        {
            Console.WriteLine("Project Name: " + ProjName);
            ConfigParser configParser = new ConfigParser(TargetProjPath + "\\" + ProjName, Constants.CfgFileName);
            if (configParser.configFilePath == null)
            {
                Console.WriteLine("Hibernate configuration file " + Constants.CfgFileName + " not found!");
                Console.WriteLine("Assume using Annotation mapping.");
                mappingParser = new AnnotationMappingParser(TargetProjPath + "\\" + ProjName, Constants.CfgFileName, false);
            }
            else if (configParser.ifHasMappingList == false)
            {
                Console.WriteLine("Hibernate configuration file doesn't contain mapping info!");
                Console.WriteLine("Assume using Annotation mapping.");
                mappingParser = new AnnotationMappingParser(TargetProjPath + "\\" + ProjName, Constants.CfgFileName, false);
            }
            else
            {
                Console.WriteLine("Hibernate DTD: " + configParser.HibernateDTD);
                Console.WriteLine("Mapping File Type: " + configParser.MappingFileType);

                if (configParser.MappingFileType == Constants.MappingFileType.XMLMapping)
                {
                    mappingParser = new XMLMappingParser(TargetProjPath + "\\" + ProjName, Constants.CfgFileName);
                }
                else if (configParser.MappingFileType == Constants.MappingFileType.AnnotationMapping)
                {
                    mappingParser = new AnnotationMappingParser(TargetProjPath + "\\" + ProjName, Constants.CfgFileName, true);
                }
                else
                {
                    Console.Error.WriteLine("[Error]Unkonw mapping type.");
                    Console.ReadKey();
                    System.Environment.Exit(1);
                }
            }

            Console.WriteLine("");
            Console.WriteLine("\n<1> Class Full Name <--> Table Name(s)");
            registeredClassFullNameToTableName = mappingParser.GetClassFullNameToTableName();
            //Utility.PrintDictionary(registeredClassFullNameToTableName);

            Console.WriteLine("\n<2> Class Property <--> Table Attribute");
            classPropertyToTableColumn = mappingParser.GetClassPropertyToTableColumn();
            //Utility.PrintDictionary(classPropertyToTableColumn);

            Console.WriteLine("\n<3> Table Name --> Table Constraints");
            tableNameToTableConstraints = mappingParser.GetTableNameToTableConstraints();
            //Utility.PrintTableConstraints(tableNameToTableConstraints);

            //Console.ReadKey();
            //Environment.Exit(0);
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
            AllMethodHeaders = new List<string>();
            AllMethodFullDescriptions = new Dictionary<string, string>();

            StringBuilder outputBuilder = new StringBuilder();

            Dictionary<string, List<string>> DBMethodToOpList = new Dictionary<string, List<string>>();
            Dictionary<string, List<string>> DBMethodToConstraitList = new Dictionary<string, List<string>>();
            Dictionary<string, List<string>> DBMethodToTableAttrNames = new Dictionary<string, List<string>>();

            int idx_m = 0;
            method_count = new Dictionary<string, int>();
            method_count.Add(Constants.SQLMethodCategory.SQLOperatingMethod.ToString(), 0);
            method_count.Add(Constants.SQLMethodCategory.LocalSQLMethod.ToString(), 0);
            method_count.Add(Constants.SQLMethodCategory.DelegatedSQLMethod.ToString(), 0);

            bool isDBMethod = true;
            foreach (MethodDefinition method in bottomUpSortedMethods)
            {

                isDBMethod = true;

                // (1) Check if POJO Class's Get/Set Function
                BasicMethod basicMethod = MethodUtil.CheckIfGetSetMethodsInPOJOClass(method, allDBClassToTableName, allDBClassPropToTableAttr);
                if (basicMethod != null)
                {
                    
                    string methodHeader = MethodDescriptionUtil.BuildMethodHeader(method);
                    string curMethodType = Constants.SQLMethodCategory.SQLOperatingMethod.ToString();
                    string curMethodTitle = "[M-" + ++idx_m + ", " + curMethodType + "] " + methodHeader;
                    outputBuilder.Append(curMethodTitle);
                    AllMethodHeaders.Add(curMethodTitle);
                    method_count[curMethodType] += 1;

                    MethodDescription curMD = MethodDescriptionUtil.DescribeBasicMethod(basicMethod, tableNameToTableConstraints);
                    outputBuilder.AppendLine(curMD.MethodDescriptionStr);
                    AllMethodFullDescriptions.Add(curMethodTitle, curMD.MethodDescriptionStr);

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
                        string curMethodType = Constants.SQLMethodCategory.LocalSQLMethod.ToString();
                        string curMethodTitle = "[M-" + ++idx_m + ", " + curMethodType + "] " + methodHeader;
                        outputBuilder.Append(curMethodTitle);
                        AllMethodHeaders.Add(curMethodTitle);
                        method_count[curMethodType] += 1;

                        HashSet<string> _DBRelatedMethods = new HashSet<string>(DBMethodToOpList.Keys);
                        IEnumerable<string> invokedDBMethodNameHeaders = MethodUtil.GetInvokedMethodNameHeaderInTheMethod(method).Intersect(_DBRelatedMethods);
                        List<List<MethodDefinition>> calleeList = cgm.findCalleeList(method);
                        MethodDescription curMD = MethodDescriptionUtil.DescribeSessionMethod(method, methodHeader, calleeList, sessionBuiltInFuncList,
                            DBMethodToOpList, DBMethodToConstraitList, DBMethodToTableAttrNames, allDBClassToTableName, allDBClassPropToTableAttr);
                        outputBuilder.AppendLine(curMD.MethodDescriptionStr);
                        AllMethodFullDescriptions.Add(curMethodTitle, curMD.MethodDescriptionStr);
                        DBMethodToOpList.Add(methodHeader, curMD.MethodOperationList);
                        DBMethodToConstraitList.Add(methodHeader, curMD.ConstraintList);
                        DBMethodToTableAttrNames.Add(methodHeader, curMD.DBTableAttrList);

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
                            string curMethodType = Constants.SQLMethodCategory.DelegatedSQLMethod.ToString();
                            string curMethodTitle = "[M-" + ++idx_m + ", " + curMethodType + "] " + methodHeader;
                            outputBuilder.Append(curMethodTitle);
                            AllMethodHeaders.Add(curMethodTitle);
                            method_count[curMethodType] += 1;

                            List<List<MethodDefinition>> calleeList = cgm.findCalleeList(method);
                            MethodDescription curMD = MethodDescriptionUtil.DescribeDelegatedMethod(methodHeader, calleeList, 
                                DBMethodToOpList, DBMethodToConstraitList);
                            outputBuilder.AppendLine(curMD.MethodDescriptionStr);
                            AllMethodFullDescriptions.Add(curMethodTitle, curMD.MethodDescriptionStr);
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

    }
}
