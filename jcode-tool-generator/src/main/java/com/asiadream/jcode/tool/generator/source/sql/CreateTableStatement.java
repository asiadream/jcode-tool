package com.asiadream.jcode.tool.generator.source.sql;

import java.util.List;

public class CreateTableStatement extends SqlStatement {
    //
    private String schemaName;
    private String tableName;
    private List<TableField> fields;

    public CreateTableStatement(String text) {
        //
        super(text);
        this.schemaName = StatementHelper.getSchemaNameFromCreateStatement(text);
        this.tableName = StatementHelper.getTableNameFromCreateStatement(text);
        this.fields = StatementHelper.getTableFieldsFromCreateStatement(text);
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public List<TableField> getFields() {
        return fields;
    }
}
