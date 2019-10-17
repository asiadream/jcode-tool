package com.asiadream.jcode.tool.project.creator;

import com.asiadream.jcode.tool.project.model.ProjectModel;

import java.io.File;

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
                createProject(child, targetHomePath + File.separator + model.getName(), isGradle);
            }
        }
    }
}
