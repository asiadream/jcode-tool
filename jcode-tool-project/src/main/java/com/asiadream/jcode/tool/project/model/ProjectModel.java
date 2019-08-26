package com.asiadream.jcode.tool.project.model;

import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.share.util.string.StringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectModel {
    //
    private String name;
    private String group;
    private String version;
    private String packaging;
    private String appName;  // Dash is allowed
    private List<ProjectType> types;

    private ProjectModel parent;
    private List<ProjectModel> children;
    private List<Dependency> dependencies;
    private List<ProjectProperty> properties;

    private String workspacePath;

    public ProjectModel(String name, String groupId) {
        //
        this(name, groupId, null, null);
    }

    public ProjectModel(String name, String groupId, String version) {
        //
        this(name, groupId, version, null);
    }

    public ProjectModel(String name, String group, String version, String packaging) {
        //
        this.name = name;
        this.appName = name;
        this.group = group;
        this.version = version;
        this.packaging = packaging;
        this.children = new ArrayList<>();
        this.dependencies = new ArrayList<>();
        this.properties = new ArrayList<>();
    }

    public ProjectConfiguration configuration(ConfigurationType configurationType) {
        //
        assert workspacePath != null : "The workspacePath can't be null.";
        return new ProjectConfiguration(configurationType, workspacePath, name, false, false);
    }

    public ProjectModel findBySuffix(String suffix) {
        //
        if (this.name.endsWith(suffix)) {
            return this;
        }

        for (ProjectModel child : children) {
            ProjectModel finded = child.findBySuffix(suffix);
            if (finded != null) {
                return finded;
            }
        }
        return null;
    }

    public boolean isPom() {
        //
        return "pom".equalsIgnoreCase(packaging);
    }

    public boolean hasParent() {
        //
        return (parent != null);
    }

    public boolean isRoot() {
        //
        return (parent == null);
    }

    public ProjectModel add(ProjectModel child) {
        //
        child.setParent(this);
        if (this.workspacePath != null) {
            child.workspacePath = this.workspacePath + File.separator + this.name;
        }
        this.children.add(child);
        return this;
    }

    public boolean hasChildren() {
        //
        return children != null && children.size() > 0;
    }

    public ProjectModel addDependency(Dependency dependency) {
        //
        this.dependencies.add(dependency);
        return this;
    }

    public ProjectModel addDependency(ProjectModel dependencyProject) {
        return addDependency(dependencyProject, null);
    }

    public ProjectModel addDependency(ProjectModel dependencyProject, DependencyType type) {
        //
        Dependency dependency = new Dependency(dependencyProject.group, dependencyProject.name, "${project.version}");
        Optional.ofNullable(type).ifPresent(_type -> dependency.setType(_type));
        this.dependencies.add(dependency);
        return this;
    }

    public ProjectModel addDependencies(List<ProjectModel> dependencyProjects) {
        //
        if (dependencyProjects == null) {
            return this;
        }

        dependencyProjects.forEach(dependencyProject -> this.addDependency(dependencyProject, null));
        return this;
    }

    public ProjectModel addDependency(String groupId, String name) {
        //
        this.dependencies.add(new Dependency(groupId, name));
        return this;
    }

    public ProjectModel addDependency(String groupId, String name, String version) {
        //
        this.dependencies.add(new Dependency(groupId, name, version));
        return this;
    }

    public boolean hasDependencies() {
        //
        return dependencies != null && dependencies.size() > 0;
    }

    public ProjectModel addProperty(String key, String value) {
        //
        this.properties.add(new ProjectProperty(key, value));
        return this;
    }

    public ProjectModel addProperty(ProjectProperty property) {
        //
        this.properties.add(property);
        return this;
    }

    public boolean hasProperties() {
        //
        return properties != null && properties.size() > 0;
    }

    public String getAppNameWithRemoveDash() {
        //
        return StringUtil.remove(appName, "-");
    }

    public List<ProjectModel> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public ProjectModel getParent() {
        return parent;
    }

    public void setParent(ProjectModel parent) {
        this.parent = parent;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getWorkspacePath() {
        return workspacePath;
    }

    public ProjectModel setWorkspacePath(String workspacePath) {
        this.workspacePath = workspacePath;
        return this;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public List<ProjectProperty> getProperties() {
        return properties;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<ProjectType> getTypes() {
        return types;
    }

    public void setTypes(List<ProjectType> types) {
        this.types = types;
    }
}
