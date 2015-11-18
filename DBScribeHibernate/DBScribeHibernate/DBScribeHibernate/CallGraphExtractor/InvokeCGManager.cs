using ABB.SrcML.Data;
using DBScribeHibernate.DBScribeHibernate.Stereotype;
using System;
using System.Collections.Generic;
using System.IO;
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
            StringBuilder outputBuilder = new StringBuilder();
            int idx_method = 0;
            foreach (MethodDefinition method in methods)
            {
                //var mdCalls = from statments in method.GetDescendantsAndSelf()
                //              from expression in statments.GetExpressions()
                //              from call in expression.GetDescendantsAndSelf<MethodCall>()
                //              select call;

                idx_method += 1;
                outputBuilder.AppendLine("=== [M-" + idx_method + "] Full Name: " + method.GetFullName());
                outputBuilder.Append("Parameters: ");
                foreach (VariableDeclaration para in method.Parameters)
                {
                    outputBuilder.Append(para.Name + "<" + para.VariableType + ">, ");
                }
                outputBuilder.AppendLine("");
                outputBuilder.AppendLine("ReturnType: " + method.ReturnType);
                outputBuilder.AppendLine("IsConstructor: " + method.IsConstructor);

                TypeDefinition curClass = MethodUtil.GetDeclaringClass(method);
                if (curClass != null)
                {
                    outputBuilder.Append("DeclaringClass: " + curClass.GetFullName());
                    foreach (TypeDefinition pc in MethodUtil.GetParentClasses(curClass))
                    {
                        outputBuilder.Append(" --> " + pc.GetFullName());
                    }
                    outputBuilder.AppendLine("");
                }
                else
                {
                    outputBuilder.Append("DeclaringClass: null");
                }
                

                outputBuilder.AppendLine("Analyze each statement: ");
                int idx_stmt = 0;
                foreach (var statements in method.GetDescendantsAndSelf())
                {
                    idx_stmt += 1;
                    outputBuilder.AppendLine("[Stmt-" + idx_stmt + "] " + statements + " || " + statements.GetType());

                    int idx_expr = 0;
                    foreach (var expression in statements.GetExpressions())
                    {
                        idx_expr += 1;
                        outputBuilder.AppendLine("\t[Expr-" + idx_expr + "] " + expression + " || " + expression.GetType());

                        int idx_exprDesc = 0;
                        foreach (var exprDesc in expression.GetDescendantsAndSelf())
                        {
                            idx_exprDesc += 1;
                            outputBuilder.AppendLine("\t\t [D-" + idx_exprDesc + "] " + exprDesc + " || " + exprDesc.GetType());
                            if (exprDesc.GetType().ToString() == "ABB.SrcML.Data.MethodCall")
                            {
                                MethodDefinition mDef = CallGraphUtil.FindMatchedMd((MethodCall)exprDesc);
                                if (mDef != null)
                                {
                                    outputBuilder.AppendLine("\t\t\t*Found MethodDefinition: " + mDef.GetFullName());
                                }
                            }
                        }
                    }
                }
                outputBuilder.AppendLine("---------------------------------------------");
                outputBuilder.AppendLine("");
            }

            string filePath = Constants.LogPath + "\\" + Util.Utility.GetProjectName(Constants.ProjName);
            Util.Utility.CreateDirectoryIfNotExist(filePath);
            StreamWriter writetext = new StreamWriter(filePath + "\\" +"analyze_methods_using_srcml.net.txt");
            writetext.Write(outputBuilder.ToString());
            writetext.Close();
            Console.WriteLine("Writing to file finished!");
        }

        public static void TestHowToUseMethodAnalyzer(IEnumerable<MethodDefinition> methods)
        {
            foreach (MethodDefinition m in methods)
            {
                HibernateMethodAnalyzer hibernateMethodAnalyzer = new HibernateMethodAnalyzer(m);
                if (hibernateMethodAnalyzer.IsSuccess != 0)
                {
                    Console.WriteLine(hibernateMethodAnalyzer.GetFailInfo());
                    continue;
                }

                //if (hibernateMethodAnalyzer.DeclaringClass.GetFullName() != "com.jspdev.biyesheji.Course")
                //{
                //    continue;
                //}

                Console.WriteLine("\n*** Start Analyzing method: " + m.GetFullName());
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
                if (returnedVar != null)
                {
                    Console.WriteLine("\n9. Return Type: " + returnedVar.GetName() + "<" + hibernateMethodAnalyzer.ReturnType + ">");
                    Console.WriteLine("IsReturnNewObj: " + hibernateMethodAnalyzer.IsReturnNewObj);
                }
                else if (hibernateMethodAnalyzer.ReturnType != null)
                {
                    Console.WriteLine("\n9. Return Type:  <" + hibernateMethodAnalyzer.ReturnType + ">");
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
