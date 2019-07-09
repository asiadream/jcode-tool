package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.generator.sdo.ClassReference;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class JavaServiceTest extends BaseFileTest {

    @Test
    public void testCreate() {
        //
        final String appName = "talk";

        JavaService javaService = new JavaService();
        // domain entity
        String domainEntityClassName = javaService.create(appName, "domain_entity", super.testDirName);
        // store interface
        //javaService.createByRefClass(domainEntityClassName, "store_interface", super.testDirName);
        // store lifecycler
        //javaService.createByRefClass(domainEntityClassName, "store_lycler", super.testDirName);
        // jpo
        javaService.create(appName, newClassRefs(domainEntityClassName), "store_jpo", super.testDirName);
        // task
        //javaService.createByRefClass(domainEntityClassName, "task", super.testDirName);

    }

    private List<ClassReference> newClassRefs(String entityClassName) {
        //
        List<ClassReference> refs = new ArrayList<>();
        refs.add(new ClassReference("entity", entityClassName, super.testDirName));

        return refs;
    }
}
