using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.Stereotype
{
    class SessionBuiltInFunction
    {
        public string FunctionName;
        /// <summary>
        /// Note that, if it's query function, TargetClassName in fact means hql/sql string.
        /// </summary>
        public string TargetTableName;

        public static readonly HashSet<string> NormalFunctions =
            new HashSet<string>(new string[] {"get", "load", "merge", "persist", "replicate", "save", "saveOrUpdate", "update",
            "delete", "createCriteria"});
        public static readonly HashSet<string> QueryFunctions =
            new HashSet<string>(new string[] {"createQuery", "createSQLQuery"});

        public static readonly HashSet<string> Inserts = new HashSet<string>(new string[] { "merge", "persist", "replicate", "save"});
        public static readonly HashSet<string> Deletes = new HashSet<string>(new string[] { "delete" });
        public static readonly HashSet<string> Updates = new HashSet<string>(new string[] { "update" });
        public static readonly HashSet<string> SaveOrUpdates = new HashSet<string>(new string[] { "saveOrUpdate" });
        public static readonly HashSet<string> Querys = new HashSet<string>(new string[] { "get", "load", "createCriteria", "createQuery", "createSQLQuery" });

        public SessionBuiltInFunction(string functionName, string targetTableName)
        {
            this.FunctionName = functionName;
            this.TargetTableName = targetTableName;
        }

        public override string ToString()
        {
            return "session." + FunctionName + "(" + TargetTableName + ")";
        }
    }
}
