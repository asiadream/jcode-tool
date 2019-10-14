package com.asiadream.jcode.tool.java.main;

import com.asiadream.jcode.tool.java.model.ClassType;
import com.asiadream.jcode.tool.java.model.JavaModel;
import com.asiadream.jcode.tool.java.model.MethodModel;
import com.asiadream.jcode.tool.java.model.ParameterModel;
import com.asiadream.jcode.tool.java.source.JavaSource;
import com.asiadream.jcode.tool.java.writer.JavaWriter;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.spec.writer.Writer;

public class JavaWrite {
    //
    private static final String TARGET_PROJECT_PATH = "./source-project";

    public static void main(String[] args) throws Exception {
        //
        ProjectConfiguration targetConfiguration = new ProjectConfiguration(ConfigurationType.Target, TARGET_PROJECT_PATH);

        // 1. java file write
        JavaSource source = createSampleJavaSource();
        Writer<JavaSource> writer = new JavaWriter(targetConfiguration);
        writer.write(source);
    }

    private static JavaSource createSampleJavaSource() {
        //
        JavaModel javaModel = new JavaModel("com.foo.bar.Test", true);

        MethodModel methodModel = new MethodModel("hello", ClassType.newClassType("com.foo.bar.ResultDTO"));
        ParameterModel parameterModel = new ParameterModel(ClassType.newClassType("com.foo.bar.TestDTO"), "testDTO");
        methodModel.addParameterModel(parameterModel);
        javaModel.addMethodModel(methodModel);

        return new JavaSource(javaModel);
    }
}
