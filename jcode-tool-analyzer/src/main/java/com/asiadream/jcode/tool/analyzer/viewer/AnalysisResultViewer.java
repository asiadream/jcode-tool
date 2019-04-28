package com.asiadream.jcode.tool.analyzer.viewer;

import java.util.List;

public class AnalysisResultViewer {
    //
    private ModuleNode root;
    
    public AnalysisResultViewer(AnalysisResult analysisResult) {
        //
        this.root = ModuleNode.createModuleNode(analysisResult.getName(), analysisResult);
    }

    public AnalysisResultViewer(List<AnalysisResult> results) {
        //
        this.root = new ModuleNode();
        for (AnalysisResult result : results) {
            ModuleNode.addModuleNode(result.getName(), result, root);
        }
    }

    public String show() {
        //
        return root.show("");
    }

    @Override
    public String toString() {
        //
        return root.toString();
    }
}
