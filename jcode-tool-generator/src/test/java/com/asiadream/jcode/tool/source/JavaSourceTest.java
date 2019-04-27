package com.asiadream.jcode.tool.source;

import com.asiadream.jcode.tool.generator.source.JavaSource;
import org.junit.Test;

public class JavaSourceTest {
    
    @Test
    public void testRemoveGetterAndSetter() throws Exception {
        //
        String physicalSourceFile = ".\\src\\test\\java\\kr\\amc\\amil\\tool\\source\\TestClass.java";
        
        JavaSource javaSource = new JavaSource(physicalSourceFile, false);
        javaSource.removeGetterAndSetter();
        
        System.out.println(javaSource.toString());
    }
 
    @Test
    public void testLexicalPrint() throws Exception {
        //
        String physicalSourceFile = ".\\src\\test\\java\\kr\\amc\\amil\\tool\\source\\TestClass2.java";
        
        JavaSource javaSource = new JavaSource(physicalSourceFile, true);
        javaSource.setName("Tes");
        
        System.out.println(javaSource.generate());
    }

}
