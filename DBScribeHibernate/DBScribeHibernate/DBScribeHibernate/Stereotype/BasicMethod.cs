using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.CallGraphExtractor
{
    /// <summary>
    /// This class defines a SQL Operating method, such as get/set/constructor defined in POJO class
    /// </summary>
    class BasicMethod
    {
        /// <summary>  Method Type -- SQL Operating Method </summary>
        public Constants.BasicMethodType MethodType;
        /// <summary> The database table it interacts with </summary>
        public string Table;
        /// <summary> List attributes it interacts with </summary>
        public HashSet<string> AttrList;

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="mType"></param>
        /// <param name="table"></param>
        /// <param name="attrList"></param>
        public BasicMethod(Constants.BasicMethodType mType, string table, HashSet<string> attrList)
        {
            this.MethodType = mType;
            this.Table = table;
            this.AttrList = attrList;
        }


        /// <summary>
        /// Override ToString()
        /// </summary>
        /// <returns></returns>
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
