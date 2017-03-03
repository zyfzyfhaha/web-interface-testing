package com.ssc.ssgm.fx.refdata.client;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * List row handler return list.
 * @author e576929
 */
public class ListRowHandler extends DefaultHandler {
    /**
     * header list.
     */
    private List<String> headerList;
    /**
     * result list.
     */
    private List<Object[]> result = new ArrayList<Object[]>();
    /**
     * public constructor.
     */
    public ListRowHandler() {
        super();
    }
    /**
     * public constructor with header list.
     * @param headerList header list to receive data
     */
    public ListRowHandler(final List<String> headerList) {
        super();
        this.headerList = headerList;
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName,
            final Attributes attributes) throws SAXException {
        if ("row".equals(qName)) {
            List<Object> row = new ArrayList<Object>();

            for (int i = 0; i < attributes.getLength(); ++i) {
                String attName = attributes.getLocalName(i);
                String attValue = attributes.getValue(i);

                if (headerList != null && headerList.size() > 0
                        && headerList.contains(attName)) {
                    row.add(attValue);
                } else if (headerList == null) {
                    row.add(attValue);
                }
            }

            result.add(row.toArray());
        }
    }
    /**
     * return parse result.
     * @return List<Object[]>
     */
    public List<Object[]> getRowResult() {
        return result;
    }
}
