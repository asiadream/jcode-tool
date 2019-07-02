package com.asiadream.jcode.tool.project.meta;

import com.asiadream.jcode.tool.share.util.string.StringUtil;

public class DependencyMeta {
    //
    private String groupId;
    private String name;
    private String version;
    private String type;
    private String ref;

    public boolean existRef() {
        //
        return StringUtil.isNotEmpty(ref);
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
