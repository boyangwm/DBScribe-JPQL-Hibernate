using ABB.SrcML.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.Stereotype
{
    /// <summary>
    /// This class extends MethodAnalyzer to perform Hibernate only method analysis.
    /// </summary>
    class HibernateMethodAnalyzer : MethodAnalyzer
    {
        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="method"></param>
        public HibernateMethodAnalyzer(MethodDefinition method) : base(method) { }

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
