package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.generator.converter.JavaConverter;
import com.asiadream.jcode.tool.generator.converter.config.ConverterConfig;
import com.asiadream.jcode.tool.generator.creator.JavaCreator;
import com.asiadream.jcode.tool.generator.meta.ExpressionContext;
import com.asiadream.jcode.tool.generator.meta.GeneratorMeta;
import com.asiadream.jcode.tool.generator.meta.JavaMeta;
import com.asiadream.jcode.tool.generator.model.JavaModel;
import com.asiadream.jcode.tool.generator.reader.JavaReader;
import com.asiadream.jcode.tool.generator.sdo.ReferenceSdo;
import com.asiadream.jcode.tool.generator.source.JavaSource;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.share.exception.ClassFileNotFoundException;
import com.asiadream.jcode.tool.share.rule.NameRule;
import com.asiadream.jcode.tool.share.rule.PackageRule;
import com.asiadream.jcode.tool.share.util.file.PathUtil;
import com.asiadream.jcode.tool.share.util.string.ClassNameUtil;
import com.asiadream.jcode.tool.share.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class JavaService {
    //
    private static Logger logger = LoggerFactory.getLogger(JavaService.class);

    private GeneratorMeta generatorMeta;

    public JavaService() {
        //
        this.generatorMeta = loadGeneratorMeta();
    }

    public JavaService(GeneratorMeta generatorMeta) {
        //
        this.generatorMeta = generatorMeta;
        if (this.generatorMeta == null) {
            this.generatorMeta = loadGeneratorMeta();
        }
    }

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
        return yaml.load(inputStream);
    }

    private ConverterConfig loadConverterConfig(String configName) {
        //
        Yaml yaml = new Yaml(new Constructor((ConverterConfig.class)));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configName + ".convert.yaml");
        return yaml.load(inputStream);
    }

    public String create(String template, String targetProjectPath) {
        //
        return create(null, template, targetProjectPath);
    }

    public String create(ReferenceSdo referenceSdo, String template, String targetProjectPath) {
        //
        String groupId = generatorMeta.getGroupId();
        String appName = generatorMeta.getAppName();

        JavaMeta javaMeta = loadJavaMeta(template);
        ExpressionContext expressionContext = constructExpressionContext(groupId, appName, referenceSdo, javaMeta);
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

    public String predictClassName(ReferenceSdo referenceSdo, String template) {
        //
        JavaMeta javaMeta = loadJavaMeta(template);
        ExpressionContext expressionContext = constructExpressionContext(generatorMeta.getGroupId(), generatorMeta.getAppName(), referenceSdo, javaMeta);
        JavaModel javaModel = javaMeta
                .replaceExp(expressionContext)   // replace expression with context (${...})
                .toJavaModel();
        return javaModel.getClassName();
    }

    public String convert(String sourceClassName, String sourceProjectPath, String configName, String targetProjectPath) {
        //
        ConverterConfig converterConfig = loadConverterConfig(configName);

        ProjectConfiguration sourceConfig = new ProjectConfiguration(ConfigurationType.Source, sourceProjectPath, true);
        ProjectConfiguration targetConfig = new ProjectConfiguration(ConfigurationType.Target, targetProjectPath, true);

        PackageRule packageRule = converterConfig.toPackageRule();
        NameRule nameRule = converterConfig.toNameRule();

        JavaConverter javaConverter = new JavaConverter(sourceConfig, targetConfig, nameRule, packageRule, converterConfig);
        try {
            return javaConverter.convert(ClassNameUtil.toSourceFileName(sourceClassName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO : ExpressionContextBuilder
    private ExpressionContext constructExpressionContext(String groupId, String appName, ReferenceSdo referenceSdo, JavaMeta javaMeta) {
        //
        ExpressionContext expressionContext = new ExpressionContext();

        expressionContext.add("groupId", groupId);
        expressionContext.add("appName", appName);
        expressionContext.add("AppName", StringUtil.toFirstUpperCase(appName));
        expressionContext.add("simpleClassName", javaMeta.getSimpleClassName());
        expressionContext.add("packageName", javaMeta.getPackageName());
        expressionContext.add("className", javaMeta.getClassName());

        if (referenceSdo != null) {
            // Set bizName with their className.
            String preBizName = referenceSdo.getPreBizName(generatorMeta.getBizNameLocation(), groupId, appName);
            expressionContext.add("preBizName", preBizName != null ? "." + preBizName : "");
            String postBizName = referenceSdo.getPostBizName(generatorMeta.getBizNameLocation(), groupId, appName);
            expressionContext.add("postBizName", postBizName != null ? "." + postBizName : "");
        }

        Optional.ofNullable(referenceSdo).ifPresent(_refSdo -> _refSdo.getReferences().forEach(ref -> {
            expressionContext.add(ref.getName() + ".className", ref.getClassName());
            expressionContext.add(ref.getName() + ".simpleClassName", ref.getSimpleClassName());
            expressionContext.add(ref.getName() + ".name", StringUtil.toFirstLowerCase(ref.getSimpleClassName()));
            JavaSource javaSource = ref.read(this::readJavaSource);
            expressionContext.add(ref.getName() + ".fields", javaSource.getFieldsAsModel());
            expressionContext.add(ref.getName() + ".methods", javaSource.getMethodsAsModel());
        }));

        // custom expression context
        Optional.ofNullable(referenceSdo).ifPresent(_refSdo -> expressionContext.addAll(_refSdo.getCustomContext()));
        Optional.ofNullable(referenceSdo).ifPresent(_refSdo -> expressionContext.addAllWithFirstUpperCase(_refSdo.getCustomContext()));

        if (logger.isTraceEnabled()) {
            logger.trace(expressionContext.show("Before update"));
        }

        expressionContext.updateExpressedValue();

        if (logger.isTraceEnabled()) {
            logger.trace(expressionContext.show(null));
        }

        return expressionContext;
    }

    public JavaSource readJavaSource(String projectPath, String className) {
        //
        JavaReader reader = new JavaReader(new ProjectConfiguration(ConfigurationType.Source, projectPath));
        try {
            JavaSource javaSource = reader.read(PathUtil.toSourceFileName(className, "java"));
            return javaSource;
        } catch (FileNotFoundException fnf) {
            throw new ClassFileNotFoundException("Class file does not exist: " + className, fnf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
