package com.asiadream.jcode.tool.share.util.string;

public class PackageNameUtil {
    //
    private static final String PACKAGE_DELIM = "\\.";

    public static String getFirstName(String packageName) {
        //
        return packageName.split(PACKAGE_DELIM)[0];
    }

    public static String getRemainNamesExceptFirst(String packageName) {
        //
        String[] arr1 = packageName.split(PACKAGE_DELIM);
        String[] arr2 = new String[arr1.length - 1];
        System.arraycopy(arr1, 1, arr2, 0, arr1.length - 1);
        return StringUtil.join(arr2, ".");
    }

    public static int size(String packageName) {
        //
        return packageName.split(PACKAGE_DELIM).length;
    }
}
