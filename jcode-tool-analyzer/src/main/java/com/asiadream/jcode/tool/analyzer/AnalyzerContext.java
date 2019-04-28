package com.asiadream.jcode.tool.analyzer;

import com.asiadream.jcode.tool.analyzer.store.JavaDependencyStore;
import com.asiadream.jcode.tool.analyzer.store.StoreConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnalyzerContext {

    public static JavaDependencyStore getJavaDependencyStore() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(StoreConfig.class);
        return ctx.getBean(JavaDependencyStore.class);
    }
}
