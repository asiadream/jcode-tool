package com.asiadream.jcode.tool.generator.source.gradle;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class GroovyMap implements Argument {
    //
    private Map<String, GroovyString> map = new LinkedHashMap<>();

    private static final String COLON = ":";

    public GroovyMap() {
        //
    }

    public GroovyMap(String key, String value) {
        //
        this.map.put(key, new GroovyString(value));
    }

    public GroovyMap put(String key, String value) {
        //
        this.map.put(key, new GroovyString(value));
        return this;
    }

    @Override
    public String print(int level) {
        //
        StringBuffer sb = new StringBuffer();

        int size = map.size();
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            String key = iter.next();
            GroovyString value = map.get(key);
            sb.append(key).append(COLON).append(" ").append(value.print());
            if (--size > 0) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
