using ABB.SrcML.Data;
using DBScribeHibernate.DBScribeHibernate.CallGraphExtractor;
using DBScribeHibernate.DBScribeHibernate.Stereotype;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.DescriptionTemplates
{
    class MethodDescription
    {
        private static string LocalMethodHeader = "This method implements the following db-related operations: ";
        private static string POJOClassMethodNote = "*Note: This method will not affect the database util Session built-in function is called";
        private static string ConstraitsHeader = "Some constraints that should be taken into account are the following: ";
        private static string DelegatedMethodHeader = "This method invokes db-related operations via delegation: ";


        public static string DescribeSessionMethod(List<SessionBuiltInFunction> sessionBuiltInFuncList, 
            IEnumerable<string> invokedDBMethodNames, Dictionary<string, List<string>> tableNameToTableConstraints)
        {
            StringBuilder builder = new StringBuilder();

            if (invokedDBMethodNames.Count() != 0)
            {
                builder.AppendLine(DelegatedMethodHeader);
            }

            return builder.ToString();
        }


        public static string DescribeBasicMethod(BasicMethod basicMethod, Dictionary<string, List<string>> tableNameToTableConstraints)
        {
            StringBuilder builder = new StringBuilder();
            builder.AppendLine(LocalMethodHeader);
            Constants.BasicMethodType mType = basicMethod.MethodType;
            string tableName = basicMethod.Table;
            HashSet<string> attrList = basicMethod.AttrList;
            if (mType == Constants.BasicMethodType.Construct)
            {
                builder.Append("- It constructs " + tableName);
                if (attrList.Count() == 0)
                {
                    builder.AppendLine(" without any initial value");
                }
                else
                {
                    builder.AppendLine(" with attributes " + string.Join(", ", attrList));
                }
            }
            else
            {
                builder.AppendLine("- It " + mType.ToString().ToLower() + "s attribute " + string.Join(", ", attrList) + " from table " + tableName);
            }
            builder.AppendLine(POJOClassMethodNote);

            if (mType == Constants.BasicMethodType.Get)
            {
                return builder.ToString();
            }

            // Get DB constraints
            List<string> constraintList = tableNameToTableConstraints[tableName];
            StringBuilder consBuilder = new StringBuilder();
            foreach (string cons in constraintList)
            {
                foreach (string attr in attrList)
                {
                    if (cons.Contains(attr))
                    {
                        consBuilder.AppendLine("- " + cons);
                        break;
                    }
                }
            }
            if (consBuilder.Length != 0)
            {
                builder.AppendLine("");
                builder.AppendLine(ConstraitsHeader);
                //builder.AppendLine("In table " + tableName + ": ");
                builder.Append(consBuilder.ToString());
            }

            return builder.ToString();
        }

        public static string BuildMethodHeader(MethodDefinition method)
        {
            StringBuilder builder = new StringBuilder();
            builder.Append(method.GetFullName() + "(");
            if (method.Parameters.Count() == 0)
            {
                builder.AppendLine(")");
            }
            else
            {
                HashSet<string> paraTypes = new HashSet<string>();
                foreach (VariableDeclaration para in method.Parameters)
                {
                    paraTypes.Add(para.VariableType.ToString());
                }
                builder.AppendLine(string.Join(", ", paraTypes) + ")");
            }
            return builder.ToString();
        }

        
        
       
    }
}
