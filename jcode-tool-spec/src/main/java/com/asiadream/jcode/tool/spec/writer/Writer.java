package com.asiadream.jcode.tool.spec.writer;

import com.asiadream.jcode.tool.spec.source.Source;

import java.io.IOException;

public interface Writer<T extends Source> {
    //
    void write(T source) throws IOException;
}
