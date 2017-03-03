package com.ssc.ssgm.fx.refdata.model.ui.selenium;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;

public class OrderNormalSizeAddPanel extends BaseWebDriver {
	
    @FindBy(xpath="//div[@id='NormalSizeAdd-PS']/button")
    private WebElement ddl_PricingService;
    @FindBy(xpath="//span[@id='FundSearchPanel-Fund-Input']/input")
    private WebElement fund_Search;
    @FindBy(xpath="//div[@id='NormalSizeAdd-CCY1-0']/button")
    private WebElement ddl_ccy1;
    @FindBy(xpath="//div[@id='NormalSizeAdd-CCY2-0']/button")
    private WebElement ddl_ccy2;
    @FindBy(xpath="//span[@id='NormalSizeAdd-NORMAL1-0']/input")
    private WebElement normalsize1_Input;
    @FindBy(xpath="//span[@id='NormalSizeAdd-NORMAL2-0']/input")
    private WebElement normalsize2_Input;

    @FindBy(xpath="//button[@id='gwt-debug-fundSearchButton']")
    private WebElement btn_searchFund;

    @FindBy(id="FundSearchPanel-Fund-List")
    private WebElement table_FundList;
    
    public boolean isPanelDisplayed(){
		By locator = By.xpath("//button[starts-with(@class,'cdtBlueBarSubmitButton')]");
		return isElementExist(locator);
	}
	
	public List<WebElement> getPricingServiceList() {
        if (!isElementExist(By.xpath("//div[@class='cdtListView']//a"))) {
            ddl_PricingService.click();
        }
        List<WebElement> pricingService = driver.findElements(By.xpath("//div[@class='cdtListView']//a"));
        return pricingService;
    }

    public void selectPricingService(String selectValue) {
        if (driver.findElement(By.xpath("//div[@id='NormalSizeAdd-PS']//button/span")).getText().contains(selectValue)) {
            return;
        }
        for (WebElement element : getPricingServiceList()) {
			if (element.getText().contains(selectValue)) {
				element.click();
				return;
			}
		}
    }
	
	public List<String> getPricingServiceValueList(){
		List<String> resultList = new ArrayList<String>();
		for (WebElement element : getPricingServiceList()) {
			resultList.add(element.getText());
		}
		return resultList;
	}
	
    public void setFund(String fundShortName) {
    	if (fundShortName == null) {
			return;
		}
    	searchFund(fundShortName);
    	selectFund(fundShortName);
    }
    
    public void searchFund(String searchValue) {
        setInputValue(fund_Search, searchValue);
        btn_searchFund.click();
        while (!btn_searchFund.isDisplayed()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e,e);
			}			
		}
    }
    
    public void additionForFund(String additionValue) {
    	fund_Search.sendKeys(additionValue);
	}
    
    public void selectFund(String fundShortName) {
    	waitingElement(By.xpath("//table[@id='FundSearchPanel-Fund-List']//tr"));
    	List<WebElement> items = driver.findElements(By.xpath("//table[@id='FundSearchPanel-Fund-List']//tr/td[2]/div"));
    	for (WebElement item : items) {
			if (item.getText().equals(fundShortName)) {
				item.click();
				break;
			}
		}
    }
    
    public String getFundValue(){
    	return fund_Search.getAttribute("value");
    }
    
	public void setCCY1(String ccy1Value) {
        selectDropDownValue(ddl_ccy1, ccy1Value);
	}
 
	public void setCCY2(String ccy2Value) {
		selectDropDownValue(ddl_ccy2, ccy2Value); 
	}
    
	public void setCCY1NormalSize(String normalSize1) {
        setInputValue(normalsize1_Input, normalSize1);   
	}
	
	public void setCCY2NormalSize(String normalSize2) {
        setInputValue(normalsize2_Input, normalSize2); 
	}

    public boolean isFundDisplayed(){
        By locator = By.xpath("//div[@title='Fund']");
        return isElementExist(locator);
    }
    
    public boolean getNormalSizeTable(String index){
        By locator = null;
        if (index.equals("CCY1")) {
            locator = By.xpath("//div[@id='NormalSizeAdd-CCY1FORM']//div[@title='Currency Pair - CCY1']");
        }else {
            locator = By.xpath("//div[@id='NormalSizeAdd-CCY2FORM']//div[@title='Currency Pair - CCY2']");
        }
        return isElementExist(locator);
    }
    
    public boolean isSaveButtonEnable(){
    	if (isElementExist(By.xpath("//button[@class='cdtBlueBarSubmitButton']"))) {
			return true;
		}else if (isElementExist(By.xpath("//button[@class='cdtBlueBarSubmitButton cdtBlueBarSubmitButton-disabled']"))) {
			return false;
		}
    	throw new NoSuchElementException("Can't find Save button");
    }
    
    public List<String[]> getFundSearchList(){
    	List<String[]> result = new ArrayList<String[]>();
    	List<WebElement> funds = table_FundList.findElements(By.tagName("tr"));
    	if (funds.size()==0) {
    		result.add(new String[]{"@Empty"});
			return result;
		}
    	for (WebElement fundRecord : funds) {
			String[] fund = new String[2];
			List<WebElement> fundInfo = fundRecord.findElements(By.xpath("td/div"));
			fund[0] = fundInfo.get(0).getText();
			fund[1] = fundInfo.get(1).getText();
			result.add(fund);
		}
    	return result;
    }
    
    private WebElement getAddOrderNormalSizeCurrency(String type) {
        WebElement targetAddNS;
        if ("CCY1".equals(type)) {
            targetAddNS = ddl_ccy1;
        } else {
            targetAddNS = ddl_ccy2;
        }
        return targetAddNS;
    }

    public List<String> getAddOrderNormalSizeCurrencyList(String type) {
        WebElement targetAddNS = getAddOrderNormalSizeCurrency(type);
        return getDropDownValueList(targetAddNS);
    }

    public void setAddOrderNormalSizeCurrencyValue(String type, String selectValue) {
        WebElement targetAddNS = getAddOrderNormalSizeCurrency(type);
        selectDropDownValue(targetAddNS, selectValue);
    }

    public String getAddOrderNormalSizeCurrencySelectedValue(String type) {
        WebElement targetAddNS = getAddOrderNormalSizeCurrency(type);
        String targetValue = targetAddNS.getText();
        return targetValue;
    }
}
