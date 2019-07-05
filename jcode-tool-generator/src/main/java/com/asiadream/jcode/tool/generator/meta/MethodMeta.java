package com.asiadream.jcode.tool.generator.meta;

import com.asiadream.jcode.tool.generator.model.Access;
import com.asiadream.jcode.tool.generator.model.ClassType;
import com.asiadream.jcode.tool.generator.model.MethodModel;
import com.asiadream.jcode.tool.share.util.string.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MethodMeta {
    //
    private String name;
    private String type;
    private Access access;
    private boolean staticMethod;
    private List<ParameterMeta> parameters;
    private String body;

    public MethodMeta replaceExp(ExpressionContext expressionContext) {
        //
        this.name = expressionContext.replaceExpString(name);
        this.type = expressionContext.replaceExpString(type);
        Optional.ofNullable(parameters).ifPresent(parameters -> parameters.forEach(parameter -> parameter.replaceExp(expressionContext)));
        this.body = expressionContext.replaceExpString(body);
        return this;
    }

    public MethodModel toMethodModel() {
        //
        MethodModel methodModel = new MethodModel(name, getMethodReturnType());
        methodModel.setAccess(access);
        methodModel.setStaticMethod(staticMethod);

        Optional.ofNullable(parameters).ifPresent(parameters -> parameters.forEach(parameter ->
                methodModel.addParameterModel(parameter.toParameterModel())));

        if (body != null) {
            methodModel.body(body);
        }

        return methodModel;
    }

    private ClassType getMethodReturnType() {
        //
        if (type == null)
            return null;
        if (type.indexOf('<') > 0) {
            ClassType returnType = ClassType.newClassType(type.substring(0, type.indexOf('<')));
            String typeArgNames = StringUtil.substringBetween(type, "<", ">");
            Arrays.asList(typeArgNames.split(",")).forEach(typeArg -> returnType.addTypeArgument(typeArg));
            return returnType;
        }
        return ClassType.newClassType(type);
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

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public boolean isStaticMethod() {
        return staticMethod;
    }

    public void setStaticMethod(boolean staticMethod) {
        this.staticMethod = staticMethod;
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
