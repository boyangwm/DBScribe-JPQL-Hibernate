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

        public static void PrintDictionary(Dictionary<string, List<string>> dict)
        {
            foreach (KeyValuePair<string, List<string>> item in dict)
            {
                Console.Write(item.Key + " <--> ");
                foreach (string tableName in item.Value)
                {
                    Console.Write(tableName + ", ");
                }
                Console.WriteLine("");
            }
            Console.WriteLine("");
        }

        public static void PrintDictionary(Dictionary<string, string> dict)
        {
            foreach (KeyValuePair<string, string> item in dict)
            {
                Console.WriteLine(item.Key + " <--> " + item.Value);
            }
        }

        public static void PrintTableConstraints(Dictionary<string, List<string>> dict)
        {
            foreach (KeyValuePair<string, List<string>> item in dict)
            {
                Console.WriteLine(item.Key + ": ");
                foreach (string constraint in item.Value)
                {
                    Console.WriteLine(constraint);
                }
            }
        }

    }
}
