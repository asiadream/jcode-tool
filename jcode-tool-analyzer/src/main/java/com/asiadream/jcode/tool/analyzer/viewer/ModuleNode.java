package com.asiadream.jcode.tool.analyzer.viewer;

import java.util.ArrayList;
import java.util.List;

public class ModuleNode {
    //
    private String name;
    private AnalysisResult result;

    private List<ModuleNode> children;
    private ModuleNode parent;

    public static ModuleNode createModuleNode(String packageName) {
        //
        ModuleNode root = new ModuleNode();
        root.add(packageName);
        return root;
    }

    public static ModuleNode createModuleNode(String packageName, AnalysisResult result) {
        //
        return addModuleNode(packageName, result, new ModuleNode());
    }

    public static ModuleNode addModuleNode(String packageName, ModuleNode node) {
        //
        return addModuleNode(packageName, null, node);
    }

    public static ModuleNode addModuleNode(String packageName, AnalysisResult result, ModuleNode node) {
        //
        ModuleNode leafNode = node.add(packageName);
        leafNode.setResult(result);
        return node;
    }

    public ModuleNode() {
        //
        this(null, null);
    }

    public ModuleNode(String name) {
        //
        this(name, null);
    }

    private ModuleNode(String name, ModuleNode parent) {
        //
        this.name = name;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

//    public ModuleNode(String name, boolean isPackageName, AnalysisResult result) {
//        //
//        this(isPackageName ? PackageNameUtil.getFirstName(name) : name, null);
//        if (isPackageName) {
//            ModuleNode leafNode = add(PackageNameUtil.getRemainNamesExceptFirst(name));
//            leafNode.setResult(result);
//        }
//    }

    private ModuleNode add(String packageName) {
        // childName : com.foo.bar
        int dotIndex = packageName.indexOf(".");
        if (dotIndex <= 0) {
            return checkAndNewChild(packageName);
        }

        String parentName = packageName.substring(0, dotIndex);
        String childName = packageName.substring(dotIndex + 1);
        ModuleNode childNode = checkAndNewChild(parentName);
        return childNode.add(childName);
    }

    private ModuleNode checkAndNewChild(String nodeName) {
        //
        ModuleNode moduleNode = find(nodeName);
        if (moduleNode == null) {
            moduleNode = new ModuleNode(nodeName, this);
            this.children.add(moduleNode);
        }
        return moduleNode;
    }

    private ModuleNode find(String nodeName) {
        //
        if (this.isRoot() && this.name == null) {
            this.name = nodeName;
            return this;
        }

        if (this.name.equals(nodeName)) {
            return this;
        }

        for (ModuleNode moduleNode : children) {
            if (moduleNode.name.equals(nodeName)) {
                return moduleNode;
            }
        }
        return null;
    }

    public boolean isRoot() {
        //
        return this.parent == null;
    }

    public String getName() {
        return name;
    }

    public AnalysisResult getResult() {
        return result;
    }

    public void setResult(AnalysisResult result) {
        this.result = result;
    }

    public List<ModuleNode> getChildren() {
        return children;
    }

    public ModuleNode getParent() {
        return parent;
    }

    public String show() {
        return show("");
    }

    public String show(String prefix) {
        //
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(prefix).append("+ ").append(name).append(showResult());
        for (ModuleNode child : children) {
            sb.append(child.show(prefix + "    "));
        }
        return sb.toString();
    }

    private String showResult() {
        //
        if (result == null) return "";
        return " -> " + result.showDependencies();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n +").append(this.name);
        for (ModuleNode child : children) {
            sb.append(child.toString());
        }
        return sb.toString();
    }
}