package com.asiadream.jcode.tool.java.creator;

import com.asiadream.jcode.tool.java.model.JavaModel;
import com.asiadream.jcode.tool.java.source.JavaSource;
import com.asiadream.jcode.tool.java.writer.JavaWriter;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.spec.creator.ProjectItemCreator;

import java.io.IOException;

public class JavaCreator extends ProjectItemCreator<JavaModel> {
    //
    private JavaWriter javaWriter;

    public JavaCreator(ProjectConfiguration targetConfiguration) {
        //
        super(targetConfiguration);
        this.javaWriter = new JavaWriter(targetConfiguration);
    }

    @Override
    public void create(JavaModel model) throws IOException {
        //
        JavaSource javaSource = new JavaSource(model);
        javaWriter.write(javaSource);
    }
}
