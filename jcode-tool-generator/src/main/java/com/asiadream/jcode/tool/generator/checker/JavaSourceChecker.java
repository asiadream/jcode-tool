package com.asiadream.jcode.tool.generator.checker;

import com.asiadream.jcode.tool.java.source.JavaSource;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class JavaSourceChecker {
    //
    private Logger logger;

    private final String[] EXCEPTION_PATTERNS = {"void", "int", "String", "long", "Long", "Integer",
            "Date", "double", "Double", "float", "Float", "Map", "HashMap", "List", "KeyValue", "TypedKeyValue",
            "char", "Char", "short", "Short", "boolean", "Boolean",
            "BatchLog", "FileInfo", "File", "TransferObject", "DataContext"};

    private String[] targetClassPostfix;
    private String typeNamePostfix;

    public JavaSourceChecker(String[] targetClassPostfix, String typeNamePostfix) {
        //
        this.targetClassPostfix = targetClassPostfix;
        this.typeNamePostfix = typeNamePostfix;
        this.logger = LoggerFactory.getLogger("dto-checker-" + typeNamePostfix.toLowerCase());
    }

    public void checkAndWarn(JavaSource source) {
        //
        if (source == null || !contains(source)) {
            return;
        }

        String className = source.getClassName();
        logger.info("check class {}", className);
        source.forEachMethod(methodDeclaration -> checkMethod(methodDeclaration, className));
    }

    private boolean contains(JavaSource source) {
        //
        String name = source.getName();
        for (String targetPostfix : targetClassPostfix) {
            if (name.endsWith(targetPostfix)) {
                return true;
            }
        }
        return false;
    }

    private void checkMethod(MethodDeclaration methodDeclaration, String className) {
        //
        if (!methodDeclaration.isPublic()) {
            return;
        }

        Type returnType = methodDeclaration.getType();
        if (!checkType(returnType)) {
            
            if (className.endsWith("ProcService") && returnType.toString().endsWith("TO")) {
                //
            } else {
                warn(methodDeclaration, className);
                return;
            }
            
        }

        for (Parameter parameter : methodDeclaration.getParameters()) {
            Type parameterType = parameter.getType();
            if (!checkType(parameterType)) {
                warn(methodDeclaration, className);
                return;
            }
        }
    }

    private void warn(MethodDeclaration methodDeclaration, String className) {
        //
        Integer lineNumber = methodDeclaration.getBegin()
                .map(p -> p.line)
                .orElse(0);
        logger.warn("{} {} : {}", className + ".java", lineNumber, methodDeclaration.getDeclarationAsString());
    }

    private boolean checkType(Type type) {
        // check type argument
        if (isException(type)) {
            return true;
        }

        if (type.isClassOrInterfaceType()) {
            Optional<NodeList<Type>> typeArguments = ((ClassOrInterfaceType) type).getTypeArguments();
            Type typeArg = typeArguments.map(types -> types.get(0)).orElse(null);
            if (typeArg != null && (isException(typeArg) || typeArg.toString().endsWith(typeNamePostfix))) {
                return true;
            }
        }

        // check type
        if (type.toString().endsWith(typeNamePostfix)) {
            return true;
        }

        return false;
    }

    private boolean isException(Type type) {
        //
        for (String pattern : EXCEPTION_PATTERNS) {
            if (type.toString().startsWith(pattern)) {
                return true;
            }
        }
        return false;
    }
}
