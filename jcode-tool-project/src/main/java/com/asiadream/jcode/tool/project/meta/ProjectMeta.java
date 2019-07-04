package com.asiadream.jcode.tool.project.meta;

import com.asiadream.jcode.tool.project.model.ProjectModel;

import java.util.List;
import java.util.Optional;

public class ProjectMeta {
    //
    private String group;
    private String version;
    private List<DependencyMeta> dependencies;
    private List<PropertyMeta> properties;
    private List<ModuleMeta> modules;

    public ProjectModel toProjectModel(String name, String baseName, String workspace) {
        //
        ProjectModel projectModel = new ProjectModel(name, group, version, "pom");
        projectModel.setBaseName(baseName);
        projectModel.setWorkspacePath(workspace);

        Optional.ofNullable(dependencies).ifPresent(deps -> deps.forEach(dep ->
                projectModel.addDependency(dep.toDependencyModel())));
        Optional.ofNullable(properties).ifPresent(props -> props.forEach(prop ->
                projectModel.addProperty(prop.getKey(), prop.getValue())));
        Optional.ofNullable(modules).ifPresent(mods -> mods.forEach(mod -> {
            ProjectModel subProject = mod.toProjectModel(projectModel);
            projectModel.add(subProject);
        }));

        return projectModel;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<DependencyMeta> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyMeta> dependencies) {
        this.dependencies = dependencies;
    }

    public List<PropertyMeta> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyMeta> properties) {
        this.properties = properties;
    }

    public List<ModuleMeta> getModules() {
        return modules;
    }

    public void setModules(List<ModuleMeta> modules) {
        this.modules = modules;
    }
}
