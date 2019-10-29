package com.asiadream.jcode.tool.spec.creator;

import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.spec.model.SourceModel;

public abstract class ProjectItemCreator<T extends SourceModel> implements Creator<T> {
    //
    protected ProjectConfiguration targetConfiguration;

    public ProjectItemCreator(ProjectConfiguration targetConfiguration) {
        //
        this.targetConfiguration = targetConfiguration;
    }

}
