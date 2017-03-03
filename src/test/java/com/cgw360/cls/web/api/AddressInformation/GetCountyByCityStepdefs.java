package com.cgw360.cls.web.api.AddressInformation;

import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Assert;
import org.omg.CosNaming.NamingContextExtPackage.URLStringHelper;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cgw360.cls.web.base.SpringContextHolder;
import com.cgw360.cls.web.client.ConnectService;
import com.cgw360.cls.web.common.AbstractStepdefs;
import com.cgw360.cls.web.dao.AddressInformation.GetCountyByCityDao;
//import com.mchange.rmi.Checkable;



import com.cgw360.cls.web.model.AddressInformation.JsonProvince;

import org.apache.http.client.ResponseHandler;
import org.apache.log4j.Logger;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GetCountyByCityStepdefs extends AbstractStepdefs{
	
	private static Logger LOGGER = Logger.getLogger(GetCountyByCityStepdefs.class);
	private GetCountyByCityDao getCountyByCityDao;
	private JsonProvince expectedResults;
	private JsonProvince actualResults;
	private String areaCodeString = "";
	private String urlString = "";
	private String ResponseBody = "";
	private String expectedErrorMessageString = "";
	
	
	@Given("^Test environment is ready for getCountyByCity interface$")
	public void init()throws Throwable{
		getCountyByCityDao = (GetCountyByCityDao) SpringContextHolder.getBean(GetCountyByCityDao.class);
	} 

	@Given("^The county detail are exist in DB$")
	public void prepareTestDataForAc1(final Map<String, String> args)throws Throwable{
		areaCodeString = args.get("AREA_CODE");
		Assert.assertTrue(getCountyByCityDao.iscountyDetailExistInDb(areaCodeString));
		
		//get the expected results
		if(getCountyByCityDao.iscountyDetailExistInDb(areaCodeString)){
			expectedResults = getCountyByCityDao.getCountyDetailFromDb(areaCodeString);			
		}
	}
	
	@When("^Call the getCountyByCity interface$")
	public void callgetCountyByCityInterface(final Map<String, String> args)throws Throwable{
		urlString = args.get("URL");
		HttpClient httpClient = new ConnectService().connectService();
		try {
			HttpPost httpPost = new HttpPost(urlString);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			ResponseBody = httpClient.execute(httpPost,responseHandler);
			LOGGER.info("=========================ResponseBody= "+ ResponseBody +"============================");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
	
	}
	
	@Then("^The county detail should be mapping with excepted results properly$")
	public void checkResponse()throws Throwable{
		actualResults = getCountyByCityDao.getJson(ResponseBody);
		Assert.assertEquals(expectedResults, actualResults);
	}
	
	@Given("^The county detail are not exist in DB$")
	public void prepareTestDataForAc2(final Map<String, String> args)throws Throwable{
		areaCodeString = args.get("AREA_CODE");
		Assert.assertFalse(getCountyByCityDao.iscountyDetailExistInDb(areaCodeString));
		//get the expected results
		expectedResults = getCountyByCityDao.getCountyDetailFromDb(areaCodeString);	
	}	
	@When("^Call the getCountyByCity Service without login$")
	public void callgetCountyByCityInterfaceWithoutLogin(final Map<String, String> args)throws Throwable{
		urlString = args.get("URL");
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost httpPost = new HttpPost(urlString);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			ResponseBody = httpClient.execute(httpPost,responseHandler);
			LOGGER.info("=========================ResponseBody= "+ ResponseBody +"============================");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			httpClient.getConnectionManager().shutdown();
		}
	}

	
	@Then("^The county error message should be mapping with excepted results properly$")
	public void checkErrorMessage(final Map<String, String> args)throws Throwable{
		expectedErrorMessageString = args.get("ERROR_MESSAGE");
		Assert.assertEquals(expectedErrorMessageString, ResponseBody);
	} 
	
}
