﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ABB.SrcML.Data;

namespace DBScribeHibernate.DBScribeHibernate.CallGraphExtractor
{
    /// <summary>
    /// This class gets caller and callee list for each method in the target project
    /// and generates call graph.
    /// (Borrowed from BoyangLi)
    /// </summary>
    public class CGManager
    {
        /// <summary>
        /// level bound, to avoid infinite recursive calls
        /// </summary>
        readonly int LEVELTHRESHOLD = 20;

        /// <summary>call graph </summary>
        CallGraph cg = new CallGraph();

        /// <summary> A dictionary for finding a method by the signiture </summary>
        readonly Dictionary<String, MethodDefinition> methodDictionary = new Dictionary<String, MethodDefinition>();

        /// <summary> Callee List for each method </summary>
        private List<List<MethodDefinition>> _calleeList;
        /// <summary> Caller List for each method </summary>
        private List<List<MethodDefinition>> _callerList;

        /// <summary>
        /// build the call graph based on all methods in the project
        /// add method and add caller-->callee edge
        /// </summary>
        /// <param name="methods"></param>
        public void BuildCallGraph(IEnumerable<MethodDefinition> methods)
        {
            //Do we have a method which can find a MethodDefinition by the 
            //method signiture? (Just implemented an Dictionary)
            foreach (MethodDefinition method in methods)
            {
                // method.GetFullName(): include package name+class name + function name
                methodDictionary[method.GetFullName()] = method;
                //add the method.
                cg.AddNode(method);
            }

            if (Constants.ShowLog)
            {
                Console.WriteLine("***Build call graph ...");
            }
            

            foreach (MethodDefinition method in methods)
            {
                //Console.WriteLine("method  {0}", method.GetFullName());
                var mdCalls = from statments in method.GetDescendantsAndSelf()
                              from expression in statments.GetExpressions()
                              from call in expression.GetDescendantsAndSelf<MethodCall>()
                              select call;
                //Console.WriteLine("calls count : {0}" , mdCalls.Count());
                foreach (MethodCall call in mdCalls)
                {
                    MethodDefinition calleeM = CallGraphUtil.FindMatchedMd(call);
                    if (calleeM == null) { continue; }
                    if (!cg.ContainsMethod(calleeM) || !cg.ContainsMethod(method))
                    {
                        Console.WriteLine("ERROR: Did not find the callee in the call graph");
                        throw new Exception("Did not find the callee");
                    }
                    cg.AddCalleeEdge(method, calleeM);
                    cg.AddCallerEdge(method, calleeM);
                }

            }
            //cg.CgPrint();
        }


        /// <summary>
        /// return function by the full name
        /// </summary>
        /// <param name="key">Method's Full Name</param>
        /// <returns></returns>
        public MethodDefinition getMethodByFullName(string key)
        {
            MethodDefinition returnMethod = null;
            if (!methodDictionary.TryGetValue(key, out returnMethod))
            {
                return new MethodDefinition();
            }
            else
            {
                return returnMethod;
            }
        }


        /// <summary>
        /// find all callee paths from m to all its reachable paths (bounded by a constant) 
        /// </summary>
        /// <param name="m"></param>
        /// <returns></returns>
        public List<List<MethodDefinition>> findCalleeList(MethodDefinition m)
        {
            _calleeList = new List<List<MethodDefinition>>();
            if (!cg.ContainsMethod(m))
            {
                return _calleeList;
            }
            List<MethodDefinition> path = new List<MethodDefinition>();
            FindCalleeListHelper(m, new List<MethodDefinition>(path), 0);

            return _calleeList;
        }


        /// <summary>
        /// The helper function for findCalleeList (level by level)
        /// </summary>
        /// <param name="m"></param>
        /// <param name="path"></param>
        /// <param name="level"></param>
        private void FindCalleeListHelper(MethodDefinition m, List<MethodDefinition> path, int level)
        {
            if (m == null)
            {
                _calleeList.Add(path);
                return;
            }

            path.Add(m);
            if (level > this.LEVELTHRESHOLD)
            {
                _calleeList.Add(path);
                return;
            }


            HashSet<MethodDefinition> curCalleeList = cg.ReturnCallee(m);
            if (curCalleeList.Count == 0)
            {
                _calleeList.Add(path);
                return;
            }
            foreach (MethodDefinition calleeM in curCalleeList)
            {
                FindCalleeListHelper(calleeM, new List<MethodDefinition>(path), level + 1);
            }
        }


        /// <summary>
        /// return all callee paths from m to all its reachable paths by function name. 
        /// </summary>
        /// <param name="m">Method's full name</param>
        /// <returns></returns>
        public List<List<MethodDefinition>> findCalleeListByName(String m)
        {
            MethodDefinition currentM = getMethodByFullName(m);
            return findCalleeList(currentM);
        }


        /// <summary>
        /// find all caller paths from m to all its reachable paths (bounded by a constant) 
        /// </summary>
        /// <param name="m"></param>
        /// <returns></returns>
        public List<List<MethodDefinition>> FindCallerList(MethodDefinition m)
        {
            _callerList = new List<List<MethodDefinition>>();
            if (!cg.ContainsMethod(m))
            {
                return _callerList;
            }
            List<MethodDefinition> path = new List<MethodDefinition>();
            FindCallerListHelper(m, new List<MethodDefinition>(path), 0);

            return _callerList;
        }



        /// <summary>
        /// The helper function for findCalleeList
        /// </summary>
        /// <param name="m"></param>
        /// <param name="path"></param>
        /// <param name="level"></param>
        private void FindCallerListHelper(MethodDefinition m, List<MethodDefinition> path, int level)
        {
            if (m == null)
            {
                _callerList.Add(path);
                return;
            }

            path.Add(m);
            if (level > this.LEVELTHRESHOLD)
            {
                _callerList.Add(path);
                return;
            }

            HashSet<MethodDefinition> curCallerList = cg.ReturnCaller(m);
            if (curCallerList.Count == 0)
            {
                _callerList.Add(path);
                return;
            }
            foreach (MethodDefinition callerM in curCallerList)
            {
                FindCallerListHelper(callerM, new List<MethodDefinition>(path), level + 1);
            }
        }


        /// <summary>
        /// return all callee paths from m to all its reachable paths by function name. 
        /// </summary>
        /// <param name="m"></param>
        /// <returns></returns>
        public List<List<MethodDefinition>> FindCallerListByName(String m)
        {
            MethodDefinition currentM = getMethodByFullName(m);
            return FindCallerList(currentM);
        }

        
    }
}
