package com.asiadream.jcode.tool.analyzer;

import com.asiadream.jcode.tool.analyzer.analyzer.Analyzer;
import com.asiadream.jcode.tool.analyzer.analyzer.PackageAnalyzer;
import com.asiadream.jcode.tool.analyzer.store.JavaDependencyStore;
import com.asiadream.jcode.tool.analyzer.store.StoreConfig;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CodeAnalysis {
	//
	private static final Logger logger  = LoggerFactory.getLogger(CodeAnalysis.class);
    //private static final String SOURCE_PROJECT_HOME = "C:\\AMIS3_DEV\\server\\workspace\\amis3";
    private static ApplicationContext ctx;
    
    public static void main(String[] args) throws Exception {
    	//
        check(args);
        ctx = new AnnotationConfigApplicationContext(StoreConfig.class);
        JavaDependencyStore store = ctx.getBean(JavaDependencyStore.class);

        analysis(store, args[0], args[1]);
        //analysis(store, "-mdm");
        //analysis(store, "-etc");
    }

    private static void check(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage : java CodeAnalysis [Project Home] [Base Package]");
            System.exit(0);
        }
    }

    private static void analysis(JavaDependencyStore store, String sourceProjectHome, String basePackage) throws Exception {
        //
        ProjectConfiguration configuration = new ProjectConfiguration(ConfigurationType.Source, sourceProjectHome, false);

        // 1. java file analyze
        //Analyzer analyzer = new JavaAnalyzer(configuration, store);
        //analyzer.analyze("com/foo/bar/service/SampleService.java");

        // 2. package analyze
        Analyzer analyzer = new PackageAnalyzer(configuration, store);
        analyzer.analyze(basePackage);
        logger.info("### Complete analyze ###");

        //
        //List<JavaDependency> list = store.findByFromModule("com.foo.bar.service.SampleService");
        //List<JavaDependency> list = store.findAll();
        //list.forEach(System.out::println);
    }
}
