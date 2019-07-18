package com.asiadream.jcode.tool.generator.meta;

import com.asiadream.jcode.tool.share.util.reflection.ObjectCallUtil;
import com.asiadream.jcode.tool.share.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class EachFieldMeta {
    //
    private static Logger logger = LoggerFactory.getLogger(EachFieldMeta.class);

    private String items;
    private String var;
    private FieldMeta field;

    public List<FieldMeta> eachFieldsByExp(ExpressionContext expressionContext) {
        //
        Object iterableObject = expressionContext.replaceExp(items);
        List<Object> objectList = (List<Object>)iterableObject;

        return objectList.stream()
                .map(this::createFieldMetaWithObject)
                .collect(Collectors.toList());
    }

    private FieldMeta createFieldMetaWithObject(Object object) {
        //
        logger.debug("createFieldMetaWithObject --> {}", object);
        String nameExp = field.getName(); // ${f.name}
        String typeExp = field.getType(); // ${f.type}

        String nameProp = StringUtil.substringBetween(nameExp, "${" + var + ".", "}");  // name
        String typeProp = StringUtil.substringBetween(typeExp, "${" + var + ".", "}");  // type

        try {
            // call by reflection --> getName(), getType()
            String name = ObjectCallUtil.invokeStringGetMethod(object, "get" + StringUtil.toFirstUpperCase(nameProp));
            String type = ObjectCallUtil.invokeStringGetMethod(object, "get" + StringUtil.toFirstUpperCase(typeProp));
            return new FieldMeta(name, type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public FieldMeta getField() {
        return field;
    }

    public void setField(FieldMeta field) {
        this.field = field;
    }
}
