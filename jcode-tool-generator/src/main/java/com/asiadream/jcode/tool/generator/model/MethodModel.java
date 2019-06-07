package com.asiadream.jcode.tool.generator.model;

import com.github.javaparser.ast.comments.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MethodModel {
    //
    private String name;
    private Access access;
    private boolean staticMethod;
    private ClassType returnType;
    private List<ParameterModel> parameterModels;
    private List<ClassType> throwns;
    private Comment comment;
    private List<String> bodyStatements;

    private MethodModel() {
        //
        this.parameterModels = new ArrayList<>();
        this.throwns = new ArrayList<>();
        this.bodyStatements = new ArrayList<>();
    }

    public MethodModel(String name, ClassType returnType) {
        //
        this();
        this.name = name;
        this.returnType = returnType;
    }

    public MethodModel(MethodModel other) {
        //
        this();
        this.name = other.name;
        this.access = other.access;
        this.returnType = ClassType.copyOf(other.returnType);
        for (ParameterModel parameterModel : other.parameterModels) {
            this.parameterModels.add(new ParameterModel(parameterModel));
        }
        for (ClassType thrown : other.throwns) {
            this.throwns.add(ClassType.copyOf(thrown));
        }
        this.comment = other.comment;
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

    public MethodModel addParameterModel(ParameterModel parameterModel) {
        //
        this.parameterModels.add(parameterModel);
        return this;
    }

    public MethodModel addParameterModel(ClassType type, String varName) {
        //
        this.parameterModels.add(new ParameterModel(type, varName));
        return this;
    }

    public MethodModel addThrown(ClassType thrown) {
        //
        this.throwns.add(thrown);
        return this;
    }

    public MethodModel addBodyStatement(String bodyStatement) {
        //
        this.bodyStatements.add(bodyStatement);
        return this;
    }

    public MethodModel body(Function<List<ParameterModel>, List<String>> bodyHandler) {
        //
        List<String> bodyStmts = bodyHandler.apply(parameterModels);
        this.bodyStatements.addAll(bodyStmts);
        return this;
    }

    public MethodModel body(String singleStatement) {
        //
        this.bodyStatements.add(singleStatement);
        return this;
    }


    public boolean isVoid() {
        return returnType == null;
    }

    public boolean isPrimitive() {
        //
        if (returnType == null) return false;
        return returnType.isPrimitive();
    }

    public boolean isPublic() {
        //
        return this.access == Access.PUBLIC;
    }

    public List<String> usingClassNames() {
        //
        List<String> classNames = new ArrayList<>();

        if (returnType != null) {
            classNames.addAll(returnType.usingClassNames());
        }

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

    public ClassType getReturnType() {
        return returnType;
    }

    public void setReturnType(ClassType returnType) {
        this.returnType = returnType;
    }

    public List<ParameterModel> getParameterModels() {
        return parameterModels;
    }

    public void setParameterModels(List<ParameterModel> parameterModels) {
        this.parameterModels = parameterModels;
    }

    public Access getAccess() {
        return access;
    }

    public MethodModel setAccess(Access access) {
        this.access = access;
        return this;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<ClassType> getThrowns() {
        return throwns;
    }

    public void setThrowns(List<ClassType> throwns) {
        this.throwns = throwns;
    }

    public List<String> getBodyStatements() {
        return bodyStatements;
    }

    public void setBodyStatements(List<String> bodyStatements) {
        this.bodyStatements = bodyStatements;
    }

    public boolean isStaticMethod() {
        return staticMethod;
    }

    public MethodModel setStaticMethod(boolean staticMethod) {
        //
        this.staticMethod = staticMethod;
        return this;
    }
}
