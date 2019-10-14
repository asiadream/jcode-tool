package com.asiadream.jcode.tool.sql.source;

import com.asiadream.jcode.tool.share.test.BaseFileTest;
import com.asiadream.jcode.tool.sql.model.SqlScript;
import com.google.gson.Gson;
import org.junit.Test;

import java.io.File;

public class SqlSourceTest extends BaseFileTest {
    //
    private static final String DELIM = File.separator;
    private static final String SOURCE_FILE_HOME = "src/test/resources";

    @Test
    public void testRead() {
        String physicalSourceFile = SOURCE_FILE_HOME + DELIM + "sreg200.txt";
        SqlSource sqlSource = new SqlSource(physicalSourceFile);

        SqlScript sqlScript = sqlSource.getSqlScript();
        System.out.println(new Gson().toJson(sqlScript));
    }
}
