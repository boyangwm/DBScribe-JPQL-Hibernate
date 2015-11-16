using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace DBScribeHibernate.DBScribeHibernate
{
    class DBConstraintExtractorXML
    {
        public static readonly string[] DBConstraintType_XML 
            = {"length", "precision", "scale", "not-null", "unique", "default"};

        public static string GetConstraintInfo(XElement ele)
        {
            string cInfo = "";
            foreach (string cType in DBConstraintType_XML)
            {
                XAttribute cAttr = ele.Attributes(cType).SingleOrDefault();
                if (cAttr != null)
                {
                    cInfo += cType + "=" + cAttr.Value + ", ";
                }
            }
            if (cInfo != "")
            {
                cInfo = cInfo.Substring(0, cInfo.Length - 2);
            }
            return cInfo;
        }
    }
}
