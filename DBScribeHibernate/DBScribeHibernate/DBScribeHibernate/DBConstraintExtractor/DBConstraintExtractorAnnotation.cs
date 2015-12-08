using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace DBScribeHibernate.DBScribeHibernate.DBConstraintExtractor
{
    /// <summary>
    /// This class extracts database constraints for Hibernate projects using Annotation mapping.
    /// </summary>
    class DBConstraintExtractorAnnotation
    {
        /// <summary> SrcML add this prefix to all the XML element's name </summary>
        public static string srcml_prefix = "{http://www.sdml.info/srcML/src}";

        /// <summary> Annotation keywords array </summary>
        public static readonly string[] AnnotationList
            = { "Length", "Max", "Min", "NotNull", "NotEmpty", "Pattern", "Patterns", "Range", "Size", 
                  "Email", "CreditCardNumber", "Digits", "EAN" };
        /// <summary> Annotation keywords set </summary>
        public static readonly HashSet<string> AnnontationSet =
            new HashSet<string>(AnnotationList);
        /// <summary> Column constraint keywords array </summary>
        public static readonly string[] ColumnConstraints
            = { "unique", "nullable", "length" };
        /// <summary> Column constraint keywords set </summary>
        public static readonly HashSet<string> ColumnConSet
            = new HashSet<string>(ColumnConstraints);

        /// <summary>
        /// Get database constraints that defined in "@Column" Annotation
        /// </summary>
        /// <param name="TableName"></param>
        /// <param name="annotationEle"></param>
        /// <returns></returns>
        public static Tuple<string, string> GetConstraintInfoFromColumnAnnotation(string TableName, XElement annotationEle)
        {
            string columnName = "";
            List<string> constraintList = new List<string>();
            XElement argumentListEle = annotationEle.Descendants(srcml_prefix + "argument_list").FirstOrDefault();
            foreach (XElement argumentEle in argumentListEle.Descendants(srcml_prefix + "argument"))
            {
                XElement exprEle = argumentEle.Descendants(srcml_prefix + "expr").FirstOrDefault();
                XElement nameEle = exprEle.Descendants(srcml_prefix + "name").FirstOrDefault();
                if (nameEle.Value == "name")
                {
                    string[] tokens = exprEle.Value.ToString().Split('"');
                    columnName = tokens[tokens.Length - 2];
                }
                else if (ColumnConSet.Contains(nameEle.Value))
                {
                    //constraintList.Add(exprEle.Value.ToString());
                    if (exprEle.Value.Contains("unique") && exprEle.Value.ToLower().Contains("true"))
                    {
                        constraintList.Add("Make sure it is unique");
                    }
                    else if (exprEle.Value.Contains("nullable") && exprEle.Value.ToLower().Contains("false"))
                    {
                        constraintList.Add("Make sure it is not null");
                    }
                    else if (exprEle.Value.Contains("length"))
                    {
                        string[] tokens = exprEle.Value.Split('=');
                        constraintList.Add("Make sure the length is not more than " + tokens[1]);
                    }
                }
            }

            if (constraintList.Count() == 0)
            {
                return Tuple.Create(columnName, "");
            }
            else
            {
                string cInfo = string.Join(", ", constraintList);
                return Tuple.Create(columnName, cInfo);
            }
        }


        /// <summary>
        /// Get database constraints that defined using all kinds of annotations, except "@Column" Annotation
        /// </summary>
        /// <param name="TableName"></param>
        /// <param name="annotationEle"></param>
        /// <param name="annotationType"></param>
        /// <param name="mappingClass"></param>
        /// <param name="classPropertyToTableColumn"></param>
        /// <returns></returns>
        public static Tuple<string, string> GetConstraintInfoFromOtherAnnotation(string TableName,
            XElement annotationEle, string annotationType, string mappingClass, Dictionary<string, string> classPropertyToTableColumn)
        {
            string columnName = "";
            string cInfo = "";
            string classPropName = "";
            XElement parentEle = annotationEle.Parent.Parent;
            if (parentEle.Name == (srcml_prefix + "decl"))
            {
                XElement typeEle = parentEle.Descendants(srcml_prefix + "type").FirstOrDefault();
                XElement nextEle = typeEle.ElementsAfterSelf(srcml_prefix + "name").FirstOrDefault();
                classPropName = nextEle.Value.ToString();
                
            }
            else if (parentEle.Name == (srcml_prefix + "function"))
            {
                classPropName = _GetClassPropName(parentEle);
            }
            string fullColName = classPropertyToTableColumn[mappingClass + "." + classPropName];
            columnName = (fullColName.Split('.'))[1];


            XElement argumetListEle = annotationEle.Descendants(srcml_prefix + "argument_list").FirstOrDefault();

            if (annotationType == "Length" || annotationType == "Range" || annotationType == "Size")
            {
                if (annotationType == "Length")
                {
                    cInfo = "Make sure the length is ";
                }
                else if (annotationType == "Range")
                {
                    cInfo = "Make sure the value is ";
                }
                else if (annotationType == "Size")
                {
                    cInfo = "Make sure the element size is ";
                }
                foreach (XElement argumentEle in argumetListEle.Descendants(srcml_prefix + "argument"))
                {
                    string no_space = Regex.Replace(argumentEle.Value, @"\s+", "");
                    string[] tokens = no_space.Split('=');
                    if (tokens[0].ToLower() == "min")
                    {
                        cInfo += "no less than " + tokens[1] + ", ";
                    }else if(tokens[0].ToLower() == "max"){
                        cInfo += "no more than " + tokens[1] + ", ";
                    }
                }
                cInfo = cInfo.Substring(0, cInfo.Length - 2);
            }
            else if (annotationType == "Max" || annotationType == "Min")
            {
                cInfo = "Make sure the value is no ";
                if (annotationType == "Max")
                {
                    cInfo += "more";
                }
                else if (annotationType == "Min")
                {
                    cInfo += "less";
                }
                cInfo += " than ";
                foreach (XElement argumentEle in argumetListEle.Descendants(srcml_prefix + "argument"))
                {
                    string no_space = Regex.Replace(argumentEle.Value, @"\s+", "");
                    string[] tokens = no_space.Split('=');
                    if (tokens[0].ToLower() == "value")
                    {
                        cInfo += tokens[1];
                    }
                }
            }
            else if (annotationType == "NotNull")
            {
                cInfo = "Make sure the value is not null";
            }
            else if (annotationType == "NotEmpty")
            {
                cInfo = "Make sure the value is not empty";
            }
            else if (annotationType == "Patterns")
            {
                cInfo = "Make sure the property match the regular expression: ";
                cInfo += argumetListEle.Value.ToString();
            }
            else if (annotationType == "Pattern")
            {
                cInfo = "Make sure the property match the regular expresion: ";
                foreach (XElement argumentEle in argumetListEle.Descendants(srcml_prefix + "argument"))
                {
                    XElement nameEle = argumentEle.Descendants(srcml_prefix + "expr").FirstOrDefault()
                        .Descendants(srcml_prefix + "name").FirstOrDefault();
                    if (nameEle.Value == "regexp")
                    {
                        string[] tokens = argumentEle.Value.Split('=');
                        cInfo += tokens[1];
                    }
                }
            }
            else if (annotationType == "Email")
            {
                cInfo = "Make sure the string is conform to the email address specification";
            }
            else if (annotationType == "CreditCardNumber")
            {
                cInfo = "Make sure the string is a well formated credit card number";
            }
            else if (annotationType == "Digits")
            {
                cInfo = "Make sure the property is a number having up to integerDigits integer digits and fractionalDigits fractional digists";
            }
            else if (annotationType == "EAN")
            {
                cInfo = "Make sure the string is a properly formated EAN or UPC-A code";
            }

            return Tuple.Create(columnName, cInfo);
        }

        /// <summary>
        /// Given a function element, return its class property name
        /// </summary>
        /// <param name="funcEle"></param>
        /// <returns></returns>
        private static string _GetClassPropName(XElement funcEle)
        {
            string classPropName = "";
            XElement returnEle = funcEle.Descendants(srcml_prefix + "return").LastOrDefault();
            XElement outerNameEle = returnEle.Descendants(srcml_prefix + "expr").FirstOrDefault()
                .Descendants(srcml_prefix + "name").FirstOrDefault();
            classPropName = outerNameEle.Value.ToString();
            if (classPropName.Contains('.'))
            {
                string[] tokens = outerNameEle.Value.ToString().Split('.');
                classPropName = tokens[tokens.Length - 1];
            }
            return classPropName;
        }
    }
}
