package com.asiadream.jcode.tool.share.util.file;

import com.asiadream.jcode.tool.share.data.Pair;
import com.asiadream.jcode.tool.share.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public abstract class PathUtil {
    //
    private static Logger logger = LoggerFactory.getLogger(PathUtil.class);
    private static final String SEPARATOR = "/";

    // com.foo.bar -> com/foo/bar
    public static String toPath(String packageName) {
        //
        return packageName.replace(".", SEPARATOR);
    }

    // mc.oo.od.Sample -> mc.oo.od, Sample
    public static Pair<String, String> devideClassName(String className) {
        //
        String[] packageFrags = className.split("\\.");
        int length = packageFrags.length;

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length - 1; i++) {
            sb.append(packageFrags[i]);
            if (i < length - 2) {
                sb.append(".");
            }
        }

        return new Pair<>(sb.toString(), packageFrags[length - 1]);
    }

    // com/foo/bar/SampleDto.xml -> com/foo/bar, SampleDto.xml
    public static Pair<String, String> devideResourceName(String sourceFilePath) {
        //
        final String separator = getSeparator(sourceFilePath);
        String[] paths = sourceFilePath.split(separator.equals("\\") ? "\\\\" : separator); // for Windows
        int length = paths.length;

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length - 1; i++) {
            sb.append(paths[i]);
            if (i < length - 2) {
                sb.append(separator);
            }
        }

        return new Pair<>(sb.toString(), paths[length - 1]);
    }

    public static String getSeparator(String projectPath) {
        //
        if (projectPath.contains(File.separator)) {
            return File.separator;
        }
        return SEPARATOR;
    }

    // com/foo/bar/SampleDto.java -> com.foo.bar.SampleDto
    public static String toClassName(String sourceFilePath) {
        //
        final String seperator = getSeparator(sourceFilePath);
        String[] paths = sourceFilePath.split(seperator.equals("\\") ? "\\\\" : seperator); // for Windows
        int length = paths.length;
        String fileName = paths[length - 1];
        int dotIndex = fileName.indexOf(".");
        String name = fileName.substring(0, dotIndex);
        //String originExtension = fileName.substring(dotIndex + 1);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length - 1; i++) {
            sb.append(paths[i]);
            sb.append(".");
        }
        sb.append(name);

        logger.debug("toClassName: {} -> {}", sourceFilePath, sb.toString());
        return sb.toString();
    }


    // com.foo.bar.SampleDto -> com/foo/bar/SampleDto.java
    public static String toSourceFileName(String className, String extension) {
        //
        String[] packageFrags = className.split("\\.");
        int length = packageFrags.length;

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {
            sb.append(packageFrags[i]);
            if (i < length - 1) {
                sb.append(SEPARATOR);
            }
        }

        sb.append(".");

        // add extension
        if (StringUtil.isNotEmpty(extension)) {
            sb.append(extension);
        }
        return sb.toString();
    }

    // /Users/danielhan/IdeaProjects/foo/foo-client/src/main/java/io/naradrama/foo/client/crud/FooRestClient.java
    // --> /Users/danielhan/IdeaProjects/foo/foo-client
    public static String toProjectPath(String physicalSourceFilePath) {
        //
        int index = physicalSourceFilePath.indexOf("/src/main/java/");
        if (index < 0) {
            throw new RuntimeException("Not project path --> " + physicalSourceFilePath);
        }
        return physicalSourceFilePath.substring(0, index);
    }

    // /Users/danielhan/IdeaProjects/foo/foo-client/src/main/java/io/naradrama/foo/client/crud/FooRestClient.java
    // --> io/naradrama/foo/client/crud/FooRestClient.java
    public static String toSourceFilePath(String physicalSourceFilePath) {
        //
        int index = physicalSourceFilePath.indexOf("/src/main/java/");
        int startIndex = index + "/src/main/java".length() + 1;
        if (index < 0 || startIndex > physicalSourceFilePath.length() - 1) {
            throw new RuntimeException("Not project path --> " + physicalSourceFilePath);
        }
        return physicalSourceFilePath.substring(startIndex);
    }

    // SampleService -> SampleLogic
    public static String changeName(String name, String skipPostFix, String addPostFix) {
        //
        StringBuffer sb = new StringBuffer();
        if (skipPostFix != null && name.endsWith(skipPostFix)) {
            String sub = name.substring(0, name.length() - skipPostFix.length());
            sb.append(sub);
        } else {
            sb.append(name);
        }

        if (addPostFix != null) {
            sb.append(addPostFix);
        }

        return sb.toString();
    }

    // com.foo.bar --> com.foo.spec
    public static String changePackage(String packageName, int skipPackageCount, String[] addPackages) {
        //
        String[] packageFrags = packageName.split("\\.");
        int length = packageFrags.length;

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length - skipPackageCount; i++) {
            sb.append(packageFrags[i]);
            sb.append(".");
        }

        if (addPackages != null && addPackages.length > 0) {
            for (int i = 0; i < addPackages.length; i++) {
                sb.append(addPackages[i]);
                sb.append(".");
            }
        }

        // remove last '.'
        String result = sb.toString();
        if (result.endsWith(".")) {
            result = result.substring(0, result.length() - 1);
        }
        return result;
    }

    // com/foo/bar/Sample.xml --> com/foo/bar/SampleDao.java
    public static String changeFileName(String sourceFilePath, String namePostFix, String extension) {
        //
        return changePath(sourceFilePath, 0, null, namePostFix, extension);
    }

    public static String changeFileName(String sourceFilePath, String skipNamePostFix, String namePostFix, String extension) {
        //
        return changePath(sourceFilePath, 0, null, skipNamePostFix, namePostFix, extension);
    }

    // [folder with skipFolder]/[addFolders]/[name][namePostFix].[extension]
    public static String changePath(String sourceFilePath, int skipFolderCount, String[] addFolders, String namePostFix, String extension) {
        //
        return changePath(sourceFilePath, skipFolderCount, addFolders, null, namePostFix, extension);
    }

    public static String changePath(String sourceFilePath, int skipFolderCount, String[] addFolders, String skipNamePostFix, String namePostFix, String extension) {
        //
        final String separator = getSeparator(sourceFilePath);
        String[] paths = sourceFilePath.split(separator.equals("\\") ? "\\\\" : separator); // for Windows
        int length = paths.length;
        String fileName = paths[length - 1];
        int dotIndex = fileName.indexOf(".");
        String name = fileName.substring(0, dotIndex);
        String originExtension = fileName.substring(dotIndex + 1);

        // make new path with skipCount
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length - 1 - skipFolderCount; i++) {
            sb.append(paths[i]);
            sb.append(separator);
        }

        // add folders to new path
        if (addFolders != null && addFolders.length > 0) {
            for (int i = 0; i < addFolders.length; i++) {
                sb.append(addFolders[i]);
                sb.append(separator);
            }
        }

        // skipNamePostFix
        if (StringUtil.isNotEmpty(skipNamePostFix)) {
            if (name.endsWith(skipNamePostFix)) {
                int idx = name.indexOf(skipNamePostFix);
                name = name.substring(0, idx);
            }
        }

        // namePostFix
        sb.append(name);
        if (StringUtil.isNotEmpty(namePostFix)) {
            sb.append(namePostFix);
        }
        sb.append(".");

        // add extension
        if (StringUtil.isNotEmpty(extension)) {
            sb.append(extension);
        } else {
            sb.append(originExtension);
        }

        return sb.toString();
    }
}
