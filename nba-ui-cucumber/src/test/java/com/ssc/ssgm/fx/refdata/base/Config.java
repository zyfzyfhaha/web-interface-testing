package com.ssc.ssgm.fx.refdata.base;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * The class to read env config file.
 * @author e532258
 **/
public class Config {
	
	protected static final Logger logger = Logger.getLogger(Config.class);
    /**
     * Config properties.
     */
    private Properties config;

    public Config() {
    	config = new Properties();
    	try {
			config.load(this.getClass().getClassLoader()
			        .getResourceAsStream("config.properties"));
		} catch (IOException e) {
			logger.error("Config file not found!");
			logger.error(e,e);
		}
    }
    
    public Config(String fileName) {
        config = new Properties();
        try {
            config.load(this.getClass().getClassLoader()
                    .getResourceAsStream(fileName));
        } catch (IOException e) {
            logger.error(fileName+" not found!");
            logger.error(e,e);
        }
    }
    
    public Properties getConfig() {
        return config;
    }

    /**
     * get property with specified property name.
     * @param propertyName
     *            the property name
     * @return String
     */
    public String getProperty(final String propertyName) {

        if (config != null) {
            return config.getProperty(propertyName);
        }

        return null;
    }

}
