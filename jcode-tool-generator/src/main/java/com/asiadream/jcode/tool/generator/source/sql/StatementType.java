package com.asiadream.jcode.tool.generator.source.sql;

public enum StatementType {
    //
    CreateTable("CREATE TABLE")
    , CommentOnColumn("COMMENT ON COLUMN")
    , CommentOnTable("COMMENT ON TABLE")
    , Etc("");

    private String command;

    public static StatementType statementType(String text) {
        //
        if (text.startsWith(StatementType.CreateTable.getCommand())) {
            return StatementType.CreateTable;
        } else if (text.startsWith(StatementType.CommentOnColumn.getCommand())) {
            return StatementType.CommentOnColumn;
        } else if (text.startsWith(StatementType.CommentOnTable.getCommand())) {
            return StatementType.CommentOnTable;
        }
        return StatementType.Etc;
    }

    StatementType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
