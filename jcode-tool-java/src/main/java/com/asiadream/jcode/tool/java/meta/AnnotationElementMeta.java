package com.asiadream.jcode.tool.java.meta;

import com.asiadream.jcode.tool.java.model.annotation.AnnotationPair;

public class AnnotationElementMeta {
    //
    private String name;
    private String value;
    private AnnotationValueType valueType;

    public void replaceExp(ExpressionContext expressionContext) {
        //
        this.name = expressionContext.replaceExpString(name);
        this.value = expressionContext.replaceExpString(value);
    }

    public AnnotationPair toAnnotationPair() {
        //
        if (valueType == AnnotationValueType.Array) {
            String[] valueArray = parseAsArray(value);
            return new AnnotationPair(name, valueArray);
        }
        return new AnnotationPair(name, value);
    }

    private static String[] parseAsArray(String value) {
        //
        if (value.indexOf(",") > 0) {
            return value.split(",");
        } else if (value.indexOf(", ") > 0) {
            return value.split(", ");
        }
        return new String[]{value};
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

    public AnnotationValueType getValueType() {
        return valueType;
    }

    public void setValueType(AnnotationValueType valueType) {
        this.valueType = valueType;
    }
}
