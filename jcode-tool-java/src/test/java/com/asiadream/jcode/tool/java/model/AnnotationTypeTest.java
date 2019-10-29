package com.asiadream.jcode.tool.java.model;

import com.asiadream.jcode.tool.java.creator.JavaCreator;
import com.asiadream.jcode.tool.java.model.annotation.ClassTypeValue;
import com.asiadream.jcode.tool.java.model.annotation.FieldAccessExpressionValue;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Test;

import java.io.IOException;

public class AnnotationTypeTest extends BaseFileTest {
    //
    private final String className = "com.foo.bar.AnnotationSample";

    @Test
    public void test() {
        //
        JavaModel javaModel = new JavaModel(className);

        // @Getter
        javaModel.addAnnotation(new AnnotationType("lombok.Getter"));

        // @Table(name = "TALK")
        AnnotationType annotationType1 = new AnnotationType("javax.persistence.Table");
        annotationType1.addPair("name", "TALK");
        javaModel.addAnnotation(annotationType1);

        // @ServiceFeature(name = "TalkTown", editions = { "Professional", "Enterprise" }, authorizedRoles = { "Admin" })
        AnnotationType annotationType2 = new AnnotationType("io.naraplatform.share.domain.drama.ServiceFeature");
        annotationType2.addPair("name", "TalkTown");
        annotationType2.addPair("edition", new String[]{ "Professional", "Enterprise" });
        javaModel.addAnnotation(annotationType2);

        // @Enumerated(EnumType.STRING)
        AnnotationType annotationType3 = new AnnotationType("javax.persistence.Enumerated");
        annotationType3.addPair(new FieldAccessExpressionValue("javax.persistence.EnumType", "EnumType.STRING"));
        javaModel.addAnnotation(annotationType3);

        // @RunWith(SpringJUnit4ClassRunner.class)
        AnnotationType annotationType4 = new AnnotationType("org.junit.runner.RunWith");
        annotationType4.addPair(new ClassTypeValue("org.springframework.test.context.junit4.SpringJUnit4ClassRunner"));
        javaModel.addAnnotation(annotationType4);

        // @SpringBootTest(classes = TalkStoreTestApplication.class)
        AnnotationType annotationType5 = new AnnotationType("org.springframework.boot.test.context.SpringBootTest");
        annotationType5.addPair("classes", new ClassTypeValue("io.naradrama.talk.store.TalkStoreTestApplication"));
        javaModel.addAnnotation(annotationType5);

        // @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
        AnnotationType annotationType6 = new AnnotationType("org.springframework.test.annotation.DirtiesContext");
        annotationType6.addPair("classMode", new FieldAccessExpressionValue("org.springframework.test.annotation.DirtiesContext", "DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD"));
        javaModel.addAnnotation(annotationType6);

        // create with model
        ProjectConfiguration targetConfiguration = new ProjectConfiguration(ConfigurationType.Target, super.testDirName);
        JavaCreator creator = new JavaCreator(targetConfiguration);
        try {
            creator.create(javaModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}