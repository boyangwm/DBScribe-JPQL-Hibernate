using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.DescriptionTemplates
{
    class MethodDescription
    {
        public Constants.MethodOperationType MethodOperationType;
        public string Table;
        public HashSet<string> AttrList;

        public MethodDescription(Constants.MethodOperationType methodOperationType, string table, HashSet<string> attrList)
        {
            this.MethodOperationType = methodOperationType;
            this.Table = table;
            this.AttrList = attrList;
        }
    }
}
