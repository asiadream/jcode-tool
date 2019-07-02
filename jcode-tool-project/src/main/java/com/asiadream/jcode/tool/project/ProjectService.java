package com.asiadream.jcode.tool.project;

import com.asiadream.jcode.tool.project.creator.NestedProjectCreator;
import com.asiadream.jcode.tool.project.meta.ProjectMeta;
import com.asiadream.jcode.tool.project.model.ProjectModel;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class ProjectService {
    //
    private ProjectMeta projectMeta = loadProjectMeta();

    public void createNestedProject(String name, String baseName, String workspace) {
        // ProjectMeta -> ProjectModel -> Project
        ProjectModel projectModel = projectMeta.toProjectModel(name, baseName, workspace);
        NestedProjectCreator projectCreator = new NestedProjectCreator(workspace);
        projectCreator.create(projectModel);
    }

    private ProjectMeta loadProjectMeta() {
        //
        Yaml yaml = new Yaml(new Constructor(ProjectMeta.class));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("project.yaml");
        ProjectMeta projectMeta = yaml.load(inputStream);
        return projectMeta;
    }

}
