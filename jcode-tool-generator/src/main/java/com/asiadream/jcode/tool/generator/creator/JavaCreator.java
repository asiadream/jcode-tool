package com.asiadream.jcode.tool.generator.creator;

import com.asiadream.jcode.tool.generator.model.JavaModel;
import com.asiadream.jcode.tool.generator.model.SourceModel;
import com.asiadream.jcode.tool.generator.source.JavaSource;
import com.asiadream.jcode.tool.generator.writer.JavaWriter;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;

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
