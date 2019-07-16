package com.asiadream.jcode.tool.generator.model;

import com.github.javaparser.ast.comments.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MethodModel {
    //
    private String name;
    private Access access;
    private boolean isStatic;
    private ClassType returnType;
    private List<ParameterModel> parameterModels;
    private List<AnnotationType> annotations;
    private List<ClassType> throwns;
    private Comment comment;
    private List<String> bodyStatements;

    private MethodModel() {
        //
        this.parameterModels = new ArrayList<>();
        this.annotations = new ArrayList<>();
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
        for (AnnotationType annotationType : other.annotations) {
            this.annotations.add(AnnotationType.copyOf(annotationType));
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

    public MethodModel body(List<String> bodyStatements) {
        //
        this.bodyStatements.addAll(bodyStatements);
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

        if (hasAnnotation()) {
            for (AnnotationType annotation : annotations) {
                classNames.addAll(annotation.usingClassNames());
            }
        }

        return classNames;
    }

    public void addAnnotation(AnnotationType annotation) {
        //
        this.annotations.add(annotation);
    }

    public void addAnnotation(String annotationClassName) {
        //
        addAnnotation(new AnnotationType(annotationClassName));
    }

    public boolean hasAnnotation() {
        //
        return this.annotations != null && this.annotations.size() > 0;
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

    public boolean isStatic() {
        return isStatic;
    }

    public List<AnnotationType> getAnnotations() {
        return annotations;
    }

    public MethodModel setStatic(boolean aStatic) {
        //
        this.isStatic = aStatic;
        return this;
    }
}
