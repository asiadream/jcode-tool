package com.asiadream.jcode.tool.groovy.model;

import java.util.List;

public class GroovyDSL implements GradleElement, GroovyPrintable {
    //
    private String name;
    private Closure closure;

    public GroovyDSL(String name) {
        //
        this.name = name;
        this.closure = new Closure();
    }

    public GroovyDSL(String name, Closure closure) {
        //
        this.name = name;
        this.closure = closure;
    }

    public GroovyDSL addElement(GradleElement element) {
        //
        this.closure.addElement(element);
        return this;
    }

    public GroovyDSL addAllElement(List<GradleElement> elements) {
        //
        this.closure.addAllElement(elements);
        return this;
    }

    @Override
    public String print(int level) {
        //
        StringBuffer sb = new StringBuffer();
        sb.append(blank(level))
        .append(name).append(" ")
        .append(closure.print(level));
        return sb.toString();
    }
}
