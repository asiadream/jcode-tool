package com.asiadream.jcode.tool.generator.main;

import com.asiadream.jcode.tool.generator.reader.Reader;
import com.asiadream.jcode.tool.generator.reader.XmlReader;
import com.asiadream.jcode.tool.generator.source.XmlSource;
import com.asiadream.jcode.tool.share.config.ConfigurationType;
import com.asiadream.jcode.tool.share.config.ProjectConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlRead {
    //
	private static final Logger logger  = LoggerFactory.getLogger(XmlRead.class);
	
    private static final String SOURCE_PROJECT_PATH = "/Users/daniel/Documents/work/source_gen/javatool-parent/source-project";

    public static void main(String[] args) throws Exception {
        //
        ProjectConfiguration configuration = new ProjectConfiguration(ConfigurationType.Source, SOURCE_PROJECT_PATH);

        Reader<XmlSource> reader = new XmlReader(configuration);
        XmlSource source = reader.read("foo/bar/SampleSqlMap.xml");
        viewXmlSource(source);
    }

    private static void viewXmlSource(XmlSource source) {
        Document document = source.getDocument();

        Element mapper = document.getDocumentElement();
        logger.info(mapper.getTagName());
        logger.info(mapper.getAttribute("namespace"));

        logger.info("-----------------------------");
        NodeList nodeList = mapper.getElementsByTagName("select");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                logger.info(element.getAttribute("id"));
            }
        }
    }

}
