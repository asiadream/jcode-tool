package com.asiadream.jcode.tool.project;

import com.asiadream.jcode.tool.project.creator.NestedProjectCreator;
import com.asiadream.jcode.tool.project.meta.ProjectCdo;
import com.asiadream.jcode.tool.project.meta.ProjectMeta;
import com.asiadream.jcode.tool.project.model.ProjectModel;
import com.asiadream.jcode.tool.share.util.string.StringUtil;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class ProjectService {
    //
    private String metaLocation;
    private ProjectMeta projectMeta;

    public ProjectService() {
        //
        this(null);
    }

    public ProjectService(String metaLocation) {
        //
        this.metaLocation = metaLocation;
        this.projectMeta = loadProjectMeta();
    }

    public void createNestedProject(ProjectCdo cdo) {
        // ProjectMeta -> ProjectModel -> Project
        ProjectModel projectModel = projectMeta.toProjectModel(cdo);
        NestedProjectCreator projectCreator = new NestedProjectCreator(cdo.getWorkspace());
        projectCreator.create(projectModel, cdo.isGradle());
    }

    private ProjectMeta loadProjectMeta() {
        //
        Yaml yaml = new Yaml(new Constructor(ProjectMeta.class));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(getMetaLocation() + "project.yaml");
        ProjectMeta projectMeta = yaml.load(inputStream);
        return projectMeta;
    }

    public ProjectMeta getProjectMeta() {
        //
        return projectMeta;
    }

    private String getMetaLocation() {
        //
        return StringUtil.isNotEmpty(metaLocation) ? metaLocation + "/" : "";
    }

}
