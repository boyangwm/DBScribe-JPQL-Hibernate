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
    /// <summary>
    /// This class provides function for every type of database methods to generate their full descriptions, 
    /// including operation list, call-chain, and database constraints.
    /// </summary>
    class MethodDescriptionUtil
    {
        /// <summary> Description header for Local SQL methods </summary>
        private static string LocalMethodHeader = "-- This method implements the following db-related operations: ";
        /// <summary> Description header for SQL Operating methods </summary>
        private static string POJOClassMethodNote = "*<b>Note</b>: This method will not affect the database until Session built-in function is called";
        /// <summary> Description header for Delegated SQL methods </summary>
        private static string DelegatedMethodHeader = "-- This method invokes db-related operations via delegation: ";
        /// <summary> Description header for database constraints </summary>
        private static string ConstraitsHeader = "-- Some constraints that should be taken into account are the following: ";
        

        /// <summary>
        /// Generate method description for Delegated SQL Method
        /// </summary>
        /// <param name="methodHeader"></param>
        /// <param name="calleeList"></param>
        /// <param name="DBMethodToOpList"></param>
        /// <param name="DBMethodToConstraitList"></param>
        /// <param name="GlobalMethodHeaderToIndex"></param>
        /// <returns></returns>
        public static MethodDescription DescribeDelegatedMethod(string methodHeader, List<List<MethodDefinition>> calleeList,
            Dictionary<string, List<string>> DBMethodToOpList, Dictionary<string, List<string>> DBMethodToConstraitList,
            Dictionary<string, int> GlobalMethodHeaderToIndex)
        {
            StringBuilder builder = new StringBuilder(); // Build all description for the method
            List<string> opList = new List<string>();
            List<string> constraintList = new List<string>();

            // Get Description From Call Chain
            StringBuilder deleBuilder = new StringBuilder();
            HashSet<string> prevDBConstraints = new HashSet<string>();
            //foreach (List<MethodDefinition> path in calleeList)
            for (int i = 0; i < calleeList.Count(); i++ )
            {
                List<MethodDefinition> path = calleeList[i];

                List<string> methodAlongCallChain = new List<string>();
                Stack<MethodDefinition> mStack = new Stack<MethodDefinition>();
                foreach (MethodDefinition md in path)
                {
                    //methodAlongCallChain.Add(md.GetFullName());
                    string mdHeader = BuildMethodHeader(md);
                    methodAlongCallChain.Add("<a href=\"#" + GlobalMethodHeaderToIndex[mdHeader] + "\">" + md.GetFullName() + "</a>");
                    mStack.Push(md);
                }
                string callChain = string.Join(" --> ", methodAlongCallChain);
                //Console.WriteLine(callChain);

                List<string> curOpList = new List<string>();

                while (mStack.Count() != 0)
                {
                    string curMethodHeader = BuildMethodHeader(mStack.Pop());
                    if (curMethodHeader == methodHeader)
                    {
                        continue;
                    }
                    if (!DBMethodToOpList.ContainsKey(curMethodHeader))
                    {
                        continue;
                    }
                    foreach (string op in DBMethodToOpList[curMethodHeader])
                    {
                        curOpList.Add(op);
                    }
                    foreach (string c in DBMethodToConstraitList[curMethodHeader])
                    {
                        prevDBConstraints.Add(c);
                    }
                }

                if (methodAlongCallChain.Count() <= 1 || curOpList.Count() == 0)
                {
                    continue;
                }

                deleBuilder.AppendLine("Via call-chain: <i>" + callChain + "</i><br/>");
                foreach (string op in curOpList)
                {
                    deleBuilder.Append(op);
                }

                if (i < calleeList.Count() - 1)
                {
                    deleBuilder.AppendLine("<br/>");
                }
            }

            if (deleBuilder.Length != 0)
            {
                builder.AppendLine("<b>" + DelegatedMethodHeader + "</b><br/>");
                builder.Append(deleBuilder.ToString());
            }

            if (prevDBConstraints.Count() != 0)
            {
                builder.AppendLine("<br/>");
                builder.AppendLine("<b>" + ConstraitsHeader + "</b><br/>");
                foreach (string pc in prevDBConstraints)
                {
                    builder.AppendLine(pc + "<br/>");
                }
            }

            return new MethodDescription(builder.ToString(), opList, constraintList, new List<string>());
        }


        /// <summary>
        /// Generate method description for Hibernate Session built-in functions, 
        /// i.e. Save(), Update(), Delete(), etc.
        /// </summary>
        /// <param name="method"></param>
        /// <param name="methodHeader"></param>
        /// <param name="calleeList"></param>
        /// <param name="sessionBuiltInFuncList"></param>
        /// <param name="DBMethodToOpList"></param>
        /// <param name="DBMethodToConstraitList"></param>
        /// <param name="DBMethodToTableNames"></param>
        /// <param name="allDBClassToTableName"></param>
        /// <param name="allDBClassPropToTableAttr"></param>
        /// <param name="tableNameToTableConstraints"></param>
        /// <param name="GlobalMethodHeaderToIndex"></param>
        /// <returns></returns>
        public static MethodDescription DescribeSessionMethod(MethodDefinition method, string methodHeader, List<List<MethodDefinition>> calleeList,
            List<SessionBuiltInFunction> sessionBuiltInFuncList, Dictionary<string, List<string>> DBMethodToOpList,
            Dictionary<string, List<string>> DBMethodToConstraitList, Dictionary<string, List<string>> DBMethodToTableNames,
            Dictionary<string, string> allDBClassToTableName, Dictionary<string, string> allDBClassPropToTableAttr,
            Dictionary<string, List<string>> tableNameToTableConstraints, Dictionary<string, int> GlobalMethodHeaderToIndex)
        {

            StringBuilder builder = new StringBuilder();
            List<string> opList = new List<string>(); // only contains local method's operation
            List<string> constraintList = new List<string>(); // only contains local method's constraints

            // Get Description From Call Chain
            StringBuilder deleBuilder = new StringBuilder();
            HashSet<string> prevDBConstraints = new HashSet<string>();
            HashSet<string> prevDBTableNames = new HashSet<string>();
            HashSet<string> prevDBAttrNames = new HashSet<string>();
            //foreach (List<MethodDefinition> path in calleeList)
            for (int i = 0; i < calleeList.Count(); i++)
            {
                List<MethodDefinition> path = calleeList[i];

                List<string> methodAlongCallChain = new List<string>();
                Stack<MethodDefinition> mStack = new Stack<MethodDefinition>();
                foreach (MethodDefinition md in path)
                {
                    //methodAlongCallChain.Add(md.GetFullName());
                    string mdHeader = BuildMethodHeader(md);
                    methodAlongCallChain.Add("<a href=\"#" + GlobalMethodHeaderToIndex[mdHeader] + "\">" + md.GetFullName() + "</a>");
                    mStack.Push(md);
                }

                string callChain = string.Join(" --> ", methodAlongCallChain);
                //Console.WriteLine(callChain);

                List<string> curOpList = new List<string>();
                while (mStack.Count() != 0)
                {
                    string curMethodHeader = BuildMethodHeader(mStack.Pop());
                    if (curMethodHeader == methodHeader)
                    {
                        continue;
                    }

                    if (!DBMethodToOpList.ContainsKey(curMethodHeader))
                    {
                        continue;
                    }
                    foreach (string op in DBMethodToOpList[curMethodHeader])
                    {
                        curOpList.Add(op);
                    }
                    foreach (string cons in DBMethodToConstraitList[curMethodHeader])
                    {
                        prevDBConstraints.Add(cons);
                    }
                    foreach (string tname in DBMethodToTableNames[curMethodHeader])
                    {
                        string[] tmps = tname.Split(';');
                        prevDBTableNames.Add(tmps[0]);
                        string[] tmps2 = tmps[1].Split(',');
                        foreach (string t in tmps2)
                        {
                            if (t != "")
                            {
                                prevDBAttrNames.Add(t);
                            }
                        }
                    }
                }

                if (methodAlongCallChain.Count() <= 1 || curOpList.Count() == 0)
                {
                    continue;
                }

                deleBuilder.AppendLine("Via call-chain: <i>" + callChain + "</i><br/>");
                foreach (string op in curOpList)
                {
                    deleBuilder.Append(op);
                }

                if (i < calleeList.Count())
                {
                    deleBuilder.AppendLine("<br/>");
                }
            }

            // Describe Session Method
            StringBuilder sessFuncBuilder = new StringBuilder();
            Dictionary<string, string> sessFuncTableNameAndAttrList = new Dictionary<string, string>();
            foreach (SessionBuiltInFunction sessFunc in sessionBuiltInFuncList)
            {
                if (SessionBuiltInFunction.Querys.Contains(sessFunc.FunctionName))
                {
                    bool ifInteractWithRealTable = true;
                    string targetTableName = sessFunc.TargetTableName;
                    if (targetTableName == "")
                    {
                        targetTableName = GetSessionQueryFuncTragetTableName(method, allDBClassToTableName);
                        if (targetTableName == "")
                        {
                            targetTableName = string.Join(", ", prevDBTableNames);
                            if (targetTableName == "")
                            {
                                ifInteractWithRealTable = false;
                            }
                        }
                    }

                    if (ifInteractWithRealTable == true)
                    {
                        //sessFuncBuilder.AppendLine("- It queries the table " + targetTableName + ". (" + sessFunc + ")");
                        sessFuncBuilder.AppendLine("- It queries the table <i>" + targetTableName + "</i><br/>");
                    }
                    else
                    {
                        sessFuncBuilder.AppendLine("- It provides query API for upper-level functions.<br/>");
                    }
                    
                }
                else if (SessionBuiltInFunction.Inserts.Contains(sessFunc.FunctionName))
                {
                    string attrListStr = string.Join(", ", prevDBAttrNames);
                    if (attrListStr == "")
                    {
                        HashSet<string> attrList = new HashSet<string>();
                        foreach (string attr in allDBClassPropToTableAttr.Values)
                        {
                            string[] tmps = attr.Split('.');
                            if (tmps[0] == sessFunc.TargetTableName)
                            {
                                attrList.Add(tmps[1]);
                            }
                        }
                        attrListStr = string.Join(", ", attrList);
                    }
                    //Console.WriteLine(attrListStr);
                    sessFuncBuilder.AppendLine("- It inserts <i>" + attrListStr + "</i> into table <i>" + sessFunc.TargetTableName + "</i><br/>");
                    if (!sessFuncTableNameAndAttrList.ContainsKey(sessFunc.TargetTableName))
                    {
                        sessFuncTableNameAndAttrList.Add(sessFunc.TargetTableName, attrListStr);
                    }
                }
                else if (SessionBuiltInFunction.Deletes.Contains(sessFunc.FunctionName))
                {
                    sessFuncBuilder.AppendLine("- It deletes rows from table <i>" + sessFunc.TargetTableName + "</i><br/>");
                }
                else if (SessionBuiltInFunction.Updates.Contains(sessFunc.FunctionName))
                {
                    string attrListStr = string.Join(", ", prevDBAttrNames);
                    if (attrListStr == "")
                    {
                        HashSet<string> attrList = new HashSet<string>();
                        foreach (string attr in allDBClassPropToTableAttr.Values)
                        {
                            string[] tmps = attr.Split('.');
                            if (tmps[0] == sessFunc.TargetTableName)
                            {
                                attrList.Add(tmps[1]);
                            }
                        }
                        attrListStr = string.Join(", ", attrList);
                    }
                    sessFuncBuilder.AppendLine("- It updates <i>" + attrListStr + "</i> into table <i>" + sessFunc.TargetTableName + "</i><br/>");
                    if (!sessFuncTableNameAndAttrList.ContainsKey(sessFunc.TargetTableName))
                    {
                        sessFuncTableNameAndAttrList.Add(sessFunc.TargetTableName, attrListStr);
                    }
                }
                else if (SessionBuiltInFunction.SaveOrUpdates.Contains(sessFunc.FunctionName))
                {
                    string attrListStr = string.Join(", ", prevDBAttrNames);
                    if (attrListStr == "")
                    {
                        HashSet<string> attrList = new HashSet<string>();
                        foreach (string attr in allDBClassPropToTableAttr.Values)
                        {
                            string[] tmps = attr.Split('.');
                            if (tmps[0] == sessFunc.TargetTableName)
                            {
                                attrList.Add(tmps[1]);
                            }
                        }
                        attrListStr = string.Join(", ", attrList);
                    }
                    sessFuncBuilder.AppendLine("- It saves or updates <i>" + attrListStr + "</i> into table <i>" + sessFunc.TargetTableName + "</i><br/>");
                    if (!sessFuncTableNameAndAttrList.ContainsKey(sessFunc.TargetTableName))
                    {
                        sessFuncTableNameAndAttrList.Add(sessFunc.TargetTableName, attrListStr);
                    }
                }
            }

            builder.AppendLine("<b>" + LocalMethodHeader + "</b><br/>");
            builder.Append(sessFuncBuilder.ToString());
            opList.Add(sessFuncBuilder.ToString());

            if (deleBuilder.Length != 0)
            {
                builder.AppendLine("<br/>");
                builder.AppendLine("<b>" + DelegatedMethodHeader + "</b><br/>");
                builder.Append(deleBuilder.ToString());
            }

            // Get DB constraints
            StringBuilder curConsBuilder = new StringBuilder();
            foreach (KeyValuePair<string, string> item in sessFuncTableNameAndAttrList)
            {
                string tablename = item.Key;
                if (tablename == "")
                {
                    continue;
                }
                string attrListStr = item.Value;
                string[] attrs = attrListStr.Split(new char[] { ' ', ',' });
                List<string> allConstraintList = tableNameToTableConstraints[tablename];
                foreach (string cons in allConstraintList)
                {
                    foreach (string attr in attrs)
                    {
                        if (attr != "" && cons.Contains(attr))
                        {
                            curConsBuilder.AppendLine("- " + cons + "<br/>");
                            constraintList.Add("- " + cons);
                            break;
                        }
                    }
                }
            }
            

            if (prevDBConstraints.Count() != 0 || curConsBuilder.Length != 0)
            {
                builder.AppendLine("<br/>");
                builder.AppendLine("<b>" + ConstraitsHeader + "</b><br/>");
                builder.Append(curConsBuilder.ToString());
                foreach (string pc in prevDBConstraints)
                {
                    builder.AppendLine(pc + "<br/>");
                }
            }
            
            return new MethodDescription(builder.ToString(), opList, constraintList, new List<string>());
        }


        /// <summary>
        /// Given a Hibernate Session query function, return the database table it interacts with.
        /// </summary>
        /// <param name="method"></param>
        /// <param name="allDBClassToTableName"></param>
        /// <returns></returns>
        public static string GetSessionQueryFuncTragetTableName(MethodDefinition method, Dictionary<string, string> allDBClassToTableName)
        {
            HashSet<string> targetTableNames = new HashSet<string>();
            //Console.WriteLine("---" + method.GetFullName());

            foreach (Statement stmt in method.GetDescendants<DeclarationStatement>())
            {
                //Console.WriteLine(stmt + " || " + stmt.GetType());
                foreach (Expression exp in stmt.GetExpressions())
                {
                    //Console.WriteLine("\t" + exp +  " || " +  exp.GetType());
                    foreach (LiteralUse lu in exp.GetDescendants<LiteralUse>())
                    {
                        //Console.WriteLine("\t\t" + lu.ToString() + " || " + lu.GetType());
                        foreach (string classFullName in allDBClassToTableName.Keys)
                        {
                            string[] tmps = classFullName.Split('.');
                            string className = tmps[tmps.Length - 1];
                            //Console.WriteLine("\t\t" + className);
                            if (lu.ToString().Contains(className))
                            {
                                targetTableNames.Add(allDBClassToTableName[classFullName]);
                            }
                        }
                    }
                }
            }
            return string.Join(", ", targetTableNames);
        }


        /// <summary>
        /// Generate method description for SQL Operating methods,
        /// which are the gets, sets, and constructors defined in POJO classes
        /// </summary>
        /// <param name="basicMethod"></param>
        /// <param name="tableNameToTableConstraints"></param>
        /// <returns></returns>
        public static MethodDescription DescribeBasicMethod(BasicMethod basicMethod, Dictionary<string, List<string>> tableNameToTableConstraints)
        {
            StringBuilder builder = new StringBuilder();
            List<string> opList = new List<string>();
            List<string> constraintList = new List<string>();
            List<string> dBTableAttrList = new List<string>();

            builder.AppendLine("<b>" + LocalMethodHeader + "</b><br/>");
            Constants.BasicMethodType mType = basicMethod.MethodType;
            string tableName = basicMethod.Table;
            HashSet<string> attrList = basicMethod.AttrList;
            dBTableAttrList.Add(tableName + ";" + string.Join(",", attrList));
            if (mType == Constants.BasicMethodType.Construct)
            {
                StringBuilder opBuilder = new StringBuilder();
                opBuilder.Append("- It constructs <i>" + tableName + "</i>");
                if (attrList.Count() == 0)
                {
                    opBuilder.AppendLine(" without any initial value<br/>");
                }
                else
                {
                    opBuilder.Append(" with attributes <i>" + string.Join(", ", attrList) + "</i><br/>");
                }
                builder.Append(opBuilder.ToString());
                opList.Add(opBuilder.ToString());
            }
            else
            {
                StringBuilder opBuilder = new StringBuilder();
                opBuilder.AppendLine("- It " + mType.ToString().ToLower() + "s attribute <i>" + string.Join(", ", attrList) + "</i> from table <i>" + tableName + "</i><br/>");
                builder.Append(opBuilder.ToString());
                opList.Add(opBuilder.ToString());
            }
            builder.AppendLine(POJOClassMethodNote + "<br/>");

            if (mType == Constants.BasicMethodType.Get)
            {
                return new MethodDescription(builder.ToString(), opList, constraintList, dBTableAttrList);
            }

            // Get DB constraints
            List<string> allConstraintList = tableNameToTableConstraints[tableName];
            StringBuilder consBuilder = new StringBuilder();
            foreach (string cons in allConstraintList)
            {
                foreach (string attr in attrList)
                {
                    if (cons.Contains(attr))
                    {
                        consBuilder.AppendLine("- " + cons + "<br/>");
                        constraintList.Add("- " + cons);
                        break;
                    }
                }
            }
            if (consBuilder.Length != 0)
            {
                builder.AppendLine("<br/>");
                builder.AppendLine("<b>" + ConstraitsHeader + "</b><br/>");
                //builder.AppendLine("In table " + tableName + ": ");
                builder.Append(consBuilder.ToString());
            }

            return new MethodDescription(builder.ToString(), opList, constraintList, dBTableAttrList);
        }


        /// <summary>
        /// Build the method header for a given method: method full name (contains package name) + full passed-in parameter list
        /// </summary>
        /// <param name="method"></param>
        /// <returns></returns>
        public static string BuildMethodHeader(MethodDefinition method)
        {
            if (method.GetFullName() == "com.jspdev.biyesheji.base._BaseRootDAO.getNamedQuery")
            {
                foreach (VariableDeclaration para in method.Parameters)
                {
                    Console.WriteLine(para.GetType());
                    Console.WriteLine(para.VariableType);
                    Console.ReadKey();
                }
            }
            StringBuilder builder = new StringBuilder();
            builder.Append(method.GetFullName() + "(");
            if (method.Parameters.Count() == 0)
            {
                builder.AppendLine(")");
            }
            else
            {
                List<string> paraTypes = new List<string>();
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
