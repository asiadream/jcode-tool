package com.asiadream.jcode.tool.generator.source.sql;

public class CommentStatement extends SqlStatement {
    //
    public enum CommentType {
        Table, Column
    }

    private CommentType commentType;
    private String schemaName;
    private String tableName;
    private String columnName;
    private String comment;

    public CommentStatement(CommentType commentType, String text) {
        //
        super(text);
        this.commentType = commentType;

        if (commentType == CommentType.Column) {
            this.schemaName = StatementHelper.getSchemaNameFromCommentOnColumnStatement(text);
            this.tableName = StatementHelper.getTableNameFromCommentOnColumnStatement(text);
            this.columnName = StatementHelper.getColumnNameFromCommentOnColumnStatement(text);
            this.comment = StatementHelper.getCommentFromCommentOnColumnStatement(text);
        } else if (commentType == CommentType.Table) {
            this.schemaName = StatementHelper.getSchemaNameFromCommentOnTableStatement(text);
            this.tableName = StatementHelper.getTableNameFromCommentOnTableStatement(text);
            this.comment = StatementHelper.getCommentFromCommentOnTableStatement(text);
        }
    }

    public CommentType getCommentType() {
        return commentType;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getComment() {
        return comment;
    }
}
