using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;
using DBScribeHibernate.DBScribeHibernate.DescriptionTemplates;
using DBScribeHibernate.DBScribeHibernate.DBConstraintExtractor;
using DBScribeHibernate.DBScribeHibernate.Util;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    class XMLMappingParser : MappingParser
    {
        private List<string> mappingFileNameList;
        private Dictionary<string, string> mappingFileNameToClassFullName;
        private Dictionary<string, string> classFullNameToTableName;
        /// <summary>
        /// FullClassName.Property --> TableName.Attribute
        /// </summary>
        private Dictionary<string, string> classPropertyToTableColumn;
        private Dictionary<string, List<string>> tableNameToTableConstraints;


        public XMLMappingParser(string targetProjPath, string cfgFileName) : base(targetProjPath, cfgFileName)
        {
            mappingFileNameList = new List<string>();
            _GetMappingFileNameList();  // set mappingFileNameList

            mappingFileNameToClassFullName = new Dictionary<string, string>();
            classFullNameToTableName = new Dictionary<string, string>();
            classPropertyToTableColumn = new Dictionary<string, string>();
            tableNameToTableConstraints = new Dictionary<string, List<string>>();
            foreach (string mappingFileName in mappingFileNameList)
            {
                XElement rootEle = XElement.Load(_GetMappingFilePath(mappingFileName));
                // (1) get Class Full Name
                XAttribute pkgAttr = rootEle.Attributes("package").SingleOrDefault();
                string pkgName = "";
                if (pkgAttr != null)
                {
                    pkgName = pkgAttr.Value;
                }
                IEnumerable<XElement> classElements = rootEle.Elements("class");
                foreach (XElement classEle in classElements)
                {
                    Tuple<string, string> nameTuple = _GetMappingFileClassName(pkgName, classEle); // set mappingFileNameToClassFullName and classFullNameToTableName
                    string classFullName = nameTuple.Item1;
                    string tableName = nameTuple.Item2;
                    this.mappingFileNameToClassFullName.Add(mappingFileName, classFullName);
                    if (this.classFullNameToTableName.ContainsKey(classFullName))
                    {
                        //Console.WriteLine("[Error] Duplicated " + classFullName);
                    }
                    else
                    {
                        this.classFullNameToTableName.Add(classFullName, tableName);
                    }

                    // (2) get Class Primary Key: (2-1) single PK (2-2) composite PK
                    XElement idEle_singlePK = classEle.Elements("id").SingleOrDefault();
                    if (idEle_singlePK != null)
                    {
                        SinglePK pk = _GetMappingFilePrimaryKey_SinglePK(idEle_singlePK, classFullName, tableName);
                        classPropertyToTableColumn.Add(pk.ClassPK, pk.TablePK);
                        if (tableNameToTableConstraints.ContainsKey(tableName))
                        {
                            tableNameToTableConstraints[tableName].Add(SchemaConstraintsTemplates.SchemaConstraintsPK(pk));
                        }
                        else
                        {
                            List<string> constraints = new List<string>();
                            constraints.Add(SchemaConstraintsTemplates.SchemaConstraintsPK(pk));
                            tableNameToTableConstraints.Add(tableName, constraints);
                        }
                    }
                    else
                    {
                        XElement idEle_compositePK = classEle.Elements("composite-id").SingleOrDefault();
                        if (idEle_compositePK == null)
                        {
                            Console.WriteLine("[Error] " + classFullName + ": no primary key found!");
                        }
                        else
                        {
                            CompositePK pk = _GetMappingFilePrimaryKey_CompositePK(idEle_compositePK, classFullName, tableName, pkgName);
                            if (pk.CompositeClassPK != "")
                            {
                                string tablePk = "";
                                foreach (string s in pk.CompositeTablePKs)
                                {
                                    tablePk += s + ", ";
                                }
                                classPropertyToTableColumn.Add(pk.CompositeClassPK, tablePk.Substring(0, tablePk.Length - 2));
                            }
                            foreach (SinglePK spk in pk.PKList)
                            {
                                classPropertyToTableColumn.Add(spk.ClassPK, spk.TablePK);
                            }

                            if (tableNameToTableConstraints.ContainsKey(tableName))
                            {
                                tableNameToTableConstraints[tableName].Add(SchemaConstraintsTemplates.SchemaConstraintsPK(pk));
                            }
                            else
                            {
                                List<string> constraints = new List<string>();
                                constraints.Add(SchemaConstraintsTemplates.SchemaConstraintsPK(pk));
                                tableNameToTableConstraints.Add(tableName, constraints);
                            }
                        }
                    }

                    //(3) get Class properties
                    List<ClassProperty> propList = _GetMappingFilePropertyList(classEle, classFullName, tableName);
                    foreach (ClassProperty prop in propList)
                    {
                        classPropertyToTableColumn.Add(prop.ClassProp, prop.TableAttr);
                        if (tableNameToTableConstraints.ContainsKey(tableName))
                        {
                            tableNameToTableConstraints[tableName].Add(SchemaConstraintsTemplates.SchemaConstraintsClassProp(prop));
                        }
                        else
                        {
                            List<string> constraints = new List<string>();
                            constraints.Add(SchemaConstraintsTemplates.SchemaConstraintsClassProp(prop));
                            tableNameToTableConstraints.Add(tableName, constraints);
                        }
                    }
                }
            }
            
        }

        /// <summary>
        /// Get MappingParser Type
        /// </summary>
        /// <returns></returns>
        public override Constants.MappingFileType GetMappingParserType()
        {
            return Constants.MappingFileType.XMLMapping;
        }

        /// <summary>
        /// Given mapping file name, get mapping file path
        /// </summary>
        /// <param name="mappingFileName"></param>
        /// <returns></returns>
        private string _GetMappingFilePath(string mappingFileName)
        {
            //string mappingFilePath = this.TargetProjPath + @"\src\" + mappingFileName;
            string mappingFilePath = Directory.GetFiles(this._targetProjPath, mappingFileName, SearchOption.AllDirectories).FirstOrDefault();
            return mappingFilePath;
        }

        /// <summary>
        /// Get ALL Mapping File Names, called by constructor
        /// </summary>
        /// <returns></returns>
        private void _GetMappingFileNameList()
        {
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
        }

        /// <summary>
        /// Given the mapping file's classEle and parsed packageName
        /// return POJO class name, and table name
        /// </summary>
        /// <param name="pkgName"></param>
        /// <param name="classEle"></param>
        /// <returns>classFullName</returns>
        private Tuple<string, string> _GetMappingFileClassName(string pkgName, XElement classEle)
        {
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
                        
            return Tuple.Create(classFullName, tableName);
        }


        /// <summary>
        /// Only handle (2-1) single PK
        /// Given the mapping file's id Element
        /// return POJO class/table primary key
        /// </summary>
        /// <param name="idEle"></param>
        /// <param name="classFullName"></param>
        /// <param name="tableName"></param>
        /// <returns>SinglePK</returns>
        private SinglePK _GetMappingFilePrimaryKey_SinglePK(XElement idEle, string classFullName, string tableName)
        {
            string classPk = idEle.Attributes("name").SingleOrDefault().Value;

            string tablePk = classPk;  // if not specified table attribute name, use class property name
            string constraintInfo = "";
            if (idEle.Attributes("column").Count() != 0)
            {
                tablePk = idEle.Attributes("column").SingleOrDefault().Value;
                constraintInfo = DBConstraintExtractorXML.GetConstraintInfo(idEle);
            }
            else
            {
                //tablePk = idEle.Descendants("column").SingleOrDefault().Attributes("name").SingleOrDefault().Value;
                XElement colEle = idEle.Descendants("column").SingleOrDefault();
                if (colEle != null)
                {
                    XAttribute tablePkAttr = colEle.Attributes("name").SingleOrDefault();
                    if (tablePkAttr != null)
                    {
                        tablePk = tablePkAttr.Value;
                    }
                    constraintInfo = DBConstraintExtractorXML.GetConstraintInfo(colEle);
                }
            }

            string pkType;
            try
            {
                pkType = idEle.Attributes("type").SingleOrDefault().Value;
            }
            catch (NullReferenceException)
            {
                pkType = "";  // <id> tag can only be "int" type or "Integer"
            }

            string pkGenerator = "";
            XElement generatorEle = idEle.Descendants("generator").SingleOrDefault();
            if (generatorEle != null)
            {
                XAttribute generatorClassAttr = generatorEle.Attributes("class").SingleOrDefault();
                if (generatorClassAttr != null)
                {
                    pkGenerator = generatorClassAttr.Value;
                }
            }
            return new SinglePK(classFullName + "." + classPk, tableName + "." + tablePk, pkType, constraintInfo, pkGenerator);
        }

        /// <summary>
        /// Only handle (2-2) composite PK
        /// Given the mapping file's id Element
        /// return POJO class/table primary key
        /// </summary>
        /// <param name="idEle"></param>
        /// <param name="classFullName"></param>
        /// <param name="tableName"></param>
        /// <param name="pkgName"></param>
        /// <returns>CompositePK</returns>
        private CompositePK _GetMappingFilePrimaryKey_CompositePK(XElement idEle, string classFullName, string tableName, string pkgName)
        {
            string classPK_C = "";
            string classPKType = "";
            XAttribute classPK_NameAttr = idEle.Attributes("name").SingleOrDefault();
            XAttribute classPK_ClassAttr = idEle.Attributes("class").SingleOrDefault();
            if (classPK_NameAttr != null && classPK_ClassAttr != null)
            {
                classPK_C = classFullName + "." + classPK_NameAttr.Value;
                classPKType = classPK_ClassAttr.Value;
                if (pkgName != "")
                {
                    classPKType = pkgName + "." + classPKType;
                }
            }

            List<string> tablePK_C = new List<string>();
            List<SinglePK> pkList = new List<SinglePK>();
            foreach (XElement subEle in idEle.Elements())
            {
                if (subEle.Name.ToString().Contains("key-"))
                {
                    string classPkL = subEle.Attributes("name").SingleOrDefault().Value;

                    string tablePkL = classPkL;
                    string constraintInfoL = "";
                    XAttribute colAttr = subEle.Attributes("column").SingleOrDefault();
                    if (colAttr != null)
                    {
                        tablePkL = colAttr.Value;
                        constraintInfoL = DBConstraintExtractorXML.GetConstraintInfo(subEle);
                    }
                    else
                    {
                        //tablePk = subEle.Descendants("column").SingleOrDefault().Attributes("name").SingleOrDefault().Value;
                        XElement colEleL = subEle.Descendants("column").SingleOrDefault();
                        if (colEleL != null)
                        {
                            XAttribute tablePkAttr = colEleL.Attributes("name").SingleOrDefault();
                            if (tablePkAttr != null)
                            {
                                tablePkL = tablePkAttr.Value;
                            }
                            constraintInfoL = DBConstraintExtractorXML.GetConstraintInfo(colEleL);
                        }
                    }

                    string pkTypeL = "";
                    XAttribute typeAttr = subEle.Attributes("type").SingleOrDefault();
                    if (typeAttr != null)
                    {
                        pkTypeL = typeAttr.Value;
                    }

                    if (classPKType != "" && classPkL != "")
                    {
                        classPkL = classPKType + "." + classPkL;
                    }

                    if (classPKType == "" && classPkL != "")
                    {
                        classPkL = classFullName + "." + classPkL;
                    }
                    
                    pkList.Add(new SinglePK(classPkL, tableName + "." + tablePkL, pkTypeL, constraintInfoL, ""));
                    tablePK_C.Add(tableName + "." + tablePkL);
                }
            }

            CompositePK pk = new CompositePK(classPK_C, tablePK_C, classPKType, pkList);
            return pk;
        }

        /// <summary>
        /// Given the mapping file name,
        /// Return all properties (excluding pk)
        /// </summary>
        /// <param name="classEle"></param>
        /// <param name="classFullName"></param>
        /// <param name="tableName"></param>
        /// <returns></returns>
        private List<ClassProperty> _GetMappingFilePropertyList(XElement classEle, string classFullName, string tableName)
        {
            List<ClassProperty> propList = new List<ClassProperty>();
            foreach (var subEle in classEle.Elements("property"))
            {
                string classProp = subEle.Attributes("name").SingleOrDefault().Value;
                //string tableProp = subEle.Attributes("column").SingleOrDefault().Value;
                string tableProp = classProp;
                string constraitInfo = "";
                if (subEle.Attributes("column").Count() != 0)
                {
                    tableProp = subEle.Attributes("column").SingleOrDefault().Value;
                    constraitInfo = DBConstraintExtractorXML.GetConstraintInfo(subEle);
                }
                else
                {
                    //tableProp = subEle.Descendants("column").SingleOrDefault().Attributes("name").SingleOrDefault().Value;
                    XElement colEle = subEle.Descendants("column").SingleOrDefault();
                    if (colEle != null)
                    {
                        XAttribute colNameAttr = colEle.Attributes("name").SingleOrDefault();
                        if (colNameAttr != null)
                        {
                            tableProp = colNameAttr.Value;
                        }
                        constraitInfo = DBConstraintExtractorXML.GetConstraintInfo(colEle);
                    }
                }

                string propType = "";
                XAttribute propAttr = subEle.Attributes("type").SingleOrDefault();
                if (propAttr != null)
                {
                    propType = propAttr.Value;
                }

                ClassProperty prop = new ClassProperty(classFullName + "." + classProp, tableName + "." + tableProp, propType, constraitInfo);
                propList.Add(prop);
            }
            return propList;
        }

        public override Dictionary<string, string> GetClassFullNameToTableName()
        {
            return classFullNameToTableName;
        }

        public override Dictionary<string, string> GetClassPropertyToTableColumn()
        {
            return classPropertyToTableColumn;
        }

        public override Dictionary<string, List<string>> GetTableNameToTableConstraints()
        {
            return tableNameToTableConstraints;
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

        
    }
}
