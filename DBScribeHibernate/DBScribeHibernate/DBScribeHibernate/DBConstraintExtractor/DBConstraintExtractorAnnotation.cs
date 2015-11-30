using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace DBScribeHibernate.DBScribeHibernate.DBConstraintExtractor
{
    class DBConstraintExtractorAnnotation
    {
        public static string srcml_prefix = "{http://www.sdml.info/srcML/src}";

        public static readonly string[] AnnotationList
            = { "Length", "Max", "Min", "NotNull", "NotEmpty", "Pattern", "Patterns", "Range", "Size", "Email", "CreditCardNumber", "Digits", "EAN" };
        public static readonly HashSet<string> AnnontationSet =
            new HashSet<string>(AnnotationList);
        public static readonly string[] ColumnConstraints
            = { "unique", "nullable", "length" };
        public static readonly HashSet<string> ColumnConSet
            = new HashSet<string>(ColumnConstraints);

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
                    constraintList.Add(exprEle.Value.ToString());
                }
            }

            if (constraintList.Count() == 0)
            {
                return Tuple.Create(columnName, "");
            }
            else
            {
                string cInfo = "(" + string.Join("), (", constraintList) + ")";
                return Tuple.Create(columnName, cInfo);
            }
        }


        public static Tuple<string, string> GetConstraintInfoFromOtherAnnotation(string TableName, XElement annotationEle, string annotationType)
        {
            string columnName = "";
            string cInfo = "";
            XElement parentEle = annotationEle.Parent;
            if (parentEle.Name == (srcml_prefix + "type"))
            {
                XElement nextEle = parentEle.ElementsAfterSelf(srcml_prefix + "name").FirstOrDefault();
                columnName = nextEle.Value.ToString();
            }
            else if (parentEle.Name == (srcml_prefix + "function"))
            {
                columnName = _GetTableColumn(parentEle, annotationType);
            }

            XElement argumetListEle = annotationEle.Descendants(srcml_prefix + "argument_list").FirstOrDefault();
            cInfo = argumetListEle.Value.ToString();
            return Tuple.Create(columnName, cInfo);
        }

        private static string _GetTableColumn(XElement funcEle, string annotationType)
        {
            string columnName = "";

            foreach (XElement annotationEle in funcEle.Descendants(srcml_prefix + "annotation"))
            {
                if (annotationType == "Column")
                {
                    XElement argumentListEle = annotationEle.Descendants(srcml_prefix + "argument_list").FirstOrDefault();
                    foreach (XElement argumentEle in argumentListEle.Descendants(srcml_prefix + "argument"))
                    {
                        XElement exprEle = argumentEle.Descendants(srcml_prefix + "expr").FirstOrDefault();
                        XElement nameEle = exprEle.Descendants(srcml_prefix + "name").FirstOrDefault();
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
    }
}
