package com.asiadream.jcode.tool.generator;

import com.asiadream.jcode.tool.generator.sdo.ReferenceSdo;
import com.asiadream.jcode.tool.share.test.BaseFileTest;
import org.junit.Test;

import java.io.File;

public class JavaServiceUpdateTest extends BaseFileTest {
    //
    private static final String DELIM = File.separator;
    private static final String SOURCE_FILE_HOME = "src/test/resources";

    private JavaService javaService = new JavaService();

    private String createEntity() {
        //
        return javaService.create(ReferenceSdo.create()
                .addCustomContext("name", "talk")
                .addCustomContext("preBizName", "")
                .addCustomContext("postBizName", ".talk"), "domain_entity", super.testDirName);
    }

    @Test
    public void testAddFields() {
        //
        String className = createEntity();

        String physicalSourceFile = SOURCE_FILE_HOME + DELIM + "sreg200.txt";
        javaService.addFields(className, super.testDirName, physicalSourceFile);
    }
}
