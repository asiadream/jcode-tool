package com.asiadream.jcode.tool.java;

import com.asiadream.jcode.tool.java.sdo.ReferenceSdo;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Assert;
import org.junit.Test;

public class JavaServiceConvertTest extends BaseFileTest {
    //
    private static final String SOURCE_PROJECT_HOME = "src/test/resources/sample-project";
    private static final String ENTITY_CLASS_NAME = "io.naradrama.talk.domain.entity.town.TalkTown";
    private static final String SERVICE_CLASS_NAME = "io.naradrama.talk.spec.task.town.TalkTownService";

    private JavaService javaService = new JavaService();

    @Test
    public void testConvert() {
        // convert to facade
        String facadeClassName = javaService.convert(SERVICE_CLASS_NAME, SOURCE_PROJECT_HOME, "service_to_facade", super.testDirName);
        // create resource
        ReferenceSdo referenceSdo = new ReferenceSdo("entity", ENTITY_CLASS_NAME, SOURCE_PROJECT_HOME);
        referenceSdo.addClassReference("facade", facadeClassName, super.testDirName);
        javaService.create(referenceSdo, "service_resource", super.testDirName);
    }

    @Test
    public void testCreateJpoCase3() {
        //
        String domainEntityClassName = "io.naradrama.talk.domain.entity.room.JoinBoard";
        String className = javaService.create(ReferenceSdo.create()
                        .addClassReference("entity", domainEntityClassName, SOURCE_PROJECT_HOME)
                , "store_jpo", super.testDirName);
        Assert.assertEquals("io.naradrama.talk.store.jpa.room.jpo.JoinBoardJpo", className);
    }


}
