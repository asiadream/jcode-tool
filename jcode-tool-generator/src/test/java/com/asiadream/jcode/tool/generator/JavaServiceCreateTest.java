package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.generator.sdo.ClassReference;
import com.asiadream.jcode.tool.generator.sdo.ReferenceSdo;
import com.asiadream.jcode.tool.share.exception.ClassFileNotFoundException;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaServiceCreateTest extends BaseFileTest {
    //
    private JavaService javaService = new JavaService();

    private String createEntity() {
        //
        return javaService.create(ReferenceSdo.create()
                .addCustomContext("name", "talk")
                .addCustomContext("preBizName", "")
                .addCustomContext("postBizName", ".talk"), "domain_entity", super.testDirName);
    }

    private String createServiceInterface(String entityClassName) {
        //
        return javaService.create(ReferenceSdo.create()
                        .addClassReference("entity", entityClassName, super.testDirName)
                , "service_interface", super.testDirName);
    }

    @Test
    public void testCreateEntity() {
        // ex) io.naradrama.talk.domain.entity.talk.Talk
        String className = createEntity();
        Assert.assertEquals("io.naradrama.talk.domain.entity.talk.Talk", className);
    }

    @Test
    public void testCreateCdo() {
        //
        String entityClassName = createEntity();
        javaService.create(ReferenceSdo.create()
                        .addClassReference("entity", entityClassName, super.testDirName)
                , "cdo", super.testDirName);
    }

    @Test
    public void testCreateStoreInterface() {
        // ex) io.naradrama.talk.domain.store.talk.TalkStore
        String domainEntityClassName = createEntity();
        String className = javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "store_interface", super.testDirName);
        Assert.assertEquals("io.naradrama.talk.domain.store.talk.TalkStore", className);
    }

    @Test
    public void testCreateJpo() {
        // ex) io.naradrama.talk.store.jpa.talk.jpo.TalkJpo
        String domainEntityClassName = createEntity();
        String className = javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "store_jpo", super.testDirName);
        Assert.assertEquals("io.naradrama.talk.store.jpa.talk.jpo.TalkJpo", className);
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
    public void testCreateJpaStoreWithPredictStoreInterfaceAndStoreInterfaceExist() {
        // Entity is required
        String domainEntityClassName = createEntity();
        // Create store interface
        javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "store_interface", super.testDirName);

        //
        String predictStoreInterfaceClassName = javaService.predictClassName(ReferenceSdo.create()
                        .addClassReference("entity", domainEntityClassName, super.testDirName)
                , "store_interface");
        String className = javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}, {"storeInterface", predictStoreInterfaceClassName}}), "store_jpa", super.testDirName);
        Assert.assertEquals("io.naradrama.talk.store.jpa.talk.TalkJpaStore", className);
    }

    @Test(expected = ClassFileNotFoundException.class)
    public void testCreateJpaStoreWithPredictStoreInterfaceAndStoreInterfaceNotExist() {
        // Entity is required
        String domainEntityClassName = createEntity();
        // Store interface not exists.

        //
        String predictStoreInterfaceClassName = javaService.predictClassName(ReferenceSdo.create()
                        .addClassReference("entity", domainEntityClassName, super.testDirName)
                , "store_interface");

        // expect RuntimeException
        javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}, {"storeInterface", predictStoreInterfaceClassName}}), "store_jpa", super.testDirName);
    }

    @Test
    public void testCreateJpaRepository() {
        // ex) io.naradrama.talk.store.jpa.talk.repository.TalkRepository
        String domainEntityClassName = createEntity();
        String className = javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "store_jpa_repository", super.testDirName);
        Assert.assertEquals("io.naradrama.talk.store.jpa.talk.repository.TalkRepository", className);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Test
    public void testCreateServiceInterface() {
        // ex) io.naradrama.talk.spec.task.talk.TalkService
        String entityClassName = createEntity();
        String className = createServiceInterface(entityClassName);
        Assert.assertEquals("io.naradrama.talk.domain.spec.task.talk.TalkTaskService", className);
    }

    @Test
    public void testCreateTask() {
        // ex) io.naradrama.talk.domain.logic.task.talk.TalkTask
        String entityClassName = createEntity();
        String serviceInterfaceClassName = createServiceInterface(entityClassName);

        String className = javaService.create(ReferenceSdo.create()
                .addClassReference("entity", entityClassName, super.testDirName)
                .addClassReference("serviceInterface", serviceInterfaceClassName, super.testDirName), "task", super.testDirName);
        Assert.assertEquals("io.naradrama.talk.domain.logic.task.talk.TalkTask", className);
    }

    @Test
    public void testCreateFacade() {
        // Service: io.naradrama.talk.domain.spec.task.talk
        // Facade : io.naradrama.talk.facade.task.talk
        // ex) io.naradrama.talk.domain.spec.task.talk.TalkTaskFacade
        // create entity
        String domainEntityClassName = createEntity();
        // create service interface
        String serviceClassName = javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "service_interface", super.testDirName);
        Assert.assertEquals("io.naradrama.talk.domain.spec.task.talk.TalkTaskService", serviceClassName);

        String facadeClassName = javaService.convert(serviceClassName, super.testDirName, "service_to_facade", super.testDirName);
        Assert.assertEquals("io.naradrama.talk.facade.task.talk.TalkTaskFacade", facadeClassName);
    }

    @Test
    public void testCreateServiceClient() {
        //
        String domainEntityClassName = createEntity();
        String serviceInterfaceName = javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}}), "service_interface", super.testDirName);
        javaService.create(newClassRefs(new String[][]{{"entity", domainEntityClassName}, {"serviceInterface", serviceInterfaceName}}), "service_client", super.testDirName);
    }

    @Test
    public void testCreateServiceCodes() {
        // entity -> service interface -> facade -> resource, client

        // create entity
        String entityClassName = createEntity();

        // create service interface
        String serviceInterfaceClassName = javaService.create(ReferenceSdo.create()
                .addClassReference("entity", entityClassName, super.testDirName), "service_interface", super.testDirName);

        // convert to facade
        String facadeClassName = javaService.convert(serviceInterfaceClassName, super.testDirName, "service_to_facade", super.testDirName);

        // create a resource with facade
        javaService.create(ReferenceSdo.create()
                        .addClassReference("entity", entityClassName, super.testDirName)
                        .addClassReference("serviceInterface", facadeClassName, super.testDirName)
                , "service_resource", super.testDirName);

        // create a client with facade
        javaService.create(ReferenceSdo.create()
                        .addClassReference("entity", entityClassName, super.testDirName)
                        .addClassReference("serviceInterface", facadeClassName, super.testDirName)
                , "service_client", super.testDirName);
    }

    private ReferenceSdo newClassRefs(String[][] refArrays) {
        //
        List<ClassReference> refs = Arrays.stream(refArrays)
                .map(names -> new ClassReference(names[0], names[1], super.testDirName))
                .collect(Collectors.toList());
        return new ReferenceSdo(refs);
    }

    @Test
    public void testPredictClassName() {
        //
        String domainEntityClassName = createEntity();
        String storeInterfaceClassName = javaService.predictClassName(ReferenceSdo.create()
                        .addClassReference("entity", domainEntityClassName, super.testDirName)
                , "store_interface");
        System.out.println(storeInterfaceClassName);
        Assert.assertEquals("io.naradrama.talk.domain.store.talk.TalkStore", storeInterfaceClassName);
    }
}
