package com.asiadream.jcode.tool.generator.javaparser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.*;
import org.junit.Test;

public class ClassAnnotationTest {

    @Test
    public void test() {
        ClassOrInterfaceDeclaration sampleClass = new ClassOrInterfaceDeclaration();
        sampleClass.setName("Sample");

        ///////////////////
        // @Getter
        ///////////////////
        sampleClass.addAnnotation(new MarkerAnnotationExpr("Getter"));

        ///////////////////
        // @ServiceFeature(name = "TalkTown", editions = { "Professional", "Enterprise" }, authorizedRoles = { "Admin" })
        ///////////////////
        NodeList<MemberValuePair> pairs = new NodeList<>();
        MemberValuePair firstPair = new MemberValuePair("name", new StringLiteralExpr("TalkTown"));   // name = "TalkTown"
        pairs.add(firstPair);

        NodeList<Expression> secondExps = new NodeList<>();
        secondExps.add(new StringLiteralExpr("Professional"));
        secondExps.add(new StringLiteralExpr("Enterprise"));
        MemberValuePair secondPair = new MemberValuePair("editions", new ArrayInitializerExpr(secondExps)); // editions = { "Professional", "Enterprise" }
        pairs.add(secondPair);

        NodeList<Expression> thirdExps = new NodeList<>();
        thirdExps.add(new StringLiteralExpr("Admin"));
        MemberValuePair thirdPair = new MemberValuePair("authorizedRoles", new ArrayInitializerExpr(thirdExps));
        pairs.add(thirdPair);

        NormalAnnotationExpr anno1 = new NormalAnnotationExpr(new Name("ServiceFeature"), pairs);
        sampleClass.addAnnotation(anno1);

        ///////////////////
        // @Enumerated(EnumType.STRING)
        ///////////////////
        FieldAccessExpr fae = new FieldAccessExpr(); // EnumType.STRING
        fae.setScope(new NameExpr("EnumType"));
        fae.setName("STRING");

        SingleMemberAnnotationExpr anno2 = new SingleMemberAnnotationExpr(new Name("Enumerated"), fae);
        sampleClass.addAnnotation(anno2);

        ///////////////////
        // @RunWith(SpringJUnit4ClassRunner.class)
        ///////////////////
        ClassExpr classExpr = new ClassExpr(JavaParser.parseClassOrInterfaceType("SpringJUnit4ClassRunner"));
        SingleMemberAnnotationExpr anno3 = new SingleMemberAnnotationExpr(new Name("RunWith"), classExpr);
        sampleClass.addAnnotation(anno3);

        ///////////////////
        // @SpringBootTest(classes = TalkStoreTestApplication.class)
        ///////////////////
        NodeList<MemberValuePair> pairs2 = new NodeList<>();
        MemberValuePair classesPair = new MemberValuePair("classes", new ClassExpr(JavaParser.parseClassOrInterfaceType("TalkStoreTestApplication")));   // name = "TalkTown"
        pairs2.add(classesPair);

        NormalAnnotationExpr anno4 = new NormalAnnotationExpr(new Name("SpringBootTest"), pairs2);
        sampleClass.addAnnotation(anno4);

        ///////////////////
        // @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
        ///////////////////
        NodeList<MemberValuePair> pairs3 = new NodeList<>();
        FieldAccessExpr fieldAccessExpr = new FieldAccessExpr();
        fieldAccessExpr.setScope(new NameExpr("DirtiesContext.ClassMode"));
        fieldAccessExpr.setName("AFTER_EACH_TEST_METHOD");

        MemberValuePair memberValuePair = new MemberValuePair("classMode", fieldAccessExpr);
        pairs3.add(memberValuePair);

        NormalAnnotationExpr anno5 = new NormalAnnotationExpr(new Name("DirtiesContext"), pairs3);
        sampleClass.addAnnotation(anno5);

        System.out.println(sampleClass);
    }

}
