package com.asiadream.jcode.tool.analyzer.analyzer;

import com.asiadream.jcode.tool.analyzer.store.JavaDependencyStore;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class JavaAnalyzer implements Analyzer {
    //
	private static final Logger logger  = LoggerFactory.getLogger(JavaAnalyzer.class);
	
    private ProjectConfiguration configuration;
    private JavaDependencyStore store;

    public JavaAnalyzer(ProjectConfiguration configuration, JavaDependencyStore store) {
        //
        this.configuration = configuration;
        this.store = store;
    }

    @Override
    public void analyze(String sourceFile) throws IOException {
        //
        String physicalSourceFile = configuration.makePhysicalJavaSourceFilePath(sourceFile);
        CompilationUnit cu = JavaParser.parse(new FileInputStream(physicalSourceFile));

        VoidVisitor<?> importVisitor = new ImportPrinter(toSourceName(sourceFile), store);
        importVisitor.visit(cu, null);
    }

    private String toSourceName(String sourceFile) {
        //
        return sourceFile.split(".java")[0].replace(File.separator, ".");
    }

    private static class ImportPrinter extends VoidVisitorAdapter<Void> {
        //
        private String sourceName;
        private JavaDependencyStore store;

        public ImportPrinter(String sourceName, JavaDependencyStore store) {
            this.sourceName = sourceName;
            this.store = store;
        }

        @Override
        public void visit(ImportDeclaration importDeclaration, Void arg) {
            super.visit(importDeclaration, arg);
            String referenceName = importDeclaration.getNameAsString();
            logger.info("{} Reference {}", sourceName, referenceName);

            new PackageDependencyWriter(sourceName, referenceName, store).write();
        }
    }
}
