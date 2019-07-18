package com.asiadream.jcode.tool.generator.converter;

import java.io.IOException;

// The converter has the role of changing the source by some rules.
public interface Converter {
    //
    String convert(String sourceFileName) throws IOException;
}
