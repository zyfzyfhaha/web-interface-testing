package com.ssc.ssgm.fx.refdata.model.ui.selenium;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;

public class TraderMappingPage extends BaseWebDriver{
	//GMBH_WSS_ID | GMBH_REUTERS_ID | SSBT_WSS_ID | SSBT_REUTERS_ID | CITY
	@FindBy(xpath="//div[@id='TraderMappingAdd-GMBHFORM']//tr[2]//input")
	private WebElement input_gmbh_wss_id;
	@FindBy(xpath="//div[@id='TraderMappingAdd-GMBHFORM']//tr[1]//input")
	private WebElement input_gmbh_reuters_id;
	@FindBy(xpath="//div[@id='TraderMappingAdd-SSBTFORM']//tr[2]//input")
	private WebElement input_ssbt_wss_id;
	@FindBy(xpath="//div[@id='TraderMappingAdd-SSBTFORM']//tr[1]//input")
	private WebElement input_ssbt_reuters_id;
	@FindBy(xpath="//div[@id='TraderMappingAdd-CITYFORM']//input")
	private WebElement input_city;
	@FindBy(id="gwt-debug-Trader-Mapping")
	private WebElement btn_Navigate;
	
	
	private Map<String, WebElement> getInputBoxMap(){
		Map<String, WebElement> inputBoxMap = new HashMap<String, WebElement>();
		inputBoxMap.put("GMBH_WSS_ID", input_gmbh_wss_id);
		inputBoxMap.put("GMBH_REUTERS_ID", input_gmbh_reuters_id);
		inputBoxMap.put("SSBT_WSS_ID", input_ssbt_wss_id);
		inputBoxMap.put("SSBT_REUTERS_ID", input_ssbt_reuters_id);
		inputBoxMap.put("CITY", input_city);
		return inputBoxMap;
	}
	
	public void setPageValues(Map<String, String> values) {
		Map<String, WebElement> inputMap = getInputBoxMap();
		setPageInputs(values, inputMap);
	}
	
	public void goTraderPage() {
		btn_Navigate.click();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
