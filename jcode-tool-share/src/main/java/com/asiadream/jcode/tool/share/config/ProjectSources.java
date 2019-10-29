package com.asiadream.jcode.tool.share.config;

public abstract class ProjectSources {
    //
    //public static final SourceFolders SOURCE_FOLDERS = SourceFolders.getDefault();

    // C://Users/user/Documents/.../src/main/java/com/foo/bar/SampleService.java -> com/foo/bar/SampleService.java
    public static String extractSourceFilePath(String physicalSourceFilePath, SourceFolders sourceFolders) {
        //
        int sourceFilePathIndex = computeSourceFilePathIndex(physicalSourceFilePath, sourceFolders);

        if (sourceFilePathIndex < 0) {
            throw new RuntimeException("physicalSourceFilePath is not correct! : " + physicalSourceFilePath);
        }

        return physicalSourceFilePath.substring(sourceFilePathIndex);
    }

    private static int computeSourceFilePathIndex(String physicalSourceFilePath, SourceFolders sourceFolders) {
        // if it contains src/main/java (ex ./source-project/src/main/java/foo/bar/Sample.java)
        int index = physicalSourceFilePath.indexOf(sourceFolders.source);
        if (index >= 0) {
            return index + sourceFolders.source.length() + 1;
        }

        // if it contains src/main/resources (ex ./source-project/src/main/resources/foo/bar/SampleSqlMap.xml)
        index = physicalSourceFilePath.indexOf(sourceFolders.resources);
        if (index >= 0) {
            return index + sourceFolders.resources.length() + 1;
        }

        return -1;
    }
}
