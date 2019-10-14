package com.asiadream.jcode.tool.spec.reader;

import java.io.IOException;

public interface Reader<T> {
    //
    T read(String sourceFilePath) throws IOException;

    default T readPhysicalFile(String physicalFilePath) throws IOException {
        return null;
    }
}
