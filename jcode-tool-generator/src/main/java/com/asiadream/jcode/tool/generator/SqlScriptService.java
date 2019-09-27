package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.generator.reader.SqlReader;
import com.asiadream.jcode.tool.generator.source.SqlSource;
import com.asiadream.jcode.tool.generator.source.sql.SqlScript;

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
