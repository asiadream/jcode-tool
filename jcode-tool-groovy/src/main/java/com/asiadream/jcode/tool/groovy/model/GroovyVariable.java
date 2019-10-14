package com.asiadream.jcode.tool.groovy.model;

public class GroovyVariable implements GroovyExpression, Argument {
    //
    private String name;

    public GroovyVariable(String name) {
        //
        this.name = name;
    }

    @Override
    public String print(int level) {
        //
        return this.name;
    }
}
