using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    class ConfigParser
    {
        public readonly string configFilePath;
        public readonly Constants.MappingFileType MappingFileType;
        public readonly string HibernateDTD;
        

        /// <summary>
        /// </summary>
        /// <param name="targetProjPath"></param>
        /// <param name="cfgFileName"></param>
        public ConfigParser(string targetProjPath, string cfgFileName)
        {
            // get config file (hibernate.cfg.xm) path
            this.configFilePath = Directory.GetFiles(targetProjPath, cfgFileName, SearchOption.AllDirectories).FirstOrDefault();
            if (this.configFilePath == null)
            {
                return;
            }

            //Get Hibernate DTD, which shows the hibernate version
            // Current API may only works for hibernate-configuration-3.0.dtd
            XDocument doc = XDocument.Load(this.configFilePath);
            String docTypeStr = doc.DocumentType.ToString();
            char[] deli = { '"', '/' };
            string[] subs = docTypeStr.Split(deli);
            foreach (var sub in subs)
            {
                if (sub.Contains(".dtd"))
                {
                    this.HibernateDTD = sub;
                }
            }

            // get mapping file type
            XElement mappingEleSample = doc.Descendants("mapping").FirstOrDefault();
            var attr = mappingEleSample.Attributes("resource").SingleOrDefault();
            if (attr != null)
            {
                this.MappingFileType = Constants.MappingFileType.XMLMapping;
            }
            else
            {
                var attr2 = mappingEleSample.Attributes("class").SingleOrDefault();
                if (attr2 != null)
                {
                    this.MappingFileType = Constants.MappingFileType.AnnotationMapping;
                }
                else
                {
                    this.MappingFileType = Constants.MappingFileType.OtherMapping;
                }
            }
        }

    }
}
