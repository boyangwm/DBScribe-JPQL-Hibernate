﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ABB.SrcML.Data;

namespace DBScribeHibernate.DBScribeHibernate.CallGraphExtractor
{
    /// <summary>
    /// This class builds the call graph for the target Hibernate project.
    /// (Borrowed from Boyang Li)
    /// </summary>
    class CallGraph
    {
        /// <summary>call graph nodes</summary>
        private readonly List<MethodDefinition> nodes = new List<MethodDefinition>();


        /// <summary> index for each method </summary>
        private readonly Dictionary<MethodDefinition, int> index = new Dictionary<MethodDefinition, int>();


        /// <summary>size of current graph (which is nodes.Count())</summary>
        private int size = 0;


        /// <summary> callee edges </summary>
        private readonly List<HashSet<int>> _calleeEdge = new List<HashSet<int>>();


        /// <summary> caller edges </summary>
        private readonly List<HashSet<int>> _callerEdge = new List<HashSet<int>>();

        public int GetNumberOfMethods()
        {
            return size;
        }

        /// <summary>
        /// check if the graph contains the method m 
        /// </summary>
        /// <param name="m"></param>
        /// <returns>return the index of the given method in the CallGraph</returns>
        public bool ContainsMethod(MethodDefinition m)
        {
            if (m == null)
            {
                return false;
            }
            return index.ContainsKey(m);
        }


        /// <summary>
        /// add a node to the graph
        /// </summary>
        /// <param name="m"></param>
        /// <returns></returns>
        public bool AddNode(MethodDefinition m)
        {
            if (!index.ContainsKey(m))
            {
                this.nodes.Add(m);
                this.index[m] = size++;
                // add the calleeEdge and the callerEdge for this newly added method
                // _calleeEdge[index[m]] --> m's calleeEdge
                // _callerEdge[index[m]] --> m's callerEdge
                this._calleeEdge.Add(new HashSet<int>());
                this._callerEdge.Add(new HashSet<int>());
                return true;
            }
            else
            {
                return false;
            }
        }


        /// <summary>
        /// add the callee Edge
        /// </summary>
        /// <param name="m_caller"></param>
        /// <param name="m_callee"></param>
        /// <returns> true if this set did not already contain the specified element </returns>
        public bool AddCalleeEdge(MethodDefinition m_caller, MethodDefinition m_callee)
        {
            int colPos = index[m_caller]; // index for the caller in the CallGraph
            int linkedNodePos = index[m_callee]; // index for the callee in the CallGraph
            HashSet<int> hs = _calleeEdge[colPos];  // get caller's _calleeEdge set
            return hs.Add(linkedNodePos); // add the index of m's callee
        }


        /// <summary>
        /// add the callee Edge
        /// </summary>
        /// <param name="m_caller"></param>
        /// <param name="m_callee"></param>
        /// <returns> true if this set did not already contain the specified element </returns>
        public bool AddCallerEdge(MethodDefinition m_caller, MethodDefinition m_callee)
        {
            int colPos = index[m_callee];
            int linkedNodePos = index[m_caller];
            HashSet<int> hs = _callerEdge[colPos];
            return hs.Add(linkedNodePos);
        }


        /// <summary>
        /// return a set of callee
        /// </summary>
        /// <param name="m"></param>
        /// <returns></returns>
        public HashSet<MethodDefinition> ReturnCallee(MethodDefinition m)
        {
            HashSet<MethodDefinition> ret_al = new HashSet<MethodDefinition>();
            int colPos = index[m];
            HashSet<int> hs = _calleeEdge[colPos];  // get all m's _calleeEdge set
            foreach (int i in hs)
            {
                ret_al.Add(nodes[i]);
            }
            return ret_al;
        }


        /// <summary>
        /// return a set of caller
        /// </summary>
        /// <param name="m"></param>
        /// <returns></returns>
        public HashSet<MethodDefinition> ReturnCaller(MethodDefinition m)
        {
            HashSet<MethodDefinition> ret_al = new HashSet<MethodDefinition>();
            int colPos = index[m];
            HashSet<int> hs = _callerEdge[colPos];
            foreach (int i in hs)
            {
                ret_al.Add(nodes[i]);
            }
            return ret_al;
        }


        /// <summary>
        /// print the call graph
        /// </summary>
        public void CgPrint()
        {
            Console.WriteLine("all nodes");
            int i = 0;
            foreach (MethodDefinition m in nodes)
            {
                Console.WriteLine("ID {0} : {1}", i++, m.GetFullName());
            }
            Console.WriteLine("calleeEdge : ");
            for (i = 0; i < _calleeEdge.Count; i++)
            {
                Console.WriteLine(i + " --- ");

                HashSet<int> hs = _calleeEdge[i];
                foreach (int ind in hs)
                {
                    Console.WriteLine(nodes[ind].GetFullName() + ";");
                }
                Console.WriteLine("");
            }
        }
    }
}
