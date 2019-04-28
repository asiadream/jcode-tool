package com.asiadream.jcode.tool.analyzer;

import com.asiadream.jcode.tool.analyzer.entity.JavaDependency;
import com.asiadream.jcode.tool.analyzer.store.JavaDependencyStore;
import com.asiadream.jcode.tool.analyzer.viewer.DependencyFlatViewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ModuleDependencyFlatView {
    //
	private static final Logger logger  = LoggerFactory.getLogger(ModuleDependencyFlatView.class);
	
    public static void main(String[] args) {
        //
        check(args);

        JavaDependencyStore store = AnalyzerContext.getJavaDependencyStore();

        // 특정 모듈이 참조하는 일부 모듈 조회
        // fromModule: amis3.bp.bp, toModule: amis3, toLevel: 3
        List<JavaDependency> list = store.findByFromModuleAndStartWithToModule(args[0], args[1], Integer.parseInt(args[2]));

        logger.info(new DependencyFlatViewer(list).show());
        logger.info("total : {}", list.size());
        
    }

    private static void check(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage : java ModuleDependencyTreeView [fromPackage] [toPackage] [toLevel]");
            System.exit(0);
        }
    }
}
