package com.asiadream.jcode.tool.generator.source.sql;

import java.util.List;

public class TableField extends SqlStatement {
    //
    private String name;
    private String type;
    private int size;
    private String defaultValue;
    private boolean notNull;
    private String comment;

    public TableField(String fieldStatement) {
        //
        super(fieldStatement);
        // Field Elements Separated By Space
        List<String> fieldElements = StatementHelper.truncatesByFieldElement(fieldStatement);
        this.name = StatementHelper.getNameFromFieldElements(fieldElements);
        this.type = StatementHelper.getTypeFromFieldElements(fieldElements);
        this.size = StatementHelper.getSizeFromFieldElements(fieldElements);
        this.defaultValue = StatementHelper.getDefaultValueFromFieldElements(fieldElements);
        this.notNull = StatementHelper.getNotNullFromFieldElements(fieldElements);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
