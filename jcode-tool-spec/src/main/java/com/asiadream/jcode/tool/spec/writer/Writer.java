package com.asiadream.jcode.tool.spec.writer;

import java.io.IOException;

public interface Writer<T> {
    //
    void write(T source) throws IOException;
}
