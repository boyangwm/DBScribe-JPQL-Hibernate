using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.Util
{
    public class Src2XML
    {
        /// <summary>
        /// Use srcML (instead of srcML.net) to convert source code to XML
        /// </summary>
        /// <param name="source"></param>
        /// <param name="xMLOutput"></param>
        /// <param name="srcmlLoc"></param>
        public static void SourceFileToXml(String source, String xMLOutput, String srcmlLoc)
        {
            String srcmlExe = srcmlLoc + "src2srcml.exe";

            //Console.WriteLine("source : " + source);
            //Console.WriteLine("output : " + xMLOutput);
            //String cmd = srcmlExe + " " + " --language=C# " + source + " -o " + xMLOutput;
            String cmd = srcmlExe + " " + " --language=Java " + source + " -o " + xMLOutput;

            Process myProcess = new Process();

            try
            {
                myProcess.StartInfo.UseShellExecute = false;
                // You can start any process, HelloWorld is a do-nothing example.
                myProcess.StartInfo.FileName = srcmlExe;
                myProcess.StartInfo.CreateNoWindow = true;
                myProcess.StartInfo.Arguments = " --language=Java " + source + " -o " + xMLOutput;
                myProcess.Start();
                // This code assumes the process you are starting will terminate itself.
                // Given that ii is started without a window so you cannot terminate it
                // on the desktop, it must terminate itself or you can do it programmatically
                // from this application using the Kill method.
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }
}
