package com.asiadream.jcode.tool.generator.meta;

import com.asiadream.jcode.tool.generator.model.ParameterModel;

public class ParameterMeta {
    //
    private String varName;
    private String type;

    public ParameterMeta() {
        //
    }

    public ParameterMeta(ParameterModel model) {
        //
        this.varName = model.getVarName();
        this.type = model.getType().toString();
    }

    public ParameterMeta replaceExp(ExpressionContext expressionContext) {
        //
        this.type = expressionContext.replaceExpString(type);
        this.varName = expressionContext.replaceExpString(varName);
        return this;
    }

    public ParameterModel toParameterModel() {
        //
        return new ParameterModel(MetaHelper.toClassType(type), MetaHelper.recommendVarName(varName, type));
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
