package com.asiadream.jcode.tool.generator.model;

import com.asiadream.jcode.tool.generator.model.annotation.AnnotationPair;
import com.asiadream.jcode.tool.generator.model.annotation.AnnotationValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnnotationType extends ClassType {
    //
    private List<AnnotationPair> pairs;

    public static AnnotationType copyOf(AnnotationType other) {
        //
        if (other == null) {
            return null;
        }
        return new AnnotationType(other);
    }

    public AnnotationType(String className) {
        //
        super(className);
        this.pairs = new ArrayList<>();
    }

    private AnnotationType(AnnotationType other) {
        //
        super(other);
        this.pairs = new ArrayList<>();
        Optional.ofNullable(other.pairs).ifPresent(pairs -> pairs.forEach(pair -> this.pairs.add(new AnnotationPair(pair))));
    }

    @Override
    public List<String> usingClassNames() {
        //
        List<String> classNames = super.usingClassNames();
        Optional.ofNullable(pairs).ifPresent(pairs -> pairs.forEach(pair -> classNames.addAll(pair.usingClassNames())));
        return classNames;
    }

    public void addPair(AnnotationPair pair) {
        //
        this.pairs.add(pair);
    }

    public void addPair(String method, String stringValue) {
        //
        this.pairs.add(new AnnotationPair(method, stringValue));
    }

    public void addPair(String method, String[] stringValues) {
        //
        this.pairs.add(new AnnotationPair(method, stringValues));
    }

    public void addPair(AnnotationValue annotationValue) {
        //
        this.pairs.add(new AnnotationPair(annotationValue));
    }

    public void addPair(String method, AnnotationValue annotationValue) {
        //
        this.pairs.add(new AnnotationPair(method, annotationValue));
    }

    public boolean hasPair() {
        //
        return this.pairs != null && this.pairs.size() > 0;
    }

    public boolean hasSingleValue() {
        //
        return this.pairs.size() == 1 && this.pairs.get(0).isSingleValue();
    }

    public AnnotationValue getSingleValue() {
        //
        if (!hasSingleValue()) {
            throw new RuntimeException("This Annotation is not single value. -> " + getName());
        }
        return this.pairs.get(0).getValue();
    }

    public List<AnnotationPair> getPairs() {
        return pairs;
    }

    @Override
    public String toString() {
        //
        return super.toString();
    }
}
