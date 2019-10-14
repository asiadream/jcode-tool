package com.asiadream.jcode.tool.spec.converter;

public enum ProjectItemType {
    //
    Java("java"), MyBatisMapper("xml"), Pom("xml");

    private String extension;

    private ProjectItemType(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
}
