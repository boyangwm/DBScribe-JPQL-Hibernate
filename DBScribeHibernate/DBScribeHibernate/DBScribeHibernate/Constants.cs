﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate
{
    class Constants
    {
        public static readonly Boolean ShowLog = true;
        public static readonly string LogPath = "_TempLog";

        public static readonly string CurWorkspace = @"E:\workspace_vs2013\DBScribe-JPQL-Hibernate\";

        public static readonly string ResultPath = CurWorkspace + @"DBScribeReports\";


        /// <summary>
        /// Target Project Location and Name
        /// </summary>
        //public static readonly string TargetProjPath = @"E:\workspace_hibernate";
        public static readonly string TargetProjPath = CurWorkspace + @"HibernateProjects";

        //// IF using <mapping resource="...xml" /> ---> xml
        //public static readonly string ProjName = @"MyFirstHibernate";
        //public static readonly string ProjName = @"HibernateOneToManyXMLMapping";
        //public static readonly string ProjName = @"HibernateComponentMappingExample";

        //// IF using <mapping class="..."/>  ---> annotation
        //public static readonly string ProjName = @"HibernateOneToOneAnnotation";
        //public static readonly string ProjName = @"HibernateManyToManyAnnotation";


        // Show result report
        public static readonly string ProjName = @"_RealProjects\BookLib"; //XML mapping
        //public static readonly string ProjName = @"_RealProjects\SISCLIV"; //XML mapping
        //public static readonly string ProjName = @"_RealProjects\Chat";  // Annotation mapping
        //public static readonly string ProjName = @"_RealProjects\UserManagementAccessControl";  // Annotation mapping
        

        /// <summary>
        /// SrcML Location
        /// </summary>
        //public static readonly string SrcmlLoc = @"E:\Research\SrcML\";
        public static readonly string SrcmlLoc = CurWorkspace + @"SrcML\";
        public static readonly string SrcmlOutput = @"_SrcmlOutput\" + Util.Utility.GetProjectName(ProjName) + @"\";

        public static readonly string TemplatesLoc = CurWorkspace + @"DBScribeHibernate\DBScribeHibernate\DBScribeHibernate\Templates";

        /// <summary>
        /// Hibernate default config file name
        /// </summary>
        public static readonly string CfgFileName = "hibernate.cfg.xml";

        /// <summary>
        /// Self-defined enumerations
        /// </summary>
        public enum MappingFileType { XMLMapping, AnnotationMapping, OtherMapping, BaseMapping };
        public enum BasicMethodType { Get, Set, Construct };
        public enum MethodOperationType { Insert, Delete, Update, Query };
        public enum SQLMethodCategory { SQLOperatingMethod, LocalSQLMethod, DelegatedSQLMethod};

        /// <summary>
        /// Hibernate Session built-in functions
        /// </summary>
        public static readonly string Session = "Session";
        


        /// <summary>
        /// SrcML.Net keywords
        /// </summary>
        public static readonly string SrcML_NameUse = "ABB.SrcML.Data.NameUse";
        public static readonly string SrcML_MethodCall = "ABB.SrcML.Data.MethodCall";


        public static string getMainMethodFullName()
        {
            if (ProjName.Equals(@"MyFirstHibernate"))
            {
                return "ManageEmployee.main";
            }
            else if (ProjName.Equals(@"HibernateOneToManyXMLMapping"))
            {
                return "com.mkyong.StockManager.main";
            }
            return "";
        }

        public static readonly string Divider = "-----------------------------------------------------------";
    }
}
