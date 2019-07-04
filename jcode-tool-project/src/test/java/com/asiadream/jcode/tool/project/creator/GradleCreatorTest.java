package com.asiadream.jcode.tool.project.creator;

import com.asiadream.jcode.tool.project.model.ProjectModel;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Test;

public class GradleCreatorTest extends BaseFileTest {
    //
    @Test
    public void testCreate() throws Exception {
        //
        ProjectModel model = new ProjectModel("SampleProject", "com.foo.bar", "1.0-SNAPSHOT", "pom");
        model.setParent(new ProjectModel("ParentProject", "com.foo.bar", "1.0-SNAPSHOT", "pom"));

        ProjectConfiguration projectConfiguration = new ProjectConfiguration(ConfigurationType.Target, super.testDirName);
        GradleCreator gradleCreator = new GradleCreator(projectConfiguration);
        gradleCreator.create(model);
    }
}