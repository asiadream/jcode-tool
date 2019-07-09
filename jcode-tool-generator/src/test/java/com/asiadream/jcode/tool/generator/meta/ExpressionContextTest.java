package com.asiadream.jcode.tool.generator.meta;

import org.junit.Assert;
import org.junit.Test;

public class ExpressionContextTest {

    @Test
    public void testReplace() {
        //
        ExpressionContext ctx = new ExpressionContext();
        ctx.add("foo", "123");
        ctx.add("bar", "456");

        Assert.assertEquals("123.456.789", ctx.replaceExpString("${foo}.456.789"));
        Assert.assertEquals("123.456.789", ctx.replaceExpString("${foo}.${bar}.789"));

    }

}