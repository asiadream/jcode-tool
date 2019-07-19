package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.generator.sdo.ClassReference;
import com.asiadream.jcode.tool.generator.sdo.ReferenceSdo;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaServiceCreateTest extends BaseFileTest {
    //
    private JavaService javaService = new JavaService();

    @Test
    public void testCreateEntity() {
        //
        javaService.create("domain_entity", super.testDirName);
    }

    @Test
    public void testCreateStoreInterface() {
        //
        String domainEntityClassName = javaService.create("domain_entity", super.testDirName);
        javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "store_interface", super.testDirName);
    }

    @Test
    public void testCreateTask() {
        //
        String domainEntityClassName = javaService.create("domain_entity", super.testDirName);
        javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "task", super.testDirName);
    }

    @Test
    public void testCreateJpo() {
        //
        String domainEntityClassName = javaService.create("domain_entity", super.testDirName);
        javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "store_jpo", super.testDirName);
    }

    @Test
    public void testCreateServiceInterface() {
        //
        String domainEntityClassName = javaService.create("domain_entity", super.testDirName);
        javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "service_interface", super.testDirName);
    }

    @Test
    public void testCreateServiceClient() {
        //
        String domainEntityClassName = javaService.create("domain_entity", super.testDirName);
        String serviceInterfaceName = javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "service_interface", super.testDirName);
        javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}, {"serviceInterface", serviceInterfaceName}}), "service_client", super.testDirName);
    }

    private ReferenceSdo newClassRefs(String[][] refArrays) {
        //
        List<ClassReference> refs = Arrays.stream(refArrays)
                .map(names -> new ClassReference(names[0], names[1], super.testDirName))
                .collect(Collectors.toList());
        return new ReferenceSdo(refs);
    }
}
