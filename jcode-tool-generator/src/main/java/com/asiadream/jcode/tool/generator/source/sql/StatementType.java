package com.asiadream.jcode.tool.generator.source.sql;

public enum StatementType {
    //
    CreateTable("CREATE TABLE"), Etc("");

    private String command;

    StatementType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
