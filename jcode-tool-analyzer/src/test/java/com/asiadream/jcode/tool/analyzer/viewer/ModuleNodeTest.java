package com.asiadream.jcode.tool.analyzer.viewer;

import com.asiadream.jcode.tool.analyzer.entity.JavaDependency;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ModuleNodeTest {

    @Test
    public void testCreateModuleNode() {
        //
        ModuleNode node = ModuleNode.createModuleNode("com.foo.bar");
        System.out.println(node.show());
        Assert.assertEquals("\n+ com\n    + foo\n        + bar", node.show(""));
    }

    @Test
    public void testAddModuleNode() {
        //
        ModuleNode root = ModuleNode.createModuleNode("com.foo.bar");
        ModuleNode.addModuleNode("com.gee.opp", root);
        System.out.println(root.show());
        Assert.assertEquals("\n+ com\n    + foo\n        + bar\n    + gee\n        + opp", root.show(""));
    }

    @Test
    public void testCreateModuleNodeAndResult() {
        //
        List<JavaDependency> dependencies = new ArrayList<>();
        dependencies.add(new JavaDependency(null, 0, "org.apache", 0));
        dependencies.add(new JavaDependency(null, 0, "org.apache1", 0));
        AnalysisResult result = new AnalysisResult("com.foo.bar", dependencies);

        ModuleNode node = ModuleNode.createModuleNode("com.foo.bar", result);
        System.out.println(node.show());
//        + com
//            + foo
//                + bar -> org.apache,org.apache1
    }

    @Test
    public void testCreateModuleNodeAndResults() {
        //
        String name1 = "com.foo.bar";
        List<JavaDependency> dependencies1 = new ArrayList<>();
        dependencies1.add(new JavaDependency(name1, 0, "org.apache", 0));
        dependencies1.add(new JavaDependency(name1, 0, "org.apache1", 0));
        AnalysisResult result1 = new AnalysisResult(name1, dependencies1);

        //
        String name2 = "com.foo.lee";
        List<JavaDependency> dependencies2 = new ArrayList<>();
        dependencies2.add(new JavaDependency(name2, 0, "org.spring", 0));
        dependencies2.add(new JavaDependency(name2, 0, "log4j", 0));
        AnalysisResult result2 = new AnalysisResult(name2, dependencies2);

        //
        ModuleNode root = new ModuleNode();
        ModuleNode.addModuleNode(name1, result1, root);
        ModuleNode.addModuleNode(name2, result2, root);

        System.out.println(root.show());
    }
}
