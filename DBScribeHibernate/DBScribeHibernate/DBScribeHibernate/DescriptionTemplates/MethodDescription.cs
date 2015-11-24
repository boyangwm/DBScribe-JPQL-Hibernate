using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.DescriptionTemplates
{
    class MethodDescription
    {
        public string MethodDescriptionStr;
        public List<string> MethodOperationList;
        public List<string> ConstraintList;
        public List<string> DBTableAttrList;

        public MethodDescription(string methodDescriptionStr, List<string> methodOperationList, List<string> constraintList, List<string> dBTableAttrList)
        {
            this.MethodDescriptionStr = methodDescriptionStr;
            this.MethodOperationList = methodOperationList;
            this.ConstraintList = constraintList;
            this.DBTableAttrList = dBTableAttrList;
        }
    }
}
