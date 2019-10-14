package com.asiadream.jcode.tool.java.model.annotation;

import java.util.List;

public interface AnnotationValue {
    AnnotationValue copyOf();
    List<String> usingClassNames();
}
