package com.asiadream.jcode.tool.generator.source;

import javax.annotation.Signed;

public class TestClass {
    private String name;
    private int age;
    
    @Signed
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        doSomething();
    }

    private void doSomething() {
    }

    public int getAge() {
        doSomething();
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    
}
