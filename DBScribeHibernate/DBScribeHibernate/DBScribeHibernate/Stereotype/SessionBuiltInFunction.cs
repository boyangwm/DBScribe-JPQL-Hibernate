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
        public string TargetClassName;

        public static readonly HashSet<string> NormalFunctions =
            new HashSet<string>(new string[] {"get", "load", "merge", "persist", "replicate", "save", "saveOrUpdate", "update",
            "delete", "createCriteria"});
        public static readonly HashSet<string> QueryFunctions =
            new HashSet<string>(new string[] {"createQuery", "createSQLQuery"});

        public SessionBuiltInFunction(string functionName, string targetClassName)
        {
            this.FunctionName = functionName;
            this.TargetClassName = targetClassName;
        }
    }
}
