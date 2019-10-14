package com.asiadream.jcode.tool.java.meta;

import com.asiadream.jcode.tool.java.model.AnnotationType;

import java.util.List;
import java.util.Optional;

public class AnnotationMeta {
    //
    private String type;
    private List<AnnotationElementMeta> elements;

    public AnnotationMeta() {
        //
    }

    public AnnotationMeta(AnnotationType model) {
        //
        this.type = model.getClassName();
        // TODO : AnnotationPair -> AnnotationElementMeta
    }

    public AnnotationMeta(AnnotationMeta other) {
        //
        this.type = other.type;
        // TODO : AnnotationElementMeta copy
    }

    public void replaceExp(ExpressionContext expressionContext) {
        //
        this.type = expressionContext.replaceExpString(type);
        Optional.ofNullable(elements).ifPresent(elements -> elements.forEach(element -> element.replaceExp(expressionContext)));
    }

    public AnnotationType toAnnotationType() {
        //
        AnnotationType annotationType = new AnnotationType(type);
        Optional.ofNullable(elements).ifPresent(elements -> elements.forEach(element -> annotationType.addPair(element.toAnnotationPair())));
        return annotationType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<AnnotationElementMeta> getElements() {
        return elements;
    }

    public void setElements(List<AnnotationElementMeta> elements) {
        this.elements = elements;
    }
}
