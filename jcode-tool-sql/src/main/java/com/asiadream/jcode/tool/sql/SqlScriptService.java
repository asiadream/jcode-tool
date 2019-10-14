package com.asiadream.jcode.tool.sql;

import com.asiadream.jcode.tool.sql.model.SqlScript;
import com.asiadream.jcode.tool.sql.reader.SqlReader;
import com.asiadream.jcode.tool.sql.source.SqlSource;

import java.io.IOException;

public class SqlScriptService {
    //
    public SqlScript readSqlScript(String physicalScriptPath) {
        //
        SqlSource sqlSource = readSqlSourceByPhysicalPath(physicalScriptPath);
        return sqlSource.getSqlScript();
    }

    private SqlSource readSqlSourceByPhysicalPath(String physicalFilePath) {
        //
        SqlReader reader = new SqlReader();
        try {
            return reader.readPhysicalFile(physicalFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
