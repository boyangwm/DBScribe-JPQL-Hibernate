using ABB.SrcML.Data;
using ABB.SrcML;
using DBScribeHibernate.DBScribeHibernate.CallGraphExtractor;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace DBScribeHibernate.DBScribeHibernate
{
    class Program
    {


        static void Main(string[] args)
        {

            //TestingPurpose.MainTestingFunction();

            DBScribeH dbScribeH = new DBScribeH(Constants.TargetProjPath + "\\" + Constants.ProjName, Constants.CfgFileName);
            dbScribeH.run();


            /// step1: run ConfigParser to link POJOs to tables in the database

            /// step2: use call graph generator to generate the call-chains

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

            Console.ReadKey();
        }

    }
}

