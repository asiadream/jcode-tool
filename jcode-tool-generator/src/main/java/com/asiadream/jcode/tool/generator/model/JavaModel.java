package com.asiadream.jcode.tool.generator.model;

import com.asiadream.jcode.tool.share.rule.NameRule;
import com.asiadream.jcode.tool.share.rule.PackageRule;
import com.asiadream.jcode.tool.share.util.json.JsonUtil;
import com.github.javaparser.ast.comments.Comment;

import java.util.ArrayList;
import java.util.List;

public class JavaModel implements SourceModel {
    //
    private ClassType classType;
    private List<AnnotationType> annotations;
    private boolean isInterface;

    private List<ClassType> extendsTypes;
    private List<ClassType> implementsTypes;

    private List<FieldModel> fields;
    private List<ConstructorModel> constructors;
    private List<MethodModel> methods;

    private Comment nodeComment;
    private Comment typeComment;

    private JavaModel() {
        //
        this.annotations = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.constructors = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.extendsTypes = new ArrayList<>();
        this.implementsTypes = new ArrayList<>();
    }

    // an example of className: com.foo.Foo
    public JavaModel(String className) {
        //
        this(className, false);
    }

    public JavaModel(String className, boolean isInterface) {
        //
        this();
        this.classType = ClassType.newClassType(className);
        this.isInterface = isInterface;
    }

    public JavaModel(String name, String packageName, boolean isInterface) {
        //
        this();
        this.classType = ClassType.newClassType(name, packageName);
        this.isInterface = isInterface;
    }

    public JavaModel(JavaModel other) {
        //
        this();

        this.classType = ClassType.copyOf(other.classType);

        for (AnnotationType annotation : other.annotations) {
            this.annotations.add(AnnotationType.copyOf(annotation));
        }

        this.isInterface = other.isInterface;

        for (ClassType type : other.extendsTypes) {
            this.extendsTypes.add(new ClassType(type));
        }

        for (ClassType type : other.implementsTypes) {
            this.implementsTypes.add(new ClassType(type));
        }

        for (FieldModel field : other.fields) {
            this.fields.add(new FieldModel(field));
        }

        for (ConstructorModel constructor : other.constructors) {
            this.constructors.add(new ConstructorModel(constructor));
        }

        for (MethodModel method : other.methods) {
            this.methods.add(new MethodModel(method));
        }

        this.nodeComment = other.nodeComment;
        this.typeComment = other.typeComment;
    }

    public void changeName(NameRule nameRule) {
        //
        if (nameRule == null) {
            return;
        }
        classType.changeName(nameRule);
    }

    public void changePackage(PackageRule packageRule) {
        //
        if (packageRule == null) {
            return;
        }

        classType.changePackage(packageRule);
    }

    public void changeMethodUsingClassPackageName(NameRule nameRule, PackageRule packageRule) {
        //
        if (packageRule == null && nameRule == null) {
            return;
        }

        for (MethodModel methodModel : methods) {
            ClassType returnType = methodModel.getReturnType();
            changeClassTypeName(returnType, nameRule, packageRule);
            changeTypeArgumentName(returnType, nameRule, packageRule);

            for (ParameterModel parameterModel : methodModel.getParameterModels()) {
                ClassType parameterType = parameterModel.getType();
                changeClassTypeName(parameterType, nameRule, packageRule);
                changeTypeArgumentName(parameterType, nameRule, packageRule);
            }
        }
    }

    private void changeTypeArgumentName(ClassType classType, NameRule nameRule, PackageRule packageRule) {
        //
        if (classType == null) {
            return;
        }
        if (!classType.hasTypeArgument()) {
            return;
        }

        for (ClassType typeArgument : classType.getTypeArguments()) {
            changeClassTypeName(typeArgument, nameRule, packageRule);
        }
    }

    private void changeClassTypeName(ClassType classType, NameRule nameRule, PackageRule packageRule) {
        //
        if (classType == null) {
            return;
        }

        if (classType.changeWholePackageAndName(packageRule)) {
            return;
        }

        classType.changeName(nameRule);
        classType.changePackage(packageRule);
    }

    public MethodModel findMethodByName(String methodName) {
        if (methods == null || methods.isEmpty()) {
            return null;
        }

        for (MethodModel methodModel : methods) {
            if (methodModel.getName().equals(methodName)) {
                return methodModel;
            }
        }
        return null;
    }

    public String getName() {
        //
        return classType.getName();
    }

    public String getPackageName() {
        //
        return classType.getPackageName();
    }

    public void setPackageName(String packageName) {
        //
        classType.setPackageName(packageName);
    }

    public JavaModel addAnnotation(AnnotationType annotationType) {
        //
        this.annotations.add(annotationType);
        return this;
    }

    public JavaModel addExtendsType(ClassType extendsType) {
        //
        this.extendsTypes.add(extendsType);
        return this;
    }

    public JavaModel addImplementsType(ClassType implementsType) {
        //
        this.implementsTypes.add(implementsType);
        return this;
    }

    public JavaModel addFieldModel(FieldModel fieldModel) {
        //
        this.fields.add(fieldModel);
        return this;
    }

    public JavaModel addConstructorModel(ConstructorModel constructorModel) {
        //
        this.constructors.add(constructorModel);
        return this;
    }

    public JavaModel addMethodModel(MethodModel methodModel) {
        //
        this.methods.add(methodModel);
        return this;
    }

    public boolean hasAnnotation() {
        //
        return this.annotations != null && this.annotations.size() > 0;
    }

    public boolean hasExtendsType() {
        //
        return this.extendsTypes != null && this.extendsTypes.size() > 0;
    }

    public boolean hasImplementsType() {
        //
        return this.implementsTypes != null && this.implementsTypes.size() > 0;
    }

    public boolean hasField() {
        //
        return this.fields != null && this.fields.size() > 0;
    }

    public boolean hasConstructor() {
        //
        return this.constructors != null && this.constructors.size() > 0;
    }

    public boolean hasMethod() {
        //
        return this.methods != null && this.methods.size() > 0;
    }

    public List<String> computeImports() {
        //
        List<String> usingClassNames = extractUsingClassNames();
        usingClassNames = removeDuplicate(usingClassNames);
        return removePrimitiveType(usingClassNames);
    }

    public List<String> computeMethodUsingClasses() {
        //
        List<String> usingClassNames = extractMethodUsingClassNames();
        usingClassNames = removeDuplicate(usingClassNames);
        return removePrimitiveType(usingClassNames);
    }

    private List<String> removePrimitiveType(List<String> nameList) {
        //
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < nameList.size(); i++) {
            String name = nameList.get(i);
            if (name.toUpperCase().equals("INT")
                    || name.toUpperCase().equals("INTEGER")
                    || name.toUpperCase().equals("SHORT")
                    || name.toUpperCase().equals("LONG")
                    || name.toUpperCase().equals("DOUBLE")
                    || name.toUpperCase().equals("FLOAT")
                    || name.toUpperCase().equals("CHAR")
                    || name.toUpperCase().equals("BOOLEAN")
                    || name.toUpperCase().equals("STRING")
                    || name.toUpperCase().equals("BYTE")
            ) {
                //
            } else {
                resultList.add(name);
            }
        }
        return resultList;
    }

    private List<String> removeDuplicate(List<String> nameList) {
        //
        List<String> resultList = new ArrayList<>();
        for (int i = 0; i < nameList.size(); i++) {
            if (!resultList.contains(nameList.get(i))) {
                resultList.add(nameList.get(i));
            }
        }
        return resultList;
    }

    private List<String> extractUsingClassNames() {
        //
        List<String> classNames = new ArrayList<>();

        if (hasAnnotation()) {
            for (AnnotationType annotation : annotations) {
                classNames.addAll(annotation.usingClassNames());
            }
        }

        if (hasExtendsType()) {
            for (ClassType classType : extendsTypes) {
                classNames.addAll(classType.usingClassNames());
            }
        }

        if (hasImplementsType()) {
            for (ClassType classType : implementsTypes) {
                classNames.addAll(classType.usingClassNames());
            }
        }

        if (hasField()) {
            for (FieldModel field : fields) {
                classNames.addAll(field.usingClassNames());
            }
        }

        if (hasConstructor()) {
            for (ConstructorModel constructor : constructors) {
                classNames.addAll(constructor.usingClassNames());
            }
        }

        if (hasMethod()) {
            for (MethodModel method : methods) {
                classNames.addAll(method.usingClassNames());
            }
        }

        return classNames;
    }

    @Deprecated
    private List<String> extractMethodUsingClassNames() {
        //
        List<String> classNames = new ArrayList<>();

        // FIXME : refactoring
        for (MethodModel methodModel : methods) {
            ClassType returnType = methodModel.getReturnType();
            if (returnType != null && !returnType.isPrimitive()) {
                classNames.add(returnType.getClassName());
                if (returnType.hasTypeArgument()) {
                    for (ClassType typeArgument : returnType.getTypeArguments()) {
                        classNames.add(typeArgument.getClassName());
                    }
                }
            }
            for (ParameterModel parameterModel : methodModel.getParameterModels()) {
                ClassType parameterType = parameterModel.getType();
                classNames.add(parameterType.getClassName());
                if (parameterType.hasTypeArgument()) {
                    for (ClassType typeArgument : parameterType.getTypeArguments()) {
                        classNames.add(typeArgument.getClassName());
                    }
                }
            }
        }
        return classNames;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public List<AnnotationType> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<AnnotationType> annotations) {
        this.annotations = annotations;
    }

    public List<ClassType> getExtendsTypes() {
        return extendsTypes;
    }

    public void setExtendsTypes(List<ClassType> extendsTypes) {
        this.extendsTypes = extendsTypes;
    }

    public List<ClassType> getImplementsTypes() {
        return implementsTypes;
    }

    public void setImplementsTypes(List<ClassType> implementsTypes) {
        this.implementsTypes = implementsTypes;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setInterface(boolean anInterface) {
        isInterface = anInterface;
    }

    public List<MethodModel> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodModel> methods) {
        this.methods = methods;
    }

    public List<FieldModel> getFields() {
        return fields;
    }

    public Comment getNodeComment() {
        return nodeComment;
    }

    public void setNodeComment(Comment nodeComment) {
        this.nodeComment = nodeComment;
    }

    public Comment getTypeComment() {
        return typeComment;
    }

    public void setTypeComment(Comment typeComment) {
        this.typeComment = typeComment;
    }

    public void setFields(List<FieldModel> fields) {
        this.fields = fields;
    }

    public List<ConstructorModel> getConstructors() {
        return constructors;
    }

    public void setConstructors(List<ConstructorModel> constructors) {
        this.constructors = constructors;
    }

    public String toJson() {
        //
        return JsonUtil.toJson(this);
    }
}
