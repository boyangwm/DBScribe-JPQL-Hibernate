using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    class AnnotationMappingParser : MappingParser
    {
        public AnnotationMappingParser(string targetProjPath, string cfgFileName) : base(targetProjPath, cfgFileName)
        {
            Console.WriteLine("Using AnnotationMappingParser.");
        }
    }
}
