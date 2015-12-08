using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.DescriptionTemplates
{
    /// <summary>
    /// This class defines Method Description
    /// </summary>
    class MethodDescription
    {
        /// <summary> Method's full description </summary>
        public string MethodDescriptionStr;
        /// <summary> Method's operation list </summary>
        public List<string> MethodOperationList;
        /// <summary> Method's constraint list </summary>
        public List<string> ConstraintList;
        /// <summary> A list of database attributes this method interacts with </summary>
        public List<string> DBTableAttrList;

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="methodDescriptionStr"></param>
        /// <param name="methodOperationList"></param>
        /// <param name="constraintList"></param>
        /// <param name="dBTableAttrList"></param>
        public MethodDescription(string methodDescriptionStr, List<string> methodOperationList, List<string> constraintList, List<string> dBTableAttrList)
        {
            this.MethodDescriptionStr = methodDescriptionStr;
            this.MethodOperationList = methodOperationList;
            this.ConstraintList = constraintList;
            this.DBTableAttrList = dBTableAttrList;
        }
    }
}
