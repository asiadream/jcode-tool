package com.asiadream.jcode.tool.project.yaml;

import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class BasicTest {
    @Test
    public void testRead() {
        Yaml yaml = new Yaml();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("yaml/Basic.yaml");
        Map<String, Object> obj = yaml.load(inputStream);
        System.out.println(obj);
        Assert.assertEquals("John", obj.get("firstName"));
        Assert.assertEquals("Doe", obj.get("lastName"));
        Assert.assertEquals(20, obj.get("age"));
    }
}
