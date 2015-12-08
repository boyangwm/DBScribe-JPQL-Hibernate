using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    /// <summary>
    /// This class defines a single primary key for a db table
    /// </summary>
    class SinglePK
    {
        /// <summary>
        /// Name in POJO class
        /// </summary>
        public readonly string ClassPK;
        /// <summary>
        /// Name in db table
        /// </summary>
        public readonly string TablePK;
        /// <summary>
        /// Its type
        /// </summary>
        public readonly string Type;
        /// <summary>
        /// Its db constraints, if any
        /// </summary>
        public readonly string ConstraintInfo;
        /// <summary>
        /// Its generator class type, if defined
        /// </summary>
        public readonly string GeneratorClass;

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="classPK"></param>
        /// <param name="tablePK"></param>
        /// <param name="type"></param>
        /// <param name="constraintInfo"></param>
        /// <param name="generatorClass"></param>
        public SinglePK(string classPK, string tablePK, string type, string constraintInfo, string generatorClass)
        {
            this.ClassPK = classPK;
            this.TablePK = tablePK;
            this.Type = type;
            this.ConstraintInfo = constraintInfo;
            this.GeneratorClass = generatorClass;
        }
    }
}
