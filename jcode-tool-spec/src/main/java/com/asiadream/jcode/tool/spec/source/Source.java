package com.asiadream.jcode.tool.spec.source;

import java.io.IOException;

public interface Source {
    void write(String physicalTargetFilePath) throws IOException;
    String getSourceFilePath();
}
