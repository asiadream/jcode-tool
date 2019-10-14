package com.asiadream.jcode.tool.java.model.annotation;

import com.asiadream.jcode.tool.java.model.ClassType;

import java.util.List;

public class ClassTypeValue implements AnnotationValue {
    //
    private ClassType value;





    public ClassTypeValue(String className) {
        //
        this.value = ClassType.newClassType(className);
    }

    public ClassTypeValue(ClassTypeValue other) {
        //
        this.value = ClassType.copyOf(other.value);
    }

    @Override
    public AnnotationValue copyOf() {
        //
        return new ClassTypeValue(this);
    }

    @Override
    public List<String> usingClassNames() {
        //
        return value.usingClassNames();
    }

    public ClassType getValue() {
        return value;
    }
}
