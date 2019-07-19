package com.asiadream.jcode.tool.share.util;

import com.asiadream.jcode.tool.share.util.string.PackageNameUtil;
import org.junit.Assert;
import org.junit.Test;

public class PackageNameUtilTest {

    @Test
    public void testGetFirstName() {
        //
        String packageName = "com.foo.bar.kkk.mmm";
        String s1 = PackageNameUtil.getFirstName(packageName);
        System.out.println(s1);
        Assert.assertEquals("com", s1);
    }

    @Test
    public void testGetLastName() {
        //
        String packageName = "com.foo.bar.kkk.mmm";
        String s1 = PackageNameUtil.getLastName(packageName);
        System.out.println(s1);
        Assert.assertEquals("mmm", s1);
    }

    @Test
    public void testRemovePartCase1() {
        //
        String packageName = "com.foo.bar.kkk.mmm";
        String s1 = PackageNameUtil.removePart(packageName, "foo.bar");
        System.out.println(s1);
        Assert.assertEquals("com.kkk.mmm", s1);
    }

    @Test
    public void testRemovePartCase2() {
        //
        String packageName = "com.foo.bar.kkk.mmm";
        String s1 = PackageNameUtil.removePart(packageName, "com");
        System.out.println(s1);
        Assert.assertEquals("foo.bar.kkk.mmm", s1);
    }

    @Test
    public void testRemovePartCase3() {
        //
        String packageName = "com.foo.bar.kkk.mmm";
        String s1 = PackageNameUtil.removePart(packageName, "mmm");
        System.out.println(s1);
        Assert.assertEquals("com.foo.bar.kkk", s1);
    }

    @Test
    public void testRemoveFirstElement() {
        //
        String packageName = "com.foo.bar.kkk.mmm";
        String s2 = PackageNameUtil.removeFirstElement(packageName);
        System.out.println(s2);
        Assert.assertEquals("foo.bar.kkk.mmm", s2);
    }

    @Test
    public void testRemoveLastElement() {
        //
        String packageName = "com.foo.bar.kkk.mmm";
        String s2 = PackageNameUtil.removeLastElement(packageName);
        System.out.println(s2);
        Assert.assertEquals("com.foo.bar.kkk", s2);
    }
}
