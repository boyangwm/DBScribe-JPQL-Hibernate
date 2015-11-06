using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ABB.SrcML.Data;
using DBScribeHibernate.DBScribeHibernate.CallGraphExtractor;

namespace DBScribeHibernate.DBScribeHibernate
{
    class InvokeCallGraphGenerator
    {
        /// <summary> Subject application location </summary>
        public string LocalProj;

        /// <summary> SrcML directory location </summary>
        public string SrcmlLoc;

        public InvokeCallGraphGenerator(string localProj, string srcmlloc)
        {
            this.LocalProj = localProj;
            this.SrcmlLoc = srcmlloc;
        }


        public void run()
        {
            //Console.Out.WriteLine("Invoke call graph generator ");

            string dataDir = @"TESTNAIVE_1.0";
            using (var project = new DataProject<CompleteWorkingSet>(dataDir, this.LocalProj, this.SrcmlLoc))
            {
                Console.WriteLine("============================");
                string unknownLogPath = Path.Combine(project.StoragePath, "unknown.log");
                DateTime start = DateTime.Now, end;
                Console.WriteLine("============================");
                using (var unknownLog = new StreamWriter(unknownLogPath))
                {
                    project.UnknownLog = unknownLog;
                    project.UpdateAsync().Wait();

                }
                end = DateTime.Now;

                NamespaceDefinition globalNamespace;
                project.WorkingSet.TryObtainReadLock(5000, out globalNamespace);
                try
                {
                    // Step 1.   Build the call graph
                    //Console.WriteLine("{0,10:N0} files", project.Data.GetFiles().Count());
                    //Console.WriteLine("{0,10:N0} namespaces", globalNamespace.GetDescendants<NamespaceDefinition>().Count());
                    //Console.WriteLine("{0,10:N0} types", globalNamespace.GetDescendants<TypeDefinition>().Count());
                    Console.WriteLine("{0,10:N0} methods", globalNamespace.GetDescendants<MethodDefinition>().Count());
                    var methods = globalNamespace.GetDescendants<MethodDefinition>();

                    CGManager cgm = new CGManager();
                    cgm.BuildCallGraph(methods);

                    GetLevelMap(Constants.getMainMethodFullName(), cgm);

                    // Step 2.   Testing
                    //TestUse1_FindCalleeList(methods, cgm);
                    //TestUse2_FindCalleeListByName("com.mkyong.StockManager.main", methods, cgm);
                    //TestUse2_FindCallerListByName("com.mkyong.stock.Stock.getStockDailyRecords", methods, cgm);
                    //TestUse3_FindCallerList(methods, cgm);

                }
                finally
                {
                    project.WorkingSet.ReleaseReadLock();
                }
            }
            Console.ReadKey();
        }


        /// <summary>
        /// Given the main method of a program, build the level map,
        /// which for each method assign a maximum level
        /// (Later description propagation pushes from maximum level to top level!)
        /// </summary>
        /// <param name="mainMethod"></param>
        /// <param name="cgm"></param>
        public Tuple<int, Dictionary<MethodDefinition, int>, Dictionary<int, HashSet<MethodDefinition>>> GetLevelMap(string mainMethod, CGManager cgm)
        {
            Tuple<int, Dictionary<MethodDefinition, int>> levelMapTuple = cgm.BuildLevelMap(mainMethod);
            int levelDepth = levelMapTuple.Item1;
            Dictionary<MethodDefinition, int> method2MaxLevel = levelMapTuple.Item2;
            Dictionary<int, HashSet<MethodDefinition>> maxLevel2Method = new Dictionary<int, HashSet<MethodDefinition>>();
            if (method2MaxLevel.Count() == 0)
            {
                return Tuple.Create(levelDepth, method2MaxLevel, maxLevel2Method);
            }

            // convert method2MaxLevel to maxLevel2Method
            foreach (KeyValuePair<MethodDefinition, int> entry in method2MaxLevel)
            {
                Console.WriteLine(entry.Key.GetFullName() + ": " + entry.Value);
                if (maxLevel2Method.ContainsKey(entry.Value))
                {
                    maxLevel2Method[entry.Value].Add(entry.Key);
                }
                else
                {
                    HashSet<MethodDefinition> temp_hs = new HashSet<MethodDefinition>();
                    temp_hs.Add(entry.Key);
                    maxLevel2Method.Add(entry.Value, temp_hs);
                }
            }

            // print methods bottom-up
            for (int i = levelDepth; i >= 0; i--)
            {
                Console.WriteLine("Level " + i + ": ");
                foreach (MethodDefinition md in maxLevel2Method[i])
                {
                    Console.WriteLine(md.GetFullName());
                }
            }
            return Tuple.Create(levelDepth, method2MaxLevel, maxLevel2Method);
        }


        public void TestUse1_FindCalleeList(IEnumerable<MethodDefinition> methods, CGManager cgm)
        {
            Console.WriteLine("======  test 1 ========= ");
            foreach (MethodDefinition m in methods)
            {
                Console.WriteLine("Method Name : {0}", m.GetFullName());
                List<List<MethodDefinition>> paths = cgm.findCalleeList(m);
                foreach (List<MethodDefinition> path in paths)
                {
                    foreach (MethodDefinition mc in path)
                    {
                        Console.Write("{0}--->", mc.GetFullName());
                    }
                    Console.WriteLine("");
                }
            }
        }

        public void TestUse2_FindCalleeListByName(string methodName, IEnumerable<MethodDefinition> methods, CGManager cgm)
        {
            Console.WriteLine("======  test 2 ========= ");
            List<List<MethodDefinition>> paths2 = cgm.findCalleeListByName(methodName);
            foreach (List<MethodDefinition> path in paths2)
            {
                foreach (MethodDefinition mc in path)
                {
                    Console.Write("{0}--->", mc.Name);
                }
                Console.WriteLine("");
            }
        }

        public void TestUse2_FindCallerListByName(string methodName, IEnumerable<MethodDefinition> methods, CGManager cgm)
        {
            List<List<MethodDefinition>> paths2 = cgm.FindCallerListByName(methodName);
            foreach (List<MethodDefinition> path in paths2)
            {
                foreach (MethodDefinition mc in path)
                {
                    Console.Write("{0}--->", mc.Name);
                }
                Console.WriteLine("");
            }
        }

        public void TestUse3_FindCallerList(IEnumerable<MethodDefinition> methods, CGManager cgm)
        {
            Console.WriteLine("======  test 3 ========= ");
            int sum = 0;
            int pathNum = 0;
            foreach (MethodDefinition m in methods)
            {
                Console.WriteLine("Method Name : {0}", m.GetFullName());
                List<List<MethodDefinition>> paths = cgm.FindCallerList(m);
                foreach (List<MethodDefinition> path in paths)
                {
                    sum += path.Count;
                    pathNum++;
                    foreach (MethodDefinition mc in path)
                    {
                        Console.Write("{0}<---", mc.GetFullName());
                    }
                    Console.WriteLine("");
                }
            }
            Console.WriteLine("average level : " + (double)sum / pathNum);
        }

    }
}
