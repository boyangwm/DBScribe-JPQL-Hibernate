using ABB.SrcML.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.Stereotype
{
    class VarInfo
    {
        public string VarName;
        public TypeUse VarType;
        public VarInfo(string varName, TypeUse varType)
        {
            this.VarName = varName;
            this.VarType = varType;
        }

    }
}
