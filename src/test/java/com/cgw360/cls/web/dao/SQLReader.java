package com.cgw360.cls.web.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SQLReader {

    private static final Logger LOG = Logger.getLogger(SQLReader.class);
    
    private static final SQLReader sqlReader = new SQLReader();
    
    private Map<String, List<String>> sqlMap = new HashMap<String, List<String>>(); 
    
    private SQLReader() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(this.getClass().getClassLoader().getResourceAsStream("sqls.xml"));
            NodeList sqlNodes = doc.getChildNodes();
            int sqlCount = sqlNodes.getLength();
            if (sqlCount != 1) {
                LOG.error("We should only have one top level <sqls> node");
                return;
            }
            
            sqlNodes = sqlNodes.item(0).getChildNodes();
            sqlCount = sqlNodes.getLength();
            
            for (int i = 0; i < sqlCount; i ++) {
                Node node = sqlNodes.item(i);
                NodeList childNode = node.getChildNodes();
                
                if (childNode.getLength() > 0) {
                    List<String> sqls = new ArrayList<String>();
                    int childSqlCount = childNode.getLength();
                    for (int j = 0; j < childSqlCount; j ++) {
                        addSqlToList(sqls, childNode.item(j).getTextContent());
                    }
                    
                    sqlMap.put(node.getNodeName(), sqls);
                } 
                
            }
            
        } catch(IOException ex) {
            LOG.error("Can't load the sql file.", ex);
        } catch (ParserConfigurationException e) {
            LOG.error("Error in pasing the sql file.", e);
        } catch (SAXException e) {
            LOG.error("Error in pasing the sql file.", e);
        }
    }
    
    private void addSqlToList(List<String> sqls, String sql) {
        if (!StringUtils.isBlank(sql)) {
            LOG.debug("Adding sql {"+sql+"}. ");
            sqls.add(sql);
        }
    }
    
    public static SQLReader getInstance() {
        return sqlReader;
    }
    
    public List<String> getSql(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return sqlMap.get(key);
    }

}
