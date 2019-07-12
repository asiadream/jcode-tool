package com.asiadream.jcode.tool.generator.model.annotation;

import java.util.ArrayList;
import java.util.List;

public class FieldAccessExpressionValue implements AnnotationValue {
    //
    private String className;
    private String accessTarget;
    private String accessField;

    private static final String ACCESS_IDENTIFIER = ".";

    public FieldAccessExpressionValue(String className, String accessExpression) {
        //
        this.className = className;
        int lastDotIndex = accessExpression.lastIndexOf(ACCESS_IDENTIFIER);
        if (lastDotIndex > 0) {
            this.accessTarget = accessExpression.substring(0, lastDotIndex);
            this.accessField = accessExpression.substring(lastDotIndex + 1);
        }
    }

    public FieldAccessExpressionValue(FieldAccessExpressionValue other) {
        //
        this.className = other.className;
        this.accessTarget = other.accessTarget;
        this.accessField = other.accessField;
    }

    @Override
    public AnnotationValue copyOf() {
        //
        return new FieldAccessExpressionValue(this);
    }

    @Override
    public List<String> usingClassNames() {
        //
        List<String> classNames = new ArrayList<>();
        classNames.add(className);
        return classNames;
    }

    public String getClassName() {
        return className;
    }

    public String getAccessTarget() {
        return accessTarget;
    }

    public String getAccessField() {
        return accessField;
    }
}
