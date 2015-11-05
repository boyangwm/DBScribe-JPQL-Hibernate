using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace DBScribeHibernate.DBScribeHibernate
{
    class ConfigParser
    {
        public string TargetProjPath;
        public readonly Constants.MappingFileType mappingFileType;
        private readonly string _cfgFilePath;

        public ConfigParser(string targetProjPath, string cfgFileName)
        {
            this.TargetProjPath = targetProjPath;
            this._cfgFilePath = Directory.GetFiles(this.TargetProjPath, cfgFileName, SearchOption.AllDirectories).FirstOrDefault();

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
            string mappingFilePath = Directory.GetFiles(this.TargetProjPath, mappingFileName, SearchOption.AllDirectories).FirstOrDefault();
            return mappingFilePath;
        }

        /// <summary>
        /// Get Hibernate DTD, which shows the hibernate version
        /// Current API only works for hibernate-configuration-3.0.dtd
        /// </summary>
        /// <returns></returns>
        public string GetHibernateVersion()
        {
            XDocument doc = XDocument.Load(this._cfgFilePath);
            String docTypeStr = doc.DocumentType.ToString();
            //Console.WriteLine(docTypeStr);
            char[] deli = { '"', '/' };
            string[] subs = docTypeStr.Split(deli);
            string hibernateDtd = "";
            foreach (var sub in subs)
            {
                if (sub.Contains(".dtd"))
                {
                    hibernateDtd = sub;
                }
            }
            return hibernateDtd;
        }

        /// <summary>
        /// Get ALL Mapping Files
        /// </summary>
        /// <returns></returns>
        public List<string> GetCfgFileList()
        {
            List<string> mappingFileNameList = new List<string>();

            XElement rootEle = XElement.Load(this._cfgFilePath);
            //Console.WriteLine("Root:" + rootEle.Name);

            // <mapping resource="Employee.hbm.xml" />
            // OR <mapping class="com.mkyong.stock.Stock" />
            foreach (var mappingEle in rootEle.Descendants("mapping"))
            {
                if (this.mappingFileType == Constants.MappingFileType.XMLMapping)
                {
                    string fullPath = mappingEle.Attributes("resource").SingleOrDefault().Value;
                    string[] words = fullPath.Split('/');
                    mappingFileNameList.Add(words[words.Count() - 1]);
                }
                else if (this.mappingFileType == Constants.MappingFileType.AnnotationMapping)
                {
                    string fullPath = mappingEle.Attributes("class").SingleOrDefault().Value;
                    // handle later...
                }
            }
            return mappingFileNameList;
        }

        /// <summary>
        /// Given the mapping file name,
        /// return POJO class name, and table name
        /// </summary>
        /// <param name="mappingFileName"></param>
        /// <returns></returns>
        public Tuple<string, string> GetMappingFileClassName(string mappingFileName)
        {
            XElement rootEle = XElement.Load(_GetMappingFilePath(mappingFileName));
            XElement classEle = rootEle.Elements("class").SingleOrDefault();
            string classNameFullPath = classEle.Attributes("name").SingleOrDefault().Value;
            //string[] words = classNameFullPath.Split('.');
            //string className = words[words.Count() - 1];
            string className = classNameFullPath;
            string tableName = classEle.Attributes("table").SingleOrDefault().Value;
            Tuple<string, string> name = new Tuple<string, string>(className, tableName);
            return name;
        }

        /// <summary>
        /// Given the mapping file name,
        /// return POJO class/table primary key
        /// </summary>
        /// <param name="mappingFileName"></param>
        /// <returns></returns>
        public Tuple<string, string, string> GetMappingFilePrimaryKey(string mappingFileName)
        {
            if (this.mappingFileType == Constants.MappingFileType.XMLMapping)
            {
                XElement rootEle = XElement.Load(_GetMappingFilePath(mappingFileName));
                XElement classEle = rootEle.Elements("class").SingleOrDefault();
                XElement idEle = classEle.Elements("id").SingleOrDefault();
                string classPk = idEle.Attributes("name").SingleOrDefault().Value;
                string tablePk;
                if (idEle.Attributes("column").Count() != 0)
                {
                    tablePk = idEle.Attributes("column").SingleOrDefault().Value;
                }
                else
                {
                    tablePk = idEle.Descendants("column").SingleOrDefault().Attributes("name").SingleOrDefault().Value;
                }
                string pkType;
                try
                {
                    pkType = idEle.Attributes("type").SingleOrDefault().Value;
                }
                catch (NullReferenceException e)
                {
                    pkType = "int";  // <id> tag can only be "int" type or "Integer"
                }
                
                return new Tuple<string, string, string>(classPk, tablePk, pkType);
            }
            else // if (this.mappingFileType == Constants.MappingFileType.AnnotationMapping)
            {
                // handle this later...
                return new Tuple<string, string, string>("", "", "");
            }
        }

        /// <summary>
        /// Given the mapping file name,
        /// Return all properties (excluding pk)
        /// </summary>
        /// <param name="mappingFileName"></param>
        /// <returns></returns>
        public List<Tuple<string, string, string>> GetMappingFilePropertyList(string mappingFileName)
        {
            List<Tuple<string, string, string>> propList = new List<Tuple<string, string, string>>();
            if (this.mappingFileType == Constants.MappingFileType.XMLMapping)
            {
                XElement rootEle = XElement.Load(_GetMappingFilePath(mappingFileName));
                XElement classEle = rootEle.Elements("class").SingleOrDefault();
                foreach (var subEle in classEle.Elements("property"))
                {
                    string classProp = subEle.Attributes("name").SingleOrDefault().Value;
                    //string tableProp = subEle.Attributes("column").SingleOrDefault().Value;
                    string tableProp;
                    if (subEle.Attributes("column").Count() != 0)
                    {
                        tableProp = subEle.Attributes("column").SingleOrDefault().Value;
                    }
                    else
                    {
                        tableProp = subEle.Descendants("column").SingleOrDefault().Attributes("name").SingleOrDefault().Value;
                    }
                    string propType = subEle.Attributes("type").SingleOrDefault().Value;
                    Tuple<string, string, string> prop = new Tuple<string, string, string>(classProp, tableProp, propType);
                    propList.Add(prop);
                }
            }
            else // if (this.mappingFileType == Constants.MappingFileType.AnnotationMapping)
            {
                // handle this later...
            }
            return propList;
        }

    }
}
