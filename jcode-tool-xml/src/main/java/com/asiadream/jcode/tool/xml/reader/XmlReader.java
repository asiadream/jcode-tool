package com.asiadream.jcode.tool.xml.reader;

import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import com.asiadream.jcode.tool.spec.reader.Reader;
import com.asiadream.jcode.tool.xml.source.XmlSource;
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
