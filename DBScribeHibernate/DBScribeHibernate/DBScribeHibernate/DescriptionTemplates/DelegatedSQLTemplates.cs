using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.DescriptionTemplates
{
    class DelegatedSQLTemplates
    {
        public static string DelegatedSQLHeader()
        {
            string line = "This method invokes db-related operations by means of delegation:\n";
            return line;
        }

        /// <summary>
        /// It queries the table(s) People 
        /// via the call-chain JobApplication.addApplicationDetails-->Student.checkIfStudent
        /// </summary>
        /// <param name="tableName"></param>
        /// <param name="attrs"></param>
        /// <returns></returns>
        public static string DelegatedSqlQuery(string tableName, string method)
        {
            string line = DelegatedSQLHeader() + "It queries the table " + tableName + " via the call-chain ";
            // CGManager has function find FindCalleeListByName(string methodName)
            line += method;
            return line;
        }

        /// <summary>
        /// It deletes rows from table(s) gradingsystem
        /// via a call to the GradeSystem.deleteGrade method
        /// </summary>
        /// <param name="tableName"></param>
        /// <param name="attrs"></param>
        /// <returns></returns>
        public static string DelegatedSqlDelete(string tableName, string method)
        {
            string line = DelegatedSQLHeader() + "It deletes rows from table " + tableName + " via a call to the ";
            // CGManager has function find FindCalleeListByName(string methodName)
            line += method + " method";
            return line;
        }
    }
}

