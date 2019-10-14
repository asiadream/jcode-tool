package com.asiadream.jcode.tool.java.converter.config;

public class FromToConfig {
    //
    String from;
    String to;
    int fromIndex;
    int toIndex;

    public FromToConfig() {
        this.fromIndex = -1;
        this.toIndex = -1;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getFromIndex() {
        return fromIndex;
    }

    public void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }

    public int getToIndex() {
        return toIndex;
    }

    public void setToIndex(int toIndex) {
        this.toIndex = toIndex;
    }
}
