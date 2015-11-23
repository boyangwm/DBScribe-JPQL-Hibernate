using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.CallGraphExtractor
{
    class BasicMethod
    {
        public Constants.BasicMethodType MethodType;
        public string Table;
        public HashSet<string> AttrList;

        public BasicMethod(Constants.BasicMethodType mType, string table, HashSet<string> attrList)
        {
            this.MethodType = mType;
            this.Table = table;
            this.AttrList = attrList;
        }

        public override string ToString()
        {
            StringBuilder outputBuilder = new StringBuilder();
            outputBuilder.Append(MethodType.ToString() + " " + Table + ": ");
            foreach(string attr in AttrList){
                outputBuilder.Append(attr + ", ");
            }
            return outputBuilder.ToString();
        }

    }
}
