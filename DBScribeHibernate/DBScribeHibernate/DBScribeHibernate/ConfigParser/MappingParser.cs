using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    /// <summary>
    /// This class is a "virtual" class. It has child classes: one for XML mapping, the other one for Annotationo mapping.
    /// </summary>
    class MappingParser
    {
        /// <summary>
        /// Target project path
        /// </summary>
        protected readonly string _targetProjPath;
        /// <summary>
        /// Hibernate Configuration File path
        /// </summary>
        protected readonly string _cfgFilePath;

        /// <summary>
        /// Constructor. Set Hibernate configuration file path.
        /// </summary>
        /// <param name="targetProjPath"></param>
        /// <param name="cfgFileName"></param>
        public MappingParser(string targetProjPath, string cfgFileName)
        {
            this._targetProjPath = targetProjPath;
            this._cfgFilePath = Directory.GetFiles(this._targetProjPath, cfgFileName, SearchOption.AllDirectories).FirstOrDefault();
        }

        /// <summary>
        /// Get mapping parse type
        /// </summary>
        /// <returns></returns>
        public virtual Constants.MappingFileType GetMappingParserType()
        {
            return Constants.MappingFileType.BaseMapping;
        }

        /// <summary>
        /// Get POJO class to db table mapping
        /// </summary>
        /// <returns></returns>
        public virtual Dictionary<string, string> GetClassFullNameToTableName()
        {
            return new Dictionary<string, string>();
        }

        /// <summary>
        /// Get class properites to table attributes mapping
        /// </summary>
        /// <returns></returns>
        public virtual Dictionary<string, string> GetClassPropertyToTableColumn()
        {
            return new Dictionary<string, string>();
        }

        /// <summary>
        /// Get database constraints for each db table
        /// </summary>
        /// <returns></returns>
        public virtual Dictionary<string, List<string>> GetTableNameToTableConstraints()
        {
            return new Dictionary<string, List<string>>();
        }

    }
}
