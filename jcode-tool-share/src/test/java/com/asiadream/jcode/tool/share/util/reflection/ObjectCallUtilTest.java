package com.asiadream.jcode.tool.share.util.reflection;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class ObjectCallUtilTest {

    @Test
    public void testInvokeGetMethod() throws Exception {
        //
        Object targetObject = new TestUser("han", "han@nextree.co.kr");
        String name = (String) ObjectCallUtil.invokeGetMethod(targetObject, "getName");
        String email = (String) ObjectCallUtil.invokeGetMethod(targetObject, "getEmail");

        //
        Assert.assertEquals("han", name);
        Assert.assertEquals("han@nextree.co.kr", email);
    }

    @Test
    public void testInvokeStringGetMethod() throws Exception {
        //
        Object targetObject = new TestUser("han", "han@nextree.co.kr");
        String name = ObjectCallUtil.invokeStringGetMethod(targetObject, "getName");
        Assert.assertEquals("han", name);

        String dateStr = ObjectCallUtil.invokeStringGetMethod(targetObject, "getDate");
        Assert.assertNull(dateStr);

        ((TestUser)targetObject).setDate(new Date());
        dateStr = ObjectCallUtil.invokeStringGetMethod(targetObject, "getDate");
        System.out.println(dateStr);
        Assert.assertNotNull(dateStr);
    }
}
