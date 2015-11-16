using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    class CompositePK
    {
        public readonly string ClassPK;
        public readonly List<string> TablePKList;
        public readonly string Type;
        //public readonly string GeneratorClass;

        
    }
}
