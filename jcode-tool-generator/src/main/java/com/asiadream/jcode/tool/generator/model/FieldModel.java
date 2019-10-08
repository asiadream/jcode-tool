package com.asiadream.jcode.tool.generator.model;

import java.util.ArrayList;
import java.util.List;

public class FieldModel {
    //
    private String name;
    private Access access;
    private ClassType type;
    private boolean isStatic;
    private boolean isFinal;
    private List<AnnotationType> annotations;
    private String initializer;
    private String lineComment;

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
        this.isStatic = other.isStatic;
        this.isFinal = other.isFinal;
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

    public FieldModel addAnnotation(AnnotationType annotation) {
        //
        this.annotations.add(annotation);
        return this;
    }

    public FieldModel addAnnotation(String annotationClassName) {
        //
        addAnnotation(new AnnotationType(annotationClassName));
        return this;
    }

    public boolean hasAnnotation() {
        //
        return this.annotations != null && this.annotations.size() > 0;
    }

    public String getName() {
        return name;
    }

    public FieldModel setName(String name) {
        this.name = name;
        return this;
    }

    public Access getAccess() {
        return access;
    }

    public FieldModel setAccess(Access access) {
        this.access = access;
        return this;
    }

    public ClassType getType() {
        return type;
    }

    public FieldModel setType(ClassType type) {
        this.type = type;
        return this;
    }

    public List<AnnotationType> getAnnotations() {
        return annotations;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public FieldModel setStatic(boolean aStatic) {
        isStatic = aStatic;
        return this;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public FieldModel setFinal(boolean aFinal) {
        isFinal = aFinal;
        return this;
    }

    public String getInitializer() {
        return initializer;
    }

    public FieldModel setInitializer(String initializer) {
        this.initializer = initializer;
        return this;
    }

    public String getLineComment() {
        return lineComment;
    }

    public FieldModel setLineComment(String lineComment) {
        this.lineComment = lineComment;
        return this;
    }
}
