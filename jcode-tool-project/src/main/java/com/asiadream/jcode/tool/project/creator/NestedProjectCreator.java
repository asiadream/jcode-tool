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
        createProject(model, targetHomePath);
    }

    private void createProject(ProjectModel model, String targetHomePath) {
        //
        ProjectCreator creator = new ProjectCreator(targetHomePath);
        creator.create(model);

        if (model.hasChildren()) {
            for (ProjectModel child : model.getChildren()) {
                createProject(child, targetHomePath + ProjectSources.PATH_DELIM + model.getName());
            }
        }
    }
}
