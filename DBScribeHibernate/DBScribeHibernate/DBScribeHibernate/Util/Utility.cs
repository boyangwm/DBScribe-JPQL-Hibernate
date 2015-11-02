using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.Util
{
    class Utility
    {
        public static void FindFileByName(string folderPath, string filter)
        {
            try
            {
                string[] dirs = Directory.GetFiles(folderPath, filter, SearchOption.AllDirectories);
                Console.WriteLine("The number of files starting with c is {0}.", dirs.Length);
                foreach (string dir in dirs)
                {
                    Console.WriteLine(dir);
                }
            }
            catch (Exception e)
            {
                Console.WriteLine("The process failed: {0}", e.ToString());
            }
        }
    }
}
