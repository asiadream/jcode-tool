package com.asiadream.jcode.tool.generator.model.annotation;

import java.util.List;
import java.util.Optional;

public class AnnotationPair {
    //
    private String method;
    private AnnotationValue value;

    public AnnotationPair(AnnotationValue value) {
        //
        this.value = value;
    }

    public AnnotationPair(String method, String stringValue) {
        //
        this.method = method;
        this.value = new AnnotationString(stringValue);
    }

    public AnnotationPair(String method, String[] stringArray) {
        //
        this.method = method;
        this.value = new AnnotationArray(stringArray);
    }

    public AnnotationPair(String method, AnnotationValue value) {
        //
        this.method = method;
        this.value = value;
    }

    public AnnotationPair(AnnotationPair other) {
        //
        this.method = other.getMethod();
        this.value = Optional.ofNullable(other.getValue())
                .map(AnnotationValue::copyOf).orElse(null);
    }

    public boolean isSingleValue() {
        //
        return this.method == null;
    }

    public List<String> usingClassNames() {
        //
        return value.usingClassNames();
    }

    public String getMethod() {
        return method;
    }

    public AnnotationValue getValue() {
        return value;
    }
}
