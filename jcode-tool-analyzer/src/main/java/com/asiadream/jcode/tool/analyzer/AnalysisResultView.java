package com.asiadream.jcode.tool.analyzer;

import com.asiadream.jcode.tool.analyzer.entity.JavaDependency;
import com.asiadream.jcode.tool.analyzer.store.JavaDependencyStore;
import com.asiadream.jcode.tool.analyzer.viewer.AnalysisResult;
import com.asiadream.jcode.tool.analyzer.viewer.AnalysisResultViewer;
import com.asiadream.jcode.tool.share.util.string.PackageNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AnalysisResultView {
    //
	private static final Logger logger  = LoggerFactory.getLogger(AnalysisResultView.class);

    public static void main(String[] args) {
        //
        check(args);

        JavaDependencyStore store = AnalyzerContext.getJavaDependencyStore();

        // kr.amc.amil.tool.generator 0 kr.amc.amil.tool 1
        String fromPackage = args[0];
        int fromSubLevel = Integer.parseInt(args[1]);
        int fromLevel = PackageNameUtil.size(fromPackage) + fromSubLevel;
        String toPackage = args[2];
        int toSubLevel = Integer.parseInt(args[3]);
        int toLevel = PackageNameUtil.size(toPackage) + toSubLevel;

        List<JavaDependency> list = store.findByStartWithFromModuleAndStartWithToModule(fromPackage, fromLevel, toPackage, toLevel);
        list = JavaDependency.shortenToModuleName(toPackage + ".", list);
        List<AnalysisResult> results = AnalysisResult.isolateResults(list);

        logger.info(new AnalysisResultViewer(results).show());
    }

    private static void check(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage : java AnalysisResultView [fromPackage] [fromSubLevel] [toPackage] [toSubLevel]");
            System.exit(0);
        }
    }
}
