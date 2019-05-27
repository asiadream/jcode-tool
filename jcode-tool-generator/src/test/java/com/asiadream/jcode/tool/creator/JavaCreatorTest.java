package com.asiadream.jcode.tool.creator;

import com.asiadream.jcode.tool.generator.creator.JavaCreator;
import com.asiadream.jcode.tool.generator.model.AnnotationType;
import com.asiadream.jcode.tool.generator.model.ClassType;
import com.asiadream.jcode.tool.generator.model.FieldModel;
import com.asiadream.jcode.tool.generator.model.JavaModel;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Test;

import java.io.IOException;

public class JavaCreatorTest extends BaseFileTest {

    @Test
    public void test() throws IOException {
        //
        ProjectConfiguration targetConfiguration = new ProjectConfiguration(ConfigurationType.Target, super.testDirName);
        JavaCreator creator = new JavaCreator(targetConfiguration);

        JavaModel javaModel = new JavaModel("com.foo.Foo");
        javaModel.addAnnotation(new AnnotationType("lombok.Getter"));
        javaModel.addAnnotation(new AnnotationType("lombok.Setter"));
        javaModel.addAnnotation(new AnnotationType("lombok.NoArgsConstructor"));
        javaModel.addFieldModel(new FieldModel("id", ClassType.newClassType("String")));
        javaModel.addFieldModel(new FieldModel("name", ClassType.newClassType("String")));

        javaModel.addExtendsType(ClassType.newClassType("com.foo.entity.NaraEntity"));
        creator.create("Foo.java", javaModel);
    }
}
