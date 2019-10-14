package com.asiadream.jcode.tool.groovy.model;

public interface GroovyPrintable {
    //
    String print(int level);

    default String print() {
        return print(0);
    }

    default String blank(int level) {
        String b = "";
        for (int i = 0; i < level; i++) {
            b = b + "    ";
        }
        return b;
    }
}
