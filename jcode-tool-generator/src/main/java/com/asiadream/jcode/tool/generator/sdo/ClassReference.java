package com.asiadream.jcode.tool.generator.sdo;

import com.asiadream.jcode.tool.generator.source.JavaSource;
import com.asiadream.jcode.tool.share.util.string.ClassNameUtil;

import java.util.function.BiFunction;

public class ClassReference {
    //
    private String name;
    private String className;
    private String projectPath;

    public ClassReference(String name, String className, String projectPath) {
        this.name = name;
        this.className = className;
        this.projectPath = projectPath;
    }

    public String getSimpleClassName() {
        //
        return ClassNameUtil.getSimpleClassName(className);
    }

    public JavaSource read(BiFunction<String, String, JavaSource> handler) {
        //
        return handler.apply(projectPath, className);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
}
