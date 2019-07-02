package com.asiadream.jcode.tool.share.util.string;

public class ClassNameUtil {
    //
    public static String getPackageName(String className) {
        //
        int lastDotIndex = className.lastIndexOf(".");
        if (lastDotIndex > 0) {
            return className.substring(0, lastDotIndex);
        }
        return className;
    }

    public static String getSimpleClassName(String className) {
        //
        int lastDotIndex = className.lastIndexOf(".");
        if (lastDotIndex > 0) {
            return className.substring(lastDotIndex + 1);
        }
        return className;
    }
}
