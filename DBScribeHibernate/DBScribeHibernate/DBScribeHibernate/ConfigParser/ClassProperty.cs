using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    class ClassProperty
    {
        public readonly string ClassProp;
        public readonly string TableAttr;
        public readonly string Type;
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
