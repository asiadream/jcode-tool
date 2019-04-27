package com.asiadream.jcode.tool.generator.checker;

import com.asiadream.jcode.tool.generator.converter.ProjectItemConverter;
import com.asiadream.jcode.tool.generator.converter.ProjectItemType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

public class DomParseChecker extends ProjectItemConverter {
    //
    private static Logger logger = LoggerFactory.getLogger("sqlmap-checker");
    
    private static Logger loggerError = LoggerFactory.getLogger("sqlmap-error-checker");

    public DomParseChecker(ProjectConfiguration sourceConfiguration) {
        super(sourceConfiguration, null, ProjectItemType.MyBatisMapper, null);
    }

    @Override
    public void convert(String sourceFilePath) throws IOException {
        //
        if (sourceFilePath.endsWith(".out.xml")) {
            System.err.println("Skip convert '.out.xml' --> " + sourceFilePath);
            return;
        }
        
        String physicalSourceFilePath = sourceConfiguration.makePhysicalResourceFilePath(sourceFilePath);
        
        File file = new File(physicalSourceFilePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(true);
        
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    logger.warn(sourceFilePath + "["+exception.getLineNumber() + ", " + exception.getColumnNumber() + "] " + exception.getMessage());
                }
                
                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    loggerError.warn(sourceFilePath + "["+exception.getLineNumber() + ", " + exception.getColumnNumber() + "] " + exception.getMessage());
                }
                
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    logger.warn(sourceFilePath + "["+exception.getLineNumber() + ", " + exception.getColumnNumber() + "] " + exception.getMessage());
                }
            });
            
            builder.parse(file);
        } catch (Exception e) {
            //loggerError.warn(sourceFilePath + " : " + e.getMessage());
        }
        
    }

}
