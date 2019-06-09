package com.asiadream.jcode.tool.project.yaml;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

import static org.junit.Assert.*;

public class TypeTest {
    @Test
    public void whenLoadYAML_thenLoadCorrectImplicitTypes() {
        Yaml yaml = new Yaml();
        Map<Object, Object> document = yaml.load("3.0: 2018-07-22");

        assertNotNull(document);
        assertEquals(1, document.size());
        assertTrue(document.containsKey(3.0d));
    }
}
