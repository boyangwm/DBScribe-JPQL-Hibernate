using ABB.SrcML.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.CallGraphExtractor
{
    /// <summary>
    /// This class provides some commonly used call graph related functions
    /// </summary>
    class CallGraphUtil
    {
        /// <summary>
        /// Find MethodDefinition matching from MethodCall
        /// </summary>
        /// <param name="mc"></param>
        /// <returns></returns>
        public static MethodDefinition FindMatchedMd(MethodCall mc)
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
        /// Given the CalleeList returned by FindCalleeList
        /// Return the Call Chain String
        /// </summary>
        /// <param name="paths"></param>
        /// <returns></returns>
        public static string GetCalleeListStr(List<List<MethodDefinition>> paths)
        {
            StringBuilder outputBuilder = new StringBuilder();
            foreach (List<MethodDefinition> path in paths)
            {
                foreach (MethodDefinition mc in path)
                {
                    outputBuilder.Append(mc.GetFullName() + " --> ");
                }
                outputBuilder.AppendLine("");
            }
            return outputBuilder.ToString();
        }
    }
}
