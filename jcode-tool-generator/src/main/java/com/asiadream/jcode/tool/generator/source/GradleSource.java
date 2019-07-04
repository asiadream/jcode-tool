package com.asiadream.jcode.tool.generator.source;

import com.asiadream.jcode.tool.generator.source.gradle.GradleScript;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class GradleSource {
    //
    private static final Logger logger = LoggerFactory.getLogger(GradleSource.class);

    private GradleScript gradleScript;
    private String sourceFilePath;

    public GradleSource(GradleScript gradleScript, String sourceFilePath) {
        //
        this.gradleScript = gradleScript;
        this.sourceFilePath = sourceFilePath;
    }

    public void write(String physicalTargetFilePath) throws IOException {
        //
        File file = new File(physicalTargetFilePath);
        if (logger.isTraceEnabled()) {
            logger.trace(gradleScript.toString());
        }
        FileUtils.writeStringToFile(file, gradleScript.toString(), "UTF-8");
    }

    public GradleScript getGradleScript() {
        return gradleScript;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    @Override
    public String toString() {
        //
        return this.gradleScript.toString();
    }
}
