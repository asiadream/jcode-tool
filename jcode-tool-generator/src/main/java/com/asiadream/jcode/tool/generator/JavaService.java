package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.generator.creator.JavaCreator;
import com.asiadream.jcode.tool.generator.meta.ExpressionContext;
import com.asiadream.jcode.tool.generator.meta.GeneratorMeta;
import com.asiadream.jcode.tool.generator.meta.JavaMeta;
import com.asiadream.jcode.tool.generator.model.JavaModel;
import com.asiadream.jcode.tool.generator.reader.JavaReader;
import com.asiadream.jcode.tool.generator.source.JavaSource;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.share.util.file.PathUtil;
import com.asiadream.jcode.tool.share.util.string.ClassNameUtil;
import com.asiadream.jcode.tool.share.util.string.StringUtil;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

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

    public String create(String baseName, String template, String targetProjectPath) {
        //
        return create(baseName, null, template, targetProjectPath);
    }

    public String createByRefClass(String refClassName, String template, String targetProjectPath) {
        //
        String baseName = StringUtil.toFirstLowerCase(ClassNameUtil.getSimpleClassName(refClassName));
        return create(baseName, refClassName, template, targetProjectPath);
    }

    private String create(String baseName, String refClassName, String template, String targetProjectPath) {
        // baseName: hello
        // Create a Domain Entity
        String groupId = generatorMeta.getGroupId();

        JavaMeta javaMeta = loadJavaMeta(template);
        ExpressionContext expressionContext = constructExpressionContext(groupId, baseName, refClassName, targetProjectPath, javaMeta);
        JavaModel javaModel = javaMeta
                .replaceExp(expressionContext)   // replace expression with context (${...})
                .toJavaModel(groupId, baseName); // create java model

        //
        ProjectConfiguration targetConfiguration = new ProjectConfiguration(ConfigurationType.Target, targetProjectPath);
        JavaCreator creator = new JavaCreator(targetConfiguration);
        try {
            creator.create(javaModel.getSourceFileName(), javaModel);
            return javaModel.getClassName();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ExpressionContext constructExpressionContext(String groupId, String baseName, String refClassName, String refClassProjectPath, JavaMeta javaMeta) {
        //
        ExpressionContext expressionContext = new ExpressionContext();
        expressionContext.add("groupId", groupId);
        expressionContext.add("baseName", baseName);
        expressionContext.add("refClassName", refClassName);
        expressionContext.add("simpleClassName", javaMeta.getSimpleClassName(baseName));
        expressionContext.add("packageName", javaMeta.getPackageName(groupId));
        expressionContext.add("className", javaMeta.getClassName(groupId, baseName));

        Optional.ofNullable(refClassName).ifPresent(className -> {
            JavaSource javaSource = readJavaSource(refClassProjectPath, className);
            expressionContext.add("refClass.fields", javaSource.getFieldsAsModel());
        });

        return expressionContext;
    }

    private JavaSource readJavaSource(String refClassProjectPath, String className) {
        //
        JavaReader reader = new JavaReader(new ProjectConfiguration(ConfigurationType.Source, refClassProjectPath));
        try {
            JavaSource javaSource = reader.read(PathUtil.toSourceFileName(className, "java"));
            return javaSource;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
