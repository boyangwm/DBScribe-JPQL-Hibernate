﻿using System;
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
    class AnnotationMappingParser : MappingParser
    {
        string prefix = "{http://www.sdml.info/srcML/src}";

        private List<string> mappingPOJOClasses;
        private Dictionary<string, string> mappingFileList;
        //private Dictionary<string, string> mappingFileNameToClassFullName;

        private Dictionary<string, string> classFullNameToTableName;
        /// <summary>
        /// FullClassName.Property --> TableName.Attribute
        /// </summary>
        private Dictionary<string, string> classPropertyToTableColumn;
        private Dictionary<string, List<string>> tableNameToTableConstraints;

        public static readonly HashSet<string> DBConstraintType_Annotation = DBConstraintExtractorAnnotation.AnnontationSet;
            

        public AnnotationMappingParser(string targetProjPath, string cfgFileName) : base(targetProjPath, cfgFileName)
        {
            Util.Utility.CreateDirectoryIfNotExist(Constants.SrcmlOutput);

            _GetMappingClasses();
            _GetMappingClassFilePath();

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
                        DBConstraintExtractorAnnotation.GetConstraintInfoFromOtherAnnotation(TableName, annotationEle, annotationType);
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

        private void _GetClassPropertyToTableColumnOneByOne(string mappingClass, string mappingFilePath)
        {
            string TableName = classFullNameToTableName[mappingClass];
            string ClassFullName = mappingClass;
            Console.WriteLine("~~~ " + ClassFullName + " <--> " + TableName);

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
            mappingPOJOClasses = new List<string>();
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

        private void _GetMappingClassFilePath()
        {
            mappingFileList = new Dictionary<string, string>();
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

        public override Constants.MappingFileType GetMappingParserType()
        {
            return Constants.MappingFileType.AnnotationMapping;
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

        private void _WaitCounter()
        {
            int x = 0;
            for (int i = 0; i < 1000; i++)
            {
                x = i;
            }
        }
    }
}
