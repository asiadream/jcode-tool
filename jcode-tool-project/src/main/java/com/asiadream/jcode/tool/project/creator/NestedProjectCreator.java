package com.asiadream.jcode.tool.project.creator;

import com.asiadream.jcode.tool.project.model.ProjectModel;
import com.asiadream.jcode.tool.share.config.ProjectSources;

public class NestedProjectCreator {
    //
    private String targetHomePath;

    public NestedProjectCreator(String targetHomePath) {
        //
        this.targetHomePath = targetHomePath;
    }

    public void create(ProjectModel model) {
        //
        createProject(model, targetHomePath, false);
    }

    public void create(ProjectModel model, boolean isGradle) {
        //
        createProject(model, targetHomePath, isGradle);
    }

    private void createProject(ProjectModel model, String targetHomePath, boolean isGradle) {
        //
        ProjectCreator creator = new ProjectCreator(targetHomePath);
        creator.create(model, isGradle);

        if (model.hasChildren()) {
            for (ProjectModel child : model.getChildren()) {
                createProject(child, targetHomePath + ProjectSources.PATH_DELIM + model.getName(), isGradle);
            }
        }
    }
}
