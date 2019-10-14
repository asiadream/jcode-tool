package com.asiadream.jcode.tool.java.creator;

import com.asiadream.jcode.tool.java.model.JavaModel;
import com.asiadream.jcode.tool.java.source.JavaSource;
import com.asiadream.jcode.tool.java.writer.JavaWriter;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.spec.creator.ProjectItemCreator;
import com.asiadream.jcode.tool.spec.model.SourceModel;

import java.io.IOException;

public class JavaCreator extends ProjectItemCreator {
    //
    private JavaWriter javaWriter;

    public JavaCreator(ProjectConfiguration targetConfiguration) {
        //
        super(targetConfiguration);
        this.javaWriter = new JavaWriter(targetConfiguration);
    }

    @Override
    public void create(String sourceFileName, SourceModel model) throws IOException {
        //
        JavaModel javaModel = (JavaModel) model; // FIXME : without casting...
        JavaSource javaSource = new JavaSource(javaModel);
        javaWriter.write(javaSource);
    }
}
