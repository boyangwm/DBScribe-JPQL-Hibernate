using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Antlr3.ST;
using System.IO;

namespace DBScribeHibernate.DBScribeHibernate.ReportGenerator
{
    class HomeGenerator
    {
        public string projName;
        public int num_db_methods;
        public int num_total_methods;
        public int num_sql_operating;
        public int num_local;
        public int num_delegated;
        public List<string> allMethodHeaders;
        public Dictionary<string, string> allMethodFullDescriptions;

        public HomeGenerator(String projName, int num_db_methods, int num_total_methods, 
            int num_sql_operating, int num_local, int num_delegated,
            List<string> allMethodHeaders, Dictionary<string, string> allMethodFullDescriptions)
        {
            this.projName = projName;
            this.num_db_methods = num_db_methods;
            this.num_total_methods = num_total_methods;
            this.num_sql_operating = num_sql_operating;
            this.num_local = num_local;
            this.num_delegated = num_delegated;
            this.allMethodHeaders = allMethodHeaders;
            this.allMethodFullDescriptions = allMethodFullDescriptions;
        }

        public void Generate(string path)
        {
            StringTemplateGroup group = new StringTemplateGroup("myGroup", Constants.TemplatesLoc);
            StringTemplate st = group.GetInstanceOf("Home");
            st.SetAttribute("ProjName", this.projName);
            st.SetAttribute("NUM_DB_METHODS", this.num_db_methods);
            st.SetAttribute("NUM_TOTAL_METHODS", this.num_total_methods);
            st.SetAttribute("NUM_SQL_OPERATING", this.num_sql_operating);
            st.SetAttribute("NUM_LOCAL", this.num_local);
            st.SetAttribute("NUM_DELEGATED", this.num_delegated);
            int ID = 1;
            foreach (string methodTitle in allMethodHeaders)
            {
                string methodDescription = allMethodFullDescriptions[methodTitle];

                st.SetAttribute("IDNum", ID++);
                st.SetAttribute("MethodSignature", methodTitle);
                //st.SetAttribute("SourceCode", "...");
                //st.SetAttribute("SwumDesc", "xxx");
                st.SetAttribute("MethodBodyDesc", methodDescription);
                //allMethodSigniture.Add(methodTitle);
            }
            //allMethodSigniture.Sort();
            //hyper index
            foreach (string methodTitle in allMethodHeaders)
            {
                st.SetAttribute("MethodLinkID", methodTitle);

            }


            //st.SetAttribute("Message", "hello ");
            String result = st.ToString(); // yields "int x = 0;"
            //Console.WriteLine(result);

            StreamWriter writetext = new StreamWriter(path);
            writetext.WriteLine(result);
            writetext.Close();

        }
        
    }
}
