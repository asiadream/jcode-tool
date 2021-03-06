package com.asiadream.jcode.tool.java.ast;

import com.asiadream.jcode.tool.java.model.*;
import com.asiadream.jcode.tool.java.model.annotation.*;
import com.asiadream.jcode.tool.share.util.string.StringUtil;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.*;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@SuppressWarnings("unused")
// Ast -> Model : to***(), Model -> Ast : create***()
public abstract class AstMapper {
    //
    // Ast:CompilationUnit -> Model:JavaModel
    public static JavaModel toJavaModel(CompilationUnit compilationUnit, FullNameProvider fullNameProvider) {
        //
        ClassOrInterfaceDeclaration classType = (ClassOrInterfaceDeclaration) compilationUnit.getType(0);
        String name = classType.getNameAsString();
        String packageName = compilationUnit.getPackageDeclaration()
                .map(PackageDeclaration::getName)
                .map(Name::asString)
                .orElseThrow(IllegalArgumentException::new);

        boolean isInterface = classType.isInterface();

        JavaModel javaModel = new JavaModel(name, packageName, isInterface);

        for (Object member : classType.getMembers()) {
            if (member instanceof MethodDeclaration) {
                MethodDeclaration method = (MethodDeclaration) member;
                javaModel.addMethodModel(toMethodModel(method, fullNameProvider));
            }
        }
        
        // Comment
        javaModel.setNodeComment(compilationUnit.getComment().orElse(null));
        javaModel.setTypeComment(classType.getComment().orElse(null));
        
        return javaModel;
    }

    // Model:JavaModel -> Ast:CompilationUnit
    public static CompilationUnit createCompilationUnit(JavaModel javaModel) {
        //
        CompilationUnit compilationUnit = new CompilationUnit();

        // package
        compilationUnit.setPackageDeclaration(javaModel.getPackageName());

        // import
        List<String> importClassNames = javaModel.computeImports();
        for (String className : importClassNames) {
            if (StringUtil.isNotEmpty(className) && className.indexOf(".") > 0) {
                compilationUnit.addImport(className);
            }
        }

        // Class
        EnumSet<Modifier> modifiers = EnumSet.of(Modifier.PUBLIC);
        ClassOrInterfaceDeclaration classType = new ClassOrInterfaceDeclaration(modifiers, javaModel.isInterface(), javaModel.getName());

        // Annotation
        Optional.ofNullable(javaModel.getAnnotations()).ifPresent(annotations -> annotations.forEach(annotation ->
                classType.addAnnotation(createAnnotationExpr(annotation))));

        // Extended Type
        if (javaModel.hasExtendsType()) {
            for (ClassType extendsType : javaModel.getExtendsTypes()) {
                classType.addExtendedType(createClassOrInterfaceType(extendsType));
            }
        }

        // Implemented Type
        if (javaModel.hasImplementsType()) {
            for (ClassType implementsType : javaModel.getImplementsTypes()) {
                classType.addImplementedType(implementsType.getName());
            }
        }

        // Field
        for (FieldModel fieldModel : javaModel.getFields()) {
            //
            FieldDeclaration fieldDeclaration = createFieldDeclaration(fieldModel);
            classType.addMember(fieldDeclaration);
        }

        // Constructor
        for (ConstructorModel constructorModel : javaModel.getConstructors()) {
            ConstructorDeclaration constructorDeclaration = createConstructorDeclaration(constructorModel);
            classType.addMember(constructorDeclaration);
        }

        // Method
        for (MethodModel methodModel : javaModel.getMethods()) {
            MethodDeclaration methodDeclaration = createMethodDeclaration(methodModel);
            if (javaModel.isInterface()) {
                methodDeclaration.setBody(null);
            }
            classType.addMember(methodDeclaration);
        }
        
        // Type Comment
        classType.setComment(javaModel.getTypeComment());

        compilationUnit.addType(classType);
        
        // Node Comment
        compilationUnit.setComment(javaModel.getNodeComment());

        return compilationUnit;
    }

    // Model:AnnotationType -> Ast:AnnotationExpr
    public static AnnotationExpr createAnnotationExpr(AnnotationType annotationType) {
        //
        if (!annotationType.hasPair()) {
            return new MarkerAnnotationExpr(annotationType.getName());
        } else if (annotationType.hasSingleValue()) {
            AnnotationValue singleValue = annotationType.getSingleValue();
            return new SingleMemberAnnotationExpr(new Name(annotationType.getName()), createExpression(singleValue));
        }

        NodeList<MemberValuePair> pairs = new NodeList<>();
        annotationType.getPairs().forEach(annotationPair -> pairs.add(createMemberValuePair(annotationPair)));
        return new NormalAnnotationExpr(new Name(annotationType.getName()), pairs);
    }

    // Model: AnnotationPair -> Ast: MemberValuePair
    public static MemberValuePair createMemberValuePair(AnnotationPair annotationPair) {
        //
        Expression expression = createExpression(annotationPair.getValue());
        return new MemberValuePair(annotationPair.getMethod(), expression);
    }

    // Model: AnnotationValue -> Ast: Expression
    public static Expression createExpression(AnnotationValue annotationValue) {
        //
        Class valueClass = annotationValue.getClass();
        if (valueClass == AnnotationString.class) {
            return new StringLiteralExpr(((AnnotationString)annotationValue).getValue());
        }
        if (valueClass == AnnotationArray.class) {
            NodeList<Expression> exps = new NodeList<>();
            ((AnnotationArray)annotationValue).getValues()
                    .forEach(_annotationValue -> exps.add(createExpression(_annotationValue)));
            return new ArrayInitializerExpr(exps);
        }
        if (valueClass == FieldAccessExpressionValue.class) {
            FieldAccessExpressionValue value = (FieldAccessExpressionValue)annotationValue;
            FieldAccessExpr expr = new FieldAccessExpr();
            expr.setScope(new NameExpr(value.getAccessTarget()));
            expr.setName(value.getAccessField());
            return expr;
        }
        if (valueClass == ClassTypeValue.class) {
            ClassTypeValue value = (ClassTypeValue)annotationValue;
            Type type = createType(value.getValue());
            return new ClassExpr(type);
        }
        throw new RuntimeException("could not find the right type..." + valueClass);
    }

    // Ast:FieldDeclaration -> Model:FieldModel
    public static FieldModel toFieldModel(FieldDeclaration fieldDeclaration, FullNameProvider fullNameProvider) {
        //
        VariableDeclarator var = fieldDeclaration.getVariable(0);
        EnumSet<Modifier> modifiers = fieldDeclaration.getModifiers();

        String name = var.getNameAsString();
        AccessSpecifier access = Modifier.getAccessSpecifier(modifiers);
        Type fieldType = var.getType();

        ClassType classType = toClassType(fieldType, fullNameProvider);

        FieldModel fieldModel = new FieldModel(name, classType);
        fieldModel.setAccess(Access.valueOf(access.name()));
        fieldModel.setStatic(modifiers.contains(Modifier.STATIC));
        fieldModel.setFinal(modifiers.contains(Modifier.FINAL));

        // TODO : AnnotationType
        return fieldModel;
    }

    // Ast:MethodDeclaration -> Model:MethodModel
    public static MethodModel toMethodModel(MethodDeclaration method, FullNameProvider fullNameProvider) {
        //
        String name = method.getNameAsString();
        AccessSpecifier access = Modifier.getAccessSpecifier(method.getModifiers());
        Type methodType = method.getType();

        ClassType returnType = toClassType(methodType, fullNameProvider);

        MethodModel methodModel = new MethodModel(name, returnType);
        methodModel.setAccess(Access.valueOf(access.name()));
        for(Parameter parameter : method.getParameters()) {
            ClassType parameterType = toClassType(parameter.getType(), fullNameProvider);
            // FIXME parameterName check
            String parameterName = parameter.getNameAsString();
            methodModel.addParameterModel(new ParameterModel(parameterType, parameterName));
        }
        
        // throws
        for (ReferenceType thrown : method.getThrownExceptions()) {
            String typeName = ((ClassOrInterfaceType)thrown).getName().asString();
            ClassType thrownClassType = ClassType.newClassType(typeName);
            methodModel.addThrown(thrownClassType);
        }
        
        // Comment
        methodModel.setComment(method.getComment().orElse(null));

        return methodModel;
    }

    public static ConstructorDeclaration createConstructorDeclaration(ConstructorModel constructorModel) {
        //
        ConstructorDeclaration constructor = new ConstructorDeclaration(constructorModel.getName());
        constructor.addModifier(Modifier.PUBLIC);

        for (ParameterModel parameterModel : constructorModel.getParameterModels()) {
            Parameter parameter = createParameter(parameterModel);
            constructor.addParameter(parameter);
        }

        BlockStmt bstmt = new BlockStmt();
        for (String stmt : constructorModel.getBodyStatements()) {
            bstmt.addStatement(stmt);
        }
        constructor.setBody(bstmt);
        return constructor;
    }

    // Model:MethodModel -> Ast:MethodDeclaration
    public static MethodDeclaration createMethodDeclaration(MethodModel methodModel) {
        //
        ClassType methodClassType = methodModel.getReturnType();
        Type methodType = createType(methodClassType);

        MethodDeclaration method = new MethodDeclaration(EnumSet.noneOf(Modifier.class), methodType, methodModel.getName());
        method.setBody(null);

        // Access
        if (methodModel.getAccess() != null && methodModel.getAccess() != Access.DEFAULT) {
            method.addModifier(Modifier.valueOf(methodModel.getAccess().name()));
        }

        // Static
        if (methodModel.isStatic()) {
            method.addModifier(Modifier.STATIC);
        }

        // Parameter
        for (ParameterModel parameterModel : methodModel.getParameterModels()) {
            Parameter parameter = createParameter(parameterModel);
            method.addParameter(parameter);
        }
        
        // throws
        for (ClassType thrown : methodModel.getThrowns()) {
            method.addThrownException(createClassOrInterfaceType(thrown));
        }

        // Annotation
        Optional.ofNullable(methodModel.getAnnotations()).ifPresent(annotations -> annotations.forEach(annotation ->
                method.addAnnotation(createAnnotationExpr(annotation))));
        
        // Comment
        method.setComment(methodModel.getComment());

        // Body
        BlockStmt bstmt = new BlockStmt();
        for (String stmt : methodModel.getBodyStatements()) {
            bstmt.addStatement(stmt);
        }
        method.setBody(bstmt);
        return method;
    }

    // Model:FieldModel -> Ast:FieldDeclaration
    public static FieldDeclaration createFieldDeclaration(FieldModel fieldModel) {
        //
        FieldDeclaration field = new FieldDeclaration();

        // Access
        Optional.ofNullable(fieldModel.getAccess()).ifPresent(access ->
                field.addModifier(Modifier.valueOf(fieldModel.getAccess().name())));

        // Static
        if (fieldModel.isStatic()) {
            field.addModifier(Modifier.STATIC);
        }

        // Final
        if (fieldModel.isFinal()) {
            field.addModifier(Modifier.FINAL);
        }

        // Annotation
        Optional.ofNullable(fieldModel.getAnnotations()).ifPresent(annotations -> annotations.forEach(annotation ->
                field.addAnnotation(createAnnotationExpr(annotation))));

        // Field type and name
        Type fieldType = createType(fieldModel.getType());
        VariableDeclarator variable = new VariableDeclarator(fieldType, fieldModel.getName());

        // Initializer
        Optional.ofNullable(fieldModel.getInitializer()).ifPresent(initializer ->
                variable.setInitializer(initializer));

        // Line Comment
        Optional.ofNullable(fieldModel.getLineComment()).ifPresent(lineComment ->
                field.setComment(new LineComment(lineComment)));

        field.addVariable(variable);

        return field;
    }

    // Ast:Type -> Model:ClassType
    public static ClassType toClassType(Type type, FullNameProvider fullNameProvider) {
        //
        if (type.isPrimitiveType()) {
            String primitiveName = type.asString();
            return ClassType.newPrimitiveType(primitiveName);
        } else if (type.isArrayType()) {
            ArrayType arrayType = (ArrayType) type;
            Type componentType = arrayType.getComponentType();
            ClassType compClassType = toClassType(componentType, fullNameProvider);
            return ClassType.newArrayType(compClassType);
        } else if (type.isVoidType()) {
            return null;
        }

        String returnTypeName = ((ClassOrInterfaceType)type).getName().asString();
        String returnTypeFullName = fullNameProvider.findFullName(returnTypeName);
        ClassType classType = ClassType.newClassType(returnTypeFullName);

        // if return type has Generic type (List<SampleDTO>)
        ifPresentTypeArgument(type, fullNameProvider, typeArgument -> classType.addTypeArgument(typeArgument));

        return classType;
    }

    // Model:ClassType -> Ast:Type
    public static Type createType(ClassType classType) {
        //
        if (classType == null) {
            return new VoidType();
        }

        if (classType.isPrimitive() && classType.isArray()) {
            Type componentType = new PrimitiveType(PrimitiveType.Primitive.valueOf(classType.getName().toUpperCase()));
            return new ArrayType(componentType);
        }

        if (classType.isPrimitive()) {
            return new PrimitiveType(PrimitiveType.Primitive.valueOf(classType.getName().toUpperCase()));
        }
        
        if (classType.isArray()) {
            ClassType elementType = ClassType.newArrayElementType(classType);
            Type componentType = createType(elementType);
            return new ArrayType(componentType);
        }

        return createClassOrInterfaceType(classType);
    }

    // Model:ParameterModel -> Ast:Parameter
    public static Parameter createParameter(ParameterModel parameterModel) {
        //
        Type type = createType(parameterModel.getType());
        Parameter parameter = new Parameter(type, parameterModel.getVarName());
        return parameter;
    }

    // Model:FieldModel -> Ast:Parameter
    public static Parameter createParameter(FieldModel fieldModel) {
        //
        Type type = createType(fieldModel.getType());
        Parameter parameter = new Parameter(type, fieldModel.getName());
        return parameter;
    }

    private static ClassOrInterfaceType createClassOrInterfaceType(ClassType classType) {
        //
        ClassOrInterfaceType returnType = JavaParser.parseClassOrInterfaceType(classType.getName());

        if (classType.hasTypeArgument()) {
            NodeList<Type> typeArguments = new NodeList<>();
            for (ClassType modelArgumentType : classType.getTypeArguments()) {
                typeArguments.add(JavaParser.parseClassOrInterfaceType(modelArgumentType.getName()));
            }
            returnType.setTypeArguments(typeArguments);
        }
        return returnType;
    }

    // before
    private static ClassType toTypeArgumentIfPresent(Type type, FullNameProvider fullNameProvider) {
        //
        if (type.isPrimitiveType()) {
            return null;
        }

        Optional<NodeList<Type>> returnTypeArguments = ((ClassOrInterfaceType) type).getTypeArguments();
        if (!returnTypeArguments.isPresent()) {
            return null;
        }
        String returnTypeArgumentName = returnTypeArguments.get().get(0).asString();
        String returnTypeArgumentFullName = fullNameProvider.findFullName(returnTypeArgumentName);
        return ClassType.newClassType(returnTypeArgumentFullName);
    }

    // using map
    private static ClassType toTypeArgument(Type type, FullNameProvider fullNameProvider) {
        //
        if (!type.isClassOrInterfaceType()) {
            return null;
        }

        Optional<NodeList<Type>> returnTypeArguments = ((ClassOrInterfaceType) type).getTypeArguments();

        ClassType classType = returnTypeArguments
                .map(nodeList -> {
                    String returnTypeArgumentName = nodeList.get(0).asString();
                    String returnTypeArgumentFullName = fullNameProvider.findFullName(returnTypeArgumentName);
                    return ClassType.newClassType(returnTypeArgumentFullName);
                })
                .orElse(null);
        return classType;
    }

    // after
    private static void ifPresentTypeArgument(Type type, FullNameProvider fullNameProvider, Consumer<ClassType> classTypeConsumer) {
        //
        if (!type.isClassOrInterfaceType()) {
            return;
        }

        Optional<NodeList<Type>> returnTypeArguments = ((ClassOrInterfaceType)type).getTypeArguments();
        returnTypeArguments.ifPresent(nodeList -> {
            for (Type typeArg : nodeList) {
                String returnTypeArgumentName = typeArg.asString();
                String returnTypeArgumentFullName = fullNameProvider.findFullName(returnTypeArgumentName);
                classTypeConsumer.accept(ClassType.newClassType(returnTypeArgumentFullName));
            }
        });
    }

    public interface FullNameProvider {
        //
        String findFullName(String simpleName);
    }
}
