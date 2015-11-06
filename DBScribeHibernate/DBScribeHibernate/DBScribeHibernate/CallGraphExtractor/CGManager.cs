﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ABB.SrcML.Data;

namespace DBScribeHibernate.DBScribeHibernate.CallGraphExtractor
{
    public class CGManager
    {
        /// <summary>
        /// level bound
        /// </summary>
        readonly int LEVELTHRESHOLD = 20;

        /// <summary>call graph </summary>
        CallGraph cg = new CallGraph();

        /// <summary> record the maximum call level for the method 
        /// root/main --> level 0
        /// leaf --> last level
        /// </summary>
        private Dictionary<MethodDefinition, int> method2Level;
        private Dictionary<int, List<MethodDefinition>> level2Method;

        /// <summary> A dictionary for finding a method by the signiture </summary>
        readonly Dictionary<String, MethodDefinition> methodDictionary = new Dictionary<String, MethodDefinition>();

        private List<List<MethodDefinition>> _calleeList;
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

            Console.WriteLine("build call graph");

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
                    MethodDefinition calleeM = FindMatchedMd(call);
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
        /// Find MethodDefinition matching from MethodCall
        /// </summary>
        /// <param name="mc"></param>
        /// <returns></returns>
        private MethodDefinition FindMatchedMd(MethodCall mc)
        {
            INamedEntity match = null;
            try
            {
                match = mc.FindMatches().FirstOrDefault();
            }
            catch (Exception e)
            {
                Console.WriteLine("{0}:{1}:{2}: Call Exception {3}", mc.Location.SourceFileName, mc.Location.StartingLineNumber, mc.Location.StartingColumnNumber, e);
            }
            if (null != match)
            {
                //Console.WriteLine("match : {0} ", match);
                if (match is MethodDefinition)
                {
                    //Console.WriteLine("method Definition");
                    MethodDefinition md = (MethodDefinition)match;
                    //Console.WriteLine("md full name :" + md.GetFullName());
                    return md;
                }
            }
            return null;
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

        /// <summary>
        /// Given the main function of the program, return the maximum level for each method
        /// </summary>
        /// <param name="mainMethod"></param>
        /// <returns></returns>
        public Tuple<Dictionary<MethodDefinition, int>, Dictionary<int, List<MethodDefinition>>> BuildLevelMap(String mainMethod)
        {
            MethodDefinition currentM = getMethodByFullName(mainMethod);

            method2Level = new Dictionary<MethodDefinition, int>();
            level2Method = new Dictionary<int, List<MethodDefinition>>();

            if (!cg.ContainsMethod(currentM))
            {
                return Tuple.Create(method2Level, level2Method);
            }
            FindCalleeListHelperForLevelMap(currentM, 0);

            return Tuple.Create(method2Level, level2Method);
        }


        private void FindCalleeListHelperForLevelMap(MethodDefinition m, int level)
        {
            if (method2Level.ContainsKey(m))
            {
                method2Level[m] = level;
            }
            else
            {
                method2Level.Add(m, level);
            }

            if (m == null)
            {
                //_calleeList.Add(path);
                return;
            }

            //path.Add(m);
            if (level > this.LEVELTHRESHOLD)
            {
                //_calleeList.Add(path);
                return;
            }

            HashSet<MethodDefinition> curCalleeList = cg.ReturnCallee(m);
            if (curCalleeList.Count == 0)
            {
                //_calleeList.Add(path);
                return;
            }
            foreach (MethodDefinition calleeM in curCalleeList)
            {
                FindCalleeListHelperForLevelMap(calleeM, level + 1);
            }
        }

    }
}
