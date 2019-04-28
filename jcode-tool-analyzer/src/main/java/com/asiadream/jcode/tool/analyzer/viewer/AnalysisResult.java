package com.asiadream.jcode.tool.analyzer.viewer;

import com.asiadream.jcode.tool.analyzer.entity.JavaDependency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalysisResult {
    //
    private String name;
    private List<JavaDependency> dependencies;

    public AnalysisResult(String name, List<JavaDependency> dependencies) {
        this.name = name;
        this.dependencies = dependencies;
    }

    public static List<AnalysisResult> isolateResults(List<JavaDependency> list) {
        //
        Map<String, List<JavaDependency>> resultMap = new HashMap<>();
        for (JavaDependency dependency : list) {
            String fromModule = dependency.getFromModule();
            if (resultMap.containsKey(fromModule)) {
                resultMap.get(fromModule).add(dependency);
            } else {
                List<JavaDependency> subList = new ArrayList<>();
                subList.add(dependency);
                resultMap.put(fromModule, subList);
            }
        }

        List<AnalysisResult> results = new ArrayList<>();
        for (String fromModule : resultMap.keySet()) {
            results.add(new AnalysisResult(fromModule, resultMap.get(fromModule)));
        }
        return results;
    }

    public String showDependencies() {
        //
        return dependencies.stream()
                .map(jd -> jd.getToModule())
                .collect(Collectors.joining(","));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JavaDependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<JavaDependency> dependencies) {
        this.dependencies = dependencies;
    }
}
