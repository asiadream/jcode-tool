package com.asiadream.jcode.tool.generator.source.gradle;

public class GroovyString implements Argument, GroovyExpression {
    //
    private String value;
    private static final String SINGLE_QUOTATION = "'";
    private static final String DOUBLE_QUOTATION = "\"";

    public GroovyString(String value) {
        //
        this.value = value;
    }

    @Override
    public String print(int level) {
        //
        if (value.contains("${") && value.contains("}")) {
            return DOUBLE_QUOTATION + value + DOUBLE_QUOTATION;
        }
        return SINGLE_QUOTATION + value + SINGLE_QUOTATION;
    }
}
