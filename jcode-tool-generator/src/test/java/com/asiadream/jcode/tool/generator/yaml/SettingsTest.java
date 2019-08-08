package com.asiadream.jcode.tool.generator.yaml;

import com.google.gson.Gson;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SettingsTest {
    @Test
    public void testLoadSettings() {
        //
        Map<String, Object> map = load();
        System.out.println(((Map)map.get("storeInterface")).get("packageName"));
    }

    @Test
    public void testSettingParameterization() {
        //
        Map<String, Object> map = load();
        Map<String, String> parameters = toParameter(map, new HashMap<>(), null);
        System.out.println(new Gson().toJson(parameters));
    }

    private Map<String, String> toParameter(Map<String, Object> srcMap, HashMap<String, String> targetMap, String keyPrefix) {
        //
        for (String key : srcMap.keySet()) {
            Object value = srcMap.get(key);
            String showKey = keyPrefix != null ? keyPrefix + "." + key : key;
            if (value.getClass() == String.class) {
                targetMap.put(showKey, (String) value);
            } else if (value instanceof Map) {
                toParameter((Map<String, Object>) value, targetMap, showKey);
            }
        }
        return targetMap;
    }

    private Map<String, Object> load() {
        Yaml yaml = new Yaml();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("settings.yaml");
        Map<String, Object> map = yaml.load(inputStream);
        return map;
    }
}
