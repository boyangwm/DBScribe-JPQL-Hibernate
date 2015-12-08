using ABB.SrcML.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.Stereotype
{
    class HibernateMethodAnalyzer : MethodAnalyzer
    {
        public HibernateMethodAnalyzer(MethodDefinition method) : base(method) { }
        
        // BuildInTypeFactory
        /// <summary>
        /// Checks the type is priliminary or not
        /// </summary>
        /// <param tu.Name="type"></param>
        /// <returns></returns>
        public override bool IsPrimitiveType(TypeUse tu) {
            if(tu != null) {
                return BuiltInTypeFactory.IsBuiltIn(tu);
            } else {
                return false;
            }
        }


        /// <summary>
        /// Checks the return type is void or not
        /// </summary>
        /// <returns></returns>
        public override bool RTypeIsVoid() {
            TypeUse type = this.ReturnType;
            if(type == null) {     // && type.Name.Equals("void")) {
                return true;
            }
            return false;
        }


        /// <summary>
        /// Checks the type is bool or not
        /// </summary>
        /// <returns></returns>
        public override bool RTypeIsBoolean() {
            TypeUse type = this.ReturnType;
            if (type != null && type.Name == "Boolean")
            {
                return true;
            }
            return false;
        }

    }
}
