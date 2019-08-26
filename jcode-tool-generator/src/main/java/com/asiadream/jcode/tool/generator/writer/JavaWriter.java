package com.asiadream.jcode.tool.generator.writer;

import com.asiadream.jcode.tool.generator.source.JavaSource;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;

import java.io.IOException;

public class JavaWriter implements Writer<JavaSource> {
    //
    private ProjectConfiguration configuration;

    public JavaWriter(ProjectConfiguration configuration) {
        //
        this.configuration = configuration;
    }

    @Override
    public void write(JavaSource source) throws IOException {
        //
        String targetFilePath = source.getSourceFilePath();
        String physicalTargetFilePath = configuration.makePhysicalJavaSourceFilePath(targetFilePath);
        writeSource(source, physicalTargetFilePath);
    }

    private void writeSource(JavaSource source, String physicalTargetFilePath) throws IOException {
        //
        source.setUseOwnPrinter(configuration.isUseOwnPrinter());
        source.write(physicalTargetFilePath);
    }
}
