package com.asiadream.jcode.tool.generator.meta.handler;

import com.asiadream.jcode.tool.generator.meta.FieldMeta;

import java.util.List;

public interface FieldMetaHandler {
    //
    List<FieldMeta> handle(FieldMeta fieldMeta);
}
