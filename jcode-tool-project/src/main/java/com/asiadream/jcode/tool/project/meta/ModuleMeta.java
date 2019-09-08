package com.asiadream.jcode.tool.project.meta;

import com.asiadream.jcode.tool.project.model.ProjectModel;
import com.asiadream.jcode.tool.project.model.ProjectType;

import java.util.*;

public class ModuleMeta {
    //
    private String name;
    private List<ProjectType> types;
    private List<DependencyMeta> dependencies;
    private List<String> packageNames;

    public ProjectModel toProjectModel(ProjectModel parent) {
        //
        Map<String, String> contextMap = getContext(parent);

        String name = ExpressionUtil.replaceExp(this.name, contextMap);
        String groupId = parent.getGroup();
        String version = parent.getVersion();
        ProjectModel projectModel = new ProjectModel(name, groupId, version);
        projectModel.setTypes(types);

        Optional.ofNullable(dependencies).ifPresent(dependencies -> dependencies.forEach(dependencyMeta -> {
            if (dependencyMeta.existRef()) {
                String ref = ExpressionUtil.replaceExp(dependencyMeta.getRef(), contextMap);
                ProjectModel subModel = parent.findBySuffix(ref);
                projectModel.addDependency(subModel, dependencyMeta.getType());
            } else {
                projectModel.addDependency(dependencyMeta.toDependencyModel());
            }
        }));

        List<String> replacedPackageNames = new ArrayList<>();
        Optional.ofNullable(packageNames).ifPresent(packageNames -> packageNames.forEach(packageName -> {
            replacedPackageNames.add(ExpressionUtil.replaceExp(packageName, contextMap));
        }));
        projectModel.setPackageNames(replacedPackageNames);

        return projectModel;
    }

    private Map<String, String> getContext(ProjectModel parent) {
        //
        Map<String, String> contextMap = new HashMap<>();
        contextMap.put("appName", parent.getAppName());
        contextMap.put("groupId", parent.getGroup());
        return contextMap;
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

    public String getAppliedName(String appName) {
        //
        return ExpressionUtil.replaceExp(this.name, "appName", appName);
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

    public List<String> getPackageNames() {
        return packageNames;
    }

    public void setPackageNames(List<String> packageNames) {
        this.packageNames = packageNames;
    }
}
