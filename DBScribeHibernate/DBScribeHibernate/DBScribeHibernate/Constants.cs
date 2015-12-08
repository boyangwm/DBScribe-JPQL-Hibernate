using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate
{
    /// <summary>
    /// This class defines the common configurations throughout the entire DBScribeHibernate project.
    /// </summary>
    class Constants
    {
        /// <summary>
        /// If show log information in the console output
        /// </summary>
        public static readonly Boolean ShowLog = true;
        /// <summary>
        /// Log file location
        /// </summary>
        public static readonly string LogPath = "_TempLog";
        /// <summary>
        /// Current DBScribeHibernate project working location
        /// </summary>
        public static readonly string CurWorkspace = @"E:\workspace_vs2013\DBScribe-JPQL-Hibernate\";
        /// <summary>
        /// DBScribeHibernate reports location
        /// </summary>
        public static readonly string ResultPath = CurWorkspace + @"DBScribeReports\";

        /// <summary>
        /// Target Project Location
        /// </summary>
        public static readonly string TargetProjPath = CurWorkspace + @"HibernateProjects";

        /// <summary>
        /// Target Prject Name
        /// </summary>
        //public static readonly string ProjName = @"_RealProjects\BookLib"; //XML mapping
        //public static readonly string ProjName = @"_RealProjects\SISCLIV"; //XML mapping
        public static readonly string ProjName = @"_RealProjects\Chat";  // Annotation mapping
        //public static readonly string ProjName = @"_RealProjects\UserManagementAccessControl";  // Annotation mapping

        /// <summary>
        /// SrcML Location
        /// </summary>
        public static readonly string SrcmlLoc = CurWorkspace + @"SrcML\";
        /// <summary>
        /// SrcML output location
        /// </summary>
        public static readonly string SrcmlOutput = @"_SrcmlOutput\" + Util.Utility.GetProjectName(ProjName) + @"\";

        /// <summary>
        /// StringTemplate templates location
        /// </summary>
        public static readonly string TemplatesLoc = CurWorkspace + @"DBScribeHibernate\DBScribeHibernate\DBScribeHibernate\Templates";

        /// <summary>
        /// Hibernate default config file name
        /// </summary>
        public static readonly string CfgFileName = "hibernate.cfg.xml";


        /// <summary>
        /// Self-defined enumeration for Hibernate mapping file type
        /// </summary>
        public enum MappingFileType { XMLMapping, AnnotationMapping, OtherMapping, BaseMapping };
        /// <summary>
        /// Self-defined enumeration for basic methods in Java POJO class
        /// </summary>
        public enum BasicMethodType { Get, Set, Construct };
        /// <summary>
        /// Self-defined enumeration for typical database operations
        /// </summary>
        public enum MethodOperationType { Insert, Delete, Update, Query };
        /// <summary>
        /// Self-defined enumeration for three categories of methods in the target project
        /// </summary>
        public enum SQLMethodCategory { SQLOperatingMethod, LocalSQLMethod, DelegatedSQLMethod};

        /// <summary>
        /// Hibernate Session keyword: "Session"
        /// </summary>
        public static readonly string Session = "Session";


        /// <summary>
        /// SrcML.Net keyword: "ABB.SrcML.Data.NameUse"
        /// </summary>
        public static readonly string SrcML_NameUse = "ABB.SrcML.Data.NameUse";
        /// <summary>
        /// SrcML.Net keyword: "ABB.SrcML.Data.MethodCall"
        /// </summary>
        public static readonly string SrcML_MethodCall = "ABB.SrcML.Data.MethodCall";

        
        /// <summary>
        /// A line divider for separating output
        /// </summary>
        public static readonly string Divider = "-----------------------------------------------------------";
    }
}
