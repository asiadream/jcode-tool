package com.asiadream.jcode.tool.spec.reader;

import com.asiadream.jcode.tool.spec.source.Source;

import java.io.IOException;

public interface Reader<T extends Source> {
    //
    T read(String sourceFilePath) throws IOException;

    default T readPhysicalFile(String physicalFilePath) throws IOException {
        return null;
    }
}
