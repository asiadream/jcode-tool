package com.asiadream.jcode.tool.generator.meta;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExpressionContext {
    //
    private Map<String, Object> contextMap;

    public ExpressionContext() {
        //
        this.contextMap = new HashMap<>();
    }

    public String replaceExpString(String containingExpression) {
        //
        return (String)replaceExp(containingExpression);
    }

    public Object replaceExp(String containingExpression) {
        // "${exp}" -> "Hello"
        if (containingExpression == null) {
            return null;
        }

        for (Iterator<String> iter = contextMap.keySet().iterator(); iter.hasNext(); ) {
            String key = iter.next();
            Object value = contextMap.get(key);
            String exp = "${" + key + "}";
            if (containingExpression.contains(exp)) {
                if (value != null && value.getClass() == String.class) {
                    return containingExpression.replace(exp, (String)value);
                } else {
                    return value;
                }
            }
        }
        return containingExpression;
    }

    public ExpressionContext add(String key, Object value) {
        //
        this.contextMap.put(key, value);
        return this;
    }

    public Object get(String key) {
        //
        return contextMap.get(key);
    }

    public String getString(String key) {
        //
        return contextMap.get(key).toString();
    }

    public boolean contains(String key) {
        //
        return contextMap.containsKey(key);
    }

}
