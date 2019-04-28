package com.asiadream.jcode.tool.analyzer.viewer;

import com.asiadream.jcode.tool.analyzer.entity.JavaDependency;

import java.util.List;

public class DependencyTreeViewer {
    //
    private ModuleNode root;

    public DependencyTreeViewer(String rootNodeName, List<JavaDependency> modules) {
        this.root = new ModuleNode(rootNodeName);
        for (JavaDependency module : modules) {
            ModuleNode.addModuleNode(module.getToModule(), this.root);
            //this.root.add(module.getToModule());
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