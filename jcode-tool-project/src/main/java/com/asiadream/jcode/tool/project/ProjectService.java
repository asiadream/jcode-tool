package com.asiadream.jcode.tool.project;

import com.asiadream.jcode.tool.project.creator.NestedProjectCreator;
import com.asiadream.jcode.tool.project.meta.ProjectCdo;
import com.asiadream.jcode.tool.project.meta.ProjectMeta;
import com.asiadream.jcode.tool.project.model.ProjectModel;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class ProjectService {
    //
    private ProjectMeta projectMeta = loadProjectMeta();

    public void createNestedProject(ProjectCdo cdo) {
        // ProjectMeta -> ProjectModel -> Project
        ProjectModel projectModel = projectMeta.toProjectModel(cdo);
        NestedProjectCreator projectCreator = new NestedProjectCreator(cdo.getWorkspace());
        projectCreator.create(projectModel, cdo.isGradle());
    }

    private ProjectMeta loadProjectMeta() {
        //
        Yaml yaml = new Yaml(new Constructor(ProjectMeta.class));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("project.yaml");
        ProjectMeta projectMeta = yaml.load(inputStream);
        return projectMeta;
    }

    public ProjectMeta getProjectMeta() {
        //
        return projectMeta;
    }

}
