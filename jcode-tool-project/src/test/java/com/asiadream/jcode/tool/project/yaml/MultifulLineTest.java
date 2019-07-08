package com.asiadream.jcode.tool.project.yaml;

import com.asiadream.jcode.tool.project.yaml.model.MultifulLine;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class MultifulLineTest {

    @Test
    public void testLine() {
        // https://docs.ansible.com/ansible/latest/reference_appendices/YAMLSyntax.html
        Yaml yaml = new Yaml(new Constructor(MultifulLine.class));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("yaml/MultifulLine.yaml");
        MultifulLine line = yaml.load(inputStream);
        System.out.println(new Gson().toJson(line));

        System.out.println("includeNewlines: " + line.getIncludeNewlines());
        Assert.assertEquals("exactly as you see\nwill appear these three\nlines of poetry\n", line.getIncludeNewlines());
        System.out.println("FoldNewlines: " + line.getFoldNewlines());
        Assert.assertEquals("this is really a single line of text despite appearances", line.getFoldNewlines());
        System.out.println("containExpression" + line.getContainExpression());
        Assert.assertEquals("${a.b}", line.getContainExpression());
    }
}
