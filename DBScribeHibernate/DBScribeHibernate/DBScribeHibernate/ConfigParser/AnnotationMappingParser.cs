using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;
using DBScribeHibernate.DBScribeHibernate.Util;
using DBScribeHibernate.DBScribeHibernate.DBConstraintExtractor;
using System.Threading;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    /// <summary>
    /// This class inherents from MappingParser. Analyze Hibernate projects using Annotation mapping.
    /// </summary>
    class AnnotationMappingParser : MappingParser
    {
        /// <summary>
        /// If Hibernate configuration file found in the target project. 
        /// If not, need manually detect POJO class by matching "@Entity" keyword.
        /// </summary>
        private bool IfFoundHibernateCfgXml;

        /// <summary>
        /// SrcML add this prefix to all the XML element's name
        /// </summary>
        private string prefix = "{http://www.sdml.info/srcML/src}";

        /// <summary>
        /// List of all POJO classes
        /// </summary>
        private List<string> mappingPOJOClasses;
        /// <summary>
        /// Dictionary that maps mapping class to its file path
        /// </summary>
        private Dictionary<string, string> mappingFileList;

        /// <summary>
        /// Dictionary that maps POJO classes to database tables
        /// </summary>
        private Dictionary<string, string> classFullNameToTableName;
        /// <summary>
        /// FullClassName.Property --> TableName.Attribute
        /// </summary>
        private Dictionary<string, string> classPropertyToTableColumn;
        /// <summary>
        /// Dictionary that maps database table to its contraints
        /// </summary>
        private Dictionary<string, List<string>> tableNameToTableConstraints;

        /// <summary>
        /// A set of Hibernate mapping annotation keywords
        /// </summary>
        public static readonly HashSet<string> DBConstraintType_Annotation = DBConstraintExtractorAnnotation.AnnontationSet;


        /// <summary>
        /// Constructor. Analyze all information needed.
        /// </summary>
        /// <param name="targetProjPath"></param>
        /// <param name="cfgFileName"></param>
        /// <param name="ifFoundHibernateCfgXml"></param>
        public AnnotationMappingParser(string targetProjPath, string cfgFileName, bool ifFoundHibernateCfgXml)
            : base(targetProjPath, cfgFileName)
        {
            Util.Utility.CreateDirectoryIfNotExist(Constants.SrcmlOutput);
            this.IfFoundHibernateCfgXml = ifFoundHibernateCfgXml;

            mappingPOJOClasses = new List<string>();
            mappingFileList = new Dictionary<string, string>();

            if (this.IfFoundHibernateCfgXml == true)
            {
                _GetMappingClasses();
                _GetMappingClassFilePath();

                // some POJO classes might not be included in the mapping file,
                // but annotated using @Entity --> need manually add them!
                _FindAllPojoClassesWithEntityAnnotation();
            }
            else
            {
                Console.WriteLine("Need manually find all POJO class list!");
                _FindAllPojoClassesWithEntityAnnotation();
            }

            // Get classFullNameToTableName
            classFullNameToTableName = new Dictionary<string, string>();
            foreach (KeyValuePair<string, string> item in mappingFileList)
            {
                // parse one mapping class
                _GetClassFullNameToTableNameOneByOne(item.Key, item.Value);
            }

            // Get classPropertyToTableColumn
            classPropertyToTableColumn = new Dictionary<string, string>();
            foreach (KeyValuePair<string, string> item in mappingFileList)
            {
                _GetClassPropertyToTableColumnOneByOne(item.Key, item.Value);
            }

            // Get tableNameToTableConstraints
            tableNameToTableConstraints = new Dictionary<string, List<string>>();
            foreach (KeyValuePair<string, string> item in mappingFileList)
            {
                _GetTableNameToTableConstraintsnOneByOne(item.Key, item.Value);
            }
            
        }

        /// <summary>
        /// Manully detect each POJO class by the annotation keyword "@Entity" 
        /// </summary>
        private void _FindAllPojoClassesWithEntityAnnotation()
        {
            string[] pathList = Directory.GetFiles(_targetProjPath, "*.java", SearchOption.AllDirectories);
            if (pathList.Length == 0)
            {
                Console.Error.WriteLine("[Error] No Java files found!");
                Console.ReadKey();
                System.Environment.Exit(1);
            }
            foreach (string path in pathList)
            {
                string className = "";
                string packageName = "";
                bool ifPojoClass = false;

                // Read the file and display it line by line.
                StreamReader fdata = new StreamReader(path);
                string line;
                while ((line = fdata.ReadLine()) != null)
                {
                    if (line.Contains("package")) // Get Java File Package Name
                    {
                        string[] tokens = line.Split(new char[] { ' ', ';' });
                        packageName = tokens[1];

                    }
                    else if (line.Contains("@Entity")) // This means this is an POJO class for Hibernate
                    {
                        ifPojoClass = true;
                        break;
                    }
                }
                fdata.Close();
                if (ifPojoClass)
                {
                    string[] tokens = path.Split(new char[] { '.', '\\' });
                    className = tokens[tokens.Length - 2];
                    string fullClassName = className;
                    if (packageName != "")
                    {
                        fullClassName = packageName + "." + className;
                    }

                    if (!mappingPOJOClasses.Contains(fullClassName))
                    {
                        mappingPOJOClasses.Add(fullClassName);
                    }
                    if (!mappingFileList.ContainsKey(fullClassName))
                    {
                        mappingFileList.Add(fullClassName, path);
                    }
                }
            }
        }

        /// <summary>
        /// For a given POJO class, find the mapping from database table to contraints
        /// </summary>
        /// <param name="mappingClass"></param>
        /// <param name="mappingFilePath"></param>
        private void _GetTableNameToTableConstraintsnOneByOne(string mappingClass, string mappingFilePath)
        {
            string TableName = classFullNameToTableName[mappingClass];
            string ClassFullName = mappingClass;
            Dictionary<string, List<string>> constraintList = new Dictionary<string, List<string>>();

            string srcml_output_path = Constants.SrcmlOutput + mappingClass + ".xml";
            if (!File.Exists(srcml_output_path))
            {
                Src2XML.SourceFileToXml(mappingFilePath, srcml_output_path, Constants.SrcmlLoc);
            }
            XElement rootEle = XElement.Load(srcml_output_path);
            XElement classEle = rootEle.Descendants(prefix + "class").FirstOrDefault();
            foreach (XElement annotationEle in classEle.Descendants(prefix + "annotation"))
            {
                string annotationType = _GetAnnotationEleType(annotationEle);
                if (annotationType == "Column")
                {
                    Tuple<string, string> conTuple = 
                        DBConstraintExtractorAnnotation.GetConstraintInfoFromColumnAnnotation(TableName, annotationEle);
                    string columnName = conTuple.Item1;
                    string cInfo = conTuple.Item2;
                    if (cInfo != "")
                    {
                        if (!constraintList.ContainsKey(columnName))
                        {
                            constraintList.Add(columnName, new List<string>());
                        }
                        constraintList[columnName].Add(cInfo);
                    }
                }
                else if (DBConstraintType_Annotation.Contains(annotationType))
                {
                    Tuple<string, string> conTuple =
                        DBConstraintExtractorAnnotation.GetConstraintInfoFromOtherAnnotation(TableName,
                                        annotationEle, annotationType, mappingClass, classPropertyToTableColumn);
                    string columnName = conTuple.Item1;
                    string cInfo = conTuple.Item2;
                    if (cInfo != "")
                    {
                        if (!constraintList.ContainsKey(columnName))
                        {
                            constraintList.Add(columnName, new List<string>());
                        }
                        constraintList[columnName].Add(cInfo);
                    }
                }
            }

            if (!tableNameToTableConstraints.ContainsKey(TableName))
            {
                List<string> tableConstraintList = new List<string>();
                foreach (KeyValuePair<string, List<string>> item in constraintList)
                {
                    List<string> curConstraintList = item.Value;
                    string cInfo = TableName + "." + item.Key + ": " + string.Join(", ", curConstraintList);
                    tableConstraintList.Add(cInfo);
                }
                tableNameToTableConstraints.Add(TableName, tableConstraintList);
            }
        }

        /// <summary>
        /// For a given POJO class, find the mapping from class properties to table attributes
        /// </summary>
        /// <param name="mappingClass"></param>
        /// <param name="mappingFilePath"></param>
        private void _GetClassPropertyToTableColumnOneByOne(string mappingClass, string mappingFilePath)
        {
            string TableName = classFullNameToTableName[mappingClass];
            string ClassFullName = mappingClass;

            string srcml_output_path = Constants.SrcmlOutput + mappingClass + ".xml";
            if (!File.Exists(srcml_output_path))
            {
                Src2XML.SourceFileToXml(mappingFilePath, srcml_output_path, Constants.SrcmlLoc);
            }
            XElement rootEle = XElement.Load(srcml_output_path);
            IEnumerable<XElement> annotationEleList = rootEle.Descendants(prefix + "annotation");
            foreach (XElement annotationEle in annotationEleList)
            {
                String annotationType = _GetAnnotationEleType(annotationEle);
                if (annotationType == "Column")
                {
                    String columnName = "";
                    String classPropName = "";

                    XElement grandParentEle = annotationEle.Parent.Parent;
                    if (grandParentEle.Name == (prefix + "function"))
                    {
                        columnName = _GetTableColumn(grandParentEle);
                        classPropName = _GetClassPropName(grandParentEle);
                        string fullClassPropName = ClassFullName + "." + classPropName;
                        if (!classPropertyToTableColumn.ContainsKey(fullClassPropName))
                        {
                            classPropertyToTableColumn.Add(fullClassPropName, TableName + "." + columnName);
                        }
                    }
                    else if (grandParentEle.Name == (prefix + "decl"))
                    {
                        // Get class property name
                        XElement parentEle = annotationEle.Parent;
                        XElement nameEle = parentEle.ElementsAfterSelf(prefix + "name").FirstOrDefault();
                        classPropName = nameEle.Value.ToString();
                        string fullClassPropName = ClassFullName + "." + classPropName;

                        // Get column name
                        XElement argumentListEle = annotationEle.Descendants(prefix + "argument_list").FirstOrDefault();
                        foreach (XElement argumentEle in argumentListEle.Descendants(prefix + "argument"))
                        {
                            XElement exprEle = argumentListEle.Descendants(prefix + "expr").FirstOrDefault();
                            XElement exprNameEle = exprEle.Descendants(prefix + "name").FirstOrDefault();
                            if (exprNameEle.Value == "name")
                            {
                                string[] tokens = exprEle.Value.ToString().Split('"');
                                columnName = tokens[tokens.Length - 2];
                                break;
                            }
                        }
                        if (!classPropertyToTableColumn.ContainsKey(fullClassPropName))
                        {
                            classPropertyToTableColumn.Add(fullClassPropName, TableName + "." + columnName);
                        }
                    }
                }
            }
        }

        /// <summary>
        /// Given a function element in XML file, return its class property name
        /// </summary>
        /// <param name="funcEle"></param>
        /// <returns></returns>
        private string _GetClassPropName(XElement funcEle)
        {
            string classPropName = "";
            XElement returnEle = funcEle.Descendants(prefix + "return").LastOrDefault();
            XElement outerNameEle = returnEle.Descendants(prefix + "expr").FirstOrDefault()
                .Descendants(prefix + "name").FirstOrDefault();
            classPropName = outerNameEle.Value.ToString();
            if (classPropName.Contains('.'))
            {
                string[] tokens = outerNameEle.Value.ToString().Split('.');
                classPropName = tokens[tokens.Length - 1];
            }
            return classPropName;
        }

        /// <summary>
        /// Given a function element in XML file, return its table attribute name
        /// </summary>
        /// <param name="funcEle"></param>
        /// <returns></returns>
        private string _GetTableColumn(XElement funcEle)
        {
            string columnName = "";

            foreach (XElement annotationEle in funcEle.Descendants(prefix + "annotation"))
            {
                string annotationType = _GetAnnotationEleType(annotationEle);
                if (annotationType == "Column")
                {
                    XElement argumentListEle = annotationEle.Descendants(prefix + "argument_list").FirstOrDefault();
                    foreach (XElement argumentEle in argumentListEle.Descendants(prefix + "argument"))
                    {
                        XElement exprEle = argumentEle.Descendants(prefix + "expr").FirstOrDefault();
                        XElement nameEle = exprEle.Descendants(prefix + "name").FirstOrDefault();
                        if (nameEle.Value == "name")
                        {
                            string[] tokens = exprEle.Value.ToString().Split('"');
                            columnName = tokens[tokens.Length - 2];
                            break;
                        }
                    }
                }
                if (columnName != "")
                {
                    break;
                }
            }
            return columnName;
        }

        /// <summary>
        /// For a given POJO class, find the mapping from POJO class to database table name
        /// </summary>
        /// <param name="mappingClass"></param>
        /// <param name="mappingFilePath"></param>
        private void _GetClassFullNameToTableNameOneByOne(string mappingClass, string mappingFilePath)
        {
            string TableName = "";
            string ClassFullName = mappingClass;

            string srcml_output_path = Constants.SrcmlOutput + mappingClass + ".xml";
            if (!File.Exists(srcml_output_path))
            {
                Src2XML.SourceFileToXml(mappingFilePath, srcml_output_path, Constants.SrcmlLoc);

                bool ifExists = false;
                while (ifExists == false)
                {
                    Thread.Sleep(50);
                    ifExists = File.Exists(srcml_output_path);
                    Console.WriteLine(ifExists);
                }
                Thread.Sleep(1000);
            }
            
            XElement rootEle = XElement.Load(srcml_output_path);
            XElement classEle = rootEle.Descendants(prefix + "class").FirstOrDefault();
            foreach (XElement annotationEle in classEle.Descendants(prefix + "annotation"))
            {
                string annotationType = _GetAnnotationEleType(annotationEle);
                if (annotationType == "Table")
                {
                    XElement argumentListEle = annotationEle.Descendants(prefix + "argument_list").FirstOrDefault();
                    IEnumerable<XElement> argumentEleList = argumentListEle.Descendants(prefix + "argument");
                    foreach (XElement argumentEle in argumentEleList)
                    {
                        IEnumerable<XElement> exprEleList = argumentEle.Descendants(prefix + "expr");
                        foreach (XElement exprEle in exprEleList)
                        {
                            XElement nameEle = exprEle.Descendants(prefix + "name").FirstOrDefault();
                            if (nameEle.Value == "name")
                            {
                                string[] tokens = exprEle.Value.ToString().Split('"');
                                TableName = tokens[tokens.Length - 2];
                                if (!classFullNameToTableName.ContainsKey(ClassFullName))
                                {
                                    classFullNameToTableName.Add(ClassFullName, TableName);
                                }
                                break;
                            }
                        }
                        if (TableName != "")
                        {
                            break;
                        }
                    }
                }
            }
        }

        /// <summary>
        /// Given an annotation element in XML file, get its type
        /// </summary>
        /// <param name="annotationEle"></param>
        /// <returns></returns>
        private string _GetAnnotationEleType(XElement annotationEle)
        {
            XElement typeEle = annotationEle.Descendants(prefix + "name").FirstOrDefault();
            return typeEle.Value.ToString();
        }

        /// <summary>
        /// Get ALL Mapping Classes, called by constructor
        /// </summary>
        /// <returns></returns>
        private void _GetMappingClasses()
        {
            
            XElement rootEle = XElement.Load(this._cfgFilePath);
            //Console.WriteLine("Root:" + rootEle.Name);

            // <mapping resource="Employee.hbm.xml" />
            // OR <mapping class="com.mkyong.stock.Stock" />
            foreach (var mappingEle in rootEle.Descendants("mapping"))
            {
                string mc = mappingEle.Attributes("class").SingleOrDefault().Value;
                mappingPOJOClasses.Add(mc);
            }
        }

        /// <summary>
        /// Obtain each POJO class's file path
        /// </summary>
        private void _GetMappingClassFilePath()
        {
            foreach (string mappingClass in mappingPOJOClasses)
            {
                string[] tokens = mappingClass.Split('.');
                string className = tokens[tokens.Count() - 1];

                string[] potentialMappingFiles = Directory.GetFiles(this._targetProjPath, className + ".java", SearchOption.AllDirectories);
                if (potentialMappingFiles.Length == 1)
                {
                    mappingFileList.Add(mappingClass, potentialMappingFiles[0]);
                }
                else
                {
                    // find the correct mapping file by checking the package name
                    List<string> subTokens = new List<string>();
                    for (int i = 0; i < tokens.Count() - 1; i++)
                    {
                        subTokens.Add(tokens[i]);
                    }
                    string packageName = string.Join(".", subTokens);
                    foreach (string potentialMappingFile in potentialMappingFiles)
                    {
                        string srcml_output_path = Constants.SrcmlOutput + mappingClass.Replace('.', '_') + ".xml";
                        Src2XML.SourceFileToXml(potentialMappingFile, srcml_output_path, Constants.SrcmlLoc);
                        string obtainedPkgName = _GetPackageNameFromSrcmlOutput(srcml_output_path);
                        if (obtainedPkgName.Equals(packageName))
                        {
                            mappingFileList.Add(mappingClass, potentialMappingFile);
                            break;
                        }
                        else
                        {
                            File.Delete(srcml_output_path);
                        }
                    }
                }
            }
        }

        /// <summary>
        /// Get the package name of POJO class by analyzing the SrcML output XML file
        /// </summary>
        /// <param name="srcml_output_path"></param>
        /// <returns></returns>
        private string _GetPackageNameFromSrcmlOutput(string srcml_output_path)
        {
            string packageName = "";
            XElement rootEle = XElement.Load(srcml_output_path);
            XElement packageEle = null;
            foreach (XElement ele in rootEle.Descendants())
            {
                if (ele.Name.ToString().Contains("package"))
                {
                    packageEle = ele;
                    break;
                }
            }
            if (packageEle != null)
            {
                string[] tokens = packageEle.Value.ToString().Split(' ');
                string[] subtokens = tokens[1].Split(';');
                packageName = subtokens[0];
            }
            return packageName;
        }

        /// <summary>
        /// Get mapping parse type
        /// </summary>
        /// <returns></returns>
        public override Constants.MappingFileType GetMappingParserType()
        {
            return Constants.MappingFileType.AnnotationMapping;
        }

        /// <summary>
        /// Get POJO class to db table mapping
        /// </summary>
        /// <returns></returns>
        public override Dictionary<string, string> GetClassFullNameToTableName()
        {
            return classFullNameToTableName;
        }

        /// <summary>
        /// Get class properites to table attributes mapping
        /// </summary>
        /// <returns></returns>
        public override Dictionary<string, string> GetClassPropertyToTableColumn()
        {
            return classPropertyToTableColumn;
        }

        /// <summary>
        /// Get database constraints for each db table
        /// </summary>
        /// <returns></returns>
        public override Dictionary<string, List<string>> GetTableNameToTableConstraints()
        {
            return tableNameToTableConstraints;
        }
    }
}
