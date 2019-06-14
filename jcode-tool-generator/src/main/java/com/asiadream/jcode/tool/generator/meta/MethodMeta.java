package com.asiadream.jcode.tool.generator.meta;

import com.asiadream.jcode.tool.generator.model.Access;
import com.asiadream.jcode.tool.generator.model.ClassType;
import com.asiadream.jcode.tool.generator.model.MethodModel;

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

    public MethodModel toMethodModel(String simpleClassName) {
        //
        MethodModel methodModel = new MethodModel(name, getMethodReturnType(simpleClassName));
        methodModel.setAccess(access);
        methodModel.setStaticMethod(staticMethod);

        Optional.ofNullable(parameters).ifPresent(parameters -> parameters.forEach(parameter ->
                methodModel.addParameterModel(parameter.toParameterModel())));

        if (body != null) {
            String replacedBody = replaceExp(body, simpleClassName);
            methodModel.body(replacedBody);
        }

        return methodModel;
    }

    private ClassType getMethodReturnType(String simpleClassName) {
        //
        if (type == null)
            return null;
        String replacedType = replaceExp(type, simpleClassName);
        return ClassType.newClassType(replacedType);
    }

    private String replaceExp(String src, String simpleClassName) {
        //
        if (src.contains("${simpleClassName}")) {
            return src.replace("${simpleClassName}", simpleClassName);
        }
        return src;
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
