package com.asiadream.jcode.tool.project.model;

import com.asiadream.jcode.tool.share.config.ConfigurationType;
import org.junit.Test;

public class ProjectModelTest {

    @Test(expected = AssertionError.class)
    public void testAssert() {
        //
        ProjectModel model = new ProjectModel("SampleProject", "com.sample", "1.0-SNAPSHOT");
        model.setWorkspacePath(null);
        model.configuration(ConfigurationType.Target);
    }
}
