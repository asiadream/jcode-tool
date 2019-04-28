package com.asiadream.jcode.tool.analyzer.viewer;

import com.asiadream.jcode.tool.analyzer.entity.JavaDependency;

import java.util.List;

public class DependencyViewer {
    //
    private ModuleNode root;

    public DependencyViewer(List<JavaDependency> modules) {
        this.root = new ModuleNode("");
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