package com.asiadream.jcode.tool.spec.creator;

import com.asiadream.jcode.tool.share.config.ProjectConfiguration;

public abstract class ProjectItemCreator implements Creator {
    //
    protected ProjectConfiguration targetConfiguration;

    public ProjectItemCreator(ProjectConfiguration targetConfiguration) {
        //
        this.targetConfiguration = targetConfiguration;
    }

}
