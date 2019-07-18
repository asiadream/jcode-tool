package com.asiadream.jcode.tool.generator.main;

import com.asiadream.jcode.tool.generator.converter.MyBatisMapperCreator;
import com.asiadream.jcode.tool.share.args.OptionParser;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MyBatisMapperCreate {
    //
    private static final Logger logger = LoggerFactory.getLogger(MyBatisMapperCreate.class);

    public void execute(String sourceProjectHome, String targetProjectHome, String sourceFile) throws IOException {
        //
        ProjectConfiguration sourceConfiguration = new ProjectConfiguration(ConfigurationType.Source, sourceProjectHome, false);
        ProjectConfiguration targetConfiguration = new ProjectConfiguration(ConfigurationType.Target, targetProjectHome, false);

        MyBatisMapperCreator myBatisMapperCreator = new MyBatisMapperCreator(sourceConfiguration, sourceConfiguration,
                targetConfiguration);
        myBatisMapperCreator.convert(sourceFile);
    }


    public static void main(String[] args) throws Exception {
        //
        OptionParser parser = new OptionParser();
        parser
                .accepts("sourceProjectHome", "The source project home path")
                .accepts("targetProjectHome", "The target project home path")
                .accepts("sourceFile", "SqlMap file")
                .parse(args);

        MyBatisMapperCreate myBatisMapperCreate = new MyBatisMapperCreate();
        logger.info(parser.toString());
        myBatisMapperCreate.execute(parser.get("sourceProjectHome"), parser.get("targetProjectHome"), parser.get("sourceFile"));

    }

}
