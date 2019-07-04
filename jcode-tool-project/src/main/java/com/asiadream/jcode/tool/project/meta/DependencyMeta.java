package com.asiadream.jcode.tool.project.meta;

import com.asiadream.jcode.tool.project.model.Dependency;
import com.asiadream.jcode.tool.project.model.DependencyType;
import com.asiadream.jcode.tool.share.util.string.StringUtil;

import java.util.Optional;

public class DependencyMeta {
    //
    private String group;
    private String name;
    private String version;
    private DependencyType type;
    private String ref;

    public Dependency toDependencyModel() {
        //
        Dependency dependency = new Dependency(group, name, version);
        Optional.ofNullable(type).ifPresent(type -> dependency.setType(type));
        return dependency;
    }

    public boolean existRef() {
        //
        return StringUtil.isNotEmpty(ref);
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DependencyType getType() {
        return type;
    }

    public void setType(DependencyType type) {
        this.type = type;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
