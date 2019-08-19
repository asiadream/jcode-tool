package com.asiadream.jcode.tool.share.util.string;

import com.google.common.base.CaseFormat;

public class StringUtil extends org.apache.commons.lang3.StringUtils {
    //
    public static String getRecommendedVariableName(String name) {
        //
        if (isJavaKeyword(name)) {
            return name + "00";
        }
        return toFirstLowerCase(name);
    }

    private static final String[] JAVA_KEYWORDS = {"abstract","boolean","break","byte","catch","char","class","continue",
            "default","do","double","else","extends","finally","float","for","if","implements","import","instanceof",
            "int","interface","long","native","new","null","package","private","protected","public","return","short",
            "static","super","switch","sychronized","this","throw","throws","try","void","while"};

    private static boolean isJavaKeyword(String name) {
        //
        String varName = name.toLowerCase();
        for (String keyword : JAVA_KEYWORDS) {
            if (keyword.equals(varName)) {
                return true;
            }
        }
        return false;
    }

    public static String toFirstLowerCase(String str) {
        //
        if (str == null) return null;

        if (Character.isLowerCase(str.charAt(0))) {
            return str;
        }

        char c[] = str.toCharArray();
        c[0] += 32;
        return new String(c);
    }

    public static String toFirstUpperCase(String str) {
        //
        if (str == null) return null;

        if (str.length() <= 0) {
            return str;
        }

        if (!Character.isLetter(str.charAt(0))) {
            return str;
        }

        if (Character.isUpperCase(str.charAt(0))) {
            return str;
        }

        char c[] = str.toCharArray();
        c[0] -= 32;
        return new String(c);
    }

    public static String removeSuffix(String s, String suffix) {
        //
        if (s != null && suffix != null && s.endsWith(suffix)) {
            return s.substring(0, s.length() - suffix.length());
        }
        return s;
    }

    public static String removePrefix(String s, String prefix) {
        //
        if (s != null && prefix != null && s.startsWith(prefix)) {
            return s.substring(prefix.length());
        }
        return s;
    }

    public static String toCamelCase(String upperUnderscoreString) {
        // HELLO_CAMEL_CASE --> helloCamelCase
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, upperUnderscoreString);
    }
}
