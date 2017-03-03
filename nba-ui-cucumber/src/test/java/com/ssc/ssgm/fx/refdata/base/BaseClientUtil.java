package com.ssc.ssgm.fx.refdata.base;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ssc.ssgm.fx.refdata.client.IClientService;
import com.ssc.ssgm.fx.refdata.client.CloudClientService;
import com.ssc.ssgm.fx.refdata.client.HttpClientService;

public class BaseClientUtil {
    protected static final Logger logger = Logger.getLogger(BaseClientUtil.class);
    private static BaseClientUtil clientUtil;
    private IClientService client;
    
    public IClientService getClient() {
        return client;
    }

    private BaseClientUtil() throws IOException{
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader()
                    .getResourceAsStream("config.properties"));
            String baseURL = properties.getProperty("baseUrl");
            if (baseURL.toLowerCase().contains("cloud")) {
                client = new CloudClientService();
            }else {
                client = new HttpClientService(); 
            }
    }
    
    public static BaseClientUtil getInstance() throws IOException {
        if (clientUtil == null) {
            clientUtil = new BaseClientUtil();
        }
        return clientUtil;
    }
    
    public List<Map<String, Object>> sendIdfRequest(final String idfNumber,
            final String[] inputNames, String[] inputs) throws Throwable {
        StringBuffer request = new StringBuffer();
        request.append("__request=" + idfNumber);
        if (inputs != null) {
            for (int i = 0; i < inputs.length; i++) {
                String value = inputs[i];
                if (value == null || value.equals("@Ignore")
                        || value.equals("")) {
                    continue;
                } else if (value.equals("@")) {
                    value = "";
                } else if (value.equals("@Null")) {
                    value = "";
                }
                request.append("&" + inputNames[i] + "=" + value);
            }
        }
        logger.info("request: " + request.toString());
        return client.callService(request.toString());
    }
        
    public String getBaseUrl() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getClassLoader()
                .getResourceAsStream("config.properties"));
        String baseURL = properties.getProperty("baseUrl");
        return baseURL;
    }
      
    public String sendUrlRequest(final String idfNumber,
            final String[] inputNames, String[] inputs) throws Throwable {
        StringBuffer request = new StringBuffer();
        String finalUrl = "";
        request.append("__request=" + idfNumber);
        if (inputs != null) {
            for (int i = 0; i < inputs.length; i++) {
                String value = inputs[i];
                if (value == null || value.equals("@Ignore")
                        || value.equals("")) {
                    continue;
                } else if (value.equals("@")) {
                    value = "";
                } else if (value.equals("@Null")) {
                    value = "";
                }
                request.append("&" + inputNames[i] + "=" + value);
            }
        }
        
        logger.info("request: " + request.toString());
        finalUrl = getBaseUrl() + "xml?" + request.toString();
        return finalUrl;
    }

	public List<Object[]> callNbaIdfService(String idfNumber, String inputJson,
			String userName) {
		List<String> headerList = Arrays.asList("JSON", "__errorMsg");
        StringBuffer request = new StringBuffer();
        request.append("__request=" + idfNumber).append("&JSON=").append(inputJson).append("&USER=").append(userName);
        logger.info("request: " + request.toString());
        return client.callService(request.toString(),headerList);
    } 
}
