package com.asiadream.jcode.tool.generator.meta;

import com.asiadream.jcode.tool.generator.model.ClassType;
import com.asiadream.jcode.tool.generator.model.FieldModel;

import java.util.List;
import java.util.Optional;

public class FieldMeta {
    //
    private String name;
    private String type;
    private List<String> annotations;

    public FieldMeta() {
    }

    public FieldMeta(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public void replaceExp(ExpressionContext expressionContext) {
        //
        this.name = expressionContext.replaceExpString(name);
        this.type = expressionContext.replaceExpString(type);
    }

    public FieldModel toFieldModel() {
        //
        FieldModel model = new FieldModel(name, ClassType.newClassType(type));
        Optional.ofNullable(annotations).ifPresent(annotations -> annotations.forEach(annotation -> model.addAnnotation(annotation)));
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

    public List<String> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }
}
