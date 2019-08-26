package com.asiadream.jcode.tool.generator.handler;

import com.asiadream.jcode.tool.generator.meta.FieldMeta;
import com.asiadream.jcode.tool.generator.meta.handler.FieldMetaHandler;
import com.asiadream.jcode.tool.generator.model.ClassType;

import java.util.ArrayList;
import java.util.List;

public class JpoFieldHandler implements FieldMetaHandler {
    //
    @Override
    public List<FieldMeta> handle(FieldMeta fieldMeta) {
        //
        List<FieldMeta> results = new ArrayList<>();
        String type = fieldMeta.getType();
        if (type.endsWith("IdName")) {
            FieldMeta id = new FieldMeta(fieldMeta);
            id.setType("String");
            id.setName(id.getName() + "Id");
            results.add(id);

            FieldMeta name = new FieldMeta(fieldMeta);
            name.setType("String");
            name.setName(name.getName() + "Name");
            results.add(name);
        } else if ("String".equals(type) || ClassType.isPrimitiveType(type)) {
            results.add(fieldMeta);
        } else {
            FieldMeta jsonFieldMeta = new FieldMeta(fieldMeta);
            jsonFieldMeta.setName(jsonFieldMeta.getName() + "Json");
            jsonFieldMeta.setType("String");
            results.add(jsonFieldMeta);
        }

        return results;
    }
}
