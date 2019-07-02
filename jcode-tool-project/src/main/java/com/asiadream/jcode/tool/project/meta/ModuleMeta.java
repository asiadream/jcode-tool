package com.asiadream.jcode.tool.project.meta;

import com.asiadream.jcode.tool.project.model.ProjectModel;

import java.util.List;
import java.util.Optional;

public class ModuleMeta {
    //
    private String name;
    private List<DependencyMeta> dependencies;

    public ProjectModel toProjectModel(ProjectModel parent) {
        //
        String name = ExpressionUtil.replaceExp(this.name, "baseName", parent.getBaseName());
        String groupId = parent.getGroupId();
        String version = parent.getVersion();
        ProjectModel projectModel = new ProjectModel(name, groupId, version);

        Optional.ofNullable(dependencies).ifPresent(dependencies -> dependencies.forEach(dependencyMeta -> {
            if (dependencyMeta.existRef()) {
                String ref = ExpressionUtil.replaceExp(dependencyMeta.getRef(), "baseName", parent.getBaseName());
                ProjectModel subModel = parent.findBySuffix(ref);
                projectModel.addDependency(subModel);
            } else {
                projectModel.addDependency(dependencyMeta.getGroupId(), dependencyMeta.getName(), dependencyMeta.getVersion());
            }
        }));

        return projectModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DependencyMeta> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyMeta> dependencies) {
        this.dependencies = dependencies;
    }
}
