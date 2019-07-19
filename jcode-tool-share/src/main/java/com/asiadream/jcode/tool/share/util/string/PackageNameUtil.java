package com.asiadream.jcode.tool.share.util.string;

public class PackageNameUtil {
    //
    private static final String PACKAGE_DELIM = "\\.";

    /**
     * Get the first element of the Package name.
     * Ex) com.foo.bar.kkk -> com
     * @param packageName A package name
     * @return The first element of the package name
     */
    public static String getFirstName(String packageName) {
        //
        return packageName.split(PACKAGE_DELIM)[0];
    }

    /**
     * Get the last element of the Package name.
     * Ex) com.foo.bar.kkk -> kkk
     * @param packageName A package name
     * @return The last element of the package name
     */
    public static String getLastName(String packageName) {
        //
        String[] elements = packageName.split(PACKAGE_DELIM);
        return elements[elements.length - 1];
    }

    /**
     * Remove the part of the package name.
     * Ex) com.foo.bar.kkk, foo.bar -> com.kkk
     * @param packageName A package name
     * @param partPackageNameToRemove A part package name to remove
     * @return The package name after removal.
     */
    public static String removePart(String packageName, String partPackageNameToRemove) {
        //
        String part = packageName
                .replace(partPackageNameToRemove, "")
                .replace("..", ".");

        if (part.startsWith(".")) {
            part = part.substring(1);
        }

        if (part.endsWith(".")) {
            part = part.substring(0, part.length() - 1);
        }

        return part;
    }

    /**
     * Remove the first element of the package name.
     * Ex) com.foo.bar.kkk -> foo.bar.kkk
     * @param packageName A package name
     * @return The package name after removal.
     */
    public static String removeFirstElement(String packageName) {
        //
        String[] arr1 = packageName.split(PACKAGE_DELIM);
        String[] arr2 = new String[arr1.length - 1];
        System.arraycopy(arr1, 1, arr2, 0, arr1.length - 1);
        return StringUtil.join(arr2, ".");
    }

    /**
     * Remove the last element of the package name.
     * Ex) com.foo.bar.kkk -> com.foo.bar
     * @param packageName A package name
     * @return The package name after removal.
     */
    public static String removeLastElement(String packageName) {
        //
        String[] arr1 = packageName.split(PACKAGE_DELIM);
        String[] arr2 = new String[arr1.length - 1];
        System.arraycopy(arr1, 0, arr2, 0, arr1.length - 1);
        return StringUtil.join(arr2, ".");
    }

    /**
     * Get the size of the element of the Package name.
     * @param packageName A package name
     * @return The size
     */
    public static int size(String packageName) {
        //
        return packageName.split(PACKAGE_DELIM).length;
    }
}
