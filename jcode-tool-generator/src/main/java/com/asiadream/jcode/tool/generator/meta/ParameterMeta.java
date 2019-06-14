package com.asiadream.jcode.tool.generator.meta;

import com.asiadream.jcode.tool.generator.model.ClassType;
import com.asiadream.jcode.tool.generator.model.ParameterModel;

public class ParameterMeta {
    //
    private String varName;
    private String type;

    public ParameterModel toParameterModel() {
        //
        return new ParameterModel(ClassType.newClassType(type), varName);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
