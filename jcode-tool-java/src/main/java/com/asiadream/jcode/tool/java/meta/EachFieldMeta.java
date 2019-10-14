package com.asiadream.jcode.tool.java.meta;

import com.asiadream.jcode.tool.java.meta.handler.FieldMetaHandler;
import com.asiadream.jcode.tool.java.model.FieldModel;
import com.asiadream.jcode.tool.share.util.reflection.ObjectCallUtil;
import com.asiadream.jcode.tool.share.util.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EachFieldMeta {
    //
    private static Logger logger = LoggerFactory.getLogger(EachFieldMeta.class);

    private String items;
    private String var;

    private String handler;

    @Deprecated
    private FieldMeta field;

    public List<FieldMeta> eachFieldsByExp(ExpressionContext expressionContext) {
        //
        Object iterableObject = expressionContext.replaceExp(items);
        List<Object> objectList = (List<Object>)iterableObject;

        if (field != null) {
            return objectList.stream()
                    .map(this::createFieldMetaWithObject)
                    .collect(Collectors.toList());
        }

        List<FieldMeta> fieldMetas = objectList.stream()
                .map(this::convertModelToMeta)
                .collect(Collectors.toList());

        // invoke handler
        FieldMetaHandler fieldMetaHandler = loadFildMetaHandler();
        if (fieldMetaHandler == null) {
            return fieldMetas;
        }

        List<FieldMeta> newFiledMetas = new ArrayList<>();
        for (FieldMeta fieldMeta : fieldMetas) {
            List<FieldMeta> metas = fieldMetaHandler.handle(fieldMeta);
            if (metas != null) {
                newFiledMetas.addAll(metas);
            }
        }
        return newFiledMetas;
    }

    private FieldMetaHandler loadFildMetaHandler() {
        //
        if (StringUtil.isEmpty(handler)) {
            return null;
        }

        try {
            Class clazz = Class.forName(handler);
            Object handlerObject = clazz.newInstance();
            FieldMetaHandler fieldMetaHandler = (FieldMetaHandler) handlerObject;
            return fieldMetaHandler;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    private FieldMeta convertModelToMeta(Object object) {
        //
        if (object.getClass() != FieldModel.class) {
            throw new RuntimeException("can not convert to Meta with this object --> " + object.getClass());
        }
        FieldMeta fieldMeta = new FieldMeta((FieldModel) object);
        return fieldMeta;
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

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }
}
