package com.asiadream.jcode.tool.java.javaparser;

import com.github.javaparser.ast.DataKey;
import com.github.javaparser.ast.body.FieldDeclaration;
import org.junit.Test;

import java.util.Optional;

public class FieldDataTest {

    private DataKey<Integer> DATA_KEY1 = new DataKey<Integer>() {
        //
    };

    private DataKey<Integer> DATA_KEY2 = new DataKey<Integer>() {
        //
    };

    @Test
    public void test() {
        //
        FieldDeclaration fieldDeclaration = new FieldDeclaration();
        fieldDeclaration.setData(DATA_KEY1, 1);
        fieldDeclaration.setData(DATA_KEY2, 2);

        FieldDeclaration fieldDeclaration2 = new FieldDeclaration();

        System.out.println(fieldDeclaration.getData(DATA_KEY1));
        System.out.println(fieldDeclaration.getData(DATA_KEY2));
        System.out.println(fieldDeclaration2.getData(DATA_KEY1));

    }

    @Test
    public void testOptional() {
        Integer blankSize = null;
        int size = Optional.ofNullable(blankSize).orElse(1);
        System.out.println(size);
    }
}
