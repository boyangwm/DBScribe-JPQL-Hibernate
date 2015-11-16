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
        IEnumerable<MethodDefinition> methods;
        CGManager cgm;
        public int num_of_methods;
        public List<MethodDefinition> bottomUpSortedMethods;
        //public HashSet<MethodDefinition> methods_LocalSQLMethods;  // methods call transaction and sessions
        //public HashSet<MethodDefinition> methods_SQLOperatingMethods;

        /// <summary> All classes in the project and their parent classes</summary>
        public Dictionary<string, List<string>> allClassToParentClasses;


        public Dictionary<string, BasicGetSetMethod> basicGetSetMethods;


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

            Step3_1_GetBasicGetSetMethods();
            Console.WriteLine("\nGet and Set Methods: ");
            foreach (KeyValuePair<string, BasicGetSetMethod> item in basicGetSetMethods)
            {
                Console.WriteLine(item.Key + " <--> " + item.Value);
            }
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
                Console.WriteLine("[Later] Will handle " + configParser.MappingFileType + " later!!!");
                Console.ReadKey();
                System.Environment.Exit(1);
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

                NamespaceDefinition globalNamespace;
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
                    //Console.WriteLine("\n================================================");
                    //InvokeCGManager.TestHowToAnalyzeMethods(methods);
                    //Console.WriteLine("\nAnalyze methods:");
                    //InvokeCGManager.TestHowToUseMethodAnalyzer(methods);

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
                HibernateMethodAnalyzer mAnalyzer = new HibernateMethodAnalyzer(m);
                if (mAnalyzer.IsSuccess != 0)
                {
                    //Console.WriteLine(mAnalyzer.GetFailInfo());
                    continue;
                }
                string curClassName = mAnalyzer.DeclaringClass.GetFullName();
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
                        foreach (TypeDefinition pc in mAnalyzer.ParentClasses)
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
                HibernateMethodAnalyzer mAnalyzer = new HibernateMethodAnalyzer(m);
                if (mAnalyzer.IsSuccess != 0)
                {
                    //Console.WriteLine(mAnalyzer.GetFailInfo());
                    continue;
                }
                string curClassName = mAnalyzer.DeclaringClass.GetFullName();
                if (allClassToParentClasses.ContainsKey(curClassName))
                {
                    continue;  // already handled one method from this class, so moving on
                }
                else
                {
                    // add its parent class(es)
                    List<string> pcList = new List<string>();
                    foreach (TypeDefinition pc in mAnalyzer.ParentClasses)
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

        public void Step3_1_GetBasicGetSetMethods()
        {
            basicGetSetMethods = new Dictionary<string, BasicGetSetMethod>();
            foreach (MethodDefinition method in bottomUpSortedMethods)
            {
                HibernateMethodAnalyzer mAnalyzer = new HibernateMethodAnalyzer(method);
                if (mAnalyzer.IsSuccess != 0)
                {
                    //Console.WriteLine(mAnalyzer.GetFailInfo());
                    continue;
                }

                //if (hibernateMethodAnalyzer.DeclaringClass.GetFullName() != "com.jspdev.biyesheji.Course")
                //{
                //    continue;
                //}
                
                string methodFullName = method.GetFullName();
                string curClassName = mAnalyzer.DeclaringClass.GetFullName();

                // (1) First, handle Basic POJO DB Class Get/Set methods
                HashSet<VariableDeclaration> getSelfFiels = mAnalyzer.GetSelfFields;
                HashSet<VariableDeclaration> setSelfFiels = mAnalyzer.SetSelfFields;
                if (getSelfFiels.Count() == 1 && setSelfFiels.Count() == 0)
                {
                    VariableDeclaration para = getSelfFiels.SingleOrDefault();
                    string fullClassPropName = curClassName + "." + para.Name;
                    if (allDBClassPropToTableAttr.ContainsKey(fullClassPropName))
                    {
                        basicGetSetMethods.Add(methodFullName, new BasicGetSetMethod(Constants.BasicMethodType.Get, allDBClassPropToTableAttr[fullClassPropName]));
                    }
                }

                if (setSelfFiels.Count() == 1 && getSelfFiels.Count() == 0)
                {
                    VariableDeclaration para = setSelfFiels.SingleOrDefault();
                    string fullClassPropName = curClassName + "." + para.Name;
                    if (allDBClassPropToTableAttr.ContainsKey(fullClassPropName))
                    {
                        basicGetSetMethods.Add(methodFullName, new BasicGetSetMethod(Constants.BasicMethodType.Set, allDBClassPropToTableAttr[fullClassPropName]));
                    }
                }

                // (2) Then, handle POJO DB class other methods
            }
        }
    }
}
