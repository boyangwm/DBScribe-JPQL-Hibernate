using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.Stereotype
{
    /// <summary>
    /// This class defines a Hibernate Session built-in function, i.e. Save, Update, Delete, etc.
    /// </summary>
    class SessionBuiltInFunction
    {
        /// <summary> Function name </summary>
        public string FunctionName;
        /// <summary>
        /// Note that, if it's query function, TargetClassName in fact means hql/sql string.
        /// </summary>
        public string TargetTableName;

        /// <summary> Set of Hibernate functions that can obtain a Session object </summary>
        public static readonly HashSet<string> GetSessionFunctions =
            new HashSet<string>(new string[] { "getCurrentSession", "openSession" });

        /// <summary> Normal Session functions </summary>
        public static readonly HashSet<string> NormalFunctions =
            new HashSet<string>(new string[] {"get", "load", "merge", "persist", "replicate", "save", "saveOrUpdate", "update",
            "delete", "createCriteria"});
        /// <summary> Session built-in query function </summary>
        public static readonly HashSet<string> QueryFunctions =
            new HashSet<string>(new string[] {"createQuery", "createSQLQuery"});

        /// <summary> Functions for inserting </summary>
        public static readonly HashSet<string> Inserts = new HashSet<string>(new string[] { "merge", "persist", "replicate", "save"});
        /// <summary> Functions for deleting  </summary>
        public static readonly HashSet<string> Deletes = new HashSet<string>(new string[] { "delete" });
        /// <summary> Functions for updating  </summary>
        public static readonly HashSet<string> Updates = new HashSet<string>(new string[] { "update" });
        /// <summary> Functions for saving or updating  </summary>
        public static readonly HashSet<string> SaveOrUpdates = new HashSet<string>(new string[] { "saveOrUpdate" });
        /// <summary> Functions for database query </summary>
        public static readonly HashSet<string> Querys = new HashSet<string>(new string[] { "get", "load", "createCriteria", "createQuery", "createSQLQuery" });

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="functionName"></param>
        /// <param name="targetTableName"></param>
        public SessionBuiltInFunction(string functionName, string targetTableName)
        {
            this.FunctionName = functionName;
            this.TargetTableName = targetTableName;
        }

        /// <summary>
        /// Override ToString()
        /// </summary>
        /// <returns></returns>
        public override string ToString()
        {
            return "session." + FunctionName + "(" + TargetTableName + ")";
        }
    }
}
