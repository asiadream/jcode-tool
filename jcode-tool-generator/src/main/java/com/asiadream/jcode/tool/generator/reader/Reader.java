package com.asiadream.jcode.tool.generator.reader;

import java.io.IOException;

public interface Reader<T> {
    //
    T read(String sourceFilePath) throws IOException;
}
