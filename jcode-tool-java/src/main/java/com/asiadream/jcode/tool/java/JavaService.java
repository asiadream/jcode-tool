package com.asiadream.jcode.tool.java;

import com.asiadream.jcode.tool.java.converter.JavaConverter;
import com.asiadream.jcode.tool.java.converter.config.ConverterConfig;
import com.asiadream.jcode.tool.java.creator.JavaCreator;
import com.asiadream.jcode.tool.java.meta.BizNameRuleMeta;
import com.asiadream.jcode.tool.java.meta.ExpressionContext;
import com.asiadream.jcode.tool.java.meta.GeneratorMeta;
import com.asiadream.jcode.tool.java.meta.JavaMeta;
import com.asiadream.jcode.tool.java.model.FieldModel;
import com.asiadream.jcode.tool.java.model.JavaModel;
import com.asiadream.jcode.tool.java.reader.JavaReader;
import com.asiadream.jcode.tool.java.sdo.ReferenceSdo;
import com.asiadream.jcode.tool.java.source.JavaSource;
import com.asiadream.jcode.tool.java.writer.JavaWriter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaService {
    //
    private static Logger logger = LoggerFactory.getLogger(JavaService.class);
    private static final boolean lexicalPreserving = false;
    private static final boolean useOwnPrinter = true;

    private String metaLocation;
    private GeneratorMeta generatorMeta;
    private BizNameRuleMeta bizNameRuleMeta;
    private Map<String, String> settings;

    public JavaService() {
        //
        this(null);
    }

    public JavaService(String metaLocation) {
        //
        this.metaLocation = metaLocation;
        this.generatorMeta = loadGeneratorMeta();
        this.bizNameRuleMeta = loadBizNameRuleMeta();
        this.settings = loadSettingsParameters();
    }

    public JavaService(GeneratorMeta generatorMeta, String metaLocation) {
        //
        this.metaLocation = metaLocation;
        this.generatorMeta = generatorMeta;
        if (this.generatorMeta == null) {
            this.generatorMeta = loadGeneratorMeta();
        }
        this.bizNameRuleMeta = loadBizNameRuleMeta();
        this.settings = loadSettingsParameters();
    }

    private GeneratorMeta loadGeneratorMeta() {
        //
        Yaml yaml = new Yaml(new Constructor(GeneratorMeta.class));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(getMetaLocation() + "generator.yaml");
        if (inputStream == null) {
            throw new RuntimeException("generator.yaml not exists.");
        }
        GeneratorMeta generatorMeta = yaml.load(inputStream);
        return generatorMeta;
    }

    private BizNameRuleMeta loadBizNameRuleMeta() {
        //
        Yaml yaml = new Yaml(new Constructor(BizNameRuleMeta.class));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(getMetaLocation() + "biz_name_rule.yaml");
        if (inputStream == null) {
            throw new RuntimeException("biz_name_rule.yaml not exists.");
        }
        BizNameRuleMeta bizNameRuleMeta = yaml.load(inputStream);
        return bizNameRuleMeta;
    }

    private JavaMeta loadJavaMeta(String template) {
        //
        Yaml yaml = new Yaml(new Constructor(JavaMeta.class));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(getMetaLocation() + template + ".yaml");
        return yaml.load(inputStream);
    }

    private ConverterConfig loadConverterConfig(String configName) {
        //
        Yaml yaml = new Yaml(new Constructor((ConverterConfig.class)));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(getMetaLocation() + configName + ".convert.yaml");
        return yaml.load(inputStream);
    }

    private Map<String, Object> loadSettings() {
        //
        Yaml yaml = new Yaml();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(getMetaLocation() + "settings.yaml");
        if (inputStream == null) {
            throw new RuntimeException("settings.yaml not exists.");
        }
        return yaml.load(inputStream);
    }

    private Map<String, String> loadSettingsParameters() {
        //
        Map<String, Object> settings = loadSettings();
        return toParameter(settings, new HashMap<>(), null);
    }

    private static Map<String, String> toParameter(Map<String, Object> srcMap, HashMap<String, String> targetMap, String keyPrefix) {
        //
        for (String key : srcMap.keySet()) {
            Object value = srcMap.get(key);
            String showKey = keyPrefix != null ? keyPrefix + "." + key : key;
            if (value.getClass() == String.class) {
                targetMap.put(showKey, (String) value);
            } else if (value instanceof Map) {
                toParameter((Map<String, Object>) value, targetMap, showKey);
            }
        }
        return targetMap;
    }

    public String create(String template, String targetProjectPath) {
        //
        return create(null, template, targetProjectPath);
    }

    public String create(ReferenceSdo referenceSdo, String template, String targetProjectPath) {
        //
        String groupId = generatorMeta.getGroupId();
        String appNameWithRemovedDash = generatorMeta.getAppNameWithRemovedDash();

        JavaMeta javaMeta = loadJavaMeta(template);

        ExpressionContext expressionContext = constructExpressionContext(groupId, appNameWithRemovedDash, referenceSdo, javaMeta);
        JavaModel javaModel = javaMeta
                .replaceExp(expressionContext)   // replace expression with context (${...})
                .toJavaModel(); // create java model

        //
        ProjectConfiguration targetConfiguration = new ProjectConfiguration(ConfigurationType.Target, targetProjectPath, lexicalPreserving, useOwnPrinter);
        JavaCreator creator = new JavaCreator(targetConfiguration);
        try {
            creator.create(javaModel.getSourceFileName(), javaModel);
            return javaModel.getClassName();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPath(String projectPath, String className) {
        //
        ProjectConfiguration configuration = new ProjectConfiguration(ConfigurationType.Target, projectPath, lexicalPreserving, useOwnPrinter);
        return configuration.makePhysicalJavaSourceFilePath(ClassNameUtil.toSourceFileName(className));
    }

    public String predictClassName(ReferenceSdo referenceSdo, String template) {
        //
        JavaMeta javaMeta = loadJavaMeta(template);

        ExpressionContext expressionContext = constructExpressionContext(generatorMeta.getGroupId(), generatorMeta.getAppNameWithRemovedDash(), referenceSdo, javaMeta);
        JavaModel javaModel = javaMeta
                .replaceExp(expressionContext)   // replace expression with context (${...})
                .toJavaModel();
        return javaModel.getClassName();
    }

    public String convert(String sourceClassName, String sourceProjectPath, String configName, String targetProjectPath) {
        //
        ConverterConfig converterConfig = loadConverterConfig(configName);

        ProjectConfiguration sourceConfig = new ProjectConfiguration(ConfigurationType.Source, sourceProjectPath, lexicalPreserving, useOwnPrinter);
        ProjectConfiguration targetConfig = new ProjectConfiguration(ConfigurationType.Target, targetProjectPath, lexicalPreserving, useOwnPrinter);

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
    private ExpressionContext constructExpressionContext(String groupId, String appNameWithRemovedDash, ReferenceSdo referenceSdo, JavaMeta javaMeta) {
        //
        ExpressionContext expressionContext = new ExpressionContext(settings);

        expressionContext.add("groupId", groupId);
        expressionContext.add("appName", appNameWithRemovedDash);
        expressionContext.add("AppName", StringUtil.toFirstUpperCase(appNameWithRemovedDash));
        expressionContext.add("simpleClassName", javaMeta.getSimpleClassName());
        expressionContext.add("packageName", javaMeta.getPackageName());
        expressionContext.add("className", javaMeta.getClassName());

        expressionContext.add("preBizName", "");
        expressionContext.add("postBizName", "");
        expressionContext.add("middleBizName", "");

        if (referenceSdo != null) {
            // Set bizName with their className.
            String preBizName = referenceSdo.getPreBizNameFromReferenceClass(bizNameRuleMeta.getBizNameLocation(), bizNameRuleMeta.getMiddleNameSeq(), groupId, appNameWithRemovedDash);
            expressionContext.add("preBizName", preBizName != null ? "." + preBizName : "");
            String postBizName = referenceSdo.getPostBizNameFromReferenceClass(bizNameRuleMeta.getBizNameLocation(), bizNameRuleMeta.getMiddleNameSeq(), groupId, appNameWithRemovedDash);
            expressionContext.add("postBizName", postBizName != null ? "." + postBizName : "");
            String middleBizName = referenceSdo.getMiddleBizNameFromReferenceClass(bizNameRuleMeta.getBizNameLocation(), bizNameRuleMeta.getMiddleNameSeq(), groupId, appNameWithRemovedDash);
            expressionContext.add("middleBizName", middleBizName != null ? "." + middleBizName : "");
        }

        // Register reference
        Optional.ofNullable(referenceSdo).ifPresent(_refSdo -> _refSdo.getReferences().forEach(ref -> {
            expressionContext.add(ref.getName() + ".packageName", ClassNameUtil.getPackageName(ref.getClassName()));
            expressionContext.add(ref.getName() + ".className", ref.getClassName());
            expressionContext.add(ref.getName() + ".simpleClassName", ref.getSimpleClassName());
            String name = StringUtil.toFirstLowerCase(ref.getSimpleClassName());
            expressionContext.add(ref.getName() + ".name", name);
            expressionContext.add(ref.getName() + ".upperUnderscoreName", StringUtil.toUpperUnderscoreString(name));

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
        JavaReader reader = new JavaReader(new ProjectConfiguration(ConfigurationType.Source, projectPath, lexicalPreserving, useOwnPrinter));
        try {
            JavaSource javaSource = reader.read(PathUtil.toSourceFileName(className, "java"));
            return javaSource;
        } catch (FileNotFoundException fnf) {
            throw new ClassFileNotFoundException("Class file does not exist: " + className, fnf);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeJavaSource(JavaSource javaSource, String projectHomePath) {
        //
        JavaWriter writer = new JavaWriter(new ProjectConfiguration(ConfigurationType.Target, projectHomePath, lexicalPreserving, useOwnPrinter));
        try {
            writer.write(javaSource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFields(String className, String targetProjectPath, List<FieldModel> fields) {
        // Read java source
        JavaSource source = readJavaSource(targetProjectPath, className);
        logger.debug("read java --> {}", source);

        // Deduplication
        List<FieldModel> nonDuplicateFields = fields.stream()
                .filter(fieldModel -> !source.isExistField(fieldModel.getName()))
                .collect(Collectors.toList());

        // Update java source
        source.addFieldAll(nonDuplicateFields, source.getFieldsSize());

        // Calculate blank size of the Line comment.
        source.calculateBlankOfLineComment();

        // Write
        writeJavaSource(source, targetProjectPath);
    }

    private String getMetaLocation() {
        //
        return StringUtil.isNotEmpty(metaLocation) ? metaLocation + "/" : "";
    }

}
