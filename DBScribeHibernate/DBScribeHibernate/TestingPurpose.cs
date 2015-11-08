using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DBScribeHibernate.DBScribeHibernate.DescriptionTemplates;
using DBScribeHibernate.DBScribeHibernate.Util;
using DBScribeHibernate.DBScribeHibernate.CallGraphExtractor;
using DBScribeHibernate.DBScribeHibernate;

namespace DBScribeHibernate
{
    class TestingPurpose
    {
        public static void MainTestingFunction()
        {
            //String source = @"E:\workspace_vs2013\srcML_example\Stock.java";
            //String xMLOutput = @"E:\workspace_vs2013\srcML_example\Stock_xml2.xml";
            //String srcmlExe = @"E:\research\SrcML\src2srcml.exe";
            //Src2XML.SourceFileToXml(source, xMLOutput, srcmlExe);

            /// step1: run ConfigParser to link POJOs to tables in the database
            //TestUseConfigParser();

            /// step2: use call graph generator to generate the call-chains
            TestUseCallGraphGenerator();

            /// step3: based on POJO/database links and call-chains, 
            /// annotate all database access methods (bottom level database accessors)

            /// step4: by analyzing Hibernate keywords, summarize high level descriptions of the db access methods
            /// in addition, apply Swum.NET to capture both linguistic and structural information

            /// step5: extract database schema constraints
            /// from XMLMapping or AnnotationMapping
            //TestUseDatabaseConstraintExtractor();

            /// step6: propagate descriptions and constraints through the call graph (bottom --> top)

            /// step7: generate natural language descriptions using the
            /// (1) propagated information and (2) local generatd information
            //TestUseDescriptionTemplates();
        }

        public static void TestUseConfigParser()
        {
            ConfigParser cpu = new ConfigParser(Constants.TargetProjPath + "/" + Constants.ProjName, Constants.CfgFileName);
            Console.WriteLine("Project Name: " + Constants.ProjName);
            string hibernateDtd = cpu.GetHibernateVersion();
            Console.WriteLine("Hibernate DTD: " + hibernateDtd);
            Console.WriteLine("Mapping File Type: " + cpu.mappingFileType.ToString());
            if (cpu.mappingFileType == Constants.MappingFileType.AnnotationMapping)
            {
                Console.WriteLine("Will handle " + cpu.mappingFileType.ToString() + " later!!!");
                Console.ReadKey();
                System.Environment.Exit(1);
            }

            List<string> mappingFileNameList = cpu.GetCfgFileList();
            int idx = 0;
            foreach (var mappingFileName in mappingFileNameList)
            {
                idx++;
                Console.WriteLine("--------------------------------------------------");
                Console.WriteLine("MappingFile-{0}: " + mappingFileName, idx);

                Tuple<string, string> name = cpu.GetMappingFileClassName(mappingFileName);
                Console.WriteLine("ClassName: " + name.Item1 + "\tTableName: " + name.Item2);

                Tuple<string, string, string> pk = cpu.GetMappingFilePrimaryKey(mappingFileName);
                Console.WriteLine("ClassPk: " + pk.Item1 + "\tTablePk: " + pk.Item2 + "\tPkType: " + pk.Item3);

                List<Tuple<string, string, string>> propList = cpu.GetMappingFilePropertyList(mappingFileName);
                foreach (var prop in propList)
                {
                    Console.WriteLine("ClassProp: " + prop.Item1 + "\tTableProp: " + prop.Item2 + "\tPropType: " + prop.Item3);
                }

            }
        }

        public static void TestUseCallGraphGenerator()
        {
            InvokeCallGraphGenerator CGGenerator = new InvokeCallGraphGenerator(Constants.TargetProjPath + "/" + Constants.ProjName, Constants.SrcmlLoc);
            CGGenerator.run();
        }

        public static void TestUseDatabaseConstraintExtractor()
        {
            DatabaseConstraintExtractor dce = new DatabaseConstraintExtractor(Constants.TargetProjPath + "/" + Constants.ProjName, Constants.CfgFileName);
            ConfigParser cpu = new ConfigParser(Constants.TargetProjPath + "/" + Constants.ProjName, Constants.CfgFileName);
            List<string> mappingFileNameList = cpu.GetCfgFileList();
            int idx = 0;
            foreach (var mappingFileName in mappingFileNameList)
            {
                idx++;
                Console.WriteLine("--------------------------------------------------");
                Console.WriteLine("MappingFile-{0}: " + mappingFileName, idx);

                dce.GetSchemaConstraintsByName(mappingFileName, "stockCode");
            }
        }

        public static void TestUseDescriptionTemplates()
        {
            string line = LocalSqlTemplates.LocalSqlInsert("Employee", "Username", "Password", "SecureQuestion");
            Console.WriteLine(line);
            line = LocalSqlTemplates.LocalSqlUpdate("Employee", "Username", "Password", "SecureQuestion");
            Console.WriteLine(line);

            line = DelegatedSQLTemplates.DelegatedSqlQuery("Employee", "<some_path>");
            Console.WriteLine(line);
            line = DelegatedSQLTemplates.DelegatedSqlDelete("Employee", "<some_method>");
            Console.WriteLine(line);

            line = SchemaConstraintsTemplates.SchemaConstraintsVarchar("Employee", "2 (Grade, Level), 100(FileLocation, FileName)");
            Console.WriteLine(line);
            line = SchemaConstraintsTemplates.SchemaConstraintsNotNull("Employee", "Salary");
            Console.WriteLine(line);
        }
    }
}
