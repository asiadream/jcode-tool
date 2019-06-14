package com.asiadream.jcode.tool.project.etc;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OptionalTest {
    @Test
    public void testNonOptional() {
        //
        List<String> maybeNull = null;

        if (maybeNull != null) {
            for (String str : maybeNull) {
                System.out.println(str);
            }
        }
    }

    @Test
    public void testOptional() {
        //
        List<String> maybeNull = Arrays.asList("1", "2"); //null;

        Optional.ofNullable(maybeNull).ifPresent(list -> list.forEach(str -> System.out.println(str)));
    }
}
