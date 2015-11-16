using DBScribeHibernate.DBScribeHibernate.ConfigParser;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.DescriptionTemplates
{
    class SchemaConstraintsTemplates
    {
        public static string SchemaConstraintsHeader()
        {
            string line = "Some constraints that should be taken into the account are the following:\n";
            return line;
        }

        public static string SchemaConstraintsVarchar(string tableName, string limits)
        {
            string line = SchemaConstraintsHeader();
            line += "Make sure the strings to be stored in " + tableName + " do not overflow the varchar limits: ";
            line += limits;
            return line;
        }

        public static string SchemaConstraintsNotNull(string tableName, string attr)
        {
            string line = SchemaConstraintsHeader();
            line += "Make sure the values in " + tableName + "." + attr + " are not null";
            return line;
        }

        public static string SchemaConstraintsPK(SinglePK singlePK)
        {
            //return "Primary key: " + singlePK.TablePK + " (type = " + singlePK.Type + ", generator class = " + singlePK.GeneratorClass + ")";
            return "Primary key: " + singlePK.TablePK + " (type=" + singlePK.Type + ")";
        }

    }
}
