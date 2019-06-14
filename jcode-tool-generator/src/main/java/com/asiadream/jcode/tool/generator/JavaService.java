package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.generator.creator.JavaCreator;
import com.asiadream.jcode.tool.generator.meta.GeneratorMeta;
import com.asiadream.jcode.tool.generator.meta.JavaMeta;
import com.asiadream.jcode.tool.generator.model.JavaModel;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;

public class JavaService {
    //
    private GeneratorMeta generatorMeta = loadGeneratorMeta();

    private GeneratorMeta loadGeneratorMeta() {
        //
        Yaml yaml = new Yaml(new Constructor(GeneratorMeta.class));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("generator.yaml");
        GeneratorMeta generatorMeta = yaml.load(inputStream);
        return generatorMeta;
    }

    private JavaMeta loadJavaMeta(String template) {
        //
        Yaml yaml = new Yaml(new Constructor(JavaMeta.class));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(template + ".yaml");
        JavaMeta javaMeta = yaml.load(inputStream);
        return javaMeta;
    }

    public void create(String baseName, String template, String targetProjectPath) {
        // baseName: hello
        // Create a Domain Entity
        String groupId = generatorMeta.getGroupId();
        ProjectConfiguration targetConfiguration = new ProjectConfiguration(ConfigurationType.Target, targetProjectPath);
        JavaCreator creator = new JavaCreator(targetConfiguration);

        //
        JavaMeta javaMeta = loadJavaMeta(template);
        JavaModel javaModel = javaMeta.toJavaModel(groupId, baseName);
        try {
            creator.create(javaModel.getSourceFileName(), javaModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private JavaModel createJavaModel(String template, String groupId, String simpleClassName) {
        //

        ////
//        JavaModel javaModel = new JavaModel(className);
//        FieldModel idField = new FieldModel("id", ClassType.newClassType("String"));
//        FieldModel nameField = new FieldModel("name", ClassType.newClassType("String"));
//
//        // Class level Annotations
//        javaModel
//                .addAnnotation(new AnnotationType("lombok.Getter"))
//                .addAnnotation(new AnnotationType("lombok.Setter"))
//                .addAnnotation(new AnnotationType("lombok.NoArgsConstructor"));
//
//        // Extends
//        javaModel.addExtendsType(ClassType.newClassType("io.naraplatform.share.domain.nara.NaraEntity"));
//
//        // Implements
//        javaModel.addImplementsType(ClassType.newClassType("io.naraplatform.share.domain.nara.NaraAggregate"));
//
//        // Fields
//        javaModel
//                .addFieldModel(idField)
//                .addFieldModel(nameField);
//
        // Constructor
//        ConstructorModel constructorModel = new ConstructorModel(simpleClassName)
//                .addParameterModel(idField)
//                .addParameterModel(nameField)
//                .body(parameters -> {
//                    List<String> stmts = new ArrayList<>();
//                    for (ParameterModel param : parameters) {
//                        stmts.add("this." + param.getVarName() + " = " + param.getVarName() + ";");
//                    }
//                    return stmts;
//                });
//        javaModel.addConstructorModel(constructorModel);
//
//        // Methods
//        MethodModel toJsonMethod = new MethodModel("toJson", ClassType.newClassType("String"))
//                .setAccess(Access.PUBLIC)
//                .body("return (new Gson()).toJson(this);");
//        javaModel.addMethodModel(toJsonMethod);
//
//        MethodModel fromJsonMethod = new MethodModel("fromJson", ClassType.newClassType(simpleClassName))
//                .setAccess(Access.PUBLIC)
//                .setStaticMethod(true)
//                .addParameterModel(ClassType.newClassType("String"), "json")
//                .body("return (new Gson()).fromJson(json, " + simpleClassName + ".class);");
//        javaModel.addMethodModel(fromJsonMethod);

        return null;
    }
}
