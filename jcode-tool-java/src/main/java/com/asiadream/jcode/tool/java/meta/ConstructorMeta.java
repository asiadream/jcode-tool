package com.asiadream.jcode.tool.java.meta;

import com.asiadream.jcode.tool.java.model.ConstructorModel;
import com.asiadream.jcode.tool.java.model.JavaModel;
import com.asiadream.jcode.tool.java.model.ParameterModel;
import com.asiadream.jcode.tool.share.util.string.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConstructorMeta {
    //
    private List<ParameterMeta> parameters;
    private String body;

    public ConstructorMeta replaceExp(ExpressionContext expressionContext) {
        //
        Optional.ofNullable(parameters).ifPresent(parameters -> parameters.forEach(parameter -> parameter.replaceExp(expressionContext)));
        this.body = expressionContext.replaceExpString(body);
        return this;
    }

    public ConstructorModel toConstructorModel(String simpleClassName, JavaModel javaModel) {
        //
        ConstructorModel constructorModel = new ConstructorModel(simpleClassName);

        Optional.ofNullable(parameters).ifPresent(parameters -> parameters.forEach(parameter ->
                constructorModel.addParameterModel(parameter.toParameterModel())));

        if (StringUtil.isNotEmpty(body)) {
            constructorModel.body(MetaHelper.toMultiStatements(body));
        } else {
            constructorModel.body(parameters -> {
                List<String> stmts = new ArrayList<>();
                for (ParameterModel param : parameters) {
                    stmts.add("this." + param.getVarName() + " = " + param.getVarName() + ";");
                }
                return stmts;
            });
        }

        return constructorModel;
    }

    public List<ParameterMeta> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterMeta> parameters) {
        this.parameters = parameters;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
