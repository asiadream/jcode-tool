package com.asiadream.jcode.tool.analyzer;

import com.asiadream.jcode.tool.analyzer.entity.JavaDependency;
import com.asiadream.jcode.tool.analyzer.store.JavaDependencyStore;
import com.asiadream.jcode.tool.analyzer.store.StoreConfig;
import com.asiadream.jcode.tool.analyzer.viewer.DependencyTreeViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class ModuleDependencyTreeView {
    //
	private static final Logger logger  = LoggerFactory.getLogger(ModuleDependencyTreeView.class);
	
    private static ApplicationContext ctx;

    public static void main(String[] args) {
        //
        check(args);

        ctx = new AnnotationConfigApplicationContext(StoreConfig.class);
        JavaDependencyStore store = ctx.getBean(JavaDependencyStore.class);

        // 특정 모듈이 참조하는 전체 모듈 조회
        //List<JavaDependency> list = store.findByFromModule("nara.pavilion");

        // 특정 모듈이 참조하는 1단계 모듈 조회
        //List<JavaDependency> list = store.findByFromModule("nara.pavilion", 2);

        // 특정 모듈이 참조하는 일부 모듈 조회
        // fromModule: amis3.bp.bp, toModule: amis3, toLevel: 3
        List<JavaDependency> list = store.findByFromModuleAndStartWithToModule(args[0], args[1], Integer.parseInt(args[2]));

        String fromModuleName = "[" + args[0] + "]";
        logger.info(new DependencyTreeViewer(fromModuleName, list).show());
        logger.info("total : {}", list.size());
        
        //list.forEach(System.out::println);
    }

    private static void check(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage : java ModuleDependencyTreeView [fromPackage] [toPackage] [toLevel]");
            System.exit(0);
        }
    }
}
