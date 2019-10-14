package com.asiadream.jcode.tool.sql.reader;

import com.asiadream.jcode.tool.spec.reader.Reader;
import com.asiadream.jcode.tool.sql.source.SqlSource;

import java.io.IOException;

public class SqlReader implements Reader<SqlSource> {
    //
    @Override
    public SqlSource read(String sourceFilePath) throws IOException {
        return null;
    }

    @Override
    public SqlSource readPhysicalFile(String physicalFilePath) throws IOException {
        //
        return new SqlSource(physicalFilePath);
    }
}
