package com.asiadream.jcode.tool.groovy.source;

import com.asiadream.jcode.tool.groovy.model.GradleScript;
import com.asiadream.jcode.tool.spec.source.Source;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class GradleSource implements Source {
    //
    private static final Logger logger = LoggerFactory.getLogger(GradleSource.class);

    private GradleScript gradleScript;
    private String sourceFilePath;

    public GradleSource(GradleScript gradleScript, String sourceFilePath) {
        //
        this.gradleScript = gradleScript;
        this.sourceFilePath = sourceFilePath;
    }

    @Override
    public void write(String physicalTargetFilePath) throws IOException {
        //
        File file = new File(physicalTargetFilePath);
        if (logger.isTraceEnabled()) {
            logger.trace("\n" + gradleScript.toString() + "\n");
        }
        FileUtils.writeStringToFile(file, gradleScript.toString(), "UTF-8");
    }

    public GradleScript getGradleScript() {
        return gradleScript;
    }

    @Override
    public String getSourceFilePath() {
        return sourceFilePath;
    }

    @Override
    public String toString() {
        //
        return this.gradleScript.toString();
    }
}
