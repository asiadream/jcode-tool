package com.asiadream.jcode.tool.java.meta;

import com.asiadream.jcode.tool.java.model.Access;
import com.asiadream.jcode.tool.java.model.ClassType;
import com.asiadream.jcode.tool.java.model.MethodModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MethodMeta {
    //
    private String name;
    private String type;
    private Access access;
    private boolean isStatic;
    private List<ParameterMeta> parameters;
    private List<AnnotationMeta> annotations;
    private String body;
    private List<String> imports;

    public MethodMeta() {
        //
    }

    public MethodMeta(MethodModel model) {
        // TODO : incomplete
        this.name = model.getName();
        this.type = Optional.ofNullable(model.getReturnType()).map(ClassType::toString).orElse(null);
        this.access = model.getAccess();
        this.isStatic = model.isStatic();
        Optional.ofNullable(model.getParameterModels()).ifPresent(parameterModels ->
                this.parameters = parameterModels.stream().map(ParameterMeta::new).collect(Collectors.toList()));
        Optional.ofNullable(model.getAnnotations()).ifPresent(annotationTypes ->
                this.annotations = annotationTypes.stream().map(AnnotationMeta::new).collect(Collectors.toList()));
        //this.body = model
    }

    public MethodMeta replaceExp(ExpressionContext expressionContext) {
        //
        this.name = expressionContext.replaceExpString(name);
        this.type = expressionContext.replaceExpString(type);

        Optional.ofNullable(parameters).ifPresent(parameters -> parameters.forEach(parameter ->
                parameter.replaceExp(expressionContext)));
        Optional.ofNullable(annotations).ifPresent(annotations -> annotations.forEach(annotation ->
                annotation.replaceExp(expressionContext)));

        this.body = expressionContext.replaceExpString(body);

        Optional.ofNullable(imports).ifPresent(imports ->
                this.imports = imports.stream()
                        .map(expressionContext::replaceExpString)
                        .collect(Collectors.toList()));
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

        methodModel.setCustomImports(imports);

        return methodModel;
    }

    public void addAnnotations(List<AnnotationMeta> annotationMetas) {
        //
        if (annotationMetas == null) {
            return;
        }

        if (this.annotations == null) {
            this.annotations = new ArrayList<>();
        }
        this.annotations.addAll(annotationMetas);
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

    public List<String> getImports() {
        return imports;
    }

    public void setImports(List<String> imports) {
        this.imports = imports;
    }
}
