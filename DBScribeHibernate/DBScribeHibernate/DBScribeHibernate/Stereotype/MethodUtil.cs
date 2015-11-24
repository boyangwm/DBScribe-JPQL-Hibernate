using ABB.SrcML.Data;
using DBScribeHibernate.DBScribeHibernate.CallGraphExtractor;
using DBScribeHibernate.DBScribeHibernate.DescriptionTemplates;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.Stereotype
{
    class MethodUtil
    {
        public static BasicMethod CheckIfGetSetMethodsInPOJOClass(MethodDefinition method, Dictionary<string, string> allDBClassToTableName, Dictionary<string, string> allDBClassPropToTableAttr)
        {
            BasicMethod basictMethod = null;

            string methodFullName = method.GetFullName();
            string curClassName = "";
            TypeDefinition declaringClass = MethodUtil.GetDeclaringClass(method);
            if (declaringClass == null)
            {
                return basictMethod;
            }
            else
            {
                curClassName = declaringClass.GetFullName();
            }

            //Console.WriteLine(curClassName);
            if (!allDBClassToTableName.ContainsKey(curClassName)) // if not from POJO class return;
            {
                return basictMethod;
            }

            HibernateMethodAnalyzer mAnalyzer = new HibernateMethodAnalyzer(method);
            if (mAnalyzer.IsSuccess != 0)
            {
                //Console.WriteLine(mAnalyzer.GetFailInfo());
                return basictMethod;
            }

            string tableName = allDBClassToTableName[curClassName];

            // (1) handle Basic POJO DB Class Constructors
            if (method.IsConstructor)
            {
                HashSet<string> attrList = new HashSet<string>();
                foreach (VariableDeclaration vd in mAnalyzer.SetSelfFields)
                {
                    string fullClassPropName = curClassName + "." + vd.Name;
                    if (allDBClassPropToTableAttr.ContainsKey(fullClassPropName))
                    {
                        string[] temps = allDBClassPropToTableAttr[fullClassPropName].Split('.');
                        attrList.Add(temps[1]);
                    }
                }
                basictMethod = new BasicMethod(Constants.BasicMethodType.Construct, tableName, attrList);
            }
            else // (2) handle Basic POJO DB Class Get/Set methods
            {
                HashSet<VariableDeclaration> getSelfFiels = mAnalyzer.GetSelfFields;
                HashSet<VariableDeclaration> setSelfFiels = mAnalyzer.SetSelfFields;
                if (getSelfFiels.Count() == 1 && setSelfFiels.Count() == 0)
                {
                    VariableDeclaration para = getSelfFiels.SingleOrDefault();
                    string fullClassPropName = curClassName + "." + para.Name;
                    if (allDBClassPropToTableAttr.ContainsKey(fullClassPropName))
                    {
                        string[] temps = allDBClassPropToTableAttr[fullClassPropName].Split('.');
                        HashSet<string> attrList = new HashSet<string>();
                        attrList.Add(temps[1]);
                        basictMethod = new BasicMethod(Constants.BasicMethodType.Get, tableName, attrList);
                    }
                }
                if (setSelfFiels.Count() == 1 && getSelfFiels.Count() == 0)
                {
                    VariableDeclaration para = setSelfFiels.SingleOrDefault();
                    string fullClassPropName = curClassName + "." + para.Name;
                    if (allDBClassPropToTableAttr.ContainsKey(fullClassPropName))
                    {
                        string[] temps = allDBClassPropToTableAttr[fullClassPropName].Split('.');
                        HashSet<string> attrList = new HashSet<string>();
                        attrList.Add(temps[1]);
                        basictMethod = new BasicMethod(Constants.BasicMethodType.Set, tableName, attrList);
                    }
                }
            }
            return basictMethod;
        }
        

        /// <summary>
        /// Check if the given method calls Session built-in function!
        /// Return a list of Session Built-in Functions(in sequency)
        /// </summary>
        /// <param name="md"></param>
        /// <param name="allDBClassToTableName"></param>
        /// <returns></returns>
        public static List<SessionBuiltInFunction> CheckIfCallSessionBuiltInFunction(MethodDefinition md, Dictionary<string, string> allDBClassToTableName)
        {
            List<SessionBuiltInFunction> sessionFunctionList = new List<SessionBuiltInFunction>();

            HashSet<string> PojoClassNames = GetPOJOClassName(allDBClassToTableName);
            Dictionary<string, string> varList = GetVariableListForMethod(md);
            //foreach (KeyValuePair<string, string> item in varList)
            //{
            //    Console.WriteLine(item.Key + " <--> " + item.Value);
            //}
            //Console.WriteLine("");

            IEnumerable<Expression> expressions = from statements in md.GetDescendantsAndSelf()
                              from expression in statements.GetExpressions()
                              select expression;
            foreach (Expression expr in expressions)
            {
                //Console.WriteLine(expr);
                Stack<SessionBuiltInFunction> fStack = new Stack<SessionBuiltInFunction>();

                List<NameUse> itemsInSameLevel = new List<NameUse>(expr.GetDescendantsAndSelf<NameUse>());
                int len = itemsInSameLevel.Count();
                for(int i = 0; i < len; i++)
                {
                    NameUse item = itemsInSameLevel[i];
                    //Console.WriteLine("\t" + item.Name + " || " + item.GetType());
                    if (item.GetType().ToString() == Constants.SrcML_NameUse)
                    {
                        //Console.WriteLine("\t" + item.Name + " || " + item.GetType());
                        if (varList.ContainsKey(item.Name) && varList[item.Name] == Constants.Session)
                        {
                            NameUse nextSibling = null;
                            if (i != len - 1)
                            {
                                nextSibling = itemsInSameLevel[++i];
                            }
                            if (nextSibling != null && nextSibling.GetType().ToString() == Constants.SrcML_MethodCall)
                            {
                                string sessionFunctionName = ((MethodCall)nextSibling).Name;

                                // Next, find the target POJO class for normal session functions
                                // And find query string for query session functions
                                string targetClassName = "";
                                if (SessionBuiltInFunction.NormalFunctions.Contains(sessionFunctionName))
                                {
                                    // When you find one session function, find the first NameUse that is a POJO class
                                    // (1) if use "String entityName" as parameter
                                    for (int j = i + 1; j < len; j++)
                                    {
                                        if (itemsInSameLevel[j].GetType().ToString() == Constants.SrcML_NameUse)
                                        {
                                            if (PojoClassNames.Contains(itemsInSameLevel[j].Name))
                                            {
                                                targetClassName = itemsInSameLevel[j].Name;
                                                break;
                                            }

                                        }
                                    }
                                    // (2) not found in (1) && if use "Object object" as parameter
                                    if (targetClassName == "")
                                    {
                                        for (int j = i + 1; j < len; j++)
                                        {
                                            if(varList.ContainsKey(itemsInSameLevel[j].Name) &&
                                                PojoClassNames.Contains(varList[itemsInSameLevel[j].Name]))
                                            {
                                                targetClassName = varList[itemsInSameLevel[j].Name];
                                                break;
                                            }
                                        }
                                    }

                                    //Console.WriteLine("\t\t~~~~ " + varList[item.Name] + "." + sessionFunctionName + "(" + targetClassName + ")");
                                    //sessionFunctionList.Add(new SessionBuiltInFunction(sessionFunctionName, targetClassName));
                                    fStack.Push(new SessionBuiltInFunction(sessionFunctionName, GetTableNameFromClassName(targetClassName, allDBClassToTableName)));
                                }
                                else if (SessionBuiltInFunction.QueryFunctions.Contains(sessionFunctionName))
                                {
                                    // Note that, if it's query function, TargetClassName in fact means hql/sql string.
                                    for (int j = i + 1; j < len; j++)
                                    {
                                        var itemName = itemsInSameLevel[j].Name;
                                        if (varList.ContainsKey(itemName))
                                        {
                                            if (varList[itemName] == "string" || varList[itemName] == "String")
                                            {
                                                targetClassName = varList[itemName];
                                                break;
                                            }
                                        }
                                    }
                                    //Console.WriteLine("\t\t" + varList[item.Name] + "." + sessionFunctionName + "(" + targetClassName + ")");
                                    //sessionFunctionList.Add(new SessionBuiltInFunction(sessionFunctionName, targetClassName));
                                    fStack.Push(new SessionBuiltInFunction(sessionFunctionName, GetTableNameFromClassName(targetClassName, allDBClassToTableName)));
                                }
                                else
                                {
                                    //Console.WriteLine("\t\t*** Other types of session function: " + sessionFunctionName);
                                }
                            }
                        }
                    }
                }
                while (fStack.Count() != 0)
                {
                    sessionFunctionList.Add(fStack.Pop());
                }
            }
            return sessionFunctionList;
        }

        private static string GetTableNameFromClassName(string className, Dictionary<string, string> allDBClassToTableName)
        {
            string tableName = "";
            foreach (KeyValuePair<string, string> item in allDBClassToTableName)
            {
                string[] tmps = item.Key.Split('.');
                if (tmps[tmps.Length - 1] == className)
                {
                    return item.Value;
                }
            }
            return tableName;
        }

        /// <summary>
        /// Get class name from class full name
        /// </summary>
        /// <param name="allDBClassToTableName"></param>
        /// <returns></returns>
        private static HashSet<string> GetPOJOClassName(Dictionary<string, string> allDBClassToTableName)
        {
            HashSet<string> classNames = new HashSet<string>();
            foreach (string fullName in allDBClassToTableName.Keys)
            {
                string[] tmps = fullName.Split('.');
                classNames.Add(tmps[tmps.Length - 1]);
            }
            return classNames;
        }

        /// <summary>
        /// Get all the varibles used in the methods, including
        /// paras, local variables, get/set field, property fields
        /// </summary>
        /// <param name="md"></param>
        /// <returns>Dictionary<string, string>: link variable name to variable type</returns>
        public static Dictionary<string, string> GetVariableListForMethod(MethodDefinition md)
        {
            Dictionary<string, string> varList = new Dictionary<string, string>();

            HibernateMethodAnalyzer mAnalyzer = new HibernateMethodAnalyzer(md);
            foreach (VariableDeclaration vi in mAnalyzer.Paras)
            {
                if (!varList.ContainsKey(vi.Name))
                {
                    varList.Add(vi.Name, vi.VariableType.ToString());
                }
            }
            foreach (VariableInfo vi in mAnalyzer.VariablesInfo)
            {
                if (!varList.ContainsKey(vi.GetName()))
                {
                    varList.Add(vi.GetName(), vi.GetVariableType().ToString());
                }
            }
            foreach (VariableDeclaration vi in mAnalyzer.GetSelfFields)
            {
                if (!varList.ContainsKey(vi.Name))
                {
                    varList.Add(vi.Name, vi.VariableType.ToString());
                }
            }
            foreach (VariableDeclaration vi in mAnalyzer.SetSelfFields)
            {
                if (!varList.ContainsKey(vi.Name))
                {
                    varList.Add(vi.Name, vi.VariableType.ToString());
                }
            }
            foreach (VariableDeclaration vi in mAnalyzer.PropertyFields)
            {
                if (!varList.ContainsKey(vi.Name))
                {
                    varList.Add(vi.Name, vi.VariableType.ToString());
                }
            }
            return varList;
        }
        

        /// <summary>
        /// Get all the methods invoked by this methods: both locally or externally
        /// </summary>
        /// <param name="md"></param>
        /// <returns></returns>
        public static HashSet<string> GetInvokedMethodNameInTheMethod(MethodDefinition md)
        {
            HashSet<string> invokedMethodNames = new HashSet<string>();
            IEnumerable<MethodCall> mdCalls = from statments in md.GetDescendantsAndSelf()
                                              from expression in statments.GetExpressions()
                                              from call in expression.GetDescendantsAndSelf<MethodCall>()
                                              select call;
            foreach (MethodCall mdc in mdCalls)
            {
                MethodDefinition mDef = CallGraphUtil.FindMatchedMd(mdc);
                if (mDef != null)
                {
                    invokedMethodNames.Add(mDef.GetFullName());
                }
            }
            return invokedMethodNames;
        }

        public static HashSet<string> GetInvokedMethodNameHeaderInTheMethod(MethodDefinition md)
        {
            HashSet<string> invokedMethodNameHeaders = new HashSet<string>();
            IEnumerable<MethodCall> mdCalls = from statments in md.GetDescendantsAndSelf()
                                              from expression in statments.GetExpressions()
                                              from call in expression.GetDescendantsAndSelf<MethodCall>()
                                              select call;
            foreach (MethodCall mdc in mdCalls)
            {
                MethodDefinition mDef = CallGraphUtil.FindMatchedMd(mdc);
                if (mDef != null)
                {
                    string methodHeader = MethodDescriptionUtil.BuildMethodHeader(mDef);
                    invokedMethodNameHeaders.Add(methodHeader);
                }
            }
            return invokedMethodNameHeaders;
        }

        /// <summary>
        /// Get all the methods invoked by this methods: both locally or externally
        /// Using MethodAnalyzer. Sometimes doesn't work!!!
        /// </summary>
        /// <param name="md"></param>
        /// <returns></returns>
        public static HashSet<string> GetInvokedMethodNameInTheMethod_Stale(MethodDefinition md)
        {
            HashSet<string> invokedMethodNames = new HashSet<string>();
            
            HibernateMethodAnalyzer mAnalyzer = new HibernateMethodAnalyzer(md);
            foreach (MethodDefinition invmd in mAnalyzer.InvokedLocalMethods)
            {
                if (invmd != null)
                {
                    invokedMethodNames.Add(invmd.GetFullName());
                }
            }
            foreach (MethodDefinition invmd in mAnalyzer.InvokedExternalMethods)
            {
                if (invmd != null)
                {
                    invokedMethodNames.Add(invmd.GetFullName());
                }
            }
            return invokedMethodNames;
        }

        public static TypeDefinition GetDeclaringClass(MethodDefinition method)
        {
            TypeDefinition declaringClass = method.GetAncestors<TypeDefinition>().FirstOrDefault();
            return declaringClass;
        }

        public static IEnumerable<TypeDefinition> GetParentClasses(TypeDefinition curClass)
        {
            IEnumerable<TypeDefinition> parentClasses = curClass.GetParentTypes(true);
            return parentClasses;
        }

        public static IEnumerable<MethodDefinition> GetAllMethodDefinitionsInOneClass(TypeDefinition curClass)
        {
            IEnumerable<MethodDefinition> methods = curClass.GetDescendants<MethodDefinition>();
            return methods;
        }

        public static IEnumerable<MethodCall> GetAllMethodCallsInOneClass(TypeDefinition curClass)
        {
            IEnumerable<MethodCall> methods = curClass.GetDescendants<MethodCall>();
            return methods;
        }

        public static IReadOnlyCollection<VariableDeclaration> GetParametersInitialInfo(MethodDefinition method)
        {
            return method.Parameters;
        }
    }
}
