package com.asiadream.jcode.tool.generator.meta;

import com.asiadream.jcode.tool.generator.model.AnnotationType;
import com.asiadream.jcode.tool.generator.model.ClassType;
import com.asiadream.jcode.tool.generator.model.ConstructorModel;
import com.asiadream.jcode.tool.generator.model.JavaModel;
import com.asiadream.jcode.tool.share.util.string.StringUtil;

import java.util.List;
import java.util.Optional;

public class JavaMeta {
    //
    private String packageSuffix; // domain.entity
    private String nameSuffix;    // Store
    private boolean interfaceFile;
    private List<FieldMeta> fields;
    private List<String> classAnnotations;
    private List<String> classExtends;
    private List<String> classImplements;
    private List<ConstructorMeta> constructors;
    private List<MethodMeta> methods;

    public JavaModel toJavaModel(String groupId, String baseName) {
        // baseName: hello, groupId: io.naradrama, packageSuffix: domain.entity
        String simpleClassName = StringUtil.toFirstUpperCase(baseName) + StringUtil.defaultString(nameSuffix, ""); // Hello
        String packageName = groupId + "." + packageSuffix;
        String className = packageName + "." + simpleClassName;

        JavaModel javaModel = new JavaModel(className, interfaceFile);

        Optional.ofNullable(fields).ifPresent(fileds -> fileds.forEach(field ->
                javaModel.addFieldModel(field.toFieldModel())));

        Optional.ofNullable(classAnnotations).ifPresent(cas -> cas.forEach(ca ->
                javaModel.addAnnotation(new AnnotationType(ca))));

        Optional.ofNullable(classExtends).ifPresent(ces -> ces.forEach(ce ->
                javaModel.addExtendsType(ClassType.newClassType(ce))));

        Optional.ofNullable(classImplements).ifPresent(cis -> cis.forEach(ci ->
                javaModel.addImplementsType(ClassType.newClassType(ci))));

        Optional.ofNullable(constructors).ifPresent(constructors -> constructors.forEach(constructor -> {
            ConstructorModel constructorModel = constructor.toConstructorModel(simpleClassName, javaModel);
            javaModel.addConstructorModel(constructorModel);
        }));

        Optional.ofNullable(methods).ifPresent(methods -> methods.forEach(method ->
                javaModel.addMethodModel(method.toMethodModel(simpleClassName))));
        return javaModel;
    }

    public String getPackageSuffix() {
        return packageSuffix;
    }

    public void setPackageSuffix(String packageSuffix) {
        this.packageSuffix = packageSuffix;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public void setNameSuffix(String nameSuffix) {
        this.nameSuffix = nameSuffix;
    }

    public List<FieldMeta> getFields() {
        return fields;
    }

    public void setFields(List<FieldMeta> fields) {
        this.fields = fields;
    }

    public List<String> getClassAnnotations() {
        return classAnnotations;
    }

    public void setClassAnnotations(List<String> classAnnotations) {
        this.classAnnotations = classAnnotations;
    }

    public List<String> getClassExtends() {
        return classExtends;
    }

    public void setClassExtends(List<String> classExtends) {
        this.classExtends = classExtends;
    }

    public List<String> getClassImplements() {
        return classImplements;
    }

    public void setClassImplements(List<String> classImplements) {
        this.classImplements = classImplements;
    }

    public List<ConstructorMeta> getConstructors() {
        return constructors;
    }

    public void setConstructors(List<ConstructorMeta> constructors) {
        this.constructors = constructors;
    }

    public List<MethodMeta> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodMeta> methods) {
        this.methods = methods;
    }

    public boolean isInterfaceFile() {
        return interfaceFile;
    }

    public void setInterfaceFile(boolean interfaceFile) {
        this.interfaceFile = interfaceFile;
    }
}
