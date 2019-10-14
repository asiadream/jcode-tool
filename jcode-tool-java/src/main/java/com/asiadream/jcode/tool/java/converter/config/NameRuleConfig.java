package com.asiadream.jcode.tool.java.converter.config;

import com.asiadream.jcode.tool.share.rule.NameRule;

import java.util.List;
import java.util.Optional;

public class NameRuleConfig {
    //
    private List<FromToConfig> changePostfixes;

    public NameRule toNameRule() {
        //
        NameRule nameRule = NameRule.newInstance();
        Optional.ofNullable(changePostfixes).ifPresent(changePostfixes -> changePostfixes.forEach(changePostfix ->
                nameRule.add(changePostfix.from, changePostfix.to)));
        return nameRule;
    }

    public List<FromToConfig> getChangePostfixes() {
        return changePostfixes;
    }

    public void setChangePostfixes(List<FromToConfig> changePostfixes) {
        this.changePostfixes = changePostfixes;
    }
}
