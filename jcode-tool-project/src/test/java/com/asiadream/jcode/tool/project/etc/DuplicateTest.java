package com.asiadream.jcode.tool.project.etc;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DuplicateTest {
    //
    String[] arr = { "bp", "am", "cm", "eh", "fa", "fm", "ga", "hr", "id", "ja", "sm", "wl", "cr", "em", "el", "fm",
            "ml", "sy", "gc", "gm", "gr", "gs", "gx", "gz", "mr", "ms", "nr", "nu", "oo", "op", "ph", "rd", "sc", "ps",
            "sa", "sb", "sd", "se", "si", "ss", "sz", "ev", "wb", "wj", "wm", "wp", "wr", "an", "ck", "cp", "ep", "ft",
            "gw", "sr" };

    @Test
    public void testDup() {
        Map<String, String> map = new HashMap<>();
        for (String str : arr) {
            System.out.println(str + ", dup ? " + map.containsKey(str));
            map.put(str, str);
        }
    }
}
