package com.asiadream.jcode.tool.generator.main;

import com.asiadream.jcode.tool.share.test.BaseFileTest;

import java.io.IOException;

public class JavaInterfaceAbstractingTest extends BaseFileTest {
    //
    private static final String SOURCE_PROJECT_HOME = "../source-project";
    private static final String SOURCE_FILE_NAME = "com/foo/bar/service/SampleService.java";
    private static final String SOURCE_BASE_PACKAGE = "com.foo";

    //@Test
    public void testExecute() throws IOException {
        //
        JavaInterfaceAbstracting abstracting = new JavaInterfaceAbstracting();
        abstracting.execute(SOURCE_PROJECT_HOME, super.testDirName, SOURCE_BASE_PACKAGE, SOURCE_FILE_NAME);
    }
}
