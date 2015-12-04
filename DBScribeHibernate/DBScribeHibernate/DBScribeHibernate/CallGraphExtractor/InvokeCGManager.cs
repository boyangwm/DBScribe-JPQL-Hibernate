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


    }
}
