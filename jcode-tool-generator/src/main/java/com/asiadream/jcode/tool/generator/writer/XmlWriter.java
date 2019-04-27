package com.asiadream.jcode.tool.generator.writer;

import com.asiadream.jcode.tool.generator.source.XmlSource;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;

import javax.xml.transform.TransformerException;
import java.io.IOException;

public class XmlWriter implements Writer<XmlSource> {
    //
    private ProjectConfiguration configuration;

    public XmlWriter(ProjectConfiguration configuration) {
        //
        this.configuration = configuration;
    }

    @Override
    public void write(XmlSource source) throws IOException {
        //
        String targetFilePath = source.getSourceFilePath();
        if (source.isResourceFile()) {
            writeSource(source, configuration.makePhysicalResourceFilePath(targetFilePath));
        } else {
            writeSource(source, configuration.makePhysicalHomeFilePath(targetFilePath));
        }
    }

    private void writeSource(XmlSource source, String physicalTargetFilePath) throws IOException {
        //
        try {
            source.write(physicalTargetFilePath);
        } catch (TransformerException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
    }
}
