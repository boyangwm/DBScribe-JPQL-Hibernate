using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.DescriptionTemplates
{
    class LocalSqlTemplates
    {
        public static string LocalSqlHeader()
        {
            string line = "This method implements the following db-related operations:\n";
            return line;
        }

        /// <summary>
        /// It inserts the Username, Password attributes into table logindetails
        /// </summary>
        /// <param name="tableName"></param>
        /// <param name="attrs"></param>
        /// <returns></returns>
        public static string LocalSqlInsert(string tableName, params string[] attrs)
        {
            string line = LocalSqlHeader() + "It inserts the ";
            if (attrs.Count() == 1)
            {
                line += attrs[0] + " attribute into table " + tableName;
            }
            else
            {
                foreach (string attr in attrs)
                {
                    line += attr + ",";
                }
                line = line.TrimEnd(',');
                line += " attributes into table " + tableName;
            }
            return line;
        }

        /// <summary>
        /// It updates the IsCurrent attribute(s) in table semester
        /// </summary>
        /// <param name="tableName"></param>
        /// <param name="attrs"></param>
        /// <returns></returns>
        public static string LocalSqlUpdate(string tableName, params string[] attrs)
        {
            string line = LocalSqlHeader() + "It updates the ";
            if (attrs.Count() == 1)
            {
                line += attrs[0] + " attribute in table " + tableName;
            }
            else
            {
                foreach (string attr in attrs)
                {
                    line += attr + ",";
                }
                line = line.TrimEnd(',');
                line += " attributes in table " + tableName;
            }
            return line;
        }
    }
}
