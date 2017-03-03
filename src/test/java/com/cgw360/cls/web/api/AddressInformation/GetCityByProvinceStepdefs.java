package com.cgw360.cls.web.api.AddressInformation;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.junit.Assert;

import com.cgw360.cls.web.base.SpringContextHolder;
import com.cgw360.cls.web.client.ConnectService;
import com.cgw360.cls.web.common.AbstractStepdefs;
import com.cgw360.cls.web.dao.AddressInformation.GetCityByProvinceDao;
import com.cgw360.cls.web.model.AddressInformation.JsonProvince;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GetCityByProvinceStepdefs extends AbstractStepdefs{
	private static Logger LOGGER = Logger.getLogger(GetCityByProvinceStepdefs.class);
	private GetCityByProvinceDao getCityByProvinceDao;
	private String areaCodeString = "";
	private String urlString = "";
	private String responseBody = "";
	private JsonProvince expectedResults;
	private JsonProvince actualResults;
	
	
	@Given("^Test environment is ready for getCityByProvince interface$")
	public void inti()throws Throwable{
		getCityByProvinceDao = (GetCityByProvinceDao) SpringContextHolder.getBean(GetCityByProvinceDao.class);
	}
	
	@When("^The city detail are exist in DB$")
	public void prepareTestDataForAc1(final Map<String, String> args)throws Throwable{
		areaCodeString =args.get("AREA_CODE");
		Assert.assertTrue(getCityByProvinceDao.isCityDetailInDb(areaCodeString));
		if(getCityByProvinceDao.isCityDetailInDb(areaCodeString)){
			expectedResults = getCityByProvinceDao.getCityDetailFromDb(areaCodeString);
		}
		
		LOGGER.info("===================================expectedResults = " + expectedResults + "===================================" );
	}
	
	@When("Call the getCityByProvince interface")
	public void callInterface(final Map<String, String> args)throws Throwable{
		urlString = args.get("URL");
		HttpClient httpClient = new ConnectService().connectService();
		try {
			HttpPost httpPost = new HttpPost(urlString);
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
	        nvps.add(new BasicNameValuePair("areacode", areaCodeString));  
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8")); 
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();  
	        //执行请求并获取结果  
	        responseBody = httpClient.execute(httpPost, responseHandler);
	        LOGGER.info("===================================responseBody = " + responseBody + "===================================" );
	        
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	@Then("^The city detail should be mapping with excepted results properly$")
	public void checkResponse()throws Throwable{
		
		actualResults = getCityByProvinceDao.getJson(responseBody);
		Assert.assertEquals(expectedResults, actualResults);
	}
	
	
	@When("^Call the getCityByProvince Service without login$") 
	public void callInterfaceWithOutLogin(final Map<String, String> args)throws Throwable{
		urlString = args.get("URL");
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(urlString);
			List <NameValuePair> nvps = new ArrayList <NameValuePair>();  
	        nvps.add(new BasicNameValuePair("areacode", areaCodeString));  
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8")); 
	        ResponseHandler<String> responseHandler = new BasicResponseHandler();  
	        //执行请求并获取结果  
	        responseBody = httpClient.execute(httpPost, responseHandler);
	        LOGGER.info("===================================responseBody = " + responseBody + "===================================" );
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	@Then("The city error message should be mapping with excepted results properly")
	public void checkErrorMessage(final Map<String, String> args)throws Throwable{
		String expectedErrorMessageString = args.get("ERROR_MESSAGE");
		Assert.assertEquals(expectedErrorMessageString, responseBody);
	}
	
	@Given("^The city detail are not exist in DB$")
	public void prepareTestDataForAc3(final Map<String, String> args)throws Throwable{
		areaCodeString =args.get("AREA_CODE");
		Assert.assertFalse(getCityByProvinceDao.isCityDetailInDb(areaCodeString));
		expectedResults = getCityByProvinceDao.getCityDetailFromDb(areaCodeString);
	}
}
