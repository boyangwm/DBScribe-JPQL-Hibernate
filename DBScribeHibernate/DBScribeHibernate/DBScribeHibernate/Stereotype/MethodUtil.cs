using ABB.SrcML.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.Stereotype
{
    class MethodUtil
    {

        /// <summary>
        /// // Get all the methods invoked by this methods: both locally or externally
        /// </summary>
        /// <param name="md"></param>
        /// <returns></returns>
        public static HashSet<string> GetInvokedMethodNameInTheMethod(MethodDefinition md)
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
