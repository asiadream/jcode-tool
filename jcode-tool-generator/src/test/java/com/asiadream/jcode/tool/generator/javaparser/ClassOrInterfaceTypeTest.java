package com.asiadream.jcode.tool.generator.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import org.junit.Assert;
import org.junit.Test;

public class ClassOrInterfaceTypeTest {
    @Test
    public void testStringArray() {
        ClassOrInterfaceType type = JavaParser.parseClassOrInterfaceType("String");
        ArrayType arrayType = new ArrayType(type);
        System.out.println(arrayType); // String[]
        Assert.assertEquals("String[]", arrayType.toString());
    }
}
