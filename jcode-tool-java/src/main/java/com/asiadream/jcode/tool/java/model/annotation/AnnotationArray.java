package com.asiadream.jcode.tool.java.model.annotation;

import java.util.*;
import java.util.stream.Collectors;

public class AnnotationArray implements AnnotationValue {
    //
    private List<AnnotationValue> values;

    public AnnotationArray(String[] array) {
        //
        this.values = Arrays.stream(array)
                .map(AnnotationString::new)
                .collect(Collectors.toList());
    }

    public AnnotationArray(AnnotationArray other) {
        //
        this.values = new ArrayList<>();
        Optional.ofNullable(other.values).ifPresent(values -> values.forEach(value -> this.values.add(value.copyOf())));
    }

    @Override
    public AnnotationValue copyOf() {
        //
        return new AnnotationArray(this);
    }

    @Override
    public List<String> usingClassNames() {
        //
        List<String> classNames = new ArrayList<>();
        Optional.ofNullable(values).ifPresent(values -> values.forEach(value -> classNames.addAll(value.usingClassNames())));
        return classNames;
    }

    public List<AnnotationValue> getValues() {
        return values;
    }
}
