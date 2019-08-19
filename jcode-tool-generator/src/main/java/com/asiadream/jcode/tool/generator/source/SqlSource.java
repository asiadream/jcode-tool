package com.asiadream.jcode.tool.generator.source;

import com.asiadream.jcode.tool.generator.source.sql.CreateTableStatement;
import com.asiadream.jcode.tool.generator.source.sql.SqlScript;
import com.asiadream.jcode.tool.generator.source.sql.SqlStatement;
import com.asiadream.jcode.tool.share.util.file.FileReadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlSource {
    //
    private static final Logger logger = LoggerFactory.getLogger(SqlSource.class);

    private SqlScript sqlScript;

    public SqlSource(String physicalSourceFile) {
        //
        String text = FileReadUtil.readString(physicalSourceFile);
        //logger.debug(text);
        this.sqlScript = new SqlScript(text);
    }

    public SqlScript getSqlScript() {
        return sqlScript;
    }

    public CreateTableStatement findFirstCreateTableStatement() {
        //
        if (sqlScript == null || !sqlScript.hasStatement()) {
            return null;
        }

        for (SqlStatement sqlStatement : sqlScript.getStatements()) {
            if (sqlStatement instanceof CreateTableStatement) {
                return (CreateTableStatement) sqlStatement;
            }
        }
        return null;
    }
}
