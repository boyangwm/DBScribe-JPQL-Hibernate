using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DBScribeHibernate.DBScribeHibernate.ConfigParser
{
    class MappingParser
    {
        protected readonly string _targetProjPath;
        protected readonly string _cfgFilePath;

        public MappingParser(string targetProjPath, string cfgFileName)
        {
            this._targetProjPath = targetProjPath;
            this._cfgFilePath = Directory.GetFiles(this._targetProjPath, cfgFileName, SearchOption.AllDirectories).FirstOrDefault();
        }

        public virtual Constants.MappingFileType GetMappingParserType()
        {
            return Constants.MappingFileType.BaseMapping;
        }

        public virtual Dictionary<string, string> GetClassFullNameToTableName()
        {
            return new Dictionary<string, string>();
        }

        public virtual Dictionary<string, string> GetClassPropertyToTableColumn()
        {
            return new Dictionary<string, string>();
        }

        public virtual Dictionary<string, List<string>> GetTableNameToTableConstraints()
        {
            return new Dictionary<string, List<string>>();
        }

    }
}
