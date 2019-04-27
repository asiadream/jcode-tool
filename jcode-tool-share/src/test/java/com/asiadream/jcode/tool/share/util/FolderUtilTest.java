package com.asiadream.jcode.tool.share.util;

import com.asiadream.jcode.tool.share.test.BaseFileTest;
import com.asiadream.jcode.tool.share.util.file.FolderUtil;
import org.junit.Test;

import java.io.File;

public class FolderUtilTest extends BaseFileTest {
    //
    @Test
    public void testCreate() {
        //
        String physicalPath = testDirName + File.separator + "foo" + File.separator + "bar";
        FolderUtil.mkdir(physicalPath);
    }
}
