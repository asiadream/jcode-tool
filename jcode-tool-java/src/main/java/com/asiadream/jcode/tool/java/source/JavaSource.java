package com.asiadream.jcode.tool.java.source;

import com.asiadream.jcode.tool.java.ast.AstMapper;
import com.asiadream.jcode.tool.java.javaparser.ToolPrintVisitor;
import com.asiadream.jcode.tool.java.model.ClassType;
import com.asiadream.jcode.tool.java.model.FieldModel;
import com.asiadream.jcode.tool.java.model.JavaModel;
import com.asiadream.jcode.tool.java.model.MethodModel;
import com.asiadream.jcode.tool.share.data.Pair;
import com.asiadream.jcode.tool.share.rule.NameRule;
import com.asiadream.jcode.tool.share.rule.PackageRule;
import com.asiadream.jcode.tool.share.util.file.PathUtil;
import com.asiadream.jcode.tool.share.util.string.StringUtil;
import com.asiadream.jcode.tool.spec.source.Source;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class JavaSource implements Source {
    //
    private static final Logger logger = LoggerFactory.getLogger(JavaSource.class);

    private CompilationUnit compilationUnit;
    private String physicalSourceFile;

    private boolean lexicalPreserving;
    private boolean useOwnPrinter;

    public JavaSource(String physicalSourceFile, boolean lexicalPreserving, boolean useOwnPrinter) throws FileNotFoundException {
        //
        this.physicalSourceFile = physicalSourceFile;
        this.lexicalPreserving = lexicalPreserving;
        this.useOwnPrinter = useOwnPrinter;

        //
        this.compilationUnit = JavaParser.parse(new FileInputStream(physicalSourceFile));
        if (lexicalPreserving) {
            LexicalPreservingPrinter.setup(compilationUnit);
        }
    }

    public JavaSource(JavaModel model) {
        //
        this.compilationUnit = AstMapper.createCompilationUnit(model);
    }

    public CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }

    public JavaModel toModel() {
        //
        return AstMapper.toJavaModel(compilationUnit, this::findFullName);
    }

    public ClassType toClassType() {
        //
        return ClassType.newClassType(getName(), getPackageName());
    }

    public boolean hasProperty(String... patterns) {
        //
        ClassOrInterfaceDeclaration classType = (ClassOrInterfaceDeclaration) compilationUnit.getType(0);
        for (FieldDeclaration fieldDeclaration : classType.getFields()) {
            String fieldTypeName = fieldDeclaration.getVariables().get(0).getType().toString();
            if (containsName(fieldTypeName, patterns)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsName(String name, String[] patterns) {
        //
        for (String pattern : patterns) {
            if (name.contains(pattern)) {
                return true;
            }
        }
        return false;
    }

    private String findFullName(String simpleClassName) {
        //
        for (Object obj : compilationUnit.getImports()) {
            ImportDeclaration importDeclaration = (ImportDeclaration) obj;
            String packageName = importDeclaration.getName().asString();
            if (packageName.endsWith("." + simpleClassName)) {
                return packageName;
            }
        }
        return simpleClassName;
    }

    public boolean isFromFile() {
        //
        return physicalSourceFile != null;
    }

    public static boolean exists(String physicalSourceFile) {
        //
        File file = new File(physicalSourceFile);
        return file.exists() && !file.isDirectory();
    }

    // com/foo/bar/SampleService.java
    @Override
    public String getSourceFilePath() {
        //
        //String packageName = compilationUnit.getPackageDeclaration().get().getNameAsString();
        String packageName = compilationUnit.getPackageDeclaration()
                .map(NodeWithName::getNameAsString)
                .orElseThrow(IllegalArgumentException::new);
        String typeName = compilationUnit.getType(0).getNameAsString();
        return packageName.replaceAll("\\.", Matcher.quoteReplacement(File.separator)) + File.separator + typeName + ".java";
    }

    public String getName() {
        //
        return compilationUnit.getType(0).getNameAsString();
    }

    public String getClassName() {
        //
        return getPackageName() + "." + getName();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private boolean isClassOrInterface() {
        //
        TypeDeclaration type = compilationUnit.getType(0);
        return type.isClassOrInterfaceDeclaration();
    }

    private ClassOrInterfaceDeclaration getClassOrInterface() {
        //
        return (ClassOrInterfaceDeclaration) compilationUnit.getType(0);
    }
    // -----------------------------------------------------------------------------------------------------------------

    public boolean isInterface() {
        //
        if (!isClassOrInterface()) {
            return false;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        return classType.isInterface();
    }

    public void setName(String name) {
        //
        TypeDeclaration<?> classType = compilationUnit.getType(0);
        classType.setName(name);

        if (classType instanceof ClassOrInterfaceDeclaration) {
            for (ConstructorDeclaration constructor : ((ClassOrInterfaceDeclaration)classType).getConstructors()) {
                constructor.setName(name);
            }
        }
    }

    public String getPackageName() {
        //
        return compilationUnit.getPackageDeclaration()
                .map(NodeWithName::getNameAsString)
                .orElse(null);
        //PackageDeclaration packageDeclaration = compilationUnit.getPackageDeclaration().orElse(null);
        //return packageDeclaration.getNameAsString();
    }

    public void setPackageName(String packageName) {
        //
        compilationUnit.getPackageDeclaration()
                .ifPresent(pd -> pd.setName(new Name(packageName)));
    }

    public void setImplementedType(JavaSource implementedType) {
        //
        setImplementedType(implementedType.getName(), implementedType.getPackageName());
    }

    public void setImplementedType(String name, String packageName) {
        //
        if (!isClassOrInterface()) {
            return;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        NodeList<ClassOrInterfaceType> nodeList = new NodeList<>();
        nodeList.add(JavaParser.parseClassOrInterfaceType(name));
        classType.setImplementedTypes(nodeList);

        compilationUnit.addImport(packageName + "." + name);
    }

    public void setExtendedType(JavaSource extendedType) {
        //
        setExtendedType(extendedType.getName(), extendedType.getPackageName());
    }

    public void setExtendedType(String name, String packageName) {
        //
        if (!isClassOrInterface()) {
            return;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        NodeList<ClassOrInterfaceType> nodeList = new NodeList<>();
        nodeList.add(JavaParser.parseClassOrInterfaceType(name));

        classType.setExtendedTypes(nodeList);

        compilationUnit.addImport(packageName + "." + name);
    }

    public String getExtendedType() {
        //
        if (!isClassOrInterface()) {
            return null;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        if (classType.getExtendedTypes().size() > 0) {
            return classType.getExtendedTypes(0).getNameAsString();
        }
        return null;
    }

    public boolean isExtends(String simpleClassName) {
        //
        if (!isClassOrInterface()) {
            return false;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        if (classType.getExtendedTypes().size() <= 0) {
            return false;
        }
        return classType.getExtendedTypes(0).getNameAsString().equals(simpleClassName);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public void addFieldAll(List<FieldModel> fieldModels) {
        //
        Optional.ofNullable(fieldModels).ifPresent(_fieldModels -> _fieldModels.forEach(this::addField));
    }

    public void addFieldAll(List<FieldModel> fieldModels, int index) {
        //
        if (fieldModels == null) {
            return;
        }

        for (int i = 0; i < fieldModels.size(); i++) {
            addField(fieldModels.get(i), index + i);
        }
    }

    public void addField(FieldModel fieldModel) {
        //
        if (!isClassOrInterface()) {
            return;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        classType.addMember(AstMapper.createFieldDeclaration(fieldModel));

        // add import
        addImport(fieldModel.getType());
    }

    public void addField(FieldModel fieldModel, int index) {
        //
        if (!isClassOrInterface()) {
            return;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        classType.getMembers().add(index, AstMapper.createFieldDeclaration(fieldModel));

        // add import
        addImport(fieldModel.getType());
    }

    public void addField(JavaSource fieldType, String varName, ClassType annotation) {
        //
        addField(fieldType.getName(), fieldType.getPackageName(), varName, annotation.getName(), annotation.getPackageName());
    }

    public void addField(String fieldTypeName, String packageName, String varName, String annotationName, String annotationPackageName) {
        //
        if (!isClassOrInterface()) {
            return;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        FieldDeclaration field = classType.addPrivateField(fieldTypeName, varName);
        addImport(fieldTypeName, packageName);

        if (annotationName != null) {
            addAnnotation(annotationName, annotationPackageName);
        }
    }

    public int getFieldsSize() {
        //
        return compilationUnit.getType(0).getFields().size();
    }

    public List<FieldModel> getFieldsAsModel() {
        //
        return compilationUnit.getType(0).getFields().stream().map(fieldDeclaration -> {
            FieldModel fieldModel = AstMapper.toFieldModel(fieldDeclaration, this::findFullName);
            //VariableDeclarator varDec = fieldDeclaration.getVariable(0);
            //return new FieldModel(varDec.getName().asString(), ClassType.newClassType(varDec.getType().asString()));
            return fieldModel;
        }).collect(Collectors.toList());
    }

    public List<MethodModel> getMethodsAsModel() {
        //
        return compilationUnit.getType(0).getMethods().stream().map(methodDec -> {
            MethodModel methodModel = AstMapper.toMethodModel(methodDec, this::findFullName);
            //MethodModel methodModel = new MethodModel(methodDec.getNameAsString(), ClassType.newClassType(methodDec.getTypeAsString()));
            return methodModel;
        }).collect(Collectors.toList());
    }

    public void addAnnotation(ClassType classType) {
        //
        addAnnotation(classType.getName(), classType.getPackageName());
    }

    public void addAnnotation(String annotation, String packageName) {
        //
        if (!isClassOrInterface()) {
            return;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        classType.addMarkerAnnotation(annotation);

        addImport(annotation, packageName);
    }

    public void addAnnotation(ClassType classType, String annotationArgs) {
        //
        addAnnotation(classType.getName(), classType.getPackageName(), annotationArgs);
    }

    public void addAnnotation(String annotation, String packageName, String annotationArgs) {
        //
        if (!isClassOrInterface()) {
            return;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        AnnotationExpr expr = new SingleMemberAnnotationExpr(new Name(annotation), new StringLiteralExpr(annotationArgs));
        classType.addAnnotation(expr);

        addImport(annotation, packageName);
    }

    public void addAnnotation(ClassType annotation, List<Pair<String, Object>> annotationArgs) {
        //
        if (!isClassOrInterface()) {
            return;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        NodeList<MemberValuePair> pairs = new NodeList<>();
        for (Pair<String, Object> pair : annotationArgs) {
            Expression expression;
            if (pair.y instanceof String) {
                expression = new StringLiteralExpr(pair.y.toString());
            } else if (pair.y instanceof Boolean) {
                expression = new BooleanLiteralExpr((Boolean) pair.y);
            } else {
                expression = new NameExpr(pair.y.toString());
            }
            pairs.add(new MemberValuePair(pair.x, expression));
        }
        AnnotationExpr expr = new NormalAnnotationExpr(new Name(annotation.getName()), pairs);
        classType.addAnnotation(expr);

        addImport(annotation.getClassName());
    }

    public void removeAnnotations(List<String> annotationsToRemove) {
        //
        if (annotationsToRemove == null) return;
        annotationsToRemove.forEach(annotationName -> findAnnotation(annotationName).ifPresent(Node::remove));
    }

    private Optional<AnnotationExpr> findAnnotation(String annotationName) {
        //
        if (!isClassOrInterface()) {
            return Optional.empty();
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        return classType.getAnnotationByName(annotationName);
    }

    public boolean containsAnnotation(String annotationClassName) {
        //
        if (!isClassOrInterface()) {
            return false;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        return classType.isAnnotationPresent(annotationClassName);
    }

    public void addImport(String importName) {
        //
        compilationUnit.addImport(importName);
    }

    public void addImport(String name, String packageName) {
        //
        compilationUnit.addImport(packageName + "." + name);
    }

    public void addImport(ClassType classType) {
        //
        if (classType == null || classType.isPrimitive() || classType.getName().equals("String")) {
            return;
        }
        compilationUnit.addImport(classType.getClassName());
    }

    public List<String> getImports() {
        //
        return compilationUnit.getImports()
                .stream()
                .map(id -> id.getName().toString())
                .collect(Collectors.toList());
    }

    public MethodDeclaration addMethod(MethodModel methodModel) {
        //
        if (!isClassOrInterface()) {
            return null;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();

        MethodDeclaration methodDeclaration = AstMapper.createMethodDeclaration(methodModel);
        classType.addMember(methodDeclaration);

        addImport(methodModel.getReturnType());

        return methodDeclaration;
    }

    public void addMethod(MethodModel methodModel, Consumer<MethodDeclaration> methodConsumer) {
        //
        MethodDeclaration methodDeclaration = addMethod(methodModel);
        methodConsumer.accept(methodDeclaration);
    }

    public void removeGetterAndSetter() {
        //
        if (!isClassOrInterface()) {
            return;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        for (FieldDeclaration fieldDeclaration : classType.getFields()) {
            VariableDeclarator variableDeclarator = fieldDeclaration.getVariable(0);
            String varName = variableDeclarator.getNameAsString();
            MethodDeclaration getter = findGetter(varName);
            if (getter != null && isPureGetterSetter(getter)) {
                getter.remove();
            }
            MethodDeclaration setter = findSetter(varName);
            if (setter != null && isPureGetterSetter(setter)) {
                setter.remove();
            }
        }
    }

    private boolean isPureGetterSetter(MethodDeclaration gettsetter) {
        //
        if (gettsetter.getAnnotations().size() > 0) {
            return false;
        }

        int stmtSize = gettsetter.getBody().map(blockStmt -> blockStmt.getStatements().size()).orElse(0);

        if (stmtSize == 0) {
            return true;
        }

        if (stmtSize > 1) {
            return false;
        }

        // stmtSize == 1
        Statement stmt = gettsetter.getBody().orElse(null).getStatement(0);

        //stmt must be return or expression.
        /*
        System.out.println("*** stmt try        ? "+ stmt.isTryStmt());
        System.out.println("*** stmt block      ? "+ stmt.isBlockStmt());
        System.out.println("*** stmt do         ? "+ stmt.isDoStmt());
        System.out.println("*** stmt empty      ? "+ stmt.isEmptyStmt());
        System.out.println("*** stmt expression ? "+ stmt.isExpressionStmt());
        System.out.println("*** stmt labeled    ? "+ stmt.isLabeledStmt());
        System.out.println("*** stmt return     ? "+ stmt.isReturnStmt());
        System.out.println("*** stmt unparsable ? "+ stmt.isUnparsableStmt());
        System.out.println(stmt);
        */

        if (stmt.isExpressionStmt() || stmt.isReturnStmt()) {
            return true;
        }

        return false;
    }

    public void forEachMethod(Consumer<MethodDeclaration> methodConsumer) {
        //
        if (!isClassOrInterface()) {
            return;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        List<MethodDeclaration> methodDeclarations = classType.getMethods();

        // for field, method ordering issue.
        for(MethodDeclaration methodDeclaration : methodDeclarations) {
            classType.remove(methodDeclaration);
        }

        for (MethodDeclaration methodDeclaration : methodDeclarations) {
            methodConsumer.accept(methodDeclaration);
            classType.addMember(methodDeclaration);
        }
    }

    public void removeMethod(String methodName) {
        //
        MethodDeclaration method = findMethod(methodName);
        if (method != null) {
            method.remove();
        }
    }

    public void removeMethodsByAnnotation(String annotationName) {
        //
        if (StringUtil.isEmpty(annotationName) || !isClassOrInterface()) {
            return;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        List<MethodDeclaration> methodDeclarations = classType.getMethods();
        methodDeclarations.forEach(methodDeclaration -> {
            if (methodDeclaration.isAnnotationPresent(annotationName)) {
                methodDeclaration.remove();
            }
        });
    }

    private MethodDeclaration findGetter(String varName) {
        //
        String getterName = "get" + Character.toUpperCase(varName.charAt(0)) + varName.substring(1);
        return findMethod(getterName);
    }

    private MethodDeclaration findSetter(String varName) {
        //
        String getterName = "set" + Character.toUpperCase(varName.charAt(0)) + varName.substring(1);
        return findMethod(getterName);
    }

    private MethodDeclaration findMethod(String methodName) {
        //
        List<MethodDeclaration> methodDeclarations = compilationUnit.getType(0).getMethods();

        for (MethodDeclaration methodDeclaration : methodDeclarations) {
            if (methodDeclaration.getNameAsString().equals(methodName)) {
                return methodDeclaration;
            }
        }
        return null;
    }

    public void changePackageAndName(NameRule nameRule, PackageRule packageRule) {
        //
        String thisClassName = getClassName();

        String changedClassName = null;
        if (packageRule != null) {
            changedClassName = packageRule.findWholeChangeImportName(thisClassName);
        }

        if (changedClassName != null) {
            Pair<String, String> packageAndName = PathUtil.devideClassName(changedClassName);
            setPackageName(packageAndName.x);
            setName(packageAndName.y);
        } else {
            // ! order sensitive. change name first!
            changeName(nameRule);
            changePackage(packageRule);
        }
    }

    private void changeName(NameRule nameRule) {
        //
        if (nameRule == null) {
            return;
        }
        String name = getName();
        String newName = nameRule.changeName(name);
        setName(newName);
    }

    private void changePackage(PackageRule packageRule) {
        //
        if (packageRule == null) {
            return;
        }

        String packageName = getPackageName();
        String name = getName();
        String newPackageName = packageRule.changePackage(packageName, name);
        setPackageName(newPackageName);
    }

    public void removeNoArgsConstructor() {
        //
        if (!isClassOrInterface()) {
            return;
        }

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        classType.getDefaultConstructor().ifPresent(Node::remove);
    }

    public void removeImports(PackageRule packageRule) {
        //
        if (!packageRule.hasRemoveImports()) {
            return;
        }

        List<ImportDeclaration> removeList = compilationUnit.getImports().stream()
                .filter(importDeclaration -> packageRule.containsRemoveImport(importDeclaration.getNameAsString()))
                .collect(Collectors.toList());

        for (ImportDeclaration toRemove : removeList) {
            compilationUnit.getImports().remove(toRemove);
        }
    }

    public void changeImports(NameRule nameRule, PackageRule packageRule) {
        //
        if (nameRule == null && packageRule == null) {
            return;
        }

        for (ImportDeclaration importDeclaration : compilationUnit.getImports()) {
            String importName = importDeclaration.getNameAsString();

            String wholeChangeImportName = packageRule.findWholeChangeImportName(importName);
            if (wholeChangeImportName != null) {
                importDeclaration.setName(wholeChangeImportName);
            } else {
                String changed = changeImportName(importName, nameRule, packageRule);
                if (StringUtil.isNotEmpty(changed) && changed.indexOf(".") > 0) {
                    importDeclaration.setName(changed);
                }
            }
        }
    }

    private String changeImportName(String importName, NameRule nameRule, PackageRule packageRule) {
        //
        String newImportName = importName;
        if (nameRule != null) {
            newImportName = nameRule.changeName(newImportName);
        }
        if (packageRule != null) {
            Pair<String, String> pair = PathUtil.devideClassName(newImportName);
            newImportName = packageRule.changePackage(pair.x, pair.y) + "." + pair.y;
        }
        return newImportName;
    }

    public void changeMethodUsingClassName(NameRule nameRule) {
        //
        if (nameRule == null) {
            return;
        }

        List<MethodDeclaration> methodDeclarations = compilationUnit.getType(0).getMethods();

        for (MethodDeclaration methodDeclaration : methodDeclarations) {
            Type returnType = methodDeclaration.getType();
            changeTypeName(returnType, nameRule);

            for (Parameter parameter : methodDeclaration.getParameters()) {
                Type parameterType = parameter.getType();
                changeTypeName(parameterType, nameRule);
            }
        }
    }

    private void changeTypeName(Type type, NameRule nameRule) {
        //
        if (nameRule == null) {
            return;
        }
        if (type.isClassOrInterfaceType()) {
            ClassOrInterfaceType classOrInterfaceType = ((ClassOrInterfaceType) type);
            String name = classOrInterfaceType.getNameAsString();
            name = nameRule.changeName(name);
            classOrInterfaceType.setName(name);
        }

        // change type arguments name
        if (type.isClassOrInterfaceType()) {
            Optional<NodeList<Type>> typeArguments = ((ClassOrInterfaceType) type).getTypeArguments();
            typeArguments.ifPresent(types -> changeTypeArgumentsName(types, nameRule));
        }
    }

    private void changeTypeArgumentsName(NodeList<Type> types, NameRule nameRule) {
        //
        Type typeArgsType = types.get(0);
        if (typeArgsType.isClassOrInterfaceType()) {
            String typeArgumentName = typeArgsType.asString();
            typeArgumentName = nameRule.changeName(typeArgumentName);
            ClassOrInterfaceType classOrInterfaceType = ((ClassOrInterfaceType) typeArgsType);
            classOrInterfaceType.setName(typeArgumentName);
        }
    }

    @Override
    public void write(String physicalTargetFilePath) throws IOException {
        //
        File file = new File(physicalTargetFilePath);
        String generated = generate();
        if (logger.isTraceEnabled()) {
            logger.trace(physicalTargetFilePath);
            logger.trace(generated);
        }

        FileUtils.writeStringToFile(file, generated, "UTF-8");
    }

    public String generate() {
        //
        logger.debug("generate... lexical ? {}, ownPrinter ? {}", lexicalPreserving, useOwnPrinter);
        if (lexicalPreserving) {
            return LexicalPreservingPrinter.print(compilationUnit);
        }
        if (useOwnPrinter) {
            PrettyPrinterConfiguration printerConfiguration = new PrettyPrinterConfiguration();
            printerConfiguration.setVisitorFactory(ToolPrintVisitor::new);
            return this.compilationUnit.toString(printerConfiguration);
        }
        return toString();
    }

    @Override
    public String toString() {
        //
        return this.compilationUnit.toString();
    }

    public boolean isUseOwnPrinter() {
        return useOwnPrinter;
    }

    public void setUseOwnPrinter(boolean useOwnPrinter) {
        this.useOwnPrinter = useOwnPrinter;
    }

    public boolean isExistField(String fieldName) {
        //
        Optional<FieldDeclaration> existField = compilationUnit.getType(0).getFieldByName(fieldName);
        return existField.isPresent();
    }

    public void calculateBlankOfLineComment() {
        //
        if (!isClassOrInterface()) {
            return;
        }

        PrettyPrinterConfiguration commentIgnore = new PrettyPrinterConfiguration();
        commentIgnore.setPrintComments(false);

        DataKey<Integer> CODE_LENGTH = new DataKey<Integer>() {
        };

        ClassOrInterfaceDeclaration classType = getClassOrInterface();
        List<FieldDeclaration> fields = classType.getFields();

        // Calculate the length of the code and max length.
        AtomicInteger maxLength = new AtomicInteger();
        fields.forEach(fieldDeclaration -> {
            String code = fieldDeclaration.toString(commentIgnore);
            int length = code.length();
            fieldDeclaration.setData(CODE_LENGTH, length);

            if (maxLength.get() < length) {
                maxLength.set(length);
            }
        });

        // Calculate the length of the blank of the line comment.
        fields.forEach(fieldDeclaration -> {
            int codeLength = fieldDeclaration.getData(CODE_LENGTH);
            int blankSize = maxLength.get() - codeLength + 1;
            logger.debug("blank size -> " + blankSize);
            fieldDeclaration.setData(ToolPrintVisitor.KEY_LINE_COMMENT_BLANK_SIZE, blankSize);
        });
    }

    public static void main(String[] args) {
        // In the case of Windows replaceAll with File.separator causes 'character to be escaped is missing' error.
        String packageName = "com.foo.bar";
        //System.out.println(packageName.replaceAll("\\.", File.separator));
        logger.info(packageName.replaceAll("\\.", Matcher.quoteReplacement(File.separator)));
    }
}
