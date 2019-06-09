package com.asiadream.jcode.tool.project.yaml;

import com.asiadream.jcode.tool.project.yaml.model.Customer;
import com.google.gson.Gson;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class CustomerTest {

    @Test
    public void testRead() {
        Yaml yaml = new Yaml();
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("yaml/Customer.yaml");
        Customer customer = yaml.load(inputStream);
        System.out.println(new Gson().toJson(customer));
    }

    @Test
    public void testRead2() {
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("yaml/Basic.yaml");
        Customer customer = yaml.load(inputStream);
        System.out.println(new Gson().toJson(customer));
    }
}
