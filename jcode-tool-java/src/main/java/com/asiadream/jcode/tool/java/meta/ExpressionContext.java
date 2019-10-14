package com.asiadream.jcode.tool.java.meta;

import com.asiadream.jcode.tool.share.data.Pair;
import com.asiadream.jcode.tool.share.util.string.StringUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExpressionContext {
    //
    private Map<String, Object> contextMap;
    // Value in the Map may contain expressions.
    private Map<String, String> expressedParameterMap;

    public ExpressionContext() {
        //
        this.contextMap = new HashMap<>();
        this.expressedParameterMap = new HashMap<>();
    }

    public ExpressionContext(Map<String, String> expressedParameterMap) {
        //
        this.contextMap = new HashMap<>();
        this.expressedParameterMap = expressedParameterMap;
    }

    public String replaceExpString(String containingExpression) {
        //
        if (containingExpression == null) {
            return null;
        }

        // Replace 'containingExpression' with contextMap.
        String replaced = replaceWithContextMap(containingExpression);

        // Replace 'containingExpression' with expressedParameterMap.
        replaced = replaceWithExpressedParameterMap(replaced);

        // Check that all expressions have been replaced.
        check(replaced);

        return replaced;
    }

    private String replaceWithContextMap(String containingExpression) {
        //
        String replaced = containingExpression;
        for (String key : contextMap.keySet()) {
            Object value = contextMap.get(key);
            String exp = "${" + key + "}";
            if (value != null && value.getClass() == String.class && containingExpression.contains(exp)) {
                replaced = replaced.replace(exp, (String) value);
            }
        }
        return replaced;
    }

    private String replaceWithExpressedParameterMap(String containingExpression) {
        //
        String replaced = containingExpression;
        for (String key : expressedParameterMap.keySet()) {
            String valueMayContainExpression = expressedParameterMap.get(key);
            String exp = "${" + key + "}";
            if (valueMayContainExpression != null && replaced.contains(exp)) {
                String value = replaceWithContextMap(valueMayContainExpression);
                replaced = replaced.replace(exp, value);
            }
        }
        return replaced;
    }

    private void check(String replaced) {
        if (replaced.contains("${")) {
            throw new RuntimeException("could not found expression --> ${" + StringUtil.substringBetween(replaced, "${", "}") + "}");
        }
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
            if (value.getClass() != String.class && containingExpression.contains(exp)) {
                return value;
            }
        }
        return containingExpression;
    }

    // if value contains ${...}, replace value.
    public void updateExpressedValue() {
        for (Iterator<String> iter = contextMap.keySet().iterator(); iter.hasNext(); ) {
            String key = iter.next();
            Object value = contextMap.get(key);
            if (value != null && value.getClass() == String.class && ((String)value).contains("${")) {
                contextMap.put(key, replaceExpString((String)value));
            }
        }
    }

    public ExpressionContext add(String key, Object value) {
        //
        this.contextMap.put(key, value);
        return this;
    }

    public ExpressionContext addAll(Map<String, String> map) {
        //
        this.contextMap.putAll(map);
        return this;
    }

    public ExpressionContext addAll(List<Pair<String, String>> keyValues) {
        //
        keyValues.forEach(keyValue -> this.contextMap.put(keyValue.x, keyValue.y));
        return this;
    }

    public ExpressionContext addAllWithFirstUpperCase(List<Pair<String, String>> keyValues) {
        //
        keyValues.forEach(keyValue -> {
            String key = StringUtil.toFirstUpperCase(keyValue.x);
            String value = StringUtil.toFirstUpperCase(keyValue.y);
            this.contextMap.put(key, value);
        });
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

    public void setExpressedParameterMap(Map<String, String> expressedParameterMap) {
        this.expressedParameterMap = expressedParameterMap;
    }

    public String show(String title) {
        //
        StringBuffer sb = new StringBuffer();

        if (StringUtil.isNotEmpty(title)) {
            sb.append("\n-------------- " + title + " ---------------");
        } else {
            sb.append("\n-------------- Express Context ---------------");
        }

        for (Iterator<String> iter = contextMap.keySet().iterator(); iter.hasNext(); ) {
            String key = iter.next();
            Object value = contextMap.get(key);
            sb.append("\n").append(key).append(":").append(value);
        }
        sb.append("\n----------------------------------------------");
        return sb.toString();
    }

    @Override
    public String toString() {
        return this.contextMap.toString();
    }
}
