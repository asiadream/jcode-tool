package com.asiadream.jcode.tool.project.yaml;

import com.asiadream.jcode.tool.project.yaml.model.Customer;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

public class ComplexTest {
    @Test
    public void testComplexObjectLoad() {
        Yaml yaml = new Yaml(new Constructor(Customer.class));
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("yaml/Complex.yaml");
        Customer customer = yaml.load(inputStream);
        System.out.println(new Gson().toJson(customer));

        Assert.assertEquals("John", customer.getFirstName());
        Assert.assertEquals("Doe", customer.getLastName());
        Assert.assertEquals("mobile", customer.getContactDetails().get(0).getType());
        Assert.assertEquals("landline", customer.getContactDetails().get(1).getType());
        Assert.assertEquals("Xyz, DEF Street", customer.getHomeAddress().getLine());
    }
}
