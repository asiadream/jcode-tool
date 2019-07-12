package com.asiadream.jcode.tool.generator.source;

public class TestClass2 {
    private String name;    // 이름
    private int age;        // 나이
    
    public void process(
            String name         // 이름
            , String email      // 이메일
            , int age           // 나이
            ) {
        //
    }
    
    public void abc() {
        process("aaa"       // aaa
                , "bbb"     // bbb
                , 333       // 333
                );
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
}
