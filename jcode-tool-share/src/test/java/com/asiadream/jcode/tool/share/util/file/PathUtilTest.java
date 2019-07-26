package com.asiadream.jcode.tool.share.util.file;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.regex.Matcher;

public class PathUtilTest {

    @Test
    public void toProjectPath() {
        //
        String physicalSourceFilePath = "/Users/user/Projects/foo/foo-spec/src/main/java/com/foo/bar/Sample.java";
        String projectPath            = "/Users/user/Projects/foo/foo-spec";
        String result = PathUtil.toProjectPath(physicalSourceFilePath);
        System.out.println(result);
        Assert.assertEquals(projectPath, result);
    }

    @Test
    public void toSourceFilePath() {
        //
        String physicalSourceFilePath = "/Users/user/Projects/foo/foo-spec/src/main/java/com/foo/bar/Sample.java";
        String sourceFilePath         = "com/foo/bar/Sample.java";
        String result = PathUtil.toSourceFilePath(physicalSourceFilePath);
        System.out.println(result);
        Assert.assertEquals(sourceFilePath, result);
    }

    @Test
    public void testPath1() {
        //
        String before = "com/foo/bar/Sample.xml".replaceAll("/", Matcher.quoteReplacement(File.separator));
        String after  = "com/foo/spec/SampleDao.java".replaceAll("/", Matcher.quoteReplacement(File.separator));

        String result = PathUtil.changePath(before, 1, new String[]{"spec"}, "Dao", "java");
        System.out.println(result);
        Assert.assertEquals(after, result);
    }

    @Test
    public void testPath1_1() {
        //
        String before = "com/foo/bar/SampleMapper.xml".replaceAll("/", Matcher.quoteReplacement(File.separator));
        String after  = "com/foo/spec/SampleDao.java".replaceAll("/", Matcher.quoteReplacement(File.separator));

        String result = PathUtil.changePath(before, 1, new String[]{"spec"}, "Mapper", "Dao", "java");
        System.out.println(result);
        Assert.assertEquals(after, result);
    }

    @Test
    public void testPath2() {
        //
        String before = "com/foo/bar/Sample.xml".replaceAll("/", Matcher.quoteReplacement(File.separator));
        String after  = "com/foo/bar/SampleDao.java".replaceAll("/", Matcher.quoteReplacement(File.separator));

        String result = PathUtil.changeFileName(before, "Dao", "java");
        System.out.println(result);
        Assert.assertEquals(after, result);
    }

    @Test
    public void testPath2_1() {
        //
        String before = "com/foo/bar/SampleMapper.xml".replaceAll("/", Matcher.quoteReplacement(File.separator));
        String after  = "com/foo/bar/SampleDao.java".replaceAll("/", Matcher.quoteReplacement(File.separator));

        String result = PathUtil.changeFileName(before, "Mapper", "Dao", "java");
        System.out.println(result);
        Assert.assertEquals(after, result);
    }

    // com/foo/bar/Sample.xml -> com/foo/bar/SampleDao.java or com/foo/SampleDao.java
    @Test
    public void testPath3() {
        //
        String before = "com/foo/bar/Sample.xml".replaceAll("/", Matcher.quoteReplacement(File.separator));
        String after  = "com/foo/SampleDao.java".replaceAll("/", Matcher.quoteReplacement(File.separator));

        String result = PathUtil.changePath(before, 1, null, "Dao", "java");
        System.out.println(result);
        Assert.assertEquals(after, result);
    }

    @Test
    public void testPath3_1() {
        //
        String before = "com/foo/bar/SampleMapper.xml".replaceAll("/", Matcher.quoteReplacement(File.separator));
        String after  = "com/foo/SampleDao.java".replaceAll("/", Matcher.quoteReplacement(File.separator));

        String result = PathUtil.changePath(before, 1, null, "Mapper", "Dao", "java");
        System.out.println(result);
        Assert.assertEquals(after, result);
    }

    @Test
    public void testToClassName() {
        // com/foo/bar/SampleDto.java -> com.foo.bar.SampleDto
        String before = "com/foo/bar/SampleDto.java".replaceAll("/", Matcher.quoteReplacement(File.separator));
        String after = "com.foo.bar.SampleDto";

        String result = PathUtil.toClassName(before);
        System.out.println(result);
        Assert.assertEquals(after, result);
    }
}