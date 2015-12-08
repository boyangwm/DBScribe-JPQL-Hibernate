using ABB.SrcML.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.Stereotype
{
    /// <summary>
    /// This class defines properties and functions that DBScribeHibernet needs for a SrcML.Net Variable type
    /// Author: Boyang Li
    /// </summary>
    public class VariableInfo {

        /// <summary>
        /// the veriable declaration
        /// </summary>
        public VariableDeclaration Variable { get; set; }
        /// <summary>
        /// record the variable is init or not. 
        /// </summary>
        public bool Initialized { get; set; }
        /// <summary>
        /// the variable is returned in the method    
        /// </summary>
        public bool IsReturned { get; set; }
        /// <summary>
        /// the variable has been modified  
        /// </summary>
        public bool IsModified { get; set; }
        /// <summary>
        /// the fields have been assigned to this variable
        /// </summary>
        public HashSet<VariableDeclaration> AssignedFields;
        /// <summary>
        /// the variable is isInstantiated in the method
        /// </summary>
        public bool IsInstantiated { get; set; }
        /// <summary>
        /// the variable is IsFieldChange in the method
        /// </summary>                      
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

        /// <summary>
        /// Get a brief description for Variable inforamtion
        /// </summary>
        /// <returns></returns>
        public string GetInitialVariableInfo()
        {
            string info = this.Variable.Name + "<" + this.Variable.VariableType + "> Initialized=" + this.Initialized;
            return info;
        }

        /// <summary>
        /// Get a full description for Variable inforamtion
        /// </summary>
        /// <returns></returns>
        public string GetFullVariableInfo()
        {
            string info = this.Variable.Name + "<" + this.Variable.VariableType + "> Initialized=" + this.Initialized;
            info += ", IsReturned=" + IsReturned + ", IsModified=" + IsModified;
            info += ", IsInstantiated=" + IsInstantiated + ", IsFieldChange=" + IsFieldChange;
            info += ", AssignedFields=";
            foreach (VariableDeclaration af in AssignedFields)
            {
                info += af.Name + " <" + af.VariableType + ">, ";
            }
            return info;
        }
    }
}

