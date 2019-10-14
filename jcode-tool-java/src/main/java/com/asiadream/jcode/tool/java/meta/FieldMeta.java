package com.asiadream.jcode.tool.java.meta;

import com.asiadream.jcode.tool.java.model.Access;
import com.asiadream.jcode.tool.java.model.ClassType;
import com.asiadream.jcode.tool.java.model.FieldModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FieldMeta {
    //
    private String name;
    private String type;
    private Access access;
    private boolean isStatic;
    private boolean isFinal;
    private List<AnnotationMeta> annotations;
    private String initializer;

    public FieldMeta() {
        //
        this.access = Access.PRIVATE;
    }

    public FieldMeta(String name, String type) {
        //
        this();
        this.name = name;
        this.type = type;
    }

    public FieldMeta(FieldModel model) {
        //
        this.name = model.getName();
        this.type = model.getType().toString();
        this.access = model.getAccess();
        this.isStatic = model.isStatic();
        this.isFinal = model.isFinal();
        Optional.ofNullable(model.getAnnotations()).ifPresent(annotationTypes ->
                this.annotations = annotationTypes.stream().map(AnnotationMeta::new).collect(Collectors.toList()));
        this.initializer = model.getInitializer();
    }

    public FieldMeta(FieldMeta other) {
        //
        this.name = other.name;
        this.type = other.type;
        this.access = other.access;
        this.isStatic = other.isStatic;
        this.isFinal = other.isFinal;
        Optional.ofNullable(other.annotations).ifPresent(annotationMetas -> {
            this.annotations = new ArrayList<>();
            for (AnnotationMeta annotationMeta : annotationMetas) {
                this.annotations.add(new AnnotationMeta(annotationMeta));
            }
        });
        this.initializer = other.initializer;
    }

    public void replaceExp(ExpressionContext expressionContext) {
        //
        this.name = expressionContext.replaceExpString(name);
        this.type = expressionContext.replaceExpString(type);
        this.initializer = expressionContext.replaceExpString(initializer);

        Optional.ofNullable(annotations).ifPresent(annotations -> annotations.forEach(annotation ->
                annotation.replaceExp(expressionContext)));
    }

    public FieldModel toFieldModel() {
        //
        if (this.type == null) {
            throw new IllegalArgumentException("field type is required in FieldMeta.");
        }
        FieldModel model = new FieldModel(MetaHelper.recommendVarName(name, type), ClassType.newClassType(type));
        model.setAccess(access);
        model.setStatic(isStatic);
        model.setFinal(isFinal);

        Optional.ofNullable(annotations).ifPresent(annotations -> annotations.forEach(annotation ->
                model.addAnnotation(annotation.toAnnotationType())));

        model.setInitializer(initializer);

        return model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean aFinal) {
        isFinal = aFinal;
    }

    public List<AnnotationMeta> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationMeta> annotations) {
        this.annotations = annotations;
    }

    public String getInitializer() {
        return initializer;
    }

    public void setInitializer(String initializer) {
        this.initializer = initializer;
    }
}
