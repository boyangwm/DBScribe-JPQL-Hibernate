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

        public static void CheckIfCallSessionBuiltInFunction(MethodDefinition md)
        {

            HashSet<VarInfo> varList = GetVariableListForMethod(md);
            IEnumerable<Expression> expressions = from statements in md.GetDescendantsAndSelf()
                              from expression in statements.GetExpressions()
                              select expression;
            foreach (Expression expr in expressions)
            {
                Console.WriteLine(expr);
                foreach (var xxx in expr.GetDescendantsAndSelf())
                {
                    Console.WriteLine("\t" + xxx + " || " + xxx.GetType());
                }
            }
            
        }

        /// <summary>
        /// Get all the varibles used in the methods, including
        /// paras, local variables, get/set field, property fields
        /// </summary>
        /// <param name="md"></param>
        /// <returns></returns>
        public static HashSet<VarInfo> GetVariableListForMethod(MethodDefinition md)
        {
            HashSet<VarInfo> varList = new HashSet<VarInfo>();

            HibernateMethodAnalyzer mAnalyzer = new HibernateMethodAnalyzer(md);
            foreach (VariableDeclaration vi in mAnalyzer.Paras)
            {
                varList.Add(new VarInfo(vi.Name, vi.VariableType));
            }
            foreach (VariableInfo vi in mAnalyzer.VariablesInfo)
            {
                varList.Add(new VarInfo(vi.GetName(), vi.GetVariableType()));
            }
            foreach (VariableDeclaration vi in mAnalyzer.GetSelfFields)
            {
                varList.Add(new VarInfo(vi.Name, vi.VariableType));
            }
            foreach (VariableDeclaration vi in mAnalyzer.SetSelfFields)
            {
                varList.Add(new VarInfo(vi.Name, vi.VariableType));
            }
            foreach (VariableDeclaration vi in mAnalyzer.PropertyFields)
            {
                varList.Add(new VarInfo(vi.Name, vi.VariableType));
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
