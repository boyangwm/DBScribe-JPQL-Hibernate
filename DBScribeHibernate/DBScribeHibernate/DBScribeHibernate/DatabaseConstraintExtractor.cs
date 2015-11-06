using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace DBScribeHibernate.DBScribeHibernate
{
    class DatabaseConstraintExtractor
    {
        public string TargetProjPath;
        public readonly Constants.MappingFileType mappingFileType;
        private readonly string _cfgFilePath;

        public DatabaseConstraintExtractor(string targetProjPath, string cfgFileName)
        {
            this.TargetProjPath = targetProjPath;
            this._cfgFilePath = Directory.GetFiles(this.TargetProjPath, cfgFileName, SearchOption.AllDirectories).SingleOrDefault();

            // get mapping file type
            XElement rootEle = XElement.Load(this._cfgFilePath);
            XElement mappingEleSample = rootEle.Descendants("mapping").FirstOrDefault();
            var attr = mappingEleSample.Attributes("resource").SingleOrDefault();
            if (attr != null)
            {
                this.mappingFileType = Constants.MappingFileType.XMLMapping;
            }
            else
            {
                this.mappingFileType = Constants.MappingFileType.AnnotationMapping;
            }
        }

        private string _GetMappingFilePath(string mappingFileName)
        {
            //string mappingFilePath = this.TargetProjPath + @"\src\" + mappingFileName;
            string mappingFilePath = Directory.GetFiles(this.TargetProjPath, mappingFileName, SearchOption.AllDirectories).SingleOrDefault();
            return mappingFilePath;
        }

        public string GetSchemaConstraintsByName(string mappingFileName, string attrName)
        {
            return "";
        }
    }
}
