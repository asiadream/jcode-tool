package com.asiadream.jcode.tool.generator.meta;

import com.asiadream.jcode.tool.generator.model.annotation.AnnotationPair;

public class AnnotationElementMeta {
    //
    private String name;
    private String value;

    public AnnotationPair toAnnotationPair() {
        //
        return new AnnotationPair(name, value);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
