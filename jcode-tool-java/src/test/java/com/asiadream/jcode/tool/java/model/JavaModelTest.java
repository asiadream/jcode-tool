package com.asiadream.jcode.tool.java.model;

import com.asiadream.jcode.tool.java.creator.JavaCreator;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import com.asiadream.jcode.tool.share.util.string.ClassNameUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaModelTest extends BaseFileTest {
    //

    @Test
    public void testCreate() {
        //
        String className = "com.foo.bar.TestCreated";
        String simpleClassName = ClassNameUtil.getSimpleClassName(className);
        //
        JavaModel javaModel = new JavaModel(className);
        FieldModel idField = new FieldModel("id", ClassType.newClassType("String"));
        FieldModel nameField = new FieldModel("name", ClassType.newClassType("String"));

        // Class level Annotations
        javaModel
                .addAnnotation(new AnnotationType("lombok.Getter"))
                .addAnnotation(new AnnotationType("lombok.Setter"))
                .addAnnotation(new AnnotationType("lombok.NoArgsConstructor"));

        // Extends
        javaModel.addExtendsType(ClassType.newClassType("io.naraplatform.share.domain.nara.NaraEntity"));

        // Implements
        javaModel.addImplementsType(ClassType.newClassType("io.naraplatform.share.domain.nara.NaraAggregate"));

        // Fields
        javaModel
                .addFieldModel(idField)
                .addFieldModel(nameField);

        // Constructor
        ConstructorModel constructorModel = new ConstructorModel(simpleClassName)
                .addParameterModel(idField)
                .addParameterModel(nameField)
                .body(parameters -> {
                    List<String> stmts = new ArrayList<>();
                    for (ParameterModel param : parameters) {
                        stmts.add("this." + param.getVarName() + " = " + param.getVarName() + ";");
                    }
                    return stmts;
                });
        javaModel.addConstructorModel(constructorModel);

        // Methods
        MethodModel toJsonMethod = new MethodModel("toJson", ClassType.newClassType("String"))
                .setAccess(Access.PUBLIC)
                .body("return (new Gson()).toJson(this);");
        javaModel.addMethodModel(toJsonMethod);

        MethodModel fromJsonMethod = new MethodModel("fromJson", ClassType.newClassType(simpleClassName))
                .setAccess(Access.PUBLIC)
                .setStatic(true)
                .addParameterModel(ClassType.newClassType("String"), "json")
                .body("return (new Gson()).fromJson(json, " + simpleClassName + ".class);");
        javaModel.addMethodModel(fromJsonMethod);

        // create with model
        ProjectConfiguration targetConfiguration = new ProjectConfiguration(ConfigurationType.Target, super.testDirName);
        JavaCreator creator = new JavaCreator(targetConfiguration);
        try {
            creator.create(javaModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
