package com.asiadream.jcode.tool.spec.creator;

import com.asiadream.jcode.tool.spec.model.SourceModel;

import java.io.IOException;

// The creator is responsible for creating a new source.
public interface Creator {
    //
    void create(String sourceFileName, SourceModel model) throws IOException;
}
