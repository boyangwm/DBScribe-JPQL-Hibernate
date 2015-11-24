﻿using ABB.SrcML.Data;
using DBScribeHibernate.DBScribeHibernate.CallGraphExtractor;
using DBScribeHibernate.DBScribeHibernate.Stereotype;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.DescriptionTemplates
{
    class MethodDescriptionUtil
    {
        private static string LocalMethodHeader = "-- This method implements the following db-related operations: ";
        private static string POJOClassMethodNote = "*Note: This method will not affect the database util Session built-in function is called";
        private static string ConstraitsHeader = "-- Some constraints that should be taken into account are the following: ";
        private static string DelegatedMethodHeader = "-- This method invokes db-related operations via delegation: ";

        public static MethodDescription DescribeDelegatedMethod(string methodHeader, List<List<MethodDefinition>> calleeList,
            Dictionary<string, List<string>> DBMethodToOpList, Dictionary<string, List<string>> DBMethodToConstraitList)
        {
            StringBuilder builder = new StringBuilder();
            List<string> opList = new List<string>();
            List<string> constraintList = new List<string>();

            // Get Description From Call Chain
            StringBuilder deleBuilder = new StringBuilder();
            HashSet<string> prevDBConstraints = new HashSet<string>();
            foreach (List<MethodDefinition> path in calleeList)
            {
                List<string> methodAlongCallChain = new List<string>();
                Stack<MethodDefinition> mStack = new Stack<MethodDefinition>();
                foreach (MethodDefinition md in path)
                {
                    methodAlongCallChain.Add(md.GetFullName());
                    mStack.Push(md);
                }
                string callChain = string.Join("-->", methodAlongCallChain);
                //Console.WriteLine(callChain);

                List<string> curOpList = new List<string>();
                
                while (mStack.Count() != 0)
                {
                    string curMethodHeader = BuildMethodHeader(mStack.Pop());
                    if (curMethodHeader == methodHeader)
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
                deleBuilder.AppendLine("Via call-chain: " + callChain);
                foreach (string op in curOpList)
                {
                    deleBuilder.Append(op);
                }
                deleBuilder.AppendLine("");
            }

            if (deleBuilder.Length != 0)
            {
                builder.AppendLine("");
                builder.AppendLine(DelegatedMethodHeader);
                builder.Append(deleBuilder.ToString());
            }

            return new MethodDescription(builder.ToString(), opList, constraintList, new List<string>());
        }

        public static MethodDescription DescribeSessionMethod(string methodHeader, List<List<MethodDefinition>> calleeList,
            List<SessionBuiltInFunction> sessionBuiltInFuncList, Dictionary<string, List<string>> DBMethodToOpList,
            Dictionary<string, List<string>> DBMethodToConstraitList, Dictionary<string, List<string>> DBMethodToTableNames)
        {
            StringBuilder builder = new StringBuilder();
            List<string> opList = new List<string>(); // only contains local method's operation
            List<string> constraintList = new List<string>(); // only contains local method's constraints (hence, empty)

            // Get Description From Call Chain
            StringBuilder deleBuilder = new StringBuilder();
            HashSet<string> prevDBConstraints = new HashSet<string>();
            HashSet<string> prevDBTableNames = new HashSet<string>();
            foreach (List<MethodDefinition> path in calleeList)
            {
                List<string> methodAlongCallChain = new List<string>();
                Stack<MethodDefinition> mStack = new Stack<MethodDefinition>();
                foreach (MethodDefinition md in path)
                {
                    methodAlongCallChain.Add(md.GetFullName());
                    mStack.Push(md);
                }
                string callChain = string.Join("-->", methodAlongCallChain);
                //Console.WriteLine(callChain);

                List<string> curOpList = new List<string>();
                while (mStack.Count() != 0)
                {
                    string curMethodHeader = BuildMethodHeader(mStack.Pop());
                    if (curMethodHeader == methodHeader)
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
                        prevDBTableNames.Add(tname);
                    }
                }
                deleBuilder.AppendLine("Via call-chain: " + callChain);
                foreach (string op in curOpList)
                {
                    deleBuilder.Append(op);
                }
                deleBuilder.AppendLine("");
            }

            // Describe Session Method
            StringBuilder sessFuncBuilder = new StringBuilder();
            foreach (SessionBuiltInFunction sessFunc in sessionBuiltInFuncList)
            {
                if (SessionBuiltInFunction.Querys.Contains(sessFunc.FunctionName))
                {
                    string targetTableName = sessFunc.TargetTableName;
                    if (targetTableName == "")
                    {
                        targetTableName = string.Join(", ", prevDBTableNames);
                    }
                    sessFuncBuilder.AppendLine("- It queries " + targetTableName + ". " + sessFunc);
                }
                else if (SessionBuiltInFunction.Inserts.Contains(sessFunc.FunctionName))
                {
                    sessFuncBuilder.AppendLine("- It inserts " + sessFunc.TargetTableName + ". " + sessFunc);
                }
                else if (SessionBuiltInFunction.Deletes.Contains(sessFunc.FunctionName))
                {
                    sessFuncBuilder.AppendLine("- It deletes " + sessFunc.TargetTableName + ". " + sessFunc);
                }
                else if (SessionBuiltInFunction.Updates.Contains(sessFunc.FunctionName))
                {
                    sessFuncBuilder.AppendLine("- It updates " + sessFunc.TargetTableName + ". " + sessFunc);
                }
                else if (SessionBuiltInFunction.SaveOrUpdates.Contains(sessFunc.FunctionName))
                {
                    sessFuncBuilder.AppendLine("- It saves or updates " + sessFunc.TargetTableName + ". " + sessFunc);
                }
            }

            builder.AppendLine(LocalMethodHeader);
            builder.Append(sessFuncBuilder.ToString());
            opList.Add(sessFuncBuilder.ToString());

            if (deleBuilder.Length != 0)
            {
                builder.AppendLine("");
                builder.AppendLine(DelegatedMethodHeader);
                builder.Append(deleBuilder.ToString());
            }
            
            return new MethodDescription(builder.ToString(), opList, constraintList, new List<string>());
        }

        private static string GetTableNameFromOpList()
        {
            string tableName = "";
            return tableName;
        }

        public static MethodDescription DescribeBasicMethod(BasicMethod basicMethod, Dictionary<string, List<string>> tableNameToTableConstraints)
        {
            StringBuilder builder = new StringBuilder();
            List<string> opList = new List<string>();
            List<string> constraintList = new List<string>();
            List<string> dBTableList = new List<string>();

            builder.AppendLine(LocalMethodHeader);
            Constants.BasicMethodType mType = basicMethod.MethodType;
            string tableName = basicMethod.Table;
            dBTableList.Add(tableName);
            HashSet<string> attrList = basicMethod.AttrList;
            if (mType == Constants.BasicMethodType.Construct)
            {
                StringBuilder opBuilder = new StringBuilder();
                opBuilder.Append("- It constructs " + tableName);
                if (attrList.Count() == 0)
                {
                    opBuilder.AppendLine(" without any initial value");
                }
                else
                {
                    opBuilder.AppendLine(" with attributes " + string.Join(", ", attrList));
                }
                builder.Append(opBuilder.ToString());
                opList.Add(opBuilder.ToString());
            }
            else
            {
                StringBuilder opBuilder = new StringBuilder();
                opBuilder.AppendLine("- It " + mType.ToString().ToLower() + "s attribute " + string.Join(", ", attrList) + " from table " + tableName);
                builder.Append(opBuilder.ToString());
                opList.Add(opBuilder.ToString());
            }
            builder.AppendLine(POJOClassMethodNote);

            if (mType == Constants.BasicMethodType.Get)
            {
                return new MethodDescription(builder.ToString(), opList, constraintList, dBTableList);
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
                        consBuilder.AppendLine("- " + cons);
                        constraintList.Add(cons);
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

            return new MethodDescription(builder.ToString(), opList, constraintList, dBTableList);
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
