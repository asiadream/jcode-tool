package com.asiadream.jcode.tool.groovy.model;

import java.util.ArrayList;
import java.util.List;

public class GradleScript implements GroovyPrintable {
    //
    private String fileName;
    private List<GradleElement> elements;

    private GradleScript() {
        //
        this.elements = new ArrayList<>();
    }

    public GradleScript(String fileName) {
        //
        this();
        this.fileName = fileName;
    }

    @Override
    public String print(int level) {
        //
        StringBuffer sb = new StringBuffer();
        int size = elements.size();
        for (GradleElement e : elements) {
            sb.append(e.print());
            if (--size > 0) {
                sb.append("\n");
                if (e.getClass() == GroovyDSL.class) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        //
        return print();
    }

    public GradleScript addElement(GradleElement element) {
        //
        this.elements.add(element);
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public List<GradleElement> getElements() {
        return elements;
    }
}
