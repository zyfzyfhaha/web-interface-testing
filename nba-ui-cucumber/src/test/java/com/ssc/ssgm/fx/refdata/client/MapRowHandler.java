package com.ssc.ssgm.fx.refdata.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * map row handler return map.
 * @author e576929
 */
public class MapRowHandler extends DefaultHandler {
    /**
     * result with specified type.
     */
    private List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

    @Override
    public void startElement(final String uri, final String localName, final String qName,
            final Attributes attributes) throws SAXException {
        if ("row".equals(qName)) {
            Map<String, Object> row = new HashMap<String, Object>();

            for (int i = 0; i < attributes.getLength(); ++i) {
                String attName = attributes.getLocalName(i);
                String attValue = attributes.getValue(i);
                row.put(attName, attValue);
            }

            result.add(row);
        }
    }

    /**
     * return parse result.
     * @return List<Map<String, Object>>
     */
    public List<Map<String, Object>> getRowResult() {
        return result;
    }
}
