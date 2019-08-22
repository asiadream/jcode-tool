package com.asiadream.jcode.tool.project;

import com.asiadream.jcode.tool.project.meta.ProjectCdo;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Test;

public class ProjectServiceTest extends BaseFileTest {
    @Test
    public void testCreateCase1() {
        //
        ProjectService service = new ProjectService();
        service.createNestedProject(new ProjectCdo("drama-depot", "io.test.msa", "0.1-SNAPSHOT", "depot", super.testDirName, true));
    }

    @Test
    public void testCreateCase2() {
        //
        ProjectService service = new ProjectService();
        service.createNestedProject(new ProjectCdo("foo-bar", "io.test.msa", "0.1-SNAPSHOT", "foo-bar", super.testDirName, true));
    }
}
