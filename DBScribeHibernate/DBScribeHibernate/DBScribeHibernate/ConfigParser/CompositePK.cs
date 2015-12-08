using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    /// <summary>
    /// This class defines a compostie primary key set
    /// </summary>
    class CompositePK
    {
        /// <summary>
        /// Name in POJO class
        /// </summary>
        public readonly string CompositeClassPK;
        /// <summary>
        /// Names in database table
        /// </summary>
        public readonly List<string> CompositeTablePKs;
        /// <summary>
        /// Its type defined in the project
        /// </summary>
        public readonly string CompositeClassPKType;
        /// <summary>
        /// Composite key is a list of single primary key list.
        /// </summary>
        public readonly List<SinglePK> PKList;

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="compositeClassPK"></param>
        /// <param name="compositeTablePKs"></param>
        /// <param name="compositeClassPKType"></param>
        /// <param name="pKList"></param>
        public CompositePK(string compositeClassPK, List<string> compositeTablePKs, string compositeClassPKType, List<SinglePK> pKList)
        {
            this.CompositeClassPK = compositeClassPK;
            this.CompositeTablePKs = compositeTablePKs;
            this.CompositeClassPKType = compositeClassPKType;
            this.PKList = pKList;
        }
    }
}
