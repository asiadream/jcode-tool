package com.asiadream.jcode.tool.generator.checker;

import com.asiadream.jcode.tool.generator.converter.ProjectItemConverter;
import com.asiadream.jcode.tool.generator.converter.ProjectItemType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class SAXParseChecker extends ProjectItemConverter {
    //
    private static Logger logger = LoggerFactory.getLogger("sqlmap-checker");

    public SAXParseChecker(ProjectConfiguration sourceConfiguration) {
        super(sourceConfiguration, null, ProjectItemType.MyBatisMapper, null);
    }

    @Override
    public String convert(String sourceFilePath) throws IOException {
        //
        if (sourceFilePath.endsWith(".out.xml")) {
            System.err.println("Skip convert '.out.xml' --> " + sourceFilePath);
            return null;
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);

        try {
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    logger.warn(sourceFilePath + "["+exception.getLineNumber() + ", " + exception.getColumnNumber() + "] " + exception.getMessage());
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    logger.warn(sourceFilePath + "["+exception.getLineNumber() + ", " + exception.getColumnNumber() + "] " + exception.getMessage());
                }

                @Override
                public void error(SAXParseException exception) throws SAXException {
                    logger.warn(sourceFilePath + "["+exception.getLineNumber() + ", " + exception.getColumnNumber() + "] " + exception.getMessage());
                }
            });

            String physicalSourceFilePath = sourceConfiguration.makePhysicalResourceFilePath(sourceFilePath);
            reader.parse(new InputSource(physicalSourceFilePath));

        } catch (SAXException e) {
            throw new IOException(e);
        } catch (ParserConfigurationException e) {
            throw new IOException(e);
        }

        return null;
    }

}
