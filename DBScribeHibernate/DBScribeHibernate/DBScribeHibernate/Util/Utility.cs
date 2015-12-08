using ABB.SrcML.Data;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.Util
{
    /// <summary>
    /// This class provides commonly used functions throughout the project
    /// </summary>
    class Utility
    {
        /// <summary>
        /// Create a directory if it doesn't exist
        /// </summary>
        /// <param name="path"></param>
        public static void CreateDirectoryIfNotExist(string path)
        {
            bool exists = System.IO.Directory.Exists(path);
            if (!exists)
                System.IO.Directory.CreateDirectory(path);
        }

        /// <summary>
        /// Get project name from the given path
        /// </summary>
        /// <param name="projNameFull"></param>
        /// <returns></returns>
        public static string GetProjectName(string projNameFull)
        {
            string[] cols = projNameFull.Split('\\');
            if (cols.Length == 1)
            {
                return projNameFull;
            }
            else
            {
                return cols[cols.Length - 1];
            }
        }

        /// <summary>
        /// Print dictionary information. (For testing purpose)
        /// </summary>
        /// <param name="dict"></param>
        /// <param name="showEmptyList"></param>
        public static void PrintDictionary(Dictionary<string, List<string>> dict, bool showEmptyList=true)
        {
            foreach (KeyValuePair<string, List<string>> item in dict)
            {
                if (showEmptyList == false && item.Value.Count() == 0)
                {
                    continue;
                }
                Console.Write(item.Key + " <--> ");
                foreach (string tableName in item.Value)
                {
                    Console.Write(tableName + ", ");
                }
                Console.WriteLine("");
            }
            Console.WriteLine("");
        }

        /// <summary>
        /// Print dictionary information. (For testing purpose)
        /// </summary>
        /// <param name="dict"></param>
        public static void PrintDictionary(Dictionary<string, string> dict)
        {
            foreach (KeyValuePair<string, string> item in dict)
            {
                Console.WriteLine(item.Key + " <--> " + item.Value);
            }
        }

        /// <summary>
        /// Print list information. (For testing purpose)
        /// </summary>
        /// <param name="l"></param>
        public static void PrintList(List<string> l){
            foreach (string s in l)
            {
                Console.WriteLine(s);
            }
        }

        /// <summary>
        /// Print list information. (For testing purpose)
        /// </summary>
        /// <param name="l"></param>
        public static void PrintList(List<Tuple<string, string, string, string>> l)
        {
            foreach (Tuple<string, string, string, string> t in l)
            {
                Console.WriteLine(t.Item1 + ", " + t.Item2 + ", " + t.Item3 + ", " + t.Item4);
            }
        }

        /// <summary>
        /// Print dictionary of table constraints. (For testing purpose)
        /// </summary>
        /// <param name="dict"></param>
        public static void PrintTableConstraints(Dictionary<string, List<string>> dict)
        {
            foreach (KeyValuePair<string, List<string>> item in dict)
            {
                Console.WriteLine("Table " + item.Key + ": ");
                foreach (string constraint in item.Value)
                {
                    Console.WriteLine(constraint);
                }
                Console.WriteLine("");
            }
        }

        /// <summary>
        /// Print VariableDeclaration information. (For testing purpose)
        /// </summary>
        /// <param name="vd"></param>
        /// <returns></returns>
        public static string GetVariableDeclarationInfo(VariableDeclaration vd)
        {
            string info = vd.Name + "<" + vd.VariableType + ">";
            return info;
        }
    }
}
