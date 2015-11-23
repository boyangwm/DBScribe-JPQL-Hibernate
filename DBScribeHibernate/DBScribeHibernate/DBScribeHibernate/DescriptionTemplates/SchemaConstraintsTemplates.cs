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
            //string onlyTablePK = singlePK.TablePK;
            //string[] segs = onlyTablePK.Split('.');
            //if (segs.Length > 1)
            //{
            //    onlyTablePK = segs[segs.Length - 1];
            //}
            string line = "Primary key: " + singlePK.TablePK + " (type=" + singlePK.Type;
            if (singlePK.ConstraintInfo == "")
            {
                line += ")";
            }
            else
            {
                line += ", " + singlePK.ConstraintInfo + ")";
            }
            return line;
        }

        public static string SchemaConstraintsPK(CompositePK compositePK)
        {
            string line = "Primary key: ";
            foreach (SinglePK pk in compositePK.PKList)
            {
                //string onlyTablePk = pk.TablePK;
                //string[] segs = onlyTablePk.Split('.');
                //if (segs.Length > 1)
                //{
                //    onlyTablePk = segs[segs.Length - 1];
                //}
                line += pk.TablePK + " (type=" + pk.Type;
                if (pk.ConstraintInfo == "")
                {
                    line += "), ";
                }
                else
                {
                    line += ", " + pk.ConstraintInfo + "), ";
                }
            }
            return line.Substring(0, line.Length - 2);
        }

        public static string SchemaConstraintsClassProp(ClassProperty classProp)
        {
            if (classProp.ConstraintInfo.Length == 0)
            {
                return "";
            }

            string line = classProp.TableAttr + " (type=" + classProp.Type;
            if (classProp.ConstraintInfo == "")
            {
                line += ")";
            }
            else
            {
                line += ", " + classProp.ConstraintInfo + ")";
            }
            return line;
        }
    }
}
