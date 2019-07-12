package com.asiadream.jcode.tool.generator.source.gradle;

import com.asiadream.jcode.tool.generator.source.gradle.GroovyMap;
import org.junit.Assert;
import org.junit.Test;

public class GroovyMapTest {

    @Test
    public void testPrint() {
        // group: 'io.naraplatform', name: 'accent', version: '0.3.1-SNAPSHOT'
        GroovyMap map = new GroovyMap();
        map.put("group", "io.naraplatform");
        map.put("name", "accent");
        map.put("version", "0.3.1-SNAPSHOT");
        System.out.println(map.print());
        Assert.assertEquals("group: 'io.naraplatform', name: 'accent', version: '0.3.1-SNAPSHOT'", map.print());
    }
}