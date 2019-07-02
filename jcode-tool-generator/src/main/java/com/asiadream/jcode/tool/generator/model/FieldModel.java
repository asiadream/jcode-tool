package com.asiadream.jcode.tool.generator.model;

import java.util.ArrayList;
import java.util.List;

public class FieldModel {
    //
    private String name;
    private Access access;
    private ClassType type;
    private List<AnnotationType> annotations;

    private FieldModel() {
        //
        this.annotations = new ArrayList<>();
    }

    public FieldModel(String name, ClassType type) {
        //
        this();
        this.name = name;
        this.type = type;
    }

    public FieldModel(FieldModel other) {
        //
        this.name = other.name;
        this.access = other.access;
        this.type = ClassType.copyOf(other.type);
        for (AnnotationType annotationType : other.annotations) {
            this.annotations.add(AnnotationType.copyOf(annotationType));
        }
    }

    public List<String> usingClassNames() {
        //
        List<String> classNames = new ArrayList<>();
        classNames.addAll(type.usingClassNames());
        if (hasAnnotation()) {
            for (AnnotationType annotation : annotations) {
                classNames.addAll(annotation.usingClassNames());
            }
        }
        return classNames;

    }

    public void addAnnotation(AnnotationType annotation) {
        //
        this.annotations.add(annotation);
    }

    public void addAnnotation(String annotationClassName) {
        //
        addAnnotation(new AnnotationType(annotationClassName));
    }

    public boolean hasAnnotation() {
        //
        return this.annotations != null && this.annotations.size() > 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public ClassType getType() {
        return type;
    }

    public void setType(ClassType type) {
        this.type = type;
    }

    public List<AnnotationType> getAnnotations() {
        return annotations;
    }
}
