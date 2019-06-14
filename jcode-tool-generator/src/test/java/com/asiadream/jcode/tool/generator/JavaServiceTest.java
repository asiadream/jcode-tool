package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Test;

public class JavaServiceTest extends BaseFileTest {

    @Test
    public void testCreate() {
        //
        JavaService javaService = new JavaService();
        // domain entity
        javaService.create("talk", "domain_entity", super.testDirName);
        // store interface
        javaService.create("talk", "store_interface", super.testDirName);
    }
}
