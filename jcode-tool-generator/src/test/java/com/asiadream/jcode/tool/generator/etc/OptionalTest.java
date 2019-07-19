package com.asiadream.jcode.tool.generator.etc;

import org.junit.Test;

import java.util.Optional;

public class OptionalTest {

    private Optional<String> nullValue = Optional.ofNullable(null);
    private Optional<String> notNullValue = Optional.ofNullable("hello");

    @Test
    public void test() {
        System.out.println("old logic:");
        System.out.println(oldLogic(nullValue));
        System.out.println(oldLogic(notNullValue));

        System.out.println("new logic:");
        System.out.println(newLogic(nullValue));
        System.out.println(newLogic(notNullValue));
    }

    private String oldLogic(Optional<String> value) {
        if (!value.isPresent()) {
            return null;
        }
        return value.get();
    }

    private String newLogic(Optional<String> value) {
        //
        return value.orElse(null);
    }
}
