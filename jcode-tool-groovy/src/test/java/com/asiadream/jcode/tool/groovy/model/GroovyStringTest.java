package com.asiadream.jcode.tool.groovy.model;

import org.junit.Assert;
import org.junit.Test;

public class GroovyStringTest {
    //
    @Test
    public void testPrint() {
        // 'io.springfox:springfox-swagger2:2.5.0'
        GroovyString groovyString = new GroovyString("io.springfox:springfox-swagger2:2.5.0");
        System.out.println(groovyString.print());
        Assert.assertEquals("'io.springfox:springfox-swagger2:2.5.0'", groovyString.print());
    }
}