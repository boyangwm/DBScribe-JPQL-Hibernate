using System;
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

        public static readonly string ResultPath = @"..\..\..\..\DBScribeReports\";

        /// <summary>
        /// Target Project Location and Name
        /// </summary>
        public static readonly string TargetProjPath = @"E:\workspace_hibernate";

        //// IF using <mapping resource="...xml" /> ---> xml
        //public static readonly string ProjName = @"MyFirstHibernate";
        //public static readonly string ProjName = @"HibernateOneToManyXMLMapping";
        //public static readonly string ProjName = @"HibernateComponentMappingExample";

        //// IF using <mapping class="..."/>  ---> annotation
        //public static readonly string ProjName = @"HibernateOneToOneAnnotation";
        //public static readonly string ProjName = @"HibernateManyToManyAnnotation";

        //// Real projects
        public static readonly string ProjName = @"_RealProjects\biyesheji";    // XML mapping
        //public static readonly string ProjName = @"_RealProjects\hibernate-spring-struts-bookstore-master";  // XML mapping
        //public static readonly string ProjName = @"_RealProjects\chat-springmvc-hibernate-master";  // Annotation mapping


        /// <summary>
        /// SrcML Location
        /// </summary>
        public static readonly string SrcmlLoc = @"E:\Research\SrcML\";
        public static readonly string SrcmlOutput = @"_SrcmlOutput\" + Util.Utility.GetProjectName(ProjName) + @"\";

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
