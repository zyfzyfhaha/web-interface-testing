package com.cgw360.cls.web.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class ConnectService {
	
		public HttpClient connectService()throws Throwable{
			//目标页面  
				String url = "http://10.10.13.17/login";
				String userName = "15000000001";
				String password = "15000000002";
		   
//				创建一个默认的HttpClient  
		   		HttpClient httpclient = new DefaultHttpClient();
		    
		        //以post方式请求网页http
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
		        
			return httpclient;  
		}
	}
	
	

