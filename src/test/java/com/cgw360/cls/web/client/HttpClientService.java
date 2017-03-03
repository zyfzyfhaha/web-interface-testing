package com.cgw360.cls.web.client;

import gherkin.deps.net.iharder.Base64.InputStream;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



import java.util.Properties;
import java.io.IOException;

import javax.xml.parsers.SAXParser;






import org.apache.http.NameValuePair;  
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;  
import org.apache.http.client.ResponseHandler;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.impl.client.BasicResponseHandler;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.message.BasicNameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.apache.commons.io.IOUtils;














import com.cgw360.cls.web.client.MapRowHandler;

import cucumber.api.java.lu.a;


 
//HttpClient使用get的方式
public class HttpClientService extends AbstractClientService{
	
	
	private static final Logger LOGGER = Logger.getLogger(HttpClientService.class);
	
	public HttpClientService(final Properties properties) {
        super(properties);
    }
	
	@Override
	public String callService(final String reuquestURL)throws Throwable  {
		
		HttpClient httpclient = new DefaultHttpClient(); 
		
	    String url = "http://10.10.13.17:9090/login";
	    String userName = "15068129031";
	    String password = "15068129031";
	   
	    //创建一个默认的HttpClient  
//	    HttpClient httpclient = new DefaultHttpClient();
	    
	        //以post方式请求网页http://www.yshjava.cn  
	        HttpPost defualtTttpPost = new HttpPost(url);  
	        //添加HTTP POST参数  
	        List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
	        nvps.add(new BasicNameValuePair("userName", userName));  
	        nvps.add(new BasicNameValuePair("password", password));  

	        //将POST参数以UTF-8编码并包装成表单实体对象  
	        defualtTttpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));  
	        //打印请求地址  
	        System.out.println("----------------------------------------");  
	        System.out.println("executing request " + defualtTttpPost.getRequestLine().getUri());  
	        System.out.println("----------------------------------------");  
	        //创建响应处理器处理服务器响应内容  
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();  
	        //执行请求并获取结果  
	        String responseBody = httpclient.execute(defualtTttpPost, responseHandler);  
	        System.out.println("----------------------------------------");  
	        System.out.println(responseBody);  
	        System.out.println("----------------------------------------");
			
	        return responseBody;  
	}
}


