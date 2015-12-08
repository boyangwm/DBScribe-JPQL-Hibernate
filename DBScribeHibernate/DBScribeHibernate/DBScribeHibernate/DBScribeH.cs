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
    /// <summary>
    /// This class calls all the DBScribeHibernateComponents.
    /// It collects information from source code and executes every step, then generete database report
    /// </summary>
    class DBScribeH
    {
        /// <summary> Target project loaction </summary>
        public string TargetProjPath;
        /// <summary> Target project name </summary>
        public string ProjName;

        /// <summary> Hibernate Configure File Related Variables</summary>
        public MappingParser mappingParser;

        /// <summary> POJO DB Classes that are registered in the mapping file</summary>
        public Dictionary<string, string> registeredClassFullNameToTableName;
        /// <summary> POJO DB Class's property --> table attributes </summary>
        public Dictionary<string, string> classPropertyToTableColumn;
        /// <summary> DB tables --> table constraints </summary>
        public Dictionary<string, List<string>> tableNameToTableConstraints;

        /// <summary> POJO DB Classes that are registered in the mapping file, as well as their parent classes</summary>
        public Dictionary<string, string> allDBClassToTableName;
        /// <summary> Class properties from POJO DB Classes that are registered in the mapping file, as well as their parent classes</summary>
        public Dictionary<string, string> allDBClassPropToTableAttr;


        /// <summary> Target project global namespace</summary>
        public NamespaceDefinition globalNamespace;
        /// <summary> List of all MethodDefinitions in target Hibernate project </summary>
        public IEnumerable<MethodDefinition> methods;
        /// <summary> Call graph manager </summary>
        public CGManager cgm;
        /// <summary> total number of methods in target project, including db methods and non-db methods </summary>
        public int num_of_methods;
        /// <summary> Dictionary for the number of methods for each DB method type </summary>
        public Dictionary<string, int> method_count;
        /// <summary> A sorted list of all methods in target project </summary>
        public List<MethodDefinition> bottomUpSortedMethods;

        /// <summary> All classes in the project and their parent classes</summary>
        public Dictionary<string, List<string>> allClassToParentClasses;

        /// <summary> Method headers for all database methods in target project </summary>
        public List<string> AllMethodHeaders;
        /// <summary> For each method in target project: method header --> method full description </summary>
        public Dictionary<string, string> AllMethodFullDescriptions;
        /// <summary> For each method in target project: method header --> global unique index </summary>
        Dictionary<string, int> GlobalMethodHeaderToIndex;

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="targetProjPath">Target project location</param>
        /// <param name="projName">Target prject name</param>
        public DBScribeH(string targetProjPath, string projName)
        {
            this.TargetProjPath = targetProjPath;
            this.ProjName = projName;
        }

        /// <summary>
        /// Run each step of DBSrcibeHibernate
        /// </summary>
        public void run()
        {
            Step1_1_ConfigParser(); // Analyze Hibernate Configuration files
            Console.WriteLine("\n\n");

            Step2_1_GenerateCallGraph();

            Step1_2_ConfigParser(); // allDBClass --> table name; all DB class properties --> table column

            // Bottom-up travse call graph to generate and propate method descriptions
            Tuple<int, string> results = Step3_1_BottomUpTraverseMethods();
            int num_methods = results.Item1; // total number of db methods
            string output = results.Item2; // an output for text file

            Utility.CreateDirectoryIfNotExist(Constants.ResultPath);
            string projName = Utility.GetProjectName(Constants.ProjName);

            // Generate DBScribe report using StringTemplate
            HomeGenerator homeGenerator = new HomeGenerator(projName, num_methods, num_of_methods, 
                method_count[Constants.SQLMethodCategory.SQLOperatingMethod.ToString()],
                method_count[Constants.SQLMethodCategory.LocalSQLMethod.ToString()],
                method_count[Constants.SQLMethodCategory.DelegatedSQLMethod.ToString()],
                AllMethodHeaders, AllMethodFullDescriptions, GlobalMethodHeaderToIndex);
            homeGenerator.Generate(Constants.ResultPath + "DBScribe_" + projName + ".html");
        }

        /// <summary>
        /// Step1 part 1 of DBSCribeHibernate: 
        /// (1) Map POJO classes and their properties to Database table and attributes. 
        /// (2) Get constraints for each database table. 
        /// There are two ways of mapping: one through XML mapping file; the other through Annotation.
        /// </summary>
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


        /// <summary>
        /// Step 2 of DBScribeHibernate. 
        /// Build call graph for the target project. 
        /// Also, topologically sorted the methods in the call graph, for later bottom-up method description propagation.
        /// </summary>
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

        /// <summary>
        /// Step 1 part 2 of DBSribeHibernate. 
        /// Complete unmapped infomation with the newly get information Step 2. 
        /// (Mainly consider POJO class hierarchy)
        /// </summary>
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

        /// <summary>
        /// Get mapping of all DB class full name to table name. 
        /// Mainly add mapping for registered class's parent classes
        /// </summary>
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
                            if (!allDBClassToTableName.ContainsKey(pcFullName))
                            {
                                allDBClassToTableName.Add(pcFullName, curTableName);
                            }
                        }
                    }
                }
            }
        }

        /// <summary>
        /// Get all the classes in the project and their parent classes
        /// </summary>
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

        /// <summary>
        /// Get mapping of all DB class property to table attribute
        /// Mainly add mapping for registered class's parent classes
        /// </summary>
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
                    if (!allDBClassPropToTableAttr.ContainsKey(pc + "." + prop))
                    {
                        allDBClassPropToTableAttr.Add(pc + "." + prop, tableAttr);
                    }
                }
            }
        }

        /// <summary>
        /// Bottom-up traverse the call graph, generate and propagate method description
        /// </summary>
        /// <returns> A Tuple: Item 1 --> total number of DB methods. Item 2 --> an output for text file format.</returns>
        public Tuple<int, string> Step3_1_BottomUpTraverseMethods()
        {
            GlobalMethodHeaderToIndex = new Dictionary<string, int>();
            int midx = 1;
            foreach (MethodDefinition method in bottomUpSortedMethods)
            {
                string methodHeader = MethodDescriptionUtil.BuildMethodHeader(method);
                GlobalMethodHeaderToIndex.Add(methodHeader, midx++);
            }


            AllMethodHeaders = new List<string>();
            AllMethodFullDescriptions = new Dictionary<string, string>();

            StringBuilder outputBuilder = new StringBuilder();

            Dictionary<string, List<string>> DBMethodToOpList = new Dictionary<string, List<string>>();
            Dictionary<string, List<string>> DBMethodToConstraitList = new Dictionary<string, List<string>>();
            Dictionary<string, List<string>> DBMethodToTableAttrNames = new Dictionary<string, List<string>>();

            method_count = new Dictionary<string, int>();
            method_count.Add(Constants.SQLMethodCategory.SQLOperatingMethod.ToString(), 0);
            method_count.Add(Constants.SQLMethodCategory.LocalSQLMethod.ToString(), 0);
            method_count.Add(Constants.SQLMethodCategory.DelegatedSQLMethod.ToString(), 0);

            bool isDBMethod = true;
            int db_m_idx = 0;
            foreach (MethodDefinition method in bottomUpSortedMethods)
            {
                string methodHeader = MethodDescriptionUtil.BuildMethodHeader(method);
                isDBMethod = true;

                // (1) Check if POJO Class's Get/Set Function
                BasicMethod basicMethod = MethodUtil.CheckIfGetSetMethodsInPOJOClass(method, allDBClassToTableName, allDBClassPropToTableAttr);
                if (basicMethod != null)
                {
                    string curMethodType = Constants.SQLMethodCategory.SQLOperatingMethod.ToString();
                    string curMethodTitle = "[M-" + ++db_m_idx + ", " + curMethodType + "] " + methodHeader;
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
                        string curMethodType = Constants.SQLMethodCategory.LocalSQLMethod.ToString();
                        string curMethodTitle = "[M-" + ++db_m_idx + ", " + curMethodType + "] " + methodHeader;
                        outputBuilder.Append(curMethodTitle);
                        AllMethodHeaders.Add(curMethodTitle);
                        method_count[curMethodType] += 1;

                        HashSet<string> _DBRelatedMethods = new HashSet<string>(DBMethodToOpList.Keys);
                        IEnumerable<string> invokedDBMethodNameHeaders = MethodUtil.GetInvokedMethodNameHeaderInTheMethod(method).Intersect(_DBRelatedMethods);
                        List<List<MethodDefinition>> calleeList = cgm.findCalleeList(method);
                        MethodDescription curMD = MethodDescriptionUtil.DescribeSessionMethod(method, methodHeader, calleeList, sessionBuiltInFuncList,
                            DBMethodToOpList, DBMethodToConstraitList, DBMethodToTableAttrNames, allDBClassToTableName, allDBClassPropToTableAttr,
                            tableNameToTableConstraints, GlobalMethodHeaderToIndex);
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
                            string curMethodType = Constants.SQLMethodCategory.DelegatedSQLMethod.ToString();
                            string curMethodTitle = "[M-" + ++db_m_idx + ", " + curMethodType + "] " + methodHeader;
                            outputBuilder.Append(curMethodTitle);
                            AllMethodHeaders.Add(curMethodTitle);
                            method_count[curMethodType] += 1;

                            List<List<MethodDefinition>> calleeList = cgm.findCalleeList(method);
                            MethodDescription curMD = MethodDescriptionUtil.DescribeDelegatedMethod(methodHeader, calleeList,
                                DBMethodToOpList, DBMethodToConstraitList, GlobalMethodHeaderToIndex);
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

            return Tuple.Create(db_m_idx, outputBuilder.ToString());
        }

    }
}
