package com.asiadream.jcode.tool.java.meta;

public class MethodMatchMeta extends MethodMeta {
    //
    private String when;

    public MethodMatchMeta replaceExp(ExpressionContext expressionContext) {
        //
        super.replaceExp(expressionContext);
        this.when = expressionContext.replaceExpString(when);
        return this;
    }

    public boolean match(String name) {
        //
        if (when == null)
            return false;
        return when.equals(name);
    }

    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }
}
