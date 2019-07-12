package com.asiadream.jcode.tool.generator.source.gradle;

import com.asiadream.jcode.tool.generator.source.gradle.GradleScript;
import com.asiadream.jcode.tool.generator.source.gradle.GroovyDSL;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class GradleScriptTest extends BaseFileTest {

    @Test
    public void testBasicWithFile() throws IOException {
        //
        GradleScript gradleScript = new GradleScript("test.gradle");

        String filePath = super.testDirName + File.separator + "test.gradle";
        File file = new File(filePath);
        FileUtils.writeStringToFile(file, gradleScript.toString(), "UTF-8");
    }

    @Test
    public void testPrint() {
        //
        GradleScript gradleScript = new GradleScript("test.gradle");
        gradleScript.addElement(new GroovyDSL("dependencies"));
        System.out.println(gradleScript.print());
        Assert.assertEquals("dependencies {\n" +
                "}", gradleScript.print());
    }


}