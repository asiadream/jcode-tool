package com.asiadream.jcode.tool.share.util.string;

import com.asiadream.jcode.tool.share.util.file.PathUtil;

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

    // (com.foo.bar.SomeService, Service) -> some
    public static String getShortSimpleName(String className, String suffix) {
        //
        String simpleClassName = ClassNameUtil.getSimpleClassName(className);
        String removedSuffix = StringUtil.removeSuffix(simpleClassName, suffix);
        return StringUtil.toFirstLowerCase(removedSuffix);
    }

    // com.foo.bar.SampleDto -> com/foo/bar/SampleDto.java
    public static String toSourceFileName(String className) {
        //
        return PathUtil.toSourceFileName(className, "java");
    }
}
