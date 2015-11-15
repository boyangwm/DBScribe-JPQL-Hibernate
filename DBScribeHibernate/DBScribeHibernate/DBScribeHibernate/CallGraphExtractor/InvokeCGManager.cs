using ABB.SrcML.Data;
using DBScribeHibernate.DBScribeHibernate.Stereotype;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.CallGraphExtractor
{
    class InvokeCGManager
    {
        /// <summary>
        /// Get topologically sorted methods in call graph
        /// </summary>
        /// <param name="methods"></param>
        /// <param name="cgm"></param>
        /// <returns>Bottom-up ordering of the methods, which is later used to propagate method description!</returns>
        public static List<MethodDefinition> GetBottomUpSortedMethodsFromCallGraph(IEnumerable<MethodDefinition> methods, CGManager cgm)
        {
            if (Constants.ShowLog)
            {
                Console.WriteLine("***Getting ordered (bottom-up) methods in call graph...");
            }
            

            HashSet<Tuple<MethodDefinition, MethodDefinition>> callerToCalleeEdges = GetCallerToCalleeEdges(methods, cgm);

            TopoSortCallGraph tscg = new TopoSortCallGraph();
            foreach (MethodDefinition m in methods)
            {
                tscg.addMethod(m);
            }
            foreach (Tuple<MethodDefinition, MethodDefinition> edge in callerToCalleeEdges)
            {
                tscg.addCallerToCalleeEdge(edge.Item1, edge.Item2);
            }
            List<MethodDefinition> sortedMethodsBottomToTop = tscg.topoSort();

            return sortedMethodsBottomToTop;
        }

        /// <summary>
        /// Get all edges in call graph
        /// </summary>
        /// <param name="methods"></param>
        /// <param name="cgm"></param>
        /// <returns></returns>
        private static HashSet<Tuple<MethodDefinition, MethodDefinition>> GetCallerToCalleeEdges(IEnumerable<MethodDefinition> methods, CGManager cgm)
        {
            HashSet<Tuple<MethodDefinition, MethodDefinition>> callerToCalleeEdges = new HashSet<Tuple<MethodDefinition, MethodDefinition>>();

            foreach (MethodDefinition callee in methods)
            {
                List<List<MethodDefinition>> paths = cgm.FindCallerList(callee);
                foreach (List<MethodDefinition> path in paths)
                // path[0]--> this callee, path[1]--> this callee's immediate caller, path[2]--> that caller's immediate caller ...
                {
                    if (path.Count() == 1) // along this path, no caller
                    {
                        continue;
                    }
                    else
                    {
                        MethodDefinition immediate_caller = path[1];
                        callerToCalleeEdges.Add(Tuple.Create(immediate_caller, callee));
                    }
                }
            }
            return callerToCalleeEdges;
        }


        /// <summary>
        /// Get methods call certain MethodCalls
        /// i.e. methods calling "beginTransaction" are LocalSQLMethods
        /// </summary>
        /// <param name="methodCallKeyword"></param>
        /// <param name="methods"></param>
        /// <returns></returns>
        public static HashSet<MethodDefinition> GetMethodesWithCertainMethodCall(string methodCallKeyword, IEnumerable<MethodDefinition> methods)
        {
            HashSet<MethodDefinition> specialMethods = new HashSet<MethodDefinition>();
            foreach (MethodDefinition method in methods)
            {
                var mdCalls = from statements in method.GetDescendantsAndSelf()
                              from expressions in statements.GetExpressions()
                              from call in expressions.GetDescendantsAndSelf<MethodCall>()
                              select call;
                foreach (MethodCall call in mdCalls)
                {
                    if (call.Name.Contains(methodCallKeyword))
                    {
                        //Console.WriteLine(method.GetFullName());
                        //Console.WriteLine("Call Name: " + call.Name);
                        //Console.WriteLine("Call ParentExpression: " + call.ParentExpression);
                        //Console.WriteLine("Call ParentStatement: " + call.ParentStatement);
                        //Console.WriteLine("Call GetAncestors: ");
                        specialMethods.Add(method);
                    }
                }
            }
            return specialMethods;
        }


        /// <summary>
        /// Get POJO class methods: i.e. get/set. These methods directly interacte with Database
        /// </summary>
        /// <param name="methods"></param>
        public static HashSet<MethodDefinition> GetSQLOperatingMethods(IEnumerable<MethodDefinition> methods, Dictionary<string, string> classFullNameToTableName)
        {
            HashSet<MethodDefinition> methods_SQLOperatingMethods = new HashSet<MethodDefinition>();
            foreach (MethodDefinition m in methods)
            {
                //if (Constants.ShowLog == true)
                //{
                //    Console.WriteLine("\n*** Start Analyzing method: " + m.GetFullName());
                //}
                
                HibernateMethodAnalyzer mAnalyzer = new HibernateMethodAnalyzer(m);
                if (mAnalyzer.IsSuccess == -1)
                {
                    Console.WriteLine("[Error] " + m.GetFullName() + ": Declaration Class == null");
                    continue;
                }

                if(classFullNameToTableName.ContainsKey(mAnalyzer.DeclaringClass.GetFullName())){
                    methods_SQLOperatingMethods.Add(m);
                }
                //Console.WriteLine("Parent Classes: ");
                //foreach (TypeDefinition dc in mAnalyzer.ParentClasses)
                //{
                //    Console.Write(" --> " + dc.GetFullName());
                //}
            }
            return methods_SQLOperatingMethods;
        }


        // The following functions are testing functions!
        private static void TestGetMethodesWithCertainMethodCall(IEnumerable<MethodDefinition> methods)
        {
            foreach (MethodDefinition method in methods)
            {
                var mdCalls = from statements in method.GetDescendantsAndSelf()
                              from expressions in statements.GetExpressions()
                              select expressions;
                foreach (Expression expression in mdCalls)
                {
                    if (expression.ToString().ToLower().Contains("session"))
                    {
                        Console.WriteLine(expression + " || " + expression.GetType());
                        foreach (MethodCall call in expression.GetDescendantsAndSelf<MethodCall>())
                        {
                            if (call.Name.ToLower().Contains("session") || call.Name.ToLower().Contains("transaction"))
                            {
                                Console.WriteLine("Call Name: " + call.Name);
                                Console.WriteLine("Call ParentExpression: " + call.ParentExpression);
                                Console.WriteLine("Call ParentStatement: " + call.ParentStatement);
                                Console.WriteLine("Call GetAncestors: ");
                                foreach (NameUse item in call.GetSiblingsBeforeSelf<NameUse>())
                                {
                                    Console.WriteLine(item + " || " + item.ParentStatement);
                                }

                                //Console.WriteLine("Arguments: ");
                                //foreach (var arg in call.Arguments)
                                //{
                                //    Console.WriteLine(arg);
                                //}
                                //Console.WriteLine("");

                                //Console.WriteLine("Components: ");
                                //foreach (var arg in call.Components)
                                //{
                                //    Console.WriteLine(arg);
                                //}

                            }
                            Console.WriteLine("-----------------------------------------------");
                        }
                    }
                }
            }
        }

        public static void TestHowToAnalyzeMethods(IEnumerable<MethodDefinition> methods)
        {
            //StreamWriter writetext = new StreamWriter("analyze_methods_get_from_srcml_net.txt");
            foreach (MethodDefinition method in methods)
            {
                //var mdCalls = from statments in method.GetDescendantsAndSelf()
                //              from expression in statments.GetExpressions()
                //              from call in expression.GetDescendantsAndSelf<MethodCall>()
                //              select call;
                //writetext.WriteLine("=== " + method.GetFullName());
                Console.WriteLine("=== " + method.GetFullName());
                foreach (var statements in method.GetDescendantsAndSelf())
                {
                    //writetext.WriteLine(statements + " || " + statements.GetType());
                    foreach (var expression in statements.GetExpressions())
                    {
                        //writetext.WriteLine("\t" + expression + " || " + expression.GetType());
                        foreach (var call in expression.GetDescendantsAndSelf())
                        {
                            //writetext.WriteLine("\t\t" + call + " || " + call.GetType());
                            if (call.GetType().ToString() == "ABB.SrcML.Data.MethodCall")
                            {
                                Console.WriteLine(statements);
                            }
                        }
                    }
                }
                //writetext.WriteLine("---------------------------------------------\n");
                Console.WriteLine("---------------------------------------------\n");
            }
            //writetext.Close();
            //Console.WriteLine("Writing to file finished!");
        }

        public static void TestHowToUseMethodAnalyzer(IEnumerable<MethodDefinition> methods)
        {
            foreach (MethodDefinition m in methods)
            {
                Console.WriteLine("\n*** Start Analyzing method: " + m.GetFullName());
                HibernateMethodAnalyzer hibernateMethodAnalyzer = new HibernateMethodAnalyzer(m);
                if (hibernateMethodAnalyzer.IsSuccess == -1)
                {
                    Console.WriteLine("[Error] Declaration Class == null");
                    continue;
                }

                Console.Write("\n1. Declaring Class: " + hibernateMethodAnalyzer.DeclaringClass.GetFullName());
                foreach (TypeDefinition dc in hibernateMethodAnalyzer.ParentClasses)
                {
                    Console.Write(" --> " + dc.GetFullName());
                }
                Console.WriteLine("");

                Console.WriteLine("\n2. Method Parameters: ");
                int idx = 0;
                foreach (VariableInfo para in hibernateMethodAnalyzer.ParametersInfo)
                {
                    idx++;
                    Console.WriteLine("(" + idx + ") " + para.GetFullVariableInfo());
                }

                Console.WriteLine("\n3. Local Variables' Info: ");
                idx = 0;
                foreach (VariableInfo para in hibernateMethodAnalyzer.VariablesInfo)
                {
                    idx++;
                    Console.WriteLine("(" + idx + ") " + para.GetFullVariableInfo());
                }

                Console.WriteLine("\n4. Set Self Fields: ");
                idx = 0;
                foreach (VariableDeclaration para in hibernateMethodAnalyzer.SetSelfFields)
                {
                    idx++;
                    Console.WriteLine("(" + idx + ") " + GetVariableDeclarationInfo(para));
                }

                Console.WriteLine("\n5. Get Self Fields: ");
                idx = 0;
                foreach (VariableDeclaration para in hibernateMethodAnalyzer.GetSelfFields)
                {
                    idx++;
                    Console.WriteLine("(" + idx + ") " + GetVariableDeclarationInfo(para));
                }

                Console.WriteLine("\n6. Property Fields: ");
                idx = 0;
                foreach (VariableDeclaration para in hibernateMethodAnalyzer.PropertyFields)
                {
                    idx++;
                    Console.WriteLine("(" + idx + ") " + GetVariableDeclarationInfo(para));
                }

                Console.WriteLine("\n7. Invoked Local Methods: ");
                idx = 0;
                foreach (MethodDefinition m_local in hibernateMethodAnalyzer.InvokedLocalMethods)
                {
                    if (m_local != null)
                    {
                        idx++;
                        Console.WriteLine("(" + idx + ") " + m_local.GetFullName());
                    }
                }

                Console.WriteLine("\n8. Invoked External Methods: ");
                idx = 0;
                foreach (MethodDefinition m_external in hibernateMethodAnalyzer.InvokedExternalMethods)
                {
                    if (m_external != null)
                    {
                        idx++;
                        Console.WriteLine("(" + idx + ") " + m_external.GetFullName());
                    }
                }

                VariableInfo returnedVar = hibernateMethodAnalyzer.ReturnedVariable;
                //TypeUse returnedType = hibernateMethodAnalyzer.ReturnType;
                if (returnedVar != null)
                {
                    Console.WriteLine("\n9. Return Type: " + returnedVar.GetName() + "<" + returnedVar.GetVariableType() + ">");
                    Console.WriteLine("IsReturnNewObj: " + hibernateMethodAnalyzer.IsReturnNewObj);
                }
            }
        }

        private static string GetVariableDeclarationInfo(VariableDeclaration vd)
        {
            string info = vd.Name + "<" + vd.VariableType + ">";
            return info;
        }
    }
}
