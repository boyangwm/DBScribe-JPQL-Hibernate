using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    class XMLMappingParser : MappingParser
    {
        private Dictionary<string, string> classFullNameToTableName;


        public XMLMappingParser(string targetProjPath, string cfgFileName) : base(targetProjPath, cfgFileName)
        {
            Console.WriteLine("Using XMLMappingParser.");

            _GetMappingFileClassName();
            
        }

        private string _GetMappingFilePath(string mappingFileName)
        {
            //string mappingFilePath = this.TargetProjPath + @"\src\" + mappingFileName;
            string mappingFilePath = Directory.GetFiles(this._targetProjPath, mappingFileName, SearchOption.AllDirectories).FirstOrDefault();
            return mappingFilePath;
        }

        /// <summary>
        /// Get ALL Mapping Files
        /// </summary>
        /// <returns></returns>
        private List<string> _GetMappingFileNameList()
        {
            List<string> mappingFileNameList = new List<string>();

            XElement rootEle = XElement.Load(this._cfgFilePath);
            //Console.WriteLine("Root:" + rootEle.Name);

            // <mapping resource="Employee.hbm.xml" />
            // OR <mapping class="com.mkyong.stock.Stock" />
            foreach (var mappingEle in rootEle.Descendants("mapping"))
            {
                string fullPath = mappingEle.Attributes("resource").SingleOrDefault().Value;
                string[] words = fullPath.Split('/');
                mappingFileNameList.Add(words[words.Count() - 1]);
            }
            return mappingFileNameList;
        }

        /// <summary>
        /// Given the mapping file name,
        /// return POJO class name, and table name
        /// </summary>
        /// <param name="mappingFileName"></param>
        /// <returns></returns>
        private void _GetMappingFileClassName()
        {
            classFullNameToTableName = new Dictionary<string, string>();

            List<string> mappingFileNameList = _GetMappingFileNameList();
            foreach (string mappingFileName in mappingFileNameList)
            {
                XElement rootEle = XElement.Load(_GetMappingFilePath(mappingFileName));
                XAttribute pkgAttr = rootEle.Attributes("package").SingleOrDefault();
                string pkgName = "";
                if (pkgAttr != null)
                {
                    pkgName = pkgAttr.Value;
                }
                XElement classEle = rootEle.Elements("class").SingleOrDefault();
                string classFullName = classEle.Attributes("name").SingleOrDefault().Value;
                //string[] words = classNameFullPath.Split('.');
                //string className = words[words.Count() - 1];
                XAttribute tableNameAttr = classEle.Attributes("table").SingleOrDefault();
                string tableName;
                if (tableNameAttr == null)
                {
                    tableName = classFullName;
                }
                else
                {
                    tableName = tableNameAttr.Value;
                }
                // add pkgName prior to className
                if (pkgName != "")
                {
                    classFullName = pkgName + "." + classFullName;
                }
                if (this.classFullNameToTableName.ContainsKey(classFullName))
                {
                    this.classFullNameToTableName[classFullName] = tableName; // this shouldn't happen! no classes with same full name
                }
                else
                {
                    this.classFullNameToTableName.Add(classFullName, tableName);
                }
                //Console.WriteLine(classFullName + " <--> " +  tableName);
            }
        }

        public override Dictionary<string, string> GetClassFullNameToTableName()
        {
            return classFullNameToTableName;
        }

        /// <summary>
        /// SQLOperatingMethods --> POJO classes's get/set functions
        /// </summary>
        public override void GetSQLOperatingMethodFullNames()
        {
            foreach (string classFullName in this.classFullNameToTableName.Keys)
            {
                string classFilePath = _GetClassFilePathByClassFullName(classFullName);
                Console.WriteLine(classFilePath);
            }
        }

        private string _GetClassFilePathByClassFullName(string classFullName)
        {
            string classFilePath = "";

            //Console.WriteLine(classFullName);
            string classFileName = classFullName.Split('.').Last() + ".java";
            //Console.WriteLine(classFileName);
            string[] classFilePaths = Directory.GetFiles(this._targetProjPath, classFileName, SearchOption.AllDirectories);
            
            if (classFilePaths.Length == 0)
            {
                Console.WriteLine("No Java file found for class " + classFileName);
            }
            else if (classFilePaths.Length == 1)
            {
                classFilePath = classFilePaths[0];

            }
            else
            {
                foreach (string cfp in classFilePaths)
                {
                    if (cfp.Contains(classFullName.Replace('.', '\\')))
                    {
                        classFilePath = cfp;
                        break;
                    }
                }
            }
            return classFilePath;
        }



        //Previously wrote functions, not sure if useful!
        
        /// <summary>
        /// Given the mapping file name,
        /// return POJO class/table primary key
        /// </summary>
        /// <param name="mappingFileName"></param>
        /// <returns></returns>
        private Tuple<string, string, string> GetMappingFilePrimaryKey(string mappingFileName)
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
            catch (NullReferenceException)
            {
                pkType = "int";  // <id> tag can only be "int" type or "Integer"
            }

            return new Tuple<string, string, string>(classPk, tablePk, pkType);
        }

        /// <summary>
        /// Given the mapping file name,
        /// Return all properties (excluding pk)
        /// </summary>
        /// <param name="mappingFileName"></param>
        /// <returns></returns>
        private List<Tuple<string, string, string>> GetMappingFilePropertyList(string mappingFileName)
        {
            List<Tuple<string, string, string>> propList = new List<Tuple<string, string, string>>();
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
            return propList;
        }

    }
}
