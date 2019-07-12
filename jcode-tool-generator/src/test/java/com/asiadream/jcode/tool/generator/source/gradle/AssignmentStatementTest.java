package com.asiadream.jcode.tool.generator.source.gradle;

import com.asiadream.jcode.tool.generator.source.gradle.AssignmentStatement;
import com.asiadream.jcode.tool.generator.source.gradle.GroovyVariable;
import org.junit.Assert;
import org.junit.Test;

public class AssignmentStatementTest {

    @Test
    public void testPrint() {
        // case1: rootProject.name = 'drama-talk'
        AssignmentStatement as = new AssignmentStatement("rootProject.name", "drama-talk");
        System.out.println(as.print());
        Assert.assertEquals("rootProject.name = 'drama-talk'", as.print());

        // case2: username = nexususer
        GroovyVariable nexususer = new GroovyVariable("nexususer");
        AssignmentStatement as2 = new AssignmentStatement(nexususer, "admin");
        System.out.println(as2.print());
        AssignmentStatement as3 = new AssignmentStatement("username", nexususer);
        System.out.println(as3.print());
        Assert.assertEquals("username = nexususer", as3.print());
    }
}