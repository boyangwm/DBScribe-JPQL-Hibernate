using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.CallGraphExtractor
{
    class BasicGetSetMethod
    {
        public Constants.BasicMethodType MethodType;
        public string TableAttr;

        public BasicGetSetMethod(Constants.BasicMethodType mType, string tableAttr)
        {
            this.MethodType = mType;
            this.TableAttr = tableAttr;
        }

        public override string ToString()
        {
            return MethodType.ToString() + " " + TableAttr;
        }

    }
}
