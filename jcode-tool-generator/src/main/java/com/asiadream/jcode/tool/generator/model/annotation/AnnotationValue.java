package com.asiadream.jcode.tool.generator.model.annotation;

import java.util.List;

public interface AnnotationValue {
    //
    AnnotationValue copyOf();
    List<String> usingClassNames();
}
