package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.generator.sdo.ClassReference;
import com.asiadream.jcode.tool.generator.sdo.ReferenceSdo;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaServiceCreateTest extends BaseFileTest {
    //
    private JavaService javaService = new JavaService();

    @Test
    public void testCreateEntity() {
        // ex) io.naradrama.talk.domain.entity.talk.Talk
        String className = createEntity();
        Assert.assertEquals("io.naradrama.talk.domain.entity.talk.Talk", className);
    }

    private String createEntity() {
        //
        ReferenceSdo referenceSdo = new ReferenceSdo();
        referenceSdo.addCustomContext("preBizName", "");
        referenceSdo.addCustomContext("postBizName", ".talk");
        return javaService.create(referenceSdo, "domain_entity", super.testDirName);
    }

    @Test
    public void testCreateStoreInterface() {
        // ex) io.naradrama.talk.domain.store.talk.TalkStore
        String domainEntityClassName = createEntity();
        String className = javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "store_interface", super.testDirName);
        Assert.assertEquals("io.naradrama.talk.domain.store.talk.TalkStore", className);
    }

    @Test
    public void testCreateJpaStore() {
        // ex) io.naradrama.talk.store.jpa.talk.TalkJpaStore
        String domainEntityClassName = createEntity();
        String storeInterfaceClassName = javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "store_interface", super.testDirName);
        String className = javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}, {"storeInterface", storeInterfaceClassName}}), "store_jpa", super.testDirName);
        Assert.assertEquals("io.naradrama.talk.store.jpa.talk.TalkJpaStore", className);
    }

    @Test
    public void testCreateTask() {
        // ex) io.naradrama.talk.domain.logic.task.talk.TalkTask
        String domainEntityClassName = createEntity();
        String className = javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "task", super.testDirName);
        Assert.assertEquals("io.naradrama.talk.domain.logic.task.talk.TalkTask", className);
    }

    @Test
    public void testCreateJpo() {
        // ex) io.naradrama.talk.store.jpa.talk.jpo.TalkJpo
        String domainEntityClassName = createEntity();
        String className = javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "store_jpo", super.testDirName);
        Assert.assertEquals("io.naradrama.talk.store.jpa.talk.jpo.TalkJpo", className);
    }

    @Test
    public void testCreateServiceInterface() {
        //
        String domainEntityClassName = createEntity();
        javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "service_interface", super.testDirName);
    }

    @Test
    public void testCreateServiceClient() {
        //
        String domainEntityClassName = createEntity();
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
