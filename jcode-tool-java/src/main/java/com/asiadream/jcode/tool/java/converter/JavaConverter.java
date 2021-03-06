package com.asiadream.jcode.tool.java.converter;

import com.asiadream.jcode.tool.java.converter.config.ConverterConfig;
import com.asiadream.jcode.tool.java.reader.JavaReader;
import com.asiadream.jcode.tool.java.source.JavaSource;
import com.asiadream.jcode.tool.java.writer.JavaWriter;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.share.exception.ConversionCancelException;
import com.asiadream.jcode.tool.share.rule.NameRule;
import com.asiadream.jcode.tool.share.rule.PackageRule;
import com.asiadream.jcode.tool.spec.converter.ProjectItemConverter;
import com.asiadream.jcode.tool.spec.converter.ProjectItemType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.function.Consumer;

public class JavaConverter extends ProjectItemConverter {
    //
    private static Logger logger = LoggerFactory.getLogger(JavaConverter.class);

    private JavaReader javaReader;
    private JavaWriter javaWriter;
    private NameRule nameRule;
    private PackageRule packageRule;
    private ConverterConfig config;

    private Consumer<JavaSource> customCodeHandler;
    private Consumer<PackageRule.ChangeImport> changeInfoHandler;

    public JavaConverter(ProjectConfiguration sourceConfiguration, ProjectConfiguration targetConfiguration) {
        //
        this(sourceConfiguration, targetConfiguration, null, null, new ConverterConfig());
    }

    public JavaConverter(ProjectConfiguration sourceConfiguration, ProjectConfiguration targetConfiguration,
                         NameRule nameRule, PackageRule packageRule) {
        //
        this(sourceConfiguration, targetConfiguration, nameRule, packageRule, new ConverterConfig());
    }

    public JavaConverter(ProjectConfiguration sourceConfiguration, ProjectConfiguration targetConfiguration,
                         NameRule nameRule, PackageRule packageRule, ConverterConfig config) {
        //
        super(sourceConfiguration, targetConfiguration, ProjectItemType.Java);
        this.nameRule = nameRule;
        this.packageRule = packageRule;
        this.config = config;
        this.javaReader = new JavaReader(sourceConfiguration);
        this.javaWriter = new JavaWriter(targetConfiguration);
    }

    public JavaConverter customCodeHandle(Consumer<JavaSource> customCodeHandler) {
        //
        this.customCodeHandler = customCodeHandler;
        return this;
    }

    public JavaConverter changeInfoHandle(Consumer<PackageRule.ChangeImport> changeInfoHandler) {
        this.changeInfoHandler = changeInfoHandler;
        return this;
    }

    /**
     * Convert Java File
     * @param sourceFilePath ex) com/foo/bar/SampleService.java
     * @throws IOException If the file does not exist.
     */
    @Override
    public String convert(String sourceFilePath) throws IOException {
        //
        JavaSource source = javaReader.read(sourceFilePath);
        if (!determineConversion(source)) {
            logger.warn("The conversion was canceled.");
            throw new ConversionCancelException("The conversion was canceled.");
        }

        // Save original class name.
        String beforeClassName = source.getClassName();

        // Change something...
        source.changePackageAndName(nameRule, packageRule);
        source.removeImports(packageRule);
        source.removeMethodsByAnnotation(config.getDeleteMarkAnnotation());
        source.removeAnnotations(config.getAnnotationsToRemove());

        if (!packageRule.isSkipChangeImports()) {
            source.changeImports(nameRule, packageRule);
        }
        source.changeMethodUsingClassName(nameRule);
        ////

        // The class name after changed.
        String afterClassName = source.getClassName();

        if (customCodeHandler != null) {
            customCodeHandler.accept(source);
        }
        if (changeInfoHandler != null) {
            PackageRule.ChangeImport changeImport = new PackageRule.ChangeImport(beforeClassName, afterClassName);
            changeInfoHandler.accept(changeImport);
        }

        javaWriter.write(source);

        return source.getClassName();
    }

    private boolean determineConversion(JavaSource source) {
        //
        String convertAnnotation = config.getConvertMarkAnnotation();
        logger.trace("[Config] Convert Mark annotation --> " + convertAnnotation);
        if (convertAnnotation == null) {
            return true;
        }
        logger.trace("Need mark annotation({}) to convert.", convertAnnotation);
        return source.containsAnnotation(convertAnnotation);
    }

}
