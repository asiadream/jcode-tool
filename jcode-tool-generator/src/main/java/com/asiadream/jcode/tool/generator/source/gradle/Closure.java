package com.asiadream.jcode.tool.generator.source.gradle;

import java.util.ArrayList;
import java.util.List;

public class Closure implements Argument {
    //
    private List<GradleElement> elements;

    public Closure() {
        //
        this.elements = new ArrayList<>();
    }

    public void addElement(GradleElement element) {
        //
        this.elements.add(element);
    }

    public void addAllElement(List<GradleElement> elements) {
        //
        this.elements.addAll(elements);
    }

    @Override
    public String print(int level) {
        //
        StringBuffer sb = new StringBuffer();
        sb.append("{\n");
        this.elements.forEach(e -> sb.append(e.print(level + 1)).append("\n"));
        sb.append(blank(level)).append("}");
        return sb.toString();
    }

}
