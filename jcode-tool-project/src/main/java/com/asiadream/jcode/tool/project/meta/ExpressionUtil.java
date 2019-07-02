package com.asiadream.jcode.tool.project.meta;

public class ExpressionUtil {

    public static String replaceExp(String src, String key, String replaceValue) {
        //
        String exp = "${" + key + "}";
        if (src.contains(exp)) {
            return src.replace(exp, replaceValue);
        }
        return src;
    }
}
