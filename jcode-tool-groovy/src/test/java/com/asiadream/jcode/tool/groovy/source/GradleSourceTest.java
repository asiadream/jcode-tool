package com.asiadream.jcode.tool.groovy.source;

import com.asiadream.jcode.tool.groovy.model.GradleScript;
import com.asiadream.jcode.tool.groovy.model.GroovyDSL;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Test;

import java.io.File;

public class GradleSourceTest extends BaseFileTest {
    //
    @Test
    public void testWrite() throws Exception {
        //
        GradleSource gradleSource = new GradleSource(createGradleScript(), "test");
        System.out.println(gradleSource.toString());
        gradleSource.write(super.testDirName + File.separator + "test.gradle");
    }

    private GradleScript createGradleScript() {
        GradleScript gradleScript = new GradleScript("test.gradle");
        gradleScript.addElement(new GroovyDSL("dependencies"));
        return gradleScript;
    }
}