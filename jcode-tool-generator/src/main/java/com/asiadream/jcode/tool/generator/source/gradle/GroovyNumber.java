package com.asiadream.jcode.tool.generator.source.gradle;

public class GroovyNumber implements Argument, GroovyExpression {
    //
    private int value;

    public GroovyNumber(int value) {
        //
        this.value = value;
    }

    @Override
    public String print(int level) {
        //
        return String.valueOf(value);
    }
}
