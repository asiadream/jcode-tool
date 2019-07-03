package com.asiadream.jcode.tool.generator.writer;

import com.asiadream.jcode.tool.generator.source.GradleSource;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;

import java.io.IOException;

public class GradleWriter implements Writer<GradleSource> {
    //
    private ProjectConfiguration configuration;

    public GradleWriter(ProjectConfiguration configuration) {
        //
        this.configuration = configuration;
    }

    @Override
    public void write(GradleSource source) throws IOException {
        //
    }
}
