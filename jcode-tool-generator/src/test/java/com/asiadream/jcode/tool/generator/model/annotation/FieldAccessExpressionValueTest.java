package com.asiadream.jcode.tool.generator.model.annotation;

import org.junit.Assert;
import org.junit.Test;

public class FieldAccessExpressionValueTest {

    @Test
    public void test() {
        //
        FieldAccessExpressionValue value = new FieldAccessExpressionValue("io.com.Foo", "Foo.Bar");
        Assert.assertEquals("Foo", value.getAccessTarget());
        Assert.assertEquals("Bar", value.getAccessField());

        FieldAccessExpressionValue value2 = new FieldAccessExpressionValue("io.com.Sample", "Sample.Foo.Bar");
        Assert.assertEquals("Sample.Foo", value2.getAccessTarget());
        Assert.assertEquals("Bar", value2.getAccessField());
    }

}