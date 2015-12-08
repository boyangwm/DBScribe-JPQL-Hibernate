using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    /// <summary>
    /// This class defines a POJO class property (not primary key)
    /// </summary>
    class ClassProperty
    {
        /// <summary>
        /// POJO class property name
        /// </summary>
        public readonly string ClassProp;
        /// <summary>
        /// Its corresponding table attribute name
        /// </summary>
        public readonly string TableAttr;
        /// <summary>
        /// Property type
        /// </summary>
        public readonly string Type;
        /// <summary>
        /// The declared database constraints for this property
        /// </summary>
        public readonly string ConstraintInfo;

        public ClassProperty(string classProp, string tableAttr, string type, string constraintInfo)
        {
            this.ClassProp = classProp;
            this.TableAttr = tableAttr;
            this.Type = type;
            this.ConstraintInfo = constraintInfo;
        }
    }
}
