package com.asiadream.jcode.tool.java.converter.config;

import com.asiadream.jcode.tool.share.rule.PackageRule;

import java.util.List;
import java.util.Optional;

public class PackageRuleConfig {
    //
    private boolean skipChangeImports;
    private List<FromToConfig> changeElements;

    public PackageRule toPackageRule() {
        //
        PackageRule packageRule = PackageRule.newInstance();

        packageRule.setSkipChangeImports(skipChangeImports);
        Optional.ofNullable(changeElements).ifPresent(changeElements -> changeElements.forEach(element ->
                packageRule.add(element.fromIndex, element.from, element.to)));

        return packageRule;
    }

    public boolean isSkipChangeImports() {
        return skipChangeImports;
    }

    public void setSkipChangeImports(boolean skipChangeImports) {
        this.skipChangeImports = skipChangeImports;
    }

    public List<FromToConfig> getChangeElements() {
        return changeElements;
    }

    public void setChangeElements(List<FromToConfig> changeElements) {
        this.changeElements = changeElements;
    }
}
