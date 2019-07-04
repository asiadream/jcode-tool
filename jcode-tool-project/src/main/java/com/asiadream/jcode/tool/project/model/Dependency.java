package com.asiadream.jcode.tool.project.model;

public class Dependency {
    //
    private String groupId;
    private String name;
    private String version;
    private DependencyType type;
    private String scope;

    public Dependency(String groupId, String name) {
        //
        this(groupId, name, null, DependencyType.Compile, null);
    }

    public Dependency(String groupId, String name, String version) {
        //
        this(groupId, name, version, DependencyType.Compile, null);
    }

    public Dependency(String groupId, String name, String version, DependencyType type, String scope) {
        //
        this.groupId = groupId;
        this.name = name;
        this.version = version;
        this.type = type;
        this.scope = scope;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * @return {groupId}:{name}:{version}
     */
    public String toColonSeparatedString() {
        //
        StringBuffer sb = new StringBuffer();
        sb.append(groupId).append(":").append(name);
        if (version != null) {
            sb.append(":").append(version);
        }
        return sb.toString();
    }

    /**
     * @return :{name}
     */
    public String toSimpleString() {
        //
        return ":" + name;
    }


}
