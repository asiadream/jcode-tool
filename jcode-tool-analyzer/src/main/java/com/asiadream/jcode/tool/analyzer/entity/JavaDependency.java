package com.asiadream.jcode.tool.analyzer.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(indexes = @Index(name = "IDX_FROM_TO_JAVA_DEP", unique = true, columnList = "fromModule,toModule"))
public class JavaDependency {
    //
    @Id
    private String id;
    private String fromModule;
    private int fromLevel;
    private String toModule;
    private int toLevel;

    public static List<JavaDependency> shortenToModuleName(String skipPrefix, List<JavaDependency> list) {
        //
        return list.stream().map(jd -> {
            String toModule = jd.getToModule();
            if (toModule.startsWith(skipPrefix)) {
                jd.setToModule(toModule.split(skipPrefix)[1]);
            }
            return jd;
        }).collect(Collectors.toList());
    }

    public JavaDependency() {
    }

    public JavaDependency(String fromModule, int fromLevel, String toModule, int toLevel) {
        //
        this.id = UUID.randomUUID().toString();
        this.fromModule = fromModule;
        this.fromLevel = fromLevel;
        this.toModule = toModule;
        this.toLevel = toLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromModule() {
        return fromModule;
    }

    public void setFromModule(String fromModule) {
        this.fromModule = fromModule;
    }

    public String getToModule() {
        return toModule;
    }

    public void setToModule(String toModule) {
        this.toModule = toModule;
    }

    public int getFromLevel() {
        return fromLevel;
    }

    public void setFromLevel(int fromLevel) {
        this.fromLevel = fromLevel;
    }

    public int getToLevel() {
        return toLevel;
    }

    public void setToLevel(int toLevel) {
        this.toLevel = toLevel;
    }

    @Override
    public String toString() {
        return "JavaDependency{" +
                "id='" + id + '\'' +
                ", fromModule='" + fromModule + '\'' +
                ", toModule='" + toModule + '\'' +
                '}';
    }
}
