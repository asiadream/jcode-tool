package com.asiadream.jcode.tool.project.meta;

public class ProjectCdo {
    //
    String name;
    String group;
    String version;
    String appName; // Dash is allowed
    String workspace;
    boolean gradle;

    public ProjectCdo(String name, String group, String version, String appName, String workspace, boolean gradle) {
        this.name = name;
        this.group = group;
        this.version = version;
        this.appName = appName;
        this.workspace = workspace;
        this.gradle = gradle;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getVersion() {
        return version;
    }

    public String getAppName() {
        return appName;
    }

    public String getWorkspace() {
        return workspace;
    }

    public boolean isGradle() {
        return gradle;
    }
}
