package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Test;

public class JavaServiceTest extends BaseFileTest {

    @Test
    public void testCreate() {
        //
        JavaService javaService = new JavaService();
        // domain entity
        String domainEntityClassName = javaService.create("talk", "domain_entity", super.testDirName);
        // store interface
        javaService.createByRefClass(domainEntityClassName, "store_interface", super.testDirName);
        // store lifecycler
        javaService.createByRefClass(domainEntityClassName, "store_lycler", super.testDirName);
        // jpo
        javaService.createByRefClass(domainEntityClassName, "store_jpo", super.testDirName);

    }
}
