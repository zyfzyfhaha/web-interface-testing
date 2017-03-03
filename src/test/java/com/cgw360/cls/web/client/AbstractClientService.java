package com.cgw360.cls.web.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;




public abstract class AbstractClientService implements IClientService{
	
	private static final Logger LOGGER = Logger.getLogger(AbstractClientService.class);
	

    /**
     * properties for client.
     */
    protected Properties properties;

    /**
     * Abstract Client Service.
     * @param prop properties.
     */
    public AbstractClientService(final Properties prop) {
        this.properties = prop;
    }
	

    @Override
    public abstract String callService(String reuquestURL) throws Throwable;
	
	/**
     * Get Base URL.
     * @return base URL.
     */
    public String getURL() {
        return properties.getProperty("baseUrl");
    }
    /**
     * Get Base User ID.
     * @return base User ID.
     */
    public String getUserId() {
        return properties.getProperty("userName");
    }
    /**
     * Get Base User pwd.
     * @return base User pwd.
     */
    public String getPassword() {
        return properties.getProperty("password");
    }
    
}
