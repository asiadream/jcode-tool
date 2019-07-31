package com.asiadream.jcode.tool.share.util;

import org.junit.Test;

import java.util.regex.Pattern;

public class RegexTest {
    //
    @Test
    public void test() {
        //
        String appName = "abc-cvb";
        Pattern p = Pattern.compile("(^[a-z]*$)");
        boolean fined = p.matcher(appName).find();
        System.out.println(fined);

    }
}
