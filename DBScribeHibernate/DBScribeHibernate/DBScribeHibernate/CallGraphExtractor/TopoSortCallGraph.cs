using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ABB.SrcML.Data;

namespace DBScribeHibernate.DBScribeHibernate.CallGraphExtractor
{
    /// <summary>
    /// Topological sort the methods in call graph(bottom-up)
    /// Each method is represented by its MethodDefinition
    /// The resulted bottom-up ordering of methods is used to propagate method description!
    /// </summary>
    class TopoSortCallGraph
    {
        private List<MethodDefinition> methods;
        private Dictionary<MethodDefinition, List<MethodDefinition>> callerToCalleeEdges; //Adjacency List (callerList)

        public TopoSortCallGraph()
        {
            methods = new List<MethodDefinition>();
            callerToCalleeEdges = new Dictionary<MethodDefinition, List<MethodDefinition>>();
        }

        public void addMethod(MethodDefinition m)
        {
            methods.Add(m);
            callerToCalleeEdges.Add(m, new List<MethodDefinition>());
        }

        /// <summary>
        /// Edge: (caller --> callee)
        /// </summary>
        /// <param name="caller"></param>
        /// <param name="callee"></param>
        public void addCallerToCalleeEdge(MethodDefinition caller, MethodDefinition callee)
        {
            callerToCalleeEdges[caller].Add(callee);
        }

        /// <summary>
        /// Topologically sort the methods in call graph
        /// </summary>
        /// <returns>Bottom-up ordering of the methods, which is later used to propagate method description!</returns>
        public List<MethodDefinition> topoSort()
        {
            Stack<MethodDefinition> topToBottom = new Stack<MethodDefinition>();

            Dictionary<MethodDefinition, bool> visited = new Dictionary<MethodDefinition, bool>();
            foreach (MethodDefinition m in methods)
            {
                visited.Add(m, false);
            }

            // call the recursive helper to store topSort
            // starting from all vertices one by one
            foreach (MethodDefinition m in methods)
            {
                if (visited[m] == false)
                {
                    topoSortHelper(m, visited, topToBottom);
                }
            }

            Stack<MethodDefinition> bottomToTop = new Stack<MethodDefinition>();
            while (topToBottom.Count() > 0)
            {
                MethodDefinition method = topToBottom.Pop();
                bottomToTop.Push(method);
            }

            return bottomToTop.ToList();
        }

        /// <summary>
        /// A recursive helper for topoSort
        /// </summary>
        /// <param name="m"></param>
        /// <param name="visited"></param>
        /// <param name="topToBottom"></param>
        private void topoSortHelper(MethodDefinition m, Dictionary<MethodDefinition, bool> visited, Stack<MethodDefinition> topToBottom)
        {
            // Mark the current method as visited
            visited[m] = true;

            List<MethodDefinition> calleeList = callerToCalleeEdges[m];
            foreach (MethodDefinition callee in calleeList)
            {
                if (!visited[callee])
                {
                    topoSortHelper(callee, visited, topToBottom);
                }
            }
            topToBottom.Push(m);
        }

        /// <summary>
        /// Print Call Graph
        /// </summary>
        public void printGraph()
        {
            foreach (MethodDefinition m in methods)
            {
                Console.Write(m + " ");
            }
            Console.WriteLine(" ");

            foreach (KeyValuePair<MethodDefinition, List<MethodDefinition>> item in callerToCalleeEdges)
            {
                MethodDefinition caller = item.Key;
                Console.Write(caller + ": ");
                foreach (MethodDefinition i in item.Value)
                {
                    Console.Write(i + " ");
                }
                Console.WriteLine(" ");
            }
        }

    }
}
