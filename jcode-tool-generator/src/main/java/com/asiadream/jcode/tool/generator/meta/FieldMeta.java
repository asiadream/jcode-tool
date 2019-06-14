package com.asiadream.jcode.tool.generator.meta;

import com.asiadream.jcode.tool.generator.model.ClassType;
import com.asiadream.jcode.tool.generator.model.FieldModel;

public class FieldMeta {
    //
    private String name;
    private String type;

    public FieldModel toFieldModel() {
        //
        return new FieldModel(name, ClassType.newClassType(type));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
