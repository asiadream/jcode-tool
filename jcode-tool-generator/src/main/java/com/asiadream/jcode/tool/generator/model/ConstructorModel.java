package com.asiadream.jcode.tool.generator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ConstructorModel {
    //
    private String name;
    private List<ParameterModel> parameterModels;
    private List<String> bodyStatements;

    public ConstructorModel(String name) {
        //
        this.name = name;
        this.parameterModels = new ArrayList<>();
        this.bodyStatements = new ArrayList<>();
    }

    public ConstructorModel(ConstructorModel other) {
        //
        this(other.name);
        for (ParameterModel parameterModel : other.parameterModels) {
            this.parameterModels.add(new ParameterModel(parameterModel));
        }
        for (String bodyStmt : other.bodyStatements) {
            this.bodyStatements.add(bodyStmt);
        }
    }

    public int parameterSize() {
        //
        return parameterModels.size();
    }

    public boolean hasParameter() {
        //
        return this.parameterModels != null && this.parameterModels.size() > 0;
    }

    public boolean hasBodyStatement() {
        //
        return this.bodyStatements != null && this.bodyStatements.size() > 0;
    }

    public ConstructorModel addParameterModel(ParameterModel parameterModel) {
        //
        this.parameterModels.add(parameterModel);
        return this;
    }

    public ConstructorModel addParameterModel(FieldModel fieldModel) {
        //
        this.parameterModels.add(new ParameterModel(fieldModel));
        return this;
    }

    public ConstructorModel addBodyStatement(String bodyStatement) {
        //
        this.bodyStatements.add(bodyStatement);
        return this;
    }

    public ConstructorModel body(Function<List<ParameterModel>, List<String>> bodyHandler) {
        //
        List<String> bodyStmts = bodyHandler.apply(parameterModels);
        this.bodyStatements.addAll(bodyStmts);
        return this;
    }

    public List<String> usingClassNames() {
        //
        List<String> classNames = new ArrayList<>();

        if (hasParameter()) {
            for (ParameterModel parameter : parameterModels) {
                classNames.addAll(parameter.usingClassNames());
            }
        }

        return classNames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParameterModel> getParameterModels() {
        return parameterModels;
    }

    public void setParameterModels(List<ParameterModel> parameterModels) {
        this.parameterModels = parameterModels;
    }

    public List<String> getBodyStatements() {
        return bodyStatements;
    }

    public void setBodyStatements(List<String> bodyStatements) {
        this.bodyStatements = bodyStatements;
    }
}
