package com.asiadream.jcode.tool.java.meta;

import com.asiadream.jcode.tool.java.model.ClassType;
import com.asiadream.jcode.tool.share.util.string.ClassNameUtil;
import com.asiadream.jcode.tool.share.util.string.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MetaHelper {
    //
    static List<String> toMultiStatements(String body) {
        //
        boolean blockMode = false;
        String block = "";
        List<String> statements = new ArrayList<>();
        for (String str : body.split("\n")) {
            //System.out.println("["+str+"]");

            //
            if (str.startsWith("if")) {
                blockMode = true;
            }

            //
            if (blockMode) {
                block = block + str;
            } else {
                statements.add(str);
            }

            if (blockMode && str.endsWith("}")) {
                statements.add(block);
                blockMode = false;
                block = "";
            }
        }

        return statements;
    }

    static ClassType toClassType(String typeString) {
        //
        if (typeString == null)
            return null;
        if (typeString.indexOf('<') > 0) {
            ClassType classType = ClassType.newClassType(typeString.substring(0, typeString.indexOf('<')));
            String typeArgNames = StringUtil.substringBetween(typeString, "<", ">");
            Arrays.asList(typeArgNames.split(",")).forEach(typeArg -> classType.addTypeArgument(typeArg));
            return classType;
        }
        if (typeString.endsWith("[]")) {
            String compTypeString = StringUtil.removeSuffix(typeString, "[]");
            return ClassType.newArrayType(ClassType.newClassType(compTypeString));
        }
        return ClassType.newClassType(typeString);
    }

    static String recommendVarName(String varName, String typeName) {
        //
        String name = StringUtil.isEmpty(varName) ? ClassNameUtil.getSimpleClassName(typeName) : varName;
        return StringUtil.getRecommendedVariableName(name);
    }
}
