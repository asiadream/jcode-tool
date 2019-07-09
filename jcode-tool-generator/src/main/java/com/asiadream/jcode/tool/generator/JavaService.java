package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.generator.creator.JavaCreator;
import com.asiadream.jcode.tool.generator.meta.ExpressionContext;
import com.asiadream.jcode.tool.generator.meta.GeneratorMeta;
import com.asiadream.jcode.tool.generator.meta.JavaMeta;
import com.asiadream.jcode.tool.generator.model.JavaModel;
import com.asiadream.jcode.tool.generator.reader.JavaReader;
import com.asiadream.jcode.tool.generator.sdo.ClassReference;
import com.asiadream.jcode.tool.generator.source.JavaSource;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.share.util.file.PathUtil;
import com.asiadream.jcode.tool.share.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public class JavaService {
    //
    private static Logger logger = LoggerFactory.getLogger(JavaService.class);

    private GeneratorMeta generatorMeta = loadGeneratorMeta();

    private GeneratorMeta loadGeneratorMeta() {
        //
        Yaml yaml = new Yaml(new Constructor(GeneratorMeta.class));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("generator.yaml");
        if (inputStream == null) {
            throw new RuntimeException("generator.yaml not exists.");
        }
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

    public String create(String appName, String template, String targetProjectPath) {
        //
        return create(appName, null, template, targetProjectPath);
    }

    public String create(String appName, List<ClassReference> refs, String template, String targetProjectPath) {
        // baseName: hello
        // Create a Domain Entity
        String groupId = generatorMeta.getGroupId();

        JavaMeta javaMeta = loadJavaMeta(template);
        ExpressionContext expressionContext = constructExpressionContext(groupId, appName, refs, javaMeta);
        JavaModel javaModel = javaMeta
                .replaceExp(expressionContext)   // replace expression with context (${...})
                .toJavaModel(); // create java model

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

    private ExpressionContext constructExpressionContext(String groupId, String appName, List<ClassReference> refs, JavaMeta javaMeta) {
        //
        ExpressionContext expressionContext = new ExpressionContext();
        expressionContext.add("groupId", groupId);
        expressionContext.add("appName", appName);
        expressionContext.add("AppName", StringUtil.toFirstUpperCase(appName));
        expressionContext.add("simpleClassName", javaMeta.getSimpleClassName());
        expressionContext.add("packageName", javaMeta.getPackageName());
        expressionContext.add("className", javaMeta.getClassName());

        Optional.ofNullable(refs).ifPresent(_refs -> _refs.forEach(ref -> {
            expressionContext.add(ref.getName() + ".className", ref.getClassName());
            expressionContext.add(ref.getName() + ".simpleClassName", ref.getSimpleClassName());
            expressionContext.add(ref.getName() + ".name", StringUtil.toFirstLowerCase(ref.getSimpleClassName()));
            JavaSource javaSource = ref.read(this::readJavaSource);
            expressionContext.add(ref.getName() + ".fields", javaSource.getFieldsAsModel());
        }));

        expressionContext.updateExpressedValue();

        if (logger.isTraceEnabled()) {
            logger.trace(expressionContext.show());
        }

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
