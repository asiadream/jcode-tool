package com.asiadream.jcode.tool.generator.writer;

import java.io.IOException;

public interface Writer<T> {
    //
    void write(T source) throws IOException;
}
