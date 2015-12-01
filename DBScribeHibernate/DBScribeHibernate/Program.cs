using ABB.SrcML.Data;
using ABB.SrcML;
using DBScribeHibernate.DBScribeHibernate.CallGraphExtractor;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace DBScribeHibernate.DBScribeHibernate
{
    class Program
    {


        static void Main(string[] args)
        {
            HashSet<string> HFiles = new HashSet<string>();
            string projPath = Constants.TargetProjPath + "\\" + Constants.ProjName;
            string[] pathList = Directory.GetFiles(projPath, "*.java", SearchOption.AllDirectories);
            foreach (string path in pathList)
            {
                StreamReader fdata = new StreamReader(path);
                string line;
                while ((line = fdata.ReadLine()) != null)
                {
                    if (line.Contains("Session") && !line.Contains("SessionFactory"))
                    {
                        Console.WriteLine(line);
                        HFiles.Add(path);
                    }
                }
                fdata.Close();
            }

            foreach (string hf in HFiles)
            {
                Console.WriteLine(hf);
            }
            

            //Console.ReadKey();

            //Util.Utility.CreateDirectoryIfNotExist(Constants.LogPath);

            //DBScribeH dbScribeH = new DBScribeH(Constants.TargetProjPath, Constants.ProjName);
            //dbScribeH.run();

            ////TestingPurpose.MainTestingFunction();

            Console.WriteLine("\nDone. Press any key to exit...");
            Console.ReadKey();
        }

    }
}

