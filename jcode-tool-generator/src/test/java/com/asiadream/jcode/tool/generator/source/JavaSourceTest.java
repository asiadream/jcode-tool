package com.asiadream.jcode.tool.generator.source;

import com.asiadream.jcode.tool.generator.JavaService;
import com.asiadream.jcode.tool.generator.model.Access;
import com.asiadream.jcode.tool.generator.model.ClassType;
import com.asiadream.jcode.tool.generator.model.FieldModel;
import com.asiadream.jcode.tool.generator.model.JavaModel;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class JavaSourceTest {
    //

    @Test
    public void testCreateWithModel() {
        //
        JavaModel javaModel = new JavaModel("com.foo.Foo");
        javaModel.addFieldModel(new FieldModel("name", ClassType.String)
                .setAccess(Access.PRIVATE)
                .setLineComment("This is a name"));
        javaModel.addFieldModel(new FieldModel("age", ClassType.Int)
                .setAccess(Access.PUBLIC)
                .setStatic(true)
                .setFinal(true)
                .setLineComment("This is age"));

        JavaSource javaSource = new JavaSource(javaModel);
        javaSource.setUseOwnPrinter(true);
        javaSource.calculateBlankOfLineComment();

        System.out.println(javaSource.generate());
    }

    // -----------------------------------------------------------------------------------------------------------------
    // TODO : Move test codes to JavaServiceTest
    private static final String DELIM = File.separator;
    private static final String SOURCE_PROJECT_HOME = "src/test/resources/sample-project";

    //@Test
    public void testIsExtends() {
        //
        JavaService javaService = new JavaService();
        JavaSource javaSource = javaService.readJavaSource(SOURCE_PROJECT_HOME, "io.naradrama.talk.domain.entity.town.TalkTown");
        System.out.println(javaSource.isExtends("DramaEntity"));
        Assert.assertTrue(javaSource.isExtends("DramaEntity"));
    }

    //@Test
    public void testIsInterface() {
        JavaService javaService = new JavaService();

        JavaSource entitySource = javaService.readJavaSource(SOURCE_PROJECT_HOME, "io.naradrama.talk.domain.entity.town.TalkTown");
        Assert.assertFalse(entitySource.isInterface());

        JavaSource serviceSource = javaService.readJavaSource(SOURCE_PROJECT_HOME, "io.naradrama.talk.spec.task.town.TalkTownService");
        Assert.assertTrue(serviceSource.isInterface());
    }

    //@Test
    public void testRemoveGetterAndSetter() throws Exception {
        //
        String physicalSourceFile = "." + DELIM + "jcode-tool-generator" + DELIM + "src" + DELIM + "test" + DELIM + "java" +
                DELIM + "com" + DELIM + "asiadream" + DELIM + "jcode" + DELIM + "tool" + DELIM + "source" + DELIM + "TestClass.java";

        JavaSource javaSource = new JavaSource(physicalSourceFile, false, false);
        javaSource.removeGetterAndSetter();

        System.out.println(javaSource.toString());
    }

    //@Test
    public void testLexicalPrint() throws Exception {
        //
        String physicalSourceFile = "." + DELIM + "jcode-tool-generator" + DELIM + "src" + DELIM + "test" + DELIM + "java" + DELIM + "kr" + DELIM + "amc" + DELIM + "amil" + DELIM + "tool" + DELIM + "source" + DELIM + "TestClass2.java";

        JavaSource javaSource = new JavaSource(physicalSourceFile, true, false);
        javaSource.setName("Tes");

        System.out.println(javaSource.generate());
    }

}
