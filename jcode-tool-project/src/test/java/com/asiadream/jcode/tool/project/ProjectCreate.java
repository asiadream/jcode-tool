package com.asiadream.jcode.tool.project;


import com.asiadream.jcode.tool.project.creator.ProjectCreator;
import com.asiadream.jcode.tool.project.model.ProjectModel;

public class ProjectCreate {
    //
    private static final String TARGET_PATH = "./source_gen/javatool-parent";

    public static void main(String[] args) throws Exception {
        //
        ProjectModel model = new ProjectModel("sample-project", "com.sample", "1.0-SNAPSHOT", "pom");
        ProjectCreator creator = new ProjectCreator(TARGET_PATH);
        creator.create(model);
    }

}
