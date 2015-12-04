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
            Util.Utility.CreateDirectoryIfNotExist(Constants.LogPath);

            DBScribeH dbScribeH = new DBScribeH(Constants.TargetProjPath, Constants.ProjName);
            dbScribeH.run();

            Console.WriteLine("\nDone. Press any key to exit...");
            Console.ReadKey();
        }

    }
}

