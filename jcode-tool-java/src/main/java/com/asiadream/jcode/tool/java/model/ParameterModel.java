package com.asiadream.jcode.tool.java.model;

import java.util.List;

public class ParameterModel {
    //
    private ClassType type;
    private String varName;

    public ParameterModel(ClassType type, String varName) {
        //
        this.type = type;
        this.varName = varName;
    }

    public ParameterModel(FieldModel fieldModel) {
        //
        this.type = ClassType.copyOf(fieldModel.getType());
        this.varName = fieldModel.getName();
    }

    public ParameterModel(ParameterModel other) {
        //
        this.type = ClassType.copyOf(other.type);
        this.varName = other.varName;
    }

    public List<String> usingClassNames() {
        //
        return type.usingClassNames();
    }

    public ClassType getType() {
        return type;
    }

    public void setType(ClassType type) {
        this.type = type;
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }
}
