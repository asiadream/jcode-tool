package com.asiadream.jcode.tool.generator;

import org.junit.Test;
import org.xml.sax.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxTest {
    
    @Test
    public void testParse() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setValidating(true);
        
        SAXParser parser = factory.newSAXParser();
        
        XMLReader reader = parser.getXMLReader();
        reader.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {
                System.out.println("["+exception.getLineNumber() + ", " + exception.getColumnNumber() + "] " + exception.getMessage());
            }
            
            @Override
            public void fatalError(SAXParseException exception) throws SAXException {
                System.out.println("["+exception.getLineNumber() + ", " + exception.getColumnNumber() + "] " + exception.getMessage());
            }
            
            @Override
            public void error(SAXParseException exception) throws SAXException {
                System.out.println("["+exception.getLineNumber() + ", " + exception.getColumnNumber() + "] " + exception.getMessage());
            }
        });
        reader.parse(new InputSource("./src/test/resources/MddpDrSptrYnManageBrkdwn.xml"));
    }

}
