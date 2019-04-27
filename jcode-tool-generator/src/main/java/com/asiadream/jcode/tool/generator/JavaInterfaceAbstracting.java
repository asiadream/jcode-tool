package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.generator.converter.JavaAbstractParam;
import com.asiadream.jcode.tool.generator.converter.JavaInterfaceAbstracter;
import com.asiadream.jcode.tool.share.args.OptionParser;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.share.rule.NameRule;

import java.io.IOException;

public class JavaInterfaceAbstracting {
    //
    public void execute(String sourceProjectHome, String targetProjectHome, String sourceDtoPackage, String sourceFileName) throws IOException {
        //
        ProjectConfiguration sourceConfiguration = new ProjectConfiguration(ConfigurationType.Source, sourceProjectHome, false);
        ProjectConfiguration targetConfiguration = new ProjectConfiguration(ConfigurationType.Target, targetProjectHome, false);
        JavaAbstractParam javaAbstractParam = new JavaAbstractParam();
        javaAbstractParam.setSourceDtoPackage(sourceDtoPackage);

        NameRule nameRule = NameRule.newInstance()
                .add("Service", "Logic");

        JavaInterfaceAbstracter abstracter = new JavaInterfaceAbstracter(sourceConfiguration, targetConfiguration, targetConfiguration,
                nameRule, null, javaAbstractParam);
        abstracter.convert(sourceFileName);
    }

    public static void main(String[] args) throws IOException {
        //
        OptionParser parser = new OptionParser();
        parser.accepts("sourceProjectHome", "A source project home path")
                .accepts("targetProjectHome", "A target project home path")
                .accepts("sourceDtoPackage", "Source Dto Package ex) com.foo")
                .accepts("sourceFileName", "A java class File name in source folder ex) com/foo/bar/SampleService.java")
                .parse(args);

        JavaInterfaceAbstracting javaInterfaceAbstracting = new JavaInterfaceAbstracting();
        javaInterfaceAbstracting.execute(parser.get("sourceProjectHome"), parser.get("targetProjectHome"), parser.get("sourceDtoPackage"), parser.get("sourceFileName"));
    }
}
