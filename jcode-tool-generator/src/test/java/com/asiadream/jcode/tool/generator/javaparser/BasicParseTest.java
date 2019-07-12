package com.asiadream.jcode.tool.generator.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;


public class BasicParseTest {

    @Test
    public void testParse() {
        CompilationUnit cu = JavaParser.parse(
                "class foo {" +
                "    public static void main(String[] args) {}" +
                "}");
        System.out.println(cu);
    }

    @Test
    public void testComplexParseWithAnnotations() {
        CompilationUnit cu = JavaParser.parse(example());
        System.out.println(cu);
    }

    private String example() {
        return "/*\n" +
                " * COPYRIGHT (c) NEXTREE Consulting 2014\n" +
                " * This software is the proprietary of NEXTREE Consulting CO.\n" +
                " * @since 2014. 6. 10.\n" +
                " */\n" +
                "\n" +
                "package io.naradrama.talk.spec.crud.town;\n" +
                "\n" +
                "import io.naradrama.talk.domain.entity.town.TalkTown;\n" +
                "import io.naradrama.talk.spec.town.sdo.TalkTownCdo;\n" +
                "import io.naraplatform.share.domain.NameValueList;\n" +
                "import io.naraplatform.share.domain.drama.FeatureTarget;\n" +
                "import io.naraplatform.share.domain.drama.ServiceFeature;\n" +
                "\n" +
                "import java.util.List;\n" +
                "\n" +
                "@ServiceFeature(\n" +
                "        name = \"TalkTown\",\n" +
                "        editions = {\"Professional\", \"Enterprise\"},\n" +
                "        authorizedRoles = {\"Admin\"}\n" +
                ")\n" +
                "@Enumerated(EnumType.STRING)\n" +
                "@RunWith(SpringJUnit4ClassRunner.class)\n" +
                "@SpringBootTest(classes = TalkStoreTestApplication.class)\n" +
                "@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)\n" +
                "public interface TalkTownService extends FeatureTarget {\n" +
                "    //\n" +
                "    String registerTalkTown(TalkTownCdo talkTownCdo);\n" +
                "    TalkTown findTalkTown(String talkTownId);\n" +
                "    List<TalkTown> findAllTalkTowns();\n" +
                "    void modifyTalkTown(String talkTownId, NameValueList nameValues);\n" +
                "    void removeTalkTown(String talkTownId);\n" +
                "    long countAllTalkTown();\n" +
                "}";
    }
}
