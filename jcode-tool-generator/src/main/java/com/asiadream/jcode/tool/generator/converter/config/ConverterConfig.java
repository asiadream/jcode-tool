package com.asiadream.jcode.tool.generator.converter.config;

import com.asiadream.jcode.tool.share.rule.NameRule;
import com.asiadream.jcode.tool.share.rule.PackageRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConverterConfig {
    //
    private String convertMarkAnnotation; // Annotation to determine conversion
    private String deleteMarkAnnotation;  // Annotation to decide whether to delete
    private List<String> annotationsToRemove;
    private NameRuleConfig nameRule;
    private PackageRuleConfig packageRule;

    public PackageRule toPackageRule() {
        //
        return Optional.ofNullable(packageRule).map(PackageRuleConfig::toPackageRule).orElse(null);
    }

    public NameRule toNameRule() {
        //
        return Optional.ofNullable(nameRule).map(NameRuleConfig::toNameRule).orElse(null);
    }
    public ConverterConfig() {
        //
        this.annotationsToRemove = new ArrayList<>();
    }

    public ConverterConfig addAnnotationToRemove(String annotationName) {
        //
        this.annotationsToRemove.add(annotationName);
        return this;
    }

    public String getConvertMarkAnnotation() {
        return convertMarkAnnotation;
    }

    public void setConvertMarkAnnotation(String convertMarkAnnotation) {
        this.convertMarkAnnotation = convertMarkAnnotation;
    }

    public String getDeleteMarkAnnotation() {
        return deleteMarkAnnotation;
    }

    public void setDeleteMarkAnnotation(String deleteMarkAnnotation) {
        this.deleteMarkAnnotation = deleteMarkAnnotation;
    }

    public List<String> getAnnotationsToRemove() {
        return annotationsToRemove;
    }

    public void setAnnotationsToRemove(List<String> annotationsToRemove) {
        this.annotationsToRemove = annotationsToRemove;
    }

    public NameRuleConfig getNameRule() {
        return nameRule;
    }

    public void setNameRule(NameRuleConfig nameRule) {
        this.nameRule = nameRule;
    }

    public PackageRuleConfig getPackageRule() {
        return packageRule;
    }

    public void setPackageRule(PackageRuleConfig packageRule) {
        this.packageRule = packageRule;
    }
}
