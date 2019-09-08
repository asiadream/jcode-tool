package com.asiadream.jcode.tool.share.config;

import com.asiadream.jcode.tool.share.util.file.PathUtil;

import java.io.File;

public class ProjectConfiguration {
    //
    private static final String PATH_DELIM = File.separator;

    private final ConfigurationType type;
    private final String projectHomePath;
    private final SourceFolders sourceFolders;

    private final String physicalJavaPath;
    private final String physicalResourcesPath;
    private final String physicalTestPath;
    private final String physicalTestResourcesPath;

    private final boolean lexicalPreserving;
    private final boolean useOwnPrinter;

    // - Terms
    // sourceFolder         : 소스 폴더                                      : src/main/java
    // sourcePath           : 소스 폴더 내의 Path                             : com/foo/bar
    // sourceFile           : 소스 폴더 내의 File(Path 포함)                   : com/foo/bar/SampleService.java
    // sourceFilePath       : 소스 폴더 내의 Path or File                     : com/foo/bar/SampleService.java
    // fileName             : 파일명(Path 미포함)                             : SampleService.java
    // physicalJavaPath     : 프로젝트 자바소스 물리적 절대경로                    : C://Users/user/Documents/.../src/main/java
    // physicalSourcePath   : 소스가 되는 Path 의 물리적 절대경로                 : C://Users/user/Documents/.../src/main/java/com/foo/bar
    // physicalSourceFile   : 소스가 되는 File 의 물리적 절대경로                 : C://Users/user/Documents/.../src/main/java/com/foo/bar/SampleService.java
    // physicalSourceFilePath   : 소스가 되는 Path or File 의 물리적 절대경로     : C://Users/user/Documents/.../src/main/java/com/foo/bar/SampleService.java

    public ProjectConfiguration(ConfigurationType type, String workspacePath, String projectName, boolean lexicalPreserving, boolean useOwnPrinter) {
        //
        this(type, workspacePath + PATH_DELIM + projectName, lexicalPreserving, useOwnPrinter);
    }

    public ProjectConfiguration(ConfigurationType type, String projectHomePath, boolean lexicalPreserving, boolean useOwnPrinter) {
        //
        this(type, projectHomePath, ProjectSources.SOURCE_FOLDERS, lexicalPreserving, useOwnPrinter);
    }

    public ProjectConfiguration(ConfigurationType type, String projectHomePath) {
        //
        this(type, projectHomePath, ProjectSources.SOURCE_FOLDERS, false, false);
    }

    public ProjectConfiguration(ConfigurationType type, String projectHomePath, SourceFolders sourceFolders, boolean lexicalPreserving, boolean useOwnPrinter) {
        //
        this.type = type;
        this.projectHomePath = projectHomePath;
        this.sourceFolders = sourceFolders;
        this.physicalJavaPath = projectHomePath + PATH_DELIM + sourceFolders.SRC_MAIN_JAVA;
        this.physicalResourcesPath = projectHomePath + PATH_DELIM + sourceFolders.SRC_MAIN_RESOURCES;
        this.physicalTestPath = projectHomePath + PATH_DELIM + sourceFolders.SRC_TEST_JAVA;
        this.physicalTestResourcesPath = projectHomePath + PATH_DELIM + sourceFolders.SRC_TEST_RESOURCES;
        this.lexicalPreserving = lexicalPreserving;
        this.useOwnPrinter = useOwnPrinter;
    }

    // com/foo/bar/SampleService.java -> C://Users/user/Documents/.../src/main/java/com/foo/bar/SampleService.java
    public String makePhysicalJavaSourceFilePath(String sourceFilePath) {
        //
        return physicalJavaPath + PATH_DELIM + sourceFilePath;
    }

    // com.foo.bar -> C://Users/user/Documents/.../src/main/java/com/foo/bar
    public String makePhysicalJavaPackagePath(String packageName) {
        //
        return physicalJavaPath + PATH_DELIM + PathUtil.toPath(packageName);
    }

    // com/foo/bar/SampleSqlMap.xml -> C://Users/user/Documents/.../src/main/java/com/foo/bar/SampleSqlMap.xml
    public String makePhysicalResourceFilePath(String sourceFilePath) {
        //
        return physicalResourcesPath + PATH_DELIM + sourceFilePath;
    }

    // pom.xml -> C://Users/user/Documents/.../SomeProject/pom.xml
    public String makePhysicalHomeFilePath(String sourceFilePath) {
        //
        return projectHomePath + PATH_DELIM + sourceFilePath;
    }

    public ConfigurationType getType() {
        return type;
    }

    public String getProjectHomePath() {
        return projectHomePath;
    }

    public String getPhysicalJavaPath() {
        return physicalJavaPath;
    }

    public String getPhysicalResourcesPath() {
        return physicalResourcesPath;
    }

    public String getPhysicalTestPath() {
        return physicalTestPath;
    }

    public String getPhysicalTestResourcesPath() {
        return physicalTestResourcesPath;
    }

    public SourceFolders getSourceFolders() {
        return sourceFolders;
    }

    public boolean isLexicalPreserving() {
        return lexicalPreserving;
    }

    public boolean isUseOwnPrinter() {
        return useOwnPrinter;
    }
}
