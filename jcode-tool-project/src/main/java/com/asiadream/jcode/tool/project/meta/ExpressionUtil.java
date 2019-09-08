package com.asiadream.jcode.tool.project.meta;

import java.util.Map;

public class ExpressionUtil {

    public static String replaceExp(String src, String key, String replaceValue) {
        //
        String exp = "${" + key + "}";
        if (src.contains(exp)) {
            return src.replace(exp, replaceValue);
        }
        return src;
    }

    public static String replaceExp(String containingExpression, Map<String, String> contextMap) {
        //
        String replaced = containingExpression;
        for (String key : contextMap.keySet()) {
            String replaceValue = contextMap.get(key);
            replaced = replaceExp(replaced, key, replaceValue);
        }
        return replaced;
    }
}
