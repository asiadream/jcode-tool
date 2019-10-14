package com.asiadream.jcode.tool.sql.model;

import java.util.List;

public class CreateTableStatement extends SqlStatement {
    //
    private String schemaName;
    private String tableName;
    private String comment;
    private List<TableField> fields;

    public CreateTableStatement(String text) {
        //
        super(text);
        this.schemaName = StatementHelper.getSchemaNameFromCreateStatement(text);
        this.tableName = StatementHelper.getTableNameFromCreateStatement(text);
        this.fields = StatementHelper.getTableFieldsFromCreateStatement(text);
    }

    public TableField findField(String fieldName) {
        //
        return fields.stream()
                .filter(tableField -> fieldName.equals(tableField.getName()))
                .findAny()
                .orElse(null);
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getComment() {
        return comment;
    }

    public List<TableField> getFields() {
        return fields;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
