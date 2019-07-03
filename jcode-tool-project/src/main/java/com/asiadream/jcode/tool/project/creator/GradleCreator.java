package com.asiadream.jcode.tool.project.creator;

import com.asiadream.jcode.tool.generator.writer.GradleWriter;
import com.asiadream.jcode.tool.project.model.ProjectModel;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;

public class GradleCreator {
    //
    private static final String GRADLE_FILE = "build.gradle";
    private GradleWriter gradleWriter;

    public GradleCreator(ProjectConfiguration configuration) {
        //
        this.gradleWriter = new GradleWriter(configuration);
    }

    public void create(ProjectModel model) {
    }
}
