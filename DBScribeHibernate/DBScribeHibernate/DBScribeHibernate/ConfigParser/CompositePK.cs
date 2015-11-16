using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    class CompositePK
    {
        public readonly string CompositeClassPK;
        public readonly List<string> CompositeTablePKs;
        public readonly string CompositeClassPKType;
        public readonly List<SinglePK> PKList;
        //public readonly string GeneratorClass;

        public CompositePK(string compositeClassPK, List<string> compositeTablePKs, string compositeClassPKType, List<SinglePK> pKList)
        {
            this.CompositeClassPK = compositeClassPK;
            this.CompositeTablePKs = compositeTablePKs;
            this.CompositeClassPKType = compositeClassPKType;
            this.PKList = pKList;
        }
    }
}
