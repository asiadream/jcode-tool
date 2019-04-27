package com.asiadream.jcode.tool.share.util;

import kr.amc.amil.tool.share.util.string.PackageNameUtil;
import org.junit.Assert;
import org.junit.Test;

public class PackageNameUtilTest {

    @Test
    public void testSplit() {
        //
        String packageName = "com.foo.bar.kkk.mmm";
        String s1 = PackageNameUtil.getFirstName(packageName);
        System.out.println(s1);
        Assert.assertEquals("com", s1);

        //
        String s2 = PackageNameUtil.getRemainNamesExceptFirst(packageName);
        System.out.println(s2);
        Assert.assertEquals("foo.bar.kkk.mmm", s2);
    }
}
