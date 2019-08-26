package com.asiadream.jcode.tool.generator.reader;

import com.asiadream.jcode.tool.generator.source.JavaSource;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;

import java.io.IOException;

public class JavaReader implements Reader<JavaSource> {
    //
    private ProjectConfiguration configuration;

    public JavaReader(ProjectConfiguration configuration) {
        //
        this.configuration = configuration;
    }

    @Override
    public JavaSource read(String sourceFilePath) throws IOException {
        //
        String physicalSourceFilePath = configuration.makePhysicalJavaSourceFilePath(sourceFilePath);
        return new JavaSource(physicalSourceFilePath, configuration.isLexicalPreserving(), configuration.isUseOwnPrinter());
    }

    public boolean exists(String sourceFilePath) {
        //
        String physicalSourceFilePath = configuration.makePhysicalJavaSourceFilePath(sourceFilePath);
        return JavaSource.exists(physicalSourceFilePath);
    }
}
