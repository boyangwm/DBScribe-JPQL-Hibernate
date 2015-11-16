using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    class SinglePK
    {
        public readonly string ClassPK;
        public readonly string TablePK;
        public readonly string Type;
        public readonly string ConstraintInfo;
        public readonly string GeneratorClass;

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
