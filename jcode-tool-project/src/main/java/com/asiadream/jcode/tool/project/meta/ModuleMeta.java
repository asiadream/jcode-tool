package com.asiadream.jcode.tool.project.meta;

import com.asiadream.jcode.tool.project.model.ProjectModel;

import java.util.List;

public class ModuleMeta {
    //
    private String suffix;
    private List<DependencyMeta> dependencies;

    public ProjectModel toProjectModel(ProjectModel parent) {
        //
        String name = parent.getName() + suffix;
        String groupId = parent.getGroupId();
        String version = parent.getVersion();
        ProjectModel projectModel = new ProjectModel(name, groupId, version);
        if (dependencies != null) {
            for (DependencyMeta dependencyMeta : dependencies) {
                if (dependencyMeta.existRef()) {
                    ProjectModel subModel = parent.findBySuffix(dependencyMeta.getRef());
                    projectModel.addDependency(subModel);
                } else {
                    projectModel.addDependency(dependencyMeta.getGroupId(), dependencyMeta.getName(), dependencyMeta.getVersion());
                }
            }
        }
        return projectModel;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public List<DependencyMeta> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyMeta> dependencies) {
        this.dependencies = dependencies;
    }
}
