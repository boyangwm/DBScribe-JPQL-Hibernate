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
                    //cInfo += cType + "=" + cAttr.Value + ", ";
                    if (cType == "length")
                    {
                        cInfo += "Make sure the length is no more than " + cAttr.Value + ", ";
                    }
                    else if (cType == "precision" || cType == "scale")
                    {
                        cInfo += "Make sure the " + cType + " is " + cAttr.Value + ", ";
                    }
                    else if (cType == "not-null" || cType == "unique")
                    {
                        if (cAttr.Value.ToLower() == "true")
                        {
                            cInfo += "Make sure the value is " + cType + ", ";
                        }
                    }
                    else if (cType == "default")
                    {
                        cInfo += "Make sure the default value is " + cAttr.Value + ", ";
                    }
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
