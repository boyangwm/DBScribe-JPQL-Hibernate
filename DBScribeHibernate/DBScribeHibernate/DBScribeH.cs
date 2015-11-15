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

namespace DBScribeHibernate
{
    class DBScribeH
    {
        /// <summary> Subject application location </summary>
        public string LocalProj;
        /// <summary> SrcML directory location </summary>
        public string SrcmlLoc;

        /// <summary> Hibernate Configure File Related Variables</summary>
        public MappingParser mappingParser;
        public Dictionary<string, string> classFullNameToTableName;


        /// <summary> Call Graph Related Variables</summary>
        public CGManager cgm;
        public IEnumerable<MethodDefinition> methods;
        public int num_of_methods;
        public List<MethodDefinition> bottomUpSortedMethods;
        public HashSet<MethodDefinition> methods_LocalSQLMethods;  // methods call transaction and sessions


        public DBScribeH(string localProj, string srcmlloc)
        {
            this.LocalProj = localProj;
            this.SrcmlLoc = srcmlloc;
        }


        public void run()
        {
            Step1_ConfigParser(); // Analyze Hibernate Configuration files
            Step2_GenerateCallGraph();
        }


        public void Step1_ConfigParser()
        {
            Console.WriteLine("Project Name: " + Constants.ProjName);
            ConfigParser configParser = new ConfigParser(Constants.TargetProjPath + "\\" + Constants.ProjName, Constants.CfgFileName);
            if (configParser.configFilePath == null)
            {
                Console.Error.WriteLine("[ERROR] Hibernate configuration file " + Constants.CfgFileName + " not found!");
                Console.ReadKey();
                System.Environment.Exit(-1);
            }

            Console.WriteLine("Hibernate DTD: " + configParser.HibernateDTD);
            Console.WriteLine("Mapping File Type: " + configParser.MappingFileType);
            Console.WriteLine("");

            if (configParser.MappingFileType == Constants.MappingFileType.XMLMapping)
            {
                mappingParser = new XMLMappingParser(Constants.TargetProjPath + "\\" + Constants.ProjName, Constants.CfgFileName);
                classFullNameToTableName = mappingParser.GetClassFullNameToTableName();
                foreach (KeyValuePair<string, string> item in classFullNameToTableName)
                {
                    Console.WriteLine(item.Key + " <--> " + item.Value);
                }
                Console.WriteLine("");

                //mappingParser.GetSQLOperatingMethodFullNames();
            }
            else if (configParser.MappingFileType == Constants.MappingFileType.AnnotationMapping)
            {
                mappingParser = new AnnotationMappingParser(Constants.TargetProjPath + "\\" + Constants.ProjName, Constants.CfgFileName);
                Console.WriteLine("Will handle " + configParser.MappingFileType + " later!!!");
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


        public void Step2_GenerateCallGraph()
        {
            // Get methods from SrcML.net
            //Console.Out.WriteLine("Invoke call graph generator ");
            string dataDir = @"TESTNAIVE_1.0";
            using (var project = new DataProject<CompleteWorkingSet>(dataDir, this.LocalProj, this.SrcmlLoc))
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
                    Console.WriteLine("\nBottom-up sorted methods:");
                    foreach (MethodDefinition m in bottomUpSortedMethods)
                    {
                        Console.WriteLine(m.GetFullName());
                    }

                    // methods calling "beginTransaction" --> LocalSQLMethods
                    methods_LocalSQLMethods = InvokeCGManager.GetMethodesWithCertainMethodCall(HibernateKeywords.beginTransaction, methods);
                    Console.WriteLine("\nLocal SQL Methods (invoking session/transaction): ");
                    foreach (MethodDefinition m in methods_LocalSQLMethods)
                    {
                        Console.WriteLine(m.GetFullName());
                    }


                    // methods in POJO class: i.e. get/set




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

    }
}
