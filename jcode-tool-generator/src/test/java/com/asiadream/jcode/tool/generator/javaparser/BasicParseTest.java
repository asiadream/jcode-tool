package com.asiadream.jcode.tool.generator.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import org.junit.Test;

public class BasicParseTest {

    @Test
    public void testFieldParse() {
        //
        CompilationUnit cu = JavaParser.parse(
                "class Foo {\n" +
                        "    private String url = RestTemplateConfig.RESOURCE_URL +\"/talktowns\";\n" +
                        "}");
        System.out.println(cu);
    }

    @Test
    public void testFieldMake() {
        //
        CompilationUnit cu = new CompilationUnit();
        ClassOrInterfaceDeclaration type = new ClassOrInterfaceDeclaration();
        type.setName("Foo");
        cu.addType(type);

        //
        FieldDeclaration fieldDeclaration = new FieldDeclaration();
        fieldDeclaration.addModifier(Modifier.PRIVATE);

        Type fieldType = JavaParser.parseClassOrInterfaceType("String");
        VariableDeclarator variable = new VariableDeclarator(fieldType, "url");
        //Expression initializer = JavaParser.parseExpression("RestTemplateConfig.RESOURCE_URL +\"/talktowns\"");
        //variable.setInitializer(initializer);
        variable.setInitializer("RestTemplateConfig.RESOURCE_URL +\"/talktowns\"");
        fieldDeclaration.addVariable(variable);

        type.addMember(fieldDeclaration);

        //
        System.out.println(cu);
    }

    @Test
    public void testSimpleParse() {
        //
        CompilationUnit cu = JavaParser.parse(simpleCode());
        System.out.println(cu);
    }

    @Test
    public void testSimpleMake() {
        //
        ParserConfiguration configuration = JavaParser.getStaticConfiguration();
        configuration.setDoNotAssignCommentsPrecedingEmptyLines(false);

        //
        PrettyPrinterConfiguration printerConfiguration = new PrettyPrinterConfiguration();
        printerConfiguration.setVisitorFactory(ToolPrintVisitor::new);

        //
        CompilationUnit cu = simpleMake();
        System.out.println(cu.toString(printerConfiguration));
    }

    @Test
    public void testSimpleParseWithLexicalPreserving() {
        //
        CompilationUnit cu = JavaParser.parse(simpleCode());
        LexicalPreservingPrinter.setup(cu);
        System.out.println(LexicalPreservingPrinter.print(cu));
    }

    //@Test
    public void testSimpleMakeWithLexicalPreserving() {
        // not working...
        CompilationUnit cu = simpleMake();
        LexicalPreservingPrinter.setup(cu);
        System.out.println(LexicalPreservingPrinter.print(cu));
    }

    private CompilationUnit simpleMake() {
        //
        CompilationUnit cu = new CompilationUnit();
        ClassOrInterfaceDeclaration type = new ClassOrInterfaceDeclaration();
        type.setName("foo");
        cu.addType(type);

        // field
        FieldDeclaration fieldDeclaration = new FieldDeclaration();
        fieldDeclaration.addModifier(Modifier.PRIVATE);
        VariableDeclarator variable = new VariableDeclarator();
        variable.setName("name");
        variable.setType("String");
        fieldDeclaration.addVariable(variable);
        type.addMember(fieldDeclaration);

        FieldDeclaration fieldDeclaration2 = new FieldDeclaration();
        fieldDeclaration2.addModifier(Modifier.PRIVATE);
        VariableDeclarator variable2 = new VariableDeclarator();
        variable2.setName("age");
        variable2.setType("int");
        fieldDeclaration2.addVariable(variable2);
        type.addMember(fieldDeclaration2);

        //
        MethodDeclaration methodDeclaration = new MethodDeclaration();
        methodDeclaration.setName("main");
        methodDeclaration.setType(new VoidType());
        methodDeclaration.addModifier(Modifier.PUBLIC);
        methodDeclaration.addModifier(Modifier.STATIC);
        ArrayType parameterType = new ArrayType(JavaParser.parseClassOrInterfaceType("String"));
        methodDeclaration.addParameter(parameterType, "args");
        type.addMember(methodDeclaration);

        //
        BlockStmt bstmt = new BlockStmt();
        bstmt.addStatement("int a = 1;");
        bstmt.addStatement("int b = 1;");
        methodDeclaration.setBody(bstmt);

        return cu;
    }

    private String simpleCode() {
        return  "class foo {\n" +
                "    public static void main(String[] args) {\n" +
                "        int a = 1;\n" +
                "        \n" +
                "        int b = 1;\n" +
                "    }\n" +
                "}\n";
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
