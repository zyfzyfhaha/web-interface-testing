package com.cgw360.cls.web.api.AddressInformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.Args;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.impl.PublicImpl;
import org.aspectj.weaver.AjAttribute.PrivilegedAttribute;
import org.json.simple.JSONObject;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.aop.ThrowsAdvice;

import com.cgw360.cls.web.base.SpringContextHolder;
import com.cgw360.cls.web.client.ConnectService;
import com.cgw360.cls.web.common.AbstractStepdefs;
import com.cgw360.cls.web.dao.AddressInformation.GetProvinceDao;
import com.cgw360.cls.web.model.AddressInformation.JsonProvince;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/** 
 * Class for getProvince Interface logic
 * @author zyf  
 * @Email  zhuyunfeng@zafh.com.cn
 * @date   2016年3月21日
 */

public class GetProvinceStepdefs extends AbstractStepdefs{
	private static final Logger LOGGER = Logger.getLogger(GetProvinceStepdefs.class);
	private GetProvinceDao getProvinceDao;
	private JsonProvince expectedResults;
	private JsonProvince actualResults;
	private String actualResultString = "";
	
/*
 * Spring injection:  base-package="com.cgw360.cls.web.dao" 
 * */
	@Given("^Test environment is ready for getProvince$")
	public void init()throws Throwable{
		getProvinceDao = (GetProvinceDao) SpringContextHolder.getBean(GetProvinceDao.class); 
	}
	
	
/*	
 * 
 * This step for ensure the test data exist in Database
 * */
	@Given("^The province detail are exist in DB$")
	public void prepareTestDataForAc1()throws Throwable{
		expectedResults = getProvinceDao.getProvinceDetail(); 						// Prepare expectedResults
		LOGGER.info("=====expectedResults is " + expectedResults + " =======" );	// Print log
	}

	
/*	
 * Use Httpclient to call interface
 * */
	@When("^Call the getProvince interface$")
	public void callInterface(final Map<String, String> args)throws Throwable{
		String url = args.get("URL"); 												// Get url from feature file 
		HttpClient httpClient = new ConnectService().connectService();				// Get a new session(system login)
		try {																			
			HttpPost defualtTttpPost = new HttpPost(url);  
		/*
		 * nvps is for post request parameter. This post request doesn't need any parameter
		 * */	        
			List <NameValuePair> nvps = new ArrayList <NameValuePair>(); 			
	        defualtTttpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8")); 
	         
/*
 * New a ResponseHandler to handle response body
 * */
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();  
	        actualResultString = httpClient.execute(defualtTttpPost, responseHandler);  
	        
		} catch (Exception e) {
			e.printStackTrace(); 
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		LOGGER.info("=====actualResultString is " + actualResultString.toString() + " =======" );
	}
	
	
/*
 *  Compare expected results and actual results(response body)
 **/	
	@Then("^The province detail should be mapping with excepted results properly$")
	public void checkResponse()throws Throwable{
		actualResults = getProvinceDao.getJson(actualResultString); 				//Transfer a json string to an object
		Assert.assertEquals(expectedResults, actualResults);
	} 	
	
	
	@When("Call the getProvince interface whitout login")
	public void callInterfaceWithoutLogin(final Map<String, String> args)throws Throwable{
		String url = args.get("URL");
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost defualtTttpPost = new HttpPost(url);  
	        List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
	        defualtTttpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));  
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();  
	        actualResultString = httpClient.execute(defualtTttpPost, responseHandler);  
	        
	       
		} catch (Exception e) {
			e.printStackTrace(); 
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
		
		LOGGER.info("=====actualResultString is " + actualResultString.toString() + " =======" );
	}
	
	@Then("^The province session timeout error message should be mapping with excepted results properly$") 
	public void checkErrorMessage(final Map<String, String> args)throws Throwable{
		String expectedErrorMessageString = args.get("ERROR_MESSAGE");
		Assert.assertEquals(expectedErrorMessageString, actualResultString);
	}
	
	
	
}
