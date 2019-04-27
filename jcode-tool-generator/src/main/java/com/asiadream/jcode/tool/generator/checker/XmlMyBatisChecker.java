package com.asiadream.jcode.tool.generator.checker;

import com.asiadream.jcode.tool.generator.source.XmlSource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlMyBatisChecker {
    public void checkAndWarn(XmlSource source) {
        Document doc = source.getDocument();
        
        NodeList nodes = doc.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            System.out.println(node.getClass().getName());
        }
    }
    
    public static void main(String[] args) throws Exception {
        XmlSource source = new XmlSource("./src/test/resources/MddpDrSptrYnManageBrkdwn.xml", null);
        XmlMyBatisChecker checker = new XmlMyBatisChecker();
        checker.checkAndWarn(source);
    }
}
