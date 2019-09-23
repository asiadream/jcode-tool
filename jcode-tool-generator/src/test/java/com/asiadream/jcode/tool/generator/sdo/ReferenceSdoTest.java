package com.asiadream.jcode.tool.generator.sdo;

import com.asiadream.jcode.tool.generator.meta.BizNameLocation;
import org.junit.Assert;
import org.junit.Test;

public class ReferenceSdoTest {

    @Test
    public void testGetBizNameCase1() {
        //
        String className = "com.foo.bar.app.biz.domain.entity.SomeEntity";
        String groupId   = "com.foo.bar";
        String appName   = "app";
        BizNameLocation location = BizNameLocation.PRE;

        ReferenceSdo sdo = new ReferenceSdo();
        sdo.addClassReference("entity", className, null);

        Assert.assertEquals("biz", sdo.getPreBizNameFromReferenceClass(location, 0, groupId, appName));
        Assert.assertNull(sdo.getPostBizNameFromReferenceClass(location, 0, groupId, appName));
    }

    @Test
    public void testGetBizNameCase2() {
        //
        String className = "com.foo.bar.app.domain.entity.biz.SomeEntity";
        String groupId   = "com.foo.bar";
        String appName   = "app";
        BizNameLocation location = BizNameLocation.POST;

        ReferenceSdo sdo = new ReferenceSdo();
        sdo.addClassReference("entity", className, null);

        Assert.assertNull(sdo.getPreBizNameFromReferenceClass(location, 0, groupId, appName));
        Assert.assertEquals("biz", sdo.getPostBizNameFromReferenceClass(location, 0, groupId, appName));
    }

    @Test
    public void testGetBizNameCase3() {
        //
        String className = "com.foo.bar.app.domain.biz.entity.SomeEntity";
        String groupId   = "com.foo.bar";
        String appName   = "app";
        BizNameLocation location = BizNameLocation.MIDDLE;
        int middleNameSeq = 1;

        ReferenceSdo sdo = new ReferenceSdo();
        sdo.addClassReference("entity", className, null);

        Assert.assertNull(sdo.getPreBizNameFromReferenceClass(location, middleNameSeq, groupId, appName));
        Assert.assertEquals("biz", sdo.getMiddleBizNameFromReferenceClass(location, middleNameSeq, groupId, appName));
        Assert.assertNull(sdo.getPostBizNameFromReferenceClass(location, middleNameSeq, groupId, appName));
    }

    @Test
    public void testGetBizNameCaseOfNoReference() {
        //
        String groupId   = "com.foo.bar";
        String appName   = "app";
        BizNameLocation location = BizNameLocation.MIDDLE;
        int middleNameSeq = 1;

        ReferenceSdo sdo = new ReferenceSdo();
        sdo.setBizName("biz");

        Assert.assertNull(sdo.getPreBizNameFromReferenceClass(location, middleNameSeq, groupId, appName));
        Assert.assertEquals("biz", sdo.getMiddleBizNameFromReferenceClass(location, middleNameSeq, groupId, appName));
        Assert.assertNull(sdo.getPostBizNameFromReferenceClass(location, middleNameSeq, groupId, appName));
    }

}