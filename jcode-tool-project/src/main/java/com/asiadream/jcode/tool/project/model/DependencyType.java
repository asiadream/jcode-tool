package com.asiadream.jcode.tool.project.model;

public enum DependencyType {
    Project, // If dependency reference other sub project.
    Implementation,
    Compile,
    CompileOnly,
    AnnotationProcessor,
    TestCompile,
    TestCompileProject,
    Runtime,
    MavenBom,
    ;
}
