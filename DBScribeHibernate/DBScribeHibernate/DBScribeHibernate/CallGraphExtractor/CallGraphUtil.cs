﻿using ABB.SrcML.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.CallGraphExtractor
{
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
    }
}