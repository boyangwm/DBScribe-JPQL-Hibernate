using ABB.SrcML.Data;
using DBScribeHibernate.DBScribeHibernate.CallGraphExtractor;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.Stereotype
{
    class MethodUtil
    {

        public static void CheckIfCallSessionBuiltInFunction(MethodDefinition md, Dictionary<string, string> allDBClassToTableName)
        {
            HashSet<string> PojoClassNames = GetPOJOClassName(allDBClassToTableName);
            Dictionary<string, string> varList = GetVariableListForMethod(md);
            foreach (KeyValuePair<string, string> item in varList)
            {
                Console.WriteLine(item.Key + " <--> " + item.Value);
            }
            Console.WriteLine("");

            IEnumerable<Expression> expressions = from statements in md.GetDescendantsAndSelf()
                              from expression in statements.GetExpressions()
                              select expression;
            foreach (Expression expr in expressions)
            {
                Console.WriteLine(expr);
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
                                MethodCall sessionFunction = (MethodCall)nextSibling;
                                string targetClassName = "";
                                if(sessionFunction.Name == Constants.SessionBuiltInFunctions.delete.ToString() ||
                                    sessionFunction.Name == Constants.SessionBuiltInFunctions.load.ToString())
                                // When you find one session function, find the first NameUse that is a POJO class
                                for (int j = i + 1; j < len; j++)
                                {
                                    if (itemsInSameLevel[j].GetType().ToString() == Constants.SrcML_NameUse)
                                    {
                                        if(PojoClassNames.Contains(itemsInSameLevel[j].Name)){
                                            targetClassName = itemsInSameLevel[j].Name;
                                        }
                                    }
                                }
                                Console.WriteLine("\t\t" + varList[item.Name] + "." + sessionFunction.Name + "(" + targetClassName + ")");
                            }
                        }
                    }
                }
            }
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
                varList.Add(vi.Name, vi.VariableType.ToString());
            }
            foreach (VariableInfo vi in mAnalyzer.VariablesInfo)
            {
                varList.Add(vi.GetName(), vi.GetVariableType().ToString());
            }
            foreach (VariableDeclaration vi in mAnalyzer.GetSelfFields)
            {
                varList.Add(vi.Name, vi.VariableType.ToString());
            }
            foreach (VariableDeclaration vi in mAnalyzer.SetSelfFields)
            {
                varList.Add(vi.Name, vi.VariableType.ToString());
            }
            foreach (VariableDeclaration vi in mAnalyzer.PropertyFields)
            {
                varList.Add(vi.Name, vi.VariableType.ToString());
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
