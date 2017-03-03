package com.ssc.ssgm.fx.refdata.model.ui.selenium;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;

public class OrderValidationAddPanel extends BaseWebDriver {
	
	@FindBy(xpath="//div[@id='OrderSizeTiersAdd-PS']/button")
    private WebElement ddl_PricingService;
	@FindBy(xpath="//div[@id='OrderSizeTiersAdd-CCY1-0']/button")
    private WebElement ddl_ccy1;
    @FindBy(xpath="//div[@id='OrderSizeTiersAdd-CCY2-0']/button")
    private WebElement ddl_ccy2;
    @FindBy(xpath="//span[@id='OrderSizeTiersAdd-MIN1-0']/input")
    private WebElement input_minsize1;
    @FindBy(xpath="//span[@id='OrderSizeTiersAdd-MIN2-0']/input")
    private WebElement input_minsize2;
    
	public boolean isPanelDisplayed(){
		By locator = By.xpath("//button[@class='cdtBlueBarSubmitButton']");
		return isElementExist(locator);
	}
	
	public List<WebElement> getPricingServiceList() {
        if (!isElementExist(By.xpath("//div[@class='cdtListView']//a"))) {
        	ddl_PricingService.click();
        }
        List<WebElement> pricingService = driver.findElements(By.xpath("//div[@class='cdtListView']//a"));
        return pricingService;
    }
	
	public void selectPricingService(String selectValue){
		selectDropDownValue(ddl_PricingService, selectValue);
	}
	
	public List<String> getPricingServiceValueList(){
		List<String> resultList = new ArrayList<String>();
		for (WebElement element : getPricingServiceList()) {
			resultList.add(element.getText());
		}
		return resultList;
	}
	
	public void setCCY1(String ccy1Value) {
       selectDropDownValue(ddl_ccy1, ccy1Value);
	}
	   
	public void setCCY2(String ccy2Value) {
        selectDropDownValue(ddl_ccy2, ccy2Value);
	}
	
	public void setMinimum(String min1Value) {
		min1Value = min1Value.replace(",", "");
        setInputValue(input_minsize1, min1Value); 
	}
	
	public void setMinimum2(String min2Value) {
		min2Value = min2Value.replace(",", "");
        setInputValue(input_minsize2, min2Value);   
	}
	
	private WebElement getToleranceTable(String index){
		By locator = null;
		if (index.equals("CCY1")) {
			locator = By.xpath("//div[@id='OrderSizeTiersAdd-CCY1FORM']//table[@id='OrderSizeTiersAdd-TIERS1-0']");
		}else {
			locator = By.xpath("//div[@id='OrderSizeTiersAdd-CCY2FORM']//table[@id='OrderSizeTiersAdd-TIERS2-0']");
		}
		return driver.findElement(locator);
	}
	
	public String[] getDefaultTierValue(String index){
		WebElement tierTable = getToleranceTable(index);
		List<WebElement> rows = tierTable.findElements(By.tagName("tr"));
		List<WebElement> cells = rows.get(0).findElements(By.tagName("input"));
		String[] result = new String[cells.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = cells.get(i).getAttribute("value");
		}
		return result;
	}
	
	public void setUpTier(String index, List<Map<String, String>> values){
		WebElement tierTable = getToleranceTable(index);		
		int rowToAdd = values.size()-2;
		for (int i = 0; i < values.size(); i++) {
			List<WebElement> rows = tierTable.findElements(By.tagName("tr"));
			List<WebElement> cells = rows.get(i).findElements(By.tagName("input"));
			if (i!=0) {
				setInputValue(cells.get(0), values.get(i).get("Range Start"));
				if (i!=values.size()-1) {
				    setInputValue(cells.get(1), values.get(i).get("Range End"));
				}
				setInputValue(cells.get(2), values.get(i).get("Factor"));
			}
			if (rowToAdd>0) {
				rows.get(i).findElements(By.tagName("button")).get(0).click();
				rowToAdd--;
			}
		}
	}
	
	 private WebElement getAddOrderSizeToleranceCurrency(String type) {
	        WebElement targetOST;
	        if ("CCY1".equals(type)) {
	            targetOST = ddl_ccy1;
	        } else {
	            targetOST = ddl_ccy2;
	        }
	        return targetOST;
	    }

	    public List<String> getAddOrderSizeToleranceCurrencyList(String type) {
	        WebElement targetOST = getAddOrderSizeToleranceCurrency(type);
	        return getDropDownValueList(targetOST);
	    }

	    public void setAddOrderSizeToleranceCurrencyValue(String type, String selectValue) {
	        WebElement targetOST = getAddOrderSizeToleranceCurrency(type);
	        selectDropDownValue(targetOST, selectValue);
	    }

	    public String getAddOrderSizeToleranceCurrencySelectedValue(String type) {
	        WebElement targetOST = getAddOrderSizeToleranceCurrency(type);
	        String targetValue = targetOST.getText();
	        return targetValue;
	    }
}
