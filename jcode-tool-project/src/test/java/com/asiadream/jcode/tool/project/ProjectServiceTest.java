package com.asiadream.jcode.tool.project;

import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Test;

public class ProjectServiceTest extends BaseFileTest {
    @Test
    public void testCreate() {
        ProjectService service = new ProjectService();
        service.createNestedProject("drama-talk", "talk", super.testDirName);
    }
}
