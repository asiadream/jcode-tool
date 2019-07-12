package com.asiadream.jcode.tool.generator.source.gradle;

import com.asiadream.jcode.tool.generator.source.gradle.GroovyMap;
import com.asiadream.jcode.tool.generator.source.gradle.MethodCall;
import org.junit.Assert;
import org.junit.Test;

public class MethodCallTest {

    @Test
    public void testPrint() {
        // case1: id 'maven'
        MethodCall mc1 = new MethodCall("id", "maven");
        System.out.println(mc1.print());
        Assert.assertEquals("id 'maven'", mc1.print());

        // case2: compile('io.naraplatform:accent:0.3.1-SNAPSHOT')
        MethodCall mc2 = new MethodCall("compile", "io.naraplatform:accent:0.3.1-SNAPSHOT");
        mc2.setPrintBracket(true);
        System.out.println(mc2.print());
        Assert.assertEquals("compile('io.naraplatform:accent:0.3.1-SNAPSHOT')", mc2.print());

        // case3: mavenLocal()
        MethodCall mc3 = new MethodCall("mavenLocal");
        System.out.println(mc3.print());
        Assert.assertEquals("mavenLocal()", mc3.print());

        // case4: id 'io.spring.dependency-management' version '1.0.7.RELEASE'
        MethodCall mc4 = new MethodCall("id", "io.spring.dependency-management"
                , new MethodCall("version", "1.0.7.RELEASE"));
        System.out.println(mc4.print());
        Assert.assertEquals("id 'io.spring.dependency-management' version '1.0.7.RELEASE'", mc4.print());

        // case5: compile group: 'io.naraplatform', name: 'accent', version: '0.3.1-SNAPSHOT'
        GroovyMap map = new GroovyMap().put("group", "io.naraplatform").put("name", "accent").put("version", "0.3.1-SNAPSHOT");
        MethodCall mc5 = new MethodCall("compile", map);
        System.out.println(mc5.print());
        Assert.assertEquals("compile group: 'io.naraplatform', name: 'accent', version: '0.3.1-SNAPSHOT'", mc5.print());

        // case6: apply plugin: 'java-library'
        MethodCall mc6 = new MethodCall("apply", new GroovyMap("plugin", "java-library"));
        System.out.println(mc6.print());
        Assert.assertEquals("apply plugin: 'java-library'", mc6.print());
    }

    @Test
    public void testNestedMethodCallPrint() {
        // api project(':talk-domain-entity')
        MethodCall mc = new MethodCall("api",
                new MethodCall("project", ":talk-domain-entity").setPrintBracket(true));
        System.out.println(mc.print());
        Assert.assertEquals("api project(':talk-domain-entity')", mc.print());
    }
}