package com.asiadream.jcode.tool.project.meta;

import com.asiadream.jcode.tool.project.model.ProjectModel;
import com.asiadream.jcode.tool.project.model.ProjectType;

import java.util.List;
import java.util.Optional;

public class ModuleMeta {
    //
    private String name;
    private List<ProjectType> types;
    private List<DependencyMeta> dependencies;

    public ProjectModel toProjectModel(ProjectModel parent) {
        //
        String name = ExpressionUtil.replaceExp(this.name, "baseName", parent.getBaseName());
        String groupId = parent.getGroup();
        String version = parent.getVersion();
        ProjectModel projectModel = new ProjectModel(name, groupId, version);
        projectModel.setTypes(types);

        Optional.ofNullable(dependencies).ifPresent(dependencies -> dependencies.forEach(dependencyMeta -> {
            if (dependencyMeta.existRef()) {
                String ref = ExpressionUtil.replaceExp(dependencyMeta.getRef(), "baseName", parent.getBaseName());
                ProjectModel subModel = parent.findBySuffix(ref);
                projectModel.addDependency(subModel, dependencyMeta.getType());
            } else {
                projectModel.addDependency(dependencyMeta.toDependencyModel());
            }
        }));

        return projectModel;
    }

    public boolean containsType(ProjectType projectType) {
        if (types == null) {
            return false;
        }
        for (ProjectType type : types) {
            if (type == projectType) {
                return true;
            }
        }
        return false;
    }

    public String getAppliedName(String baseName) {
        //
        return ExpressionUtil.replaceExp(this.name, "baseName", baseName);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProjectType> getTypes() {
        return types;
    }

    public void setTypes(List<ProjectType> types) {
        this.types = types;
    }

    public List<DependencyMeta> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<DependencyMeta> dependencies) {
        this.dependencies = dependencies;
    }
}
