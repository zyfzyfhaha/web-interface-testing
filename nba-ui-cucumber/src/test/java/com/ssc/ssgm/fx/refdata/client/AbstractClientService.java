package com.ssc.ssgm.fx.refdata.client;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.ssc.ssgm.fx.refdata.base.Config;

/**
 * client service.
 * @author e531984
 */
public abstract class AbstractClientService implements IClientService {

    /**
     * properties for client.
     */
    protected Properties properties;

    /**
     * Abstract Client Service.
     * @param prop properties.
     */
    public AbstractClientService() {
        this.properties = new Config().getConfig();
    }
    
    public AbstractClientService(final Properties prop) {
        this.properties = prop;
    }

    @Override
    public abstract List<Map<String, Object>> callService(String reuquestURL);

    @Override
    public abstract List<Object[]> callService(String reuquestURL,
            List<String> headerList);

    /**
     * Get Base URL.
     * @return base URL.
     */
    public String getURL() {
        return properties.getProperty("baseUrl")+"xml";
    }
    /**
     * Get Base User ID.
     * @return base User ID.
     */
    public String getUserId() {
        return properties.getProperty("userId");
    }
    /**
     * Get Base User pwd.
     * @return base User pwd.
     */
    public String getPassword() {
        return properties.getProperty("password");
    }
    /**
     * whether user PW Matrix.
     * @return whether user PW Matrix.
     */
    public boolean useMatrix() {
        return Boolean.parseBoolean(properties.getProperty("useMatrix"));
    }
}
