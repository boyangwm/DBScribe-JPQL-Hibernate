using ABB.SrcML.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.Stereotype
{
    public class VariableInfo {

        // the veriable declaration
        public VariableDeclaration Variable { get; set; }
        // record the variable is init or not. 
        public bool Initialized { get; set; }
        // the variable is returned in the method                     
        public bool IsReturned { get; set; }
        // the variable has been modified                           
        public bool IsModified { get; set; }

        // the fields have been assigned to this variable
        public HashSet<VariableDeclaration> AssignedFields;
        // the variable is isInstantiated in the method          
        public bool IsInstantiated { get; set; }                                
        public bool IsFieldChange { get; set; }

        /// <summary>
        /// constructor
        /// </summary>
        /// <param name="vd"></param>
        public VariableInfo(VariableDeclaration vd) {
            IsReturned = false;
            IsModified = false;
            AssignedFields = new HashSet<VariableDeclaration>();

            this.Variable = vd;
            if(vd.Initializer != null) {
                Initialized = true;
            }

            PrintVariableInfo();
        }

        /// <summary>
        /// Get the name of the variable 
        /// </summary>
        /// <returns></returns>
        public String GetName() {
            return this.Variable.Name;
        }

        /// <summary>
        /// Get the type of the variable
        /// </summary>
        /// <returns></returns>
        public TypeUse GetVariableType() {
            return this.Variable.VariableType;
        }

        public void PrintVariableInfo()
        {
            Console.WriteLine("varable info: " + this.Variable.Name + " (" + this.Variable.VariableType + ") initialized=" + this.Initialized);
        }
    }
}

