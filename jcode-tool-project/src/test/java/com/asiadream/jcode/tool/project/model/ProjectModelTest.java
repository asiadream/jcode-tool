package com.asiadream.jcode.tool.project.model;

import com.asiadream.jcode.tool.share.config.ConfigurationType;
import org.junit.Test;

public class ProjectModelTest {

    @Test(expected = AssertionError.class)
    public void testAssert() {
        //
        ProjectModel model = new ProjectModel("SampleProject", "com.sample", "1.0-SNAPSHOT");
        model.setWorkspacePath(null);
        model.configuration(ConfigurationType.Target);
    }

//    @Test
//    public void testCreateProjectModel() {
//        //
//        ProjectModel projectModelLevel1 = new ProjectModel(name, groupId, version, "pom")
//                .setWorkspacePath(workspace)
//                .addDependency("junit", "junit")
//                .addDependency("org.projectlombok", "lombok", "1.18.6")
//                .addDependency("com.google.code.gson", "gson", "2.8.5")
//                .addProperty("spring.boot.version", "2.1.3.RELEASE")
//                .addProperty("spring.cloud.version", "Greenwich.RELEASE")
//                .addProperty("spring.cloud.stream.version", "Fishtown.M3");
//
//        // level2 (adapter, boot, entity, logic, proxy, service, share, store-jpa)
//        ProjectModel adapterModel = new ProjectModel(settings.getAdapterName(), groupId, version);
//        projectModelLevel1.add(adapterModel);
//
//        ProjectModel entityModel = new ProjectModel(settings.getEntityName(), groupId, version);
//        entityModel.addDependency("io.naraplatform", "share-domain", "0.2.2-SNAPSHOT");
//        projectModelLevel1.add(entityModel);
//
//        ProjectModel logicModel = new ProjectModel(settings.getLogicName(), groupId, version);
//        logicModel.addDependency(entityModel);
//        projectModelLevel1.add(logicModel);
//
//        ProjectModel proxyModel = new ProjectModel(settings.getProxyName(), groupId, version);
//        proxyModel.addDependency(logicModel);
//        proxyModel.addDependency("io.naraplatform", "share-event", "0.2.2-SNAPSHOT");
//        projectModelLevel1.add(proxyModel);
//
//        ProjectModel serviceModel = new ProjectModel(settings.getServiceName(), groupId, version);
//        serviceModel.addDependency(logicModel);
//        serviceModel.addDependency("org.springframework.boot", "spring-boot-starter-web");
//        projectModelLevel1.add(serviceModel);
//
//        ProjectModel shareModel = new ProjectModel(settings.getShareName(), groupId, version);
//        projectModelLevel1.add(shareModel);
//
//        ProjectModel jpaModel = new ProjectModel(settings.getJpaName(), groupId, version);
//        jpaModel.addDependency(logicModel);
//        jpaModel.addDependency("org.springframework.boot", "spring-boot-starter-data-jpa");
//        projectModelLevel1.add(jpaModel);
//
//        ProjectModel bootModel = new ProjectModel(settings.getBootName(), groupId, version);
//        bootModel.addDependency(jpaModel);
//        bootModel.addDependency(serviceModel);
//        projectModelLevel1.add(bootModel);
//    }
}
