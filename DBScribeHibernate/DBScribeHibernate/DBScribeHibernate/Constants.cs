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

        /// <summary>
        /// SrcML Location
        /// </summary>
        public static readonly string SrcmlLoc = @"E:\research\SrcML";

        /// <summary>
        /// Target Project Location and Name
        /// </summary>
        public static readonly string TargetProjPath = @"E:\workspace_hibernate";
        //// IF using <mapping resource="...xml" /> ---> xml
        public static readonly string ProjName = @"MyFirstHibernate";
        //public static readonly string ProjName = @"HibernateOneToManyXMLMapping";
        //public static readonly string ProjName = @"HibernateComponentMappingExample";
        //public static readonly string ProjName = @"hibernateSchemaGenerationExample";

        //// IF using <mapping class="..."/>  ---> annotation
        //public static readonly string ProjName = @"HibernateManyToManyAnnotation";
        //public static readonly string ProjName = @"HibernateManyToManyThirdTableAnnotation";
        //public static readonly string ProjName = @"HibernateOneToOneAnnotation";

        //// Real projects
        //public static readonly string ProjName = @"_RealProjects\biyesheji";    // XML mapping
        //public static readonly string ProjName = @"_RealProjects\hibernate-spring-struts-bookstore-master";  // XML mapping
        //public static readonly string ProjName = @"_RealProjects\chat-springmvc-hibernate-master";  // Annotation mapping
        //public static readonly string ProjName = @"_RealProjects\TicketsOn-spring-master"; // Annotation mapping

        /// <summary>
        /// Hibernate default config file name
        /// </summary>
        public static readonly string CfgFileName = "hibernate.cfg.xml";

        /// <summary>
        /// Self-defined enumerations
        /// </summary>
        public enum MappingFileType { XMLMapping, AnnotationMapping, OtherMapping };


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
    }
}
