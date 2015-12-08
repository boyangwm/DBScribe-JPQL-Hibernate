using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Linq;

namespace DBScribeHibernate.DBScribeHibernate
{
    /// <summary>
    /// This class extracts database constraints for Hibernate projects using XML mapping.
    /// </summary>
    class DBConstraintExtractorXML
    {
        /// <summary>
        /// Array of database constraint keywords
        /// </summary>
        public static readonly string[] DBConstraintType_XML 
            = {"length", "precision", "scale", "not-null", "unique", "default"};


        /// <summary>
        /// Given an XML element (the one defined a class property), 
        /// return constraint information, if any.
        /// </summary>
        /// <param name="ele"></param>
        /// <returns></returns>
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
                        cInfo += "Make sure the decimal " + cType + " is " + cAttr.Value + ", ";
                    }
                    else if (cType == "not-null")
                    {
                        if (cAttr.Value.ToLower() == "true")
                        {
                            cInfo += "Make sure the value is not null, ";
                        }
                    }
                    else if (cType == "unique")
                    {
                        if (cAttr.Value.ToLower() == "true")
                        {
                            cInfo += "Make sure the value is unique, ";
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
