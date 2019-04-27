package com.asiadream.jcode.tool.generator.reader;

import com.asiadream.jcode.tool.generator.source.XmlSource;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class XmlReader implements Reader<XmlSource> {
    //
    private ProjectConfiguration configuration;

    public XmlReader(ProjectConfiguration configuration) {
        //
        this.configuration = configuration;
    }

    @Override
    public XmlSource read(String sourceFilePath) throws IOException {
        //
        String physicalSourceFilePath = configuration.makePhysicalResourceFilePath(sourceFilePath);
        try {
            return new XmlSource(physicalSourceFilePath, sourceFilePath);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
