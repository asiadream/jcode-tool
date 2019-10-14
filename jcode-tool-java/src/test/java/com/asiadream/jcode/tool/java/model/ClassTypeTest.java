package com.asiadream.jcode.tool.java.model;

import org.junit.Assert;
import org.junit.Test;

public class ClassTypeTest {

    @Test
    public void testToStringJoining() {
        ClassType classType = ClassType.newClassType("java.util.List");
        classType.addTypeArgument(ClassType.newClassType("String"));
        classType.addTypeArgument(ClassType.newClassType("int"));
        System.out.println(classType.toString());
        Assert.assertEquals("java.util.List<String,int>", classType.toString());
    }

}