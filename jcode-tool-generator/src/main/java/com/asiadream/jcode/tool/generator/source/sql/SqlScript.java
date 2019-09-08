package com.asiadream.jcode.tool.generator.source.sql;

import java.util.List;

public class SqlScript {
    //
    private List<SqlStatement> statements;

    public SqlScript(String text) {
        //
        this.statements = SqlStatementBuilder.build(text);
    }

    public boolean hasStatement() {
        //
        return this.statements != null && this.statements.size() > 0;
    }

    public List<SqlStatement> getStatements() {
        return statements;
    }

    public <T extends SqlStatement> List<T> getStatements(Class<T> type) {
        //
        return SqlStatementBuilder.findStatements(this.statements, type);
    }

}
