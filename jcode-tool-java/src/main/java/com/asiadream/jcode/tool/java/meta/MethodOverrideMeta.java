package com.asiadream.jcode.tool.java.meta;

import java.util.List;
import java.util.Optional;

public class MethodOverrideMeta extends MethodMeta {
    //
    private List<MethodMatchMeta> matches;

    public MethodOverrideMeta replaceExp(ExpressionContext expressionContext) {
        //
        super.replaceExp(expressionContext);
        Optional.ofNullable(matches).ifPresent(matches -> matches.forEach(match -> match.replaceExp(expressionContext)));
        return this;
    }

    public void override(MethodMeta methodMeta) {
        // TODO : add the others
        Optional.ofNullable(getAccess()).ifPresent(methodMeta::setAccess);
        Optional.ofNullable(getAnnotations()).ifPresent(methodMeta::addAnnotations);
        // override body if method name match
        Optional<MethodMatchMeta> matchMeta = findMatch(methodMeta.getName());
        matchMeta.ifPresent(_matchMeta -> {
            methodMeta.setBody(_matchMeta.getBody());
            methodMeta.addAnnotations(_matchMeta.getAnnotations());
            methodMeta.setImports(_matchMeta.getImports());
        });
    }

    private Optional<MethodMatchMeta> findMatch(String name) {
        //
        if (matches == null) {
            return Optional.ofNullable(null);
        }
        return matches.stream().filter(match -> match.match(name)).findAny();
    }

    public List<MethodMatchMeta> getMatches() {
        return matches;
    }

    public void setMatches(List<MethodMatchMeta> matches) {
        this.matches = matches;
    }
}
