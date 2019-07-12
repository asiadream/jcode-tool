package com.asiadream.jcode.tool.generator.model.annotation;

import java.util.Collections;
import java.util.List;

public class AnnotationString implements AnnotationValue {
    //
    private String value;

    public AnnotationString(String value) {
        //
        this.value = value;
    }

    public AnnotationString(AnnotationString other) {
        //
        this.value = other.value;
    }

    @Override
    public AnnotationValue copyOf() {
        //
        return new AnnotationString(this);
    }

    @Override
    public List<String> usingClassNames() {
        //
        return Collections.emptyList();
    }

    public String getValue() {
        return value;
    }
}
