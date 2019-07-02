package com.asiadream.jcode.tool.generator.meta;

import com.asiadream.jcode.tool.generator.model.ClassType;
import com.asiadream.jcode.tool.generator.model.ParameterModel;
import com.asiadream.jcode.tool.share.util.string.ClassNameUtil;
import com.asiadream.jcode.tool.share.util.string.StringUtil;

public class ParameterMeta {
    //
    private String varName;
    private String type;

    public ParameterMeta replaceExp(ExpressionContext expressionContext) {
        //
        this.type = expressionContext.replaceExpString(type);
        return this;
    }

    public ParameterModel toParameterModel() {
        //
        String varName = StringUtil.isEmpty(this.varName) ? ClassNameUtil.getSimpleClassName(type) : this.varName;
        String recommendedVarName = StringUtil.getRecommendedVariableName(varName);
        return new ParameterModel(ClassType.newClassType(type), recommendedVarName);
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
