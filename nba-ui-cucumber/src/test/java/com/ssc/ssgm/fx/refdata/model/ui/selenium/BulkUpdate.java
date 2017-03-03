package com.ssc.ssgm.fx.refdata.model.ui.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.management.Query;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

//import com.gargoylesoftware.htmlunit.util.StringUtils;
import org.apache.commons.lang.StringUtils;

import com.ssc.ssgm.fx.refdata.base.BaseClientUtil;
import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;
import com.ssc.ssgm.fx.refdata.base.Constants;
import com.ssc.ssgm.fx.refdata.model.CurrencyPairGroup;
import com.ssc.ssgm.fx.refdata.model.FundCustId;
import com.ssc.ssgm.fx.refdata.model.JsonOrderNormalSize;
import com.ssc.ssgm.fx.refdata.model.OrderTolerance;
import com.ssc.ssgm.fx.refdata.model.QueryFundByIm;
import com.ssc.ssgm.fx.refdata.model.QueryIm;

public class BulkUpdate extends BaseWebDriver {

	@FindBy(xpath = "//div[@id='NormalSizeAdd-PS']/button")
	private WebElement ddl_PricingService;
	@FindBy(xpath = "//span[@id='imSearchPanel-Im-Input']/input")
	private WebElement im_TextBox;
	@FindBy(xpath = "//button[@id='gwt-debug-imSearchButton']")
	private WebElement im_Search;
	@FindBy(xpath = "//span[@id='FundSearchPanel-Fund-Input']/input")
	private WebElement fund_TextBox;
	@FindBy(xpath = "//div[@class='hzn-choices']")
	private WebElement selectedfund;
	@FindBy(xpath = "//button[@id='gwt-debug-fundSearchButton']")
	private WebElement fund_Search;
	@FindBy(xpath = "//div[@id='NormalSizeAdd-CCY1-0']/button")
	private WebElement ddl_ccy1;
	@FindBy(xpath = "//div[@id='NormalSizeAdd-CCY2-0']/button")
	private WebElement ddl_ccy2;
	@FindBy(xpath = "//span[@id='NormalSizeAdd-NORMAL1-0']/input")
	private WebElement normalsize1_Input;
	@FindBy(xpath = "//span[@id='NormalSizeAdd-NORMAL2-0']/input")
	private WebElement normalsize2_Input;

	public List<WebElement> getPricingServiceList() {
		if (!isElementExist(By.xpath("//div[@class='cdtListView']//a"))) {
			ddl_PricingService.click();
		}
		List<WebElement> pricingService = driver.findElements(By
				.xpath("//div[@class='cdtListView']//a"));
		return pricingService;
	}

	public void selectPricingService(String selectValue) {
		if (driver
				.findElement(
						By.xpath("//div[@id='NormalSizeAdd-PS']//button/span"))
				.getText().contains(selectValue)) {
			return;
		}
		for (WebElement element : getPricingServiceList()) {
			if (element.getText().contains(selectValue)) {
				element.click();
				return;
			}
		}
	}

	public void searchIM(String searchValue) {
		WebElement im = driver.findElement(By.id("imSearchPanel-Im-Input"));
		WebElement im_input = im.findElement(By.tagName("input"));

		setInputValue(im_input, searchValue);
		im_Search.click();
		while (!im_Search.isDisplayed()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e, e);
			}
		}
	}
	
	
	public void searchFund(String searchValue) {
		WebElement fund = driver.findElement(By.id("FundSearchPanel-Fund-Input"));
		WebElement fund_input = fund.findElement(By.tagName("input"));

		setInputValue(fund_input, searchValue);
		fund_Search.click();
		while (!fund_Search.isDisplayed()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e, e);
			}
		}
	}
	
	
	public void clickSearchFund(){
		fund_Search.click();
		
	}
	
	
	public boolean isImDropdownListNotExist(){
		
		WebElement imList = driver.findElement(By.id("imSearchPanel-Im-List"));
		if (imList.isDisplayed()) {
			return false;
		}
		return true;
	}
	
	
public boolean isFundDropdownListNotExist(){
		
		WebElement fundList = driver.findElement(By.id("FundSearchPanel-Fund-List"));
		if (fundList.isDisplayed()) {
			return false;
		}
		return true;
	}
	
	public void selectIm(String im){
		
		WebElement imName = driver.findElement(By.xpath("//div[contains(.,'STATE STREET GLOBAL ADVISORS BOSTON')]"));
		imName.click();
	}
	
	public List<QueryIm> getImAndIdFromUI() {
		List<QueryIm> ImAndId = new ArrayList<QueryIm>();
		WebElement imList = driver.findElement(By.id("imSearchPanel-Im-List"));

		// IM ros
		List<WebElement> imRow = imList.findElements(By.tagName("tr"));
		for (WebElement im : imRow) {

			List<WebElement> imDetail = im.findElements(By.tagName("div"));

			QueryIm fc = new QueryIm();

			
			//scroll down
			if (StringUtils.isNotBlank(imDetail.get(0).getText())
					|| StringUtils.isNotBlank(imDetail.get(1).getText())) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView();", imDetail.get(0));
			}

			fc.setIm(imDetail.get(0).getText());
			fc.setImId(imDetail.get(1).getText());
			ImAndId.add(fc);
		}

		return ImAndId;
	}
	

	
	public List<QueryFundByIm> getFundAndIdFromUI() {
		List<QueryFundByIm> FundAndId = new ArrayList<QueryFundByIm>();
		WebElement fundList = driver.findElement(By.id("FundSearchPanel-Fund-List"));

		// IM ros
		List<WebElement> fundRow = fundList.findElements(By.tagName("tr"));
		for (WebElement fund : fundRow) {

			List<WebElement> fundDetail = fund.findElements(By.tagName("div"));

			QueryFundByIm fc = new QueryFundByIm();

			
			//scroll down
			if (StringUtils.isNotBlank(fundDetail.get(0).getText())
					|| StringUtils.isNotBlank(fundDetail.get(1).getText())) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("arguments[0].scrollIntoView();", fundDetail.get(0));
			}

			fc.setFund(fundDetail.get(0).getText());
			fc.setCustId(fundDetail.get(1).getText());
			FundAndId.add(fc);
		}

		
		return FundAndId;
	}
	
	
	public void clickFund(String fundName){
		WebElement fundNameElement = driver.findElement(By.xpath("//div[contains(.,'"+ fundName +"')]"));
		fundNameElement.click();
	}

	
	public boolean selectedFund(String fundName){
		
		WebElement SelectedFundElement = driver.findElement(By.xpath("//div[contains(.,'"+ fundName +"')]"));
		if(!SelectedFundElement.isDisplayed()){
			return false;
		}
		return true;
	}
	
	public int selectedFundCount(){
		
		WebElement fundList = driver.findElement(By.id("FundSearchPanel-Fund-List"));
		List<WebElement> fundRow = fundList.findElements(By.tagName("span"));
		int count = fundRow.size();
		return count;
	}
	
	public void deleteFund(){
		
		WebElement fundList = driver.findElement(By.xpath("//div[@class='search-choice']"));
		List<WebElement> fundRow = fundList.findElements(By.tagName("div"));
		for(WebElement a : fundRow){
			a.click();
		}
		
	}
	
public boolean isFundDeleted(){
		
		WebElement fundList = driver.findElement(By.xpath("//div[@class='search-choice']"));
		List<WebElement> fundRow = fundList.findElements(By.tagName("div"));
		if(fundRow.size() != 0){
			return false;
		}
		return true;
	}
	
}




