package com.asiadream.jcode.tool.source.gradle;

import com.asiadream.jcode.tool.generator.source.gradle.AssignmentStatement;
import com.asiadream.jcode.tool.generator.source.gradle.GroovyDSL;
import com.asiadream.jcode.tool.generator.source.gradle.GroovyVariable;
import com.asiadream.jcode.tool.generator.source.gradle.MethodCall;
import org.junit.Assert;
import org.junit.Test;

public class GroovyDSLTest {

    @Test
    public void testPrint1() {
        String result = "dependencies {\n" +
                "    api project(':talk-domain-logic')\n" +
                "    testCompile('junit:junit')\n" +
                "}";

        GroovyDSL dsl = new GroovyDSL("dependencies")
                .addElement(new MethodCall("api", new MethodCall("project", ":talk-domain-logic").setPrintBracket(true)))
                .addElement(new MethodCall("testCompile", "junit:junit").setPrintBracket(true));
        System.out.println(dsl.print());
        Assert.assertEquals(result, dsl.print());
    }

    @Test
    public void testPrint2() {
        String result =
                "maven {\n" +
                "    credentials {\n" +
                "        username = nexususer\n" +
                "        password = nexuspassword\n" +
                "    }\n" +
                "    url '${nexusbaseurl}/nara-public/'\n" +
                "}";

        GroovyVariable username = new GroovyVariable("nexususer");
        GroovyVariable password = new GroovyVariable("nexuspassword");
        GroovyDSL dsl = new GroovyDSL("maven")
                .addElement(new GroovyDSL("credentials")
                        .addElement(new AssignmentStatement("username", username))
                        .addElement(new AssignmentStatement("password", password)))
                .addElement(new MethodCall("url", "${nexusbaseurl}/nara-public/"));
        System.out.println(dsl.print());
        Assert.assertEquals(result, dsl.print());
    }

}