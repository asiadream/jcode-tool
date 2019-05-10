package com.asiadream.jcode.tool.source;

import com.asiadream.jcode.tool.generator.source.JavaSource;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class JavaSourceTest {
    //
    private static final String DELIM = File.separator;
    
    //@Test
    public void testRemoveGetterAndSetter() throws Exception {
        //
        String physicalSourceFile = "." + DELIM + "jcode-tool-generator" + DELIM + "src" + DELIM + "test" + DELIM + "java" +
                DELIM + "com" + DELIM + "asiadream" + DELIM + "jcode" + DELIM + "tool" + DELIM + "source" + DELIM + "TestClass.java";

        JavaSource javaSource = new JavaSource(physicalSourceFile, false);
        javaSource.removeGetterAndSetter();

        System.out.println(javaSource.toString());
    }
 
    //@Test
    public void testLexicalPrint() throws Exception {
        //
        String physicalSourceFile = "." + DELIM + "jcode-tool-generator" + DELIM + "src" + DELIM + "test" + DELIM + "java" + DELIM + "kr" + DELIM + "amc" + DELIM + "amil" + DELIM + "tool" + DELIM + "source" + DELIM + "TestClass2.java";

        JavaSource javaSource = new JavaSource(physicalSourceFile, true);
        javaSource.setName("Tes");

        System.out.println(javaSource.generate());
    }

}
