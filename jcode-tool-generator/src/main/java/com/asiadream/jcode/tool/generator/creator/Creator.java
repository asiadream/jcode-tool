package com.asiadream.jcode.tool.generator.creator;

import com.asiadream.jcode.tool.generator.model.SourceModel;

import java.io.IOException;

// The creator is responsible for creating a new source.
public interface Creator {
    //
    void create(String sourceFileName, SourceModel model) throws IOException;
}
