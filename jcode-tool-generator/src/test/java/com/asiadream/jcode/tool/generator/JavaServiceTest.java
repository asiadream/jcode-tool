package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.generator.sdo.ClassReference;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JavaServiceTest extends BaseFileTest {
    //
    private final String appName = "talk";
    private JavaService javaService = new JavaService();

    @Test
    public void testCreateEntity() {
        //
        // domain entity
        String domainEntityClassName = javaService.create(appName, "domain_entity", super.testDirName);
        // store interface
        //javaService.createByRefClass(domainEntityClassName, "store_interface", super.testDirName);
        // store lifecycler
        //javaService.createByRefClass(domainEntityClassName, "store_lycler", super.testDirName);
        // jpo
        //javaService.create(appName, newClassRefs(domainEntityClassName), "store_jpo", super.testDirName);
        // task
        //javaService.createByRefClass(domainEntityClassName, "task", super.testDirName);
    }

    @Test
    public void testCreateStoreInterface() {
        //
        String domainEntityClassName = javaService.create(appName, "domain_entity", super.testDirName);
        javaService.create(appName, newClassRefs(domainEntityClassName), "store_interface", super.testDirName);
    }

    @Test
    public void testCreateTask() {
        String domainEntityClassName = javaService.create(appName, "domain_entity", super.testDirName);
        javaService.create(appName, newClassRefs(domainEntityClassName), "task", super.testDirName);
    }

    @Test
    public void testCreateJpo() {
        String domainEntityClassName = javaService.create(appName, "domain_entity", super.testDirName);
        javaService.create(appName, newClassRefs(domainEntityClassName), "store_jpo", super.testDirName);
    }

    private List<ClassReference> newClassRefs(String entityClassName) {
        //
        List<ClassReference> refs = new ArrayList<>();
        refs.add(new ClassReference("entity", entityClassName, super.testDirName));

        return refs;
    }
}
