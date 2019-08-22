package com.asiadream.jcode.tool.project.meta;

import com.asiadream.jcode.tool.project.model.ProjectModel;
import com.asiadream.jcode.tool.project.model.ProjectType;

import java.util.List;
import java.util.Optional;

public class ProjectMeta {
    //
    private List<DependencyMeta> dependencies;
    private List<PropertyMeta> properties;
    private List<ModuleMeta> modules;

    public ProjectModel toProjectModel(ProjectCdo cdo) {
        //
        ProjectModel projectModel = new ProjectModel(cdo.name, cdo.group, cdo.version, "pom");
        projectModel.setAppName(cdo.appName);
        projectModel.setWorkspacePath(cdo.workspace);

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

    public String findModuleName(ProjectType projectType, String baseName) {
        //
        ModuleMeta finded = findFirstModuleByType(projectType);
        if (finded == null) {
            throw new RuntimeException("could not find module with " + projectType);
        }
        return finded.getAppliedName(baseName);
    }

    public ModuleMeta findFirstModuleByType(ProjectType projectType) {
        //
        if (modules == null) {
            return null;
        }

        for (ModuleMeta moduleMeta : modules) {
            if (moduleMeta.containsType(projectType)) {
                return moduleMeta;
            }
        }
        return null;
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
