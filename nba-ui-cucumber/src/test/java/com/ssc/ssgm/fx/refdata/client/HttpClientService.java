package com.ssc.ssgm.fx.refdata.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.filters.StringInputStream;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class HttpClientService extends AbstractClientService {
    /**
     * Logger variable.
     */
    private static final Logger LOGGER = Logger.getLogger(HttpClientService.class);

    /**
     * Reactor the constructor.
     * @param properties
     *            the ifg properties
     */
    public HttpClientService() {
        super();
    }
    
    public HttpClientService(final Properties properties) {
        super(properties);
    }

    @Override
    public List<Map<String, Object>> callService(final String reuquestURL) {
        HttpURLConnection con = null;
        InputStream in = null;
        try {
            con = (HttpURLConnection) new URL(getURL()).openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            try {
                out.writeBytes(reuquestURL.toString());
                out.flush();
            } finally {
                out.close();
            }
            if (LOGGER.isDebugEnabled()) {
                String text = IOUtils.toString(con.getInputStream(), "UTF-8");
                LOGGER.info("post response: [" + text + "]");
                in = new StringInputStream(text);
            } else {
                in = con.getInputStream();
            }
            SAXParser parser = new SAXParser();
            MapRowHandler handler = new MapRowHandler();
            parser.setContentHandler(handler);
            parser.parse(new InputSource(in));
            return handler.getRowResult();
        } catch (MalformedURLException e) {
        	LOGGER.error(e,e);
        } catch (IOException e) {
        	LOGGER.error(e,e);
        } catch (SAXException e) {
        	LOGGER.error(e,e);
        } finally {
            if (con != null) {
                con.disconnect();
            }
            if (in != null) {
            	try {
					in.close();
				} catch (IOException e) {
					LOGGER.error(e,e);
				}
            }
        }
        return null;
    }

    @Override
    public List<Object[]> callService(final String reuquestURL,
            final List<String> headerList) {
        HttpURLConnection con = null;
        InputStream in = null;
        try {
            con = (HttpURLConnection) new URL(getURL()).openConnection();
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            try {
                out.writeBytes(reuquestURL.toString());
                out.flush();
            } finally {
                out.close();
            }
            if (LOGGER.isDebugEnabled()) {
                String text = IOUtils.toString(con.getInputStream(), "UTF-8");
                LOGGER.info("post response: [" + text + "]");
                in = new StringInputStream(text);
            } else {
                in = con.getInputStream();
            }
            SAXParser parser = new SAXParser();
            ListRowHandler handler = new ListRowHandler(headerList);
            parser.setContentHandler(handler);
            parser.parse(new InputSource(in));
            return handler.getRowResult();
        } catch (MalformedURLException e) {
        	LOGGER.error(e,e);
        } catch (IOException e) {
        	LOGGER.error(e,e);
        } catch (SAXException e) {
        	LOGGER.error(e,e);
        } finally {
            if (con != null) {
                con.disconnect();
            }
            if (in != null) {
            	try {
					in.close();
				} catch (IOException e) {
					LOGGER.error(e,e);
				}
            }
        }
        return null;
    }
}
