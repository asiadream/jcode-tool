package com.asiadream.jcode.tool.generator.source.gradle;

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
        this.elements.forEach(e -> sb.append(e.print()));
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
