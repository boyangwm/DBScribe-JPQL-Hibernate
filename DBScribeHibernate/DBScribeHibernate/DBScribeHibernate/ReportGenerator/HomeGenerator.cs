using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Antlr3.ST;
using System.IO;

namespace DBScribeHibernate.DBScribeHibernate.ReportGenerator
{
    /// <summary>
    /// This class generates HTML-formate DBScribeHibernate report using StringTemplate. 
    /// The template (stored in /Templates/Home.st) is borrowed from Boyang Li.
    /// </summary>
    class HomeGenerator
    {
        /// <summary> Target project name </summary>
        public string projName;
        /// <summary> Number of database methods </summary>
        public int num_db_methods;
        /// <summary> Number of total methods in the target project </summary>
        public int num_total_methods;
        /// <summary> Number of SQL operating methods </summary>
        public int num_sql_operating;
        /// <summary> Number of Local SQL methods </summary>
        public int num_local;
        /// <summary> Number of Delegated SQL methods </summary>
        public int num_delegated;
        /// <summary> List of all database method headers </summary>
        public List<string> allMethodTitles;
        /// <summary> Dictionary that maps method header to method's full description, including method operations and database constraints</summary>
        public Dictionary<string, string> allMethodFullDescriptions;
        /// <summary> Dictionary that maps method header to its global method index </summary>
        public Dictionary<string, int> globalMethodHeaderToIndex;

        /// <summary>
        /// Constructor
        /// </summary>
        /// <param name="projName"></param>
        /// <param name="num_db_methods"></param>
        /// <param name="num_total_methods"></param>
        /// <param name="num_sql_operating"></param>
        /// <param name="num_local"></param>
        /// <param name="num_delegated"></param>
        /// <param name="allMethodHeaders"></param>
        /// <param name="allMethodFullDescriptions"></param>
        /// <param name="globalMethodHeaderToIndex"></param>
        public HomeGenerator(String projName, int num_db_methods, int num_total_methods, 
            int num_sql_operating, int num_local, int num_delegated,
            List<string> allMethodHeaders, Dictionary<string, string> allMethodFullDescriptions,
            Dictionary<string, int> globalMethodHeaderToIndex)
        {
            this.projName = projName;
            this.num_db_methods = num_db_methods;
            this.num_total_methods = num_total_methods;
            this.num_sql_operating = num_sql_operating;
            this.num_local = num_local;
            this.num_delegated = num_delegated;
            this.allMethodTitles = allMethodHeaders;
            this.allMethodFullDescriptions = allMethodFullDescriptions;
            this.globalMethodHeaderToIndex = globalMethodHeaderToIndex;
        }

        /// <summary>
        /// Given the output report path, generate HTML-formate report for the target project.
        /// </summary>
        /// <param name="path">Report location</param>
        public void Generate(string path)
        {
            List<string> methodFullNameAndParams = new List<string>();

            StringTemplateGroup group = new StringTemplateGroup("myGroup", Constants.TemplatesLoc);
            StringTemplate st = group.GetInstanceOf("Home");
            st.SetAttribute("ProjName", this.projName);
            st.SetAttribute("NUM_DB_METHODS", this.num_db_methods);
            st.SetAttribute("NUM_TOTAL_METHODS", this.num_total_methods);
            st.SetAttribute("NUM_SQL_OPERATING", this.num_sql_operating);
            st.SetAttribute("NUM_LOCAL", this.num_local);
            st.SetAttribute("NUM_DELEGATED", this.num_delegated);
            foreach (string methodTitle in allMethodTitles)
            {
                string[] tokens = methodTitle.Split(new string[] { "] " }, StringSplitOptions.None);
                string methodHeader = tokens[1];
                methodFullNameAndParams.Add(methodHeader);
                string methodDescription = allMethodFullDescriptions[methodTitle];

                st.SetAttribute("IDNum", globalMethodHeaderToIndex[methodHeader]);
                st.SetAttribute("MethodSignature", methodTitle);
                //st.SetAttribute("SourceCode", "...");
                //st.SetAttribute("SwumDesc", "xxx");
                st.SetAttribute("MethodBodyDesc", methodDescription);
                //allMethodSigniture.Add(methodTitle);
            }
            
            methodFullNameAndParams.Sort();
            //hyper index
            foreach (string mh in methodFullNameAndParams)
            {
                st.SetAttribute("MethodLinkID", mh);

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
