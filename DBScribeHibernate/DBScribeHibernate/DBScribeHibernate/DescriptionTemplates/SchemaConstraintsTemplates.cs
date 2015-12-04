using DBScribeHibernate.DBScribeHibernate.ConfigParser;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DBScribeHibernate.DBScribeHibernate.Util;

namespace DBScribeHibernate.DBScribeHibernate.DescriptionTemplates
{
    class SchemaConstraintsTemplates
    {
        public static string SchemaConstraintsHeader()
        {
            string line = "Some constraints that should be taken into the account are the following:\n";
            return line;
        }

        public static string SchemaConstraintsPK(SinglePK singlePK)
        {
            string line = singlePK.TablePK + " is the primary key. ";

            if (singlePK.Type != "")
            {
                line += "It's type is " + singlePK.Type + ". ";
            }
            
            if (singlePK.ConstraintInfo != "")
            {
                line += singlePK.ConstraintInfo + ".";
            }
            return line;
        }

        public static string SchemaConstraintsPK(CompositePK compositePK)
        {
            string line = "";
            foreach (SinglePK pk in compositePK.PKList)
            {
                line += SchemaConstraintsPK(pk) + "<br/>- ";
            }
            return line.Substring(0, line.Length - 7);
        }

        public static string SchemaConstraintsClassProp(ClassProperty classProp)
        {
            if (classProp.ConstraintInfo.Length == 0)
            {
                return "";
            }

            string line = classProp.TableAttr + ": ";
            if (classProp.Type != "")
            {
                line += "It's type is " + classProp.Type + ". ";
            }
            if (classProp.ConstraintInfo != "")
            {
                line += classProp.ConstraintInfo + ".";
            }
            return line;
        }
    }
}
