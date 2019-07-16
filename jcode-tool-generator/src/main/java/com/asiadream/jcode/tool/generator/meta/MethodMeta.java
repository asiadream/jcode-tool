package com.asiadream.jcode.tool.generator.meta;

import com.asiadream.jcode.tool.generator.model.Access;
import com.asiadream.jcode.tool.generator.model.MethodModel;

import java.util.List;
import java.util.Optional;

public class MethodMeta {
    //
    private String name;
    private String type;
    private Access access;
    private boolean isStatic;
    private List<ParameterMeta> parameters;
    private List<AnnotationMeta> annotations;
    private String body;

    public MethodMeta replaceExp(ExpressionContext expressionContext) {
        //
        this.name = expressionContext.replaceExpString(name);
        this.type = expressionContext.replaceExpString(type);

        Optional.ofNullable(parameters).ifPresent(parameters -> parameters.forEach(parameter ->
                parameter.replaceExp(expressionContext)));

        Optional.ofNullable(annotations).ifPresent(annotations -> annotations.forEach(annotation ->
                annotation.replaceExp(expressionContext)));

        this.body = expressionContext.replaceExpString(body);
        return this;
    }

    public MethodModel toMethodModel() {
        //
        MethodModel methodModel = new MethodModel(name, MetaHelper.toClassType(type));
        methodModel.setAccess(access);
        methodModel.setStatic(isStatic);

        Optional.ofNullable(parameters).ifPresent(parameters -> parameters.forEach(parameter ->
                methodModel.addParameterModel(parameter.toParameterModel())));

        Optional.ofNullable(annotations).ifPresent(annotations -> annotations.forEach(annotation ->
                methodModel.addAnnotation(annotation.toAnnotationType())));

        if (body != null) {
            methodModel.body(MetaHelper.toMultiStatements(body));
        }

        return methodModel;
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

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    public List<ParameterMeta> getParameters() {
        return parameters;
    }

    public void setParameters(List<ParameterMeta> parameters) {
        this.parameters = parameters;
    }

    public List<AnnotationMeta> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationMeta> annotations) {
        this.annotations = annotations;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
