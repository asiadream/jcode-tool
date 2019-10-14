package com.asiadream.jcode.tool.java.meta;

import com.asiadream.jcode.tool.java.model.MethodModel;
import com.asiadream.jcode.tool.share.util.reflection.ObjectCallUtil;
import com.asiadream.jcode.tool.share.util.string.StringUtil;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EachMethodMeta {
    //
    private static Logger logger = LoggerFactory.getLogger(EachMethodMeta.class);

    private String items;
    private String var;
    private MethodOverrideMeta methodOverrides;

    @Deprecated
    private MethodMeta methodMapping; // TODO

    public EachMethodMeta replaceExp(ExpressionContext expressionContext) {
        //
        Optional.ofNullable(methodOverrides).ifPresent(methodOverrides -> methodOverrides.replaceExp(expressionContext));
        return this;
    }

    public List<MethodMeta> eachMethodsByExp(ExpressionContext expressionContext) {
        //
        Object iterableObject = expressionContext.replaceExp(items);
        List<Object> objectList = (List<Object>)iterableObject;
        if (methodMapping != null) {
            return objectList.stream()
                    .map(object -> createMethodMetaWithMapping(object, methodMapping))
                    .collect(Collectors.toList());
        } else {
            return objectList.stream()
                    .map(this::convertModelToMeta)
                    .collect(Collectors.toList());
        }
    }

    private MethodMeta convertModelToMeta(Object object) {
        //
        if (object.getClass() != MethodModel.class) {
            throw new RuntimeException("can not convert to Meta with this object --> " + object.getClass());
        }
        MethodMeta methodMeta = new MethodMeta((MethodModel) object);
        // override methodMeta
        Optional.ofNullable(methodOverrides).ifPresent(methodOverrides -> methodOverrides.override(methodMeta));
        return methodMeta;
    }

    private MethodMeta createMethodMetaWithMapping(Object object, MethodMeta methodMapping) {
        //
        logger.debug("createMethodMetaWithMapping --> {}", object);  // ex) MethodModel
        String nameExp = methodMapping.getName(); // ${f.name}
        String typeExp = methodMapping.getType(); // ${f.returnType}

        String nameProp = StringUtil.substringBetween(nameExp, "${" + var + ".", "}");  // name
        String typeProp = StringUtil.substringBetween(typeExp, "${" + var + ".", "}");  // returnType
        // call by reflection --> getName(), getType()
        try {
            String name = ObjectCallUtil.invokeStringGetMethod(object, "get" + StringUtil.toFirstUpperCase(nameProp));
            Object type = ObjectCallUtil.invokeGetMethod(object, "get" + StringUtil.toFirstUpperCase(typeProp));
            logger.debug("return type --> {}", new Gson().toJson(type));
            MethodMeta methodMeta = new MethodMeta();
            methodMeta.setName(name);
            methodMeta.setType(Optional.ofNullable(type).map(Object::toString).orElse(null));
            return methodMeta;
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

    public MethodMeta getMethodMapping() {
        return methodMapping;
    }

    public void setMethodMapping(MethodMeta methodMapping) {
        this.methodMapping = methodMapping;
    }

    public MethodOverrideMeta getMethodOverrides() {
        return methodOverrides;
    }

    public void setMethodOverrides(MethodOverrideMeta methodOverrides) {
        this.methodOverrides = methodOverrides;
    }
}
