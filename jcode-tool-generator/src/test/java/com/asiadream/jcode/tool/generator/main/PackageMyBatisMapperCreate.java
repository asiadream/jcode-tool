package com.asiadream.jcode.tool.generator.main;

import com.asiadream.jcode.tool.generator.converter.MyBatisMapperCreator;
import com.asiadream.jcode.tool.generator.converter.PackageConverter;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;

public class PackageMyBatisMapperCreate {
    //
    private static final String SOURCE_PROJECT_PATH = "./source-project";

    public static void main(String[] args) throws Exception {
        //
        ProjectConfiguration sourceConfiguration = new ProjectConfiguration(ConfigurationType.Source, SOURCE_PROJECT_PATH);
        ProjectConfiguration targetConfiguration = new ProjectConfiguration(ConfigurationType.Target, SOURCE_PROJECT_PATH);

        MyBatisMapperCreator myBatisMapperCreator = new MyBatisMapperCreator(sourceConfiguration, sourceConfiguration,
                targetConfiguration);
        PackageConverter packageConverter = new PackageConverter(myBatisMapperCreator);
        packageConverter.convert("foo.bar");
    }

}
