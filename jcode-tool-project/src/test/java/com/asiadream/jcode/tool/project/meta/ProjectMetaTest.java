package com.asiadream.jcode.tool.project.meta;

import com.asiadream.jcode.tool.project.model.ProjectType;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class ProjectMetaTest {

    @Test
    public void testFindEntityModule() {
        //
        ProjectMeta projectMeta = loadProjectMeta();
        ModuleMeta moduleMeta = projectMeta.findFirstModuleByType(ProjectType.Entity);
        Assert.assertNotNull(moduleMeta);
    }

    @Test
    public void testEntityModuleName() {
        //
        ProjectMeta projectMeta = loadProjectMeta();
        Assert.assertEquals("talk-domain-entity", projectMeta.findModuleName(ProjectType.Entity, "talk"));
    }


    private ProjectMeta loadProjectMeta() {
        //
        Yaml yaml = new Yaml(new Constructor(ProjectMeta.class));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("project.yaml");
        return yaml.load(inputStream);
    }

}