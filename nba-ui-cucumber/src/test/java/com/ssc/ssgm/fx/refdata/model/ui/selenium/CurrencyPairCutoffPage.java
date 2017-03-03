package com.ssc.ssgm.fx.refdata.model.ui.selenium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ssc.ssgm.fx.refdata.base.BaseClientUtil;
import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;
import com.ssc.ssgm.fx.refdata.model.OrderTolerance;

public class CurrencyPairCutoffPage extends BaseWebDriver{
    @FindBy(xpath="//div[@id='gwt-debug-ccy1Input']/button")
    private WebElement ddl_query_ccy1;
    @FindBy(xpath="//div[@id='gwt-debug-ccy2Input']/button")
    private WebElement ddl_query_ccy2;
    @FindBy(xpath="//div[@id='gwt-debug-pricingServiceDropdown']/button")
    private WebElement ddl_query_PricingService;    
    @FindBy(xpath="//div[@id='CurrencyPairAdd-CCY1-0']/button")
	private WebElement ddl_add_ccy1;
	@FindBy(xpath="//div[@id='CurrencyPairAdd-CCY2-0']/button")
	private WebElement ddl_add_ccy2;
	@FindBy(xpath="//span[@id='CurrencyPairAdd-DEADLINE-0']/input")
	private WebElement input_Cutoff;
	@FindBy(xpath="//div[@id='CurrencyPairAdd-PS']/button")
	private WebElement ddl_add_PricingService;
	@FindBy(xpath="//button[@class='cdtBlueBarButton']")
    private WebElement btn_Delete;
	@FindBy(xpath="//div[@id='gwt-debug-CCY-Pair-Deadline']")
	private WebElement btn_Navigate;
	
    private String currentUrl=""; 
	
	private Map<String, WebElement> getInputsMap(){
		Map<String, WebElement> inputsMap = new HashMap<String, WebElement>();
		inputsMap.put("CCY1", ddl_add_ccy1);
		inputsMap.put("CCY2", ddl_add_ccy2);
		inputsMap.put("Cutoff", input_Cutoff);
		inputsMap.put("Pricing Service", ddl_add_PricingService);
		return inputsMap;
	}
	
	public void setPageValues(Map<String, String> values) {
		Map<String, WebElement> inputMap = getInputsMap();
		setPageInputs(values, inputMap);
	}
	
    public void openCutoffPage() {
        btn_Navigate.click();
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		currentUrl = getCurrentUrl();
	}

    public void clickDeleteButton() {
        if (!waitingElement(By.xpath("//button[@class='cdtBlueBarButton']"))) {
            throw new NoSuchElementException("Delete button not found");
        }
        btn_Delete.click();
    }
    
    public void deleteConfirm(String yesOrNo) throws InterruptedException {
        if (waitingElement(By.xpath("//div[@id='CommonMessageBox']//div[@class='gwt-HTML']"))) {
            Thread.sleep(500);
            if (yesOrNo.equalsIgnoreCase("ok")) {
                By locator = By.xpath("//div[@id='CommonMessageBox']//button[starts-with(@class,'cdtPrimaryButton ')][1]");
                driver.findElement(locator).click();
                waitLoadingEnd();
            }else {
                By locator = By.xpath("//div[@id='CommonMessageBox']//button[starts-with(@class,'cdtPrimaryButton ')][2]");
                driver.findElement(locator).click();
               
            }
             waitingElementDisappear(By.xpath("//div[@id='CommonMessageBox']//button[starts-with(@class,'cdtPrimaryButton ')][2]"));
        }else {
            throw new NoSuchElementException("Delete confirm page not found");
        }
    }

    public void setQueryCCY1(String ccy1Value) {
        selectDropDownValue(ddl_query_ccy1, ccy1Value);
    }

    public void setQueryCCY2(String ccy2Value) {
        selectDropDownValue(ddl_query_ccy2, ccy2Value);
    }
    
    public void selectQueryPricingService(String selectValue){
        selectDropDownValue(ddl_query_PricingService, selectValue);
    }
    
    public List<OrderTolerance> getOrderList(List<Map<String, Object>> orderMaps) {       
        List<OrderTolerance> orderList = new ArrayList<OrderTolerance>();        
        for (int i = 0; i < orderMaps.size(); i++) {
            orderList.add(new OrderTolerance(orderMaps.get(i)));
        }
        return orderList;
    }
    
    public List<OrderTolerance> getDeadlineUIResult() {
        int onePageNum = getOnePageNumbers();
        int totalNum = getTotalQueryNumbers();
        List<OrderTolerance> uiQueryResults = new ArrayList<OrderTolerance>();
        do {
            onePageNum = getOnePageNumbers();
            List<OrderTolerance> pageResult = getOrderList(getOnePageResults());
            for (int i = 0; i < pageResult.size(); i++) {
                uiQueryResults.add(pageResult.get(i));
            }
            if (onePageNum != totalNum) {
                gotoNextQueryPage();
            }
        }
        while (onePageNum < totalNum);
        return uiQueryResults;
    }
    
    public List<Map<String, Object>> getOnePageResults() {
        List<WebElement> referenceData = new ArrayList<WebElement>();
        List<Map<String, Object>> pageResults = new ArrayList<Map<String, Object>>();
        if (waitingElement(By.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
            referenceData = driver.findElements(By.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
            for (int i = 0; i < referenceData.size(); i++) {
                List<WebElement> value = referenceData.get(i).findElements(By.tagName("td"));
                Map<String, Object> oneQuery = new HashMap<String, Object>();
                oneQuery.put("PRICING_SERVICE", value.get(1).getText());
                oneQuery.put("CCY1", value.get(2).getText());
                oneQuery.put("CCY2", value.get(3).getText());
                oneQuery.put("DEADLINE", value.get(4).findElement(By.tagName("input")).getAttribute("value").replace(",", ""));
                pageResults.add(oneQuery);
            }
            return pageResults;
        }
        return pageResults;
    }
    
    public void selectDeleteRecord(String groupID) {
        List<WebElement> referenceData = new ArrayList<WebElement>();
        if (waitingElement(By.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
            referenceData = driver.findElements(By.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
            for (int i = 0; i < referenceData.size(); i++) {
                List<WebElement> value = referenceData.get(i).findElements(By.tagName("td"));
                String uiKey = value.get(1).getText() + "_" + value.get(2).getText() + "_" + value.get(3).getText();
                if (uiKey.equals(groupID)) {
                    WebElement checkBox = value.get(0).findElement(By.xpath("div/input"));
                    boolean isChecked = checkBox.isSelected();
                    if (!isChecked) {
                        value.get(0).click();
                    } 
                }
            }
        }
    }
    
    public void selectAllDeleteRecord() {
        WebElement  checkBox = driver.findElement(By.xpath("//div[@class='dataGridWidget']//th[1]/input"));
        boolean isChecked = checkBox.isSelected();
        if (!isChecked) {
            checkBox.click();
        }                   
    }
    
    private WebElement getCurrencyDDL(String type, boolean isAddPanel) {
        WebElement targetDDL;
        if ("CCY1".equals(type)) {
            if (isAddPanel == true) {
                targetDDL = ddl_add_ccy1;
            } else {
                targetDDL = ddl_query_ccy1;
            }
        } else {
            if (isAddPanel == true) {
                targetDDL = ddl_add_ccy2;
            } else {
                targetDDL = ddl_query_ccy2;
            }
        }
        return targetDDL;
    }

    public List<String> getCurrencyDDLList(String type, boolean isAddPanel) {
        WebElement targetDDL = getCurrencyDDL(type, isAddPanel);
        return getDropDownValueList(targetDDL);
    }

    public void setCurrencyDDLValue(String type, boolean isAddPanel, String selectValue) {
        WebElement targetDDL = getCurrencyDDL(type, isAddPanel);
        selectDropDownValue(targetDDL, selectValue);
    }

    public String getCurrencyDDLSelectedValue(String type, boolean isAddPanel) {
        WebElement targetDDL = getCurrencyDDL(type, isAddPanel);
        String targetValue = targetDDL.getText();
        return targetValue;
    }
    
    public void modifyDeadline(int rowId, Map<String, String> values) {
        List<WebElement> referenceData = new ArrayList<WebElement>();  
        if (waitingElement(By.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
            referenceData = driver.findElements(By.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
            List<WebElement> value = referenceData.get(rowId).findElements(By.tagName("td"));      
            WebElement deadline = value.get(4).findElement(By.tagName("input"));
            setInputValue(deadline, values.get("DEADLINE"));
        }
    }
    
    public Map<String, Object> getActualResult(String rowID) {
        int rowId = Integer.parseInt(rowID);
        List<WebElement> referenceData = new ArrayList<WebElement>();  
        Map<String, Object> oneResult = new HashMap<String, Object>();    
        if (waitingElement(By.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
            referenceData = driver.findElements(By.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
            List<WebElement> value = referenceData.get(rowId).findElements(By.tagName("td"));                    
            oneResult.put("PRICING_SERVICE", value.get(1).getText());
            oneResult.put("CCY1", value.get(2).getText());
            oneResult.put("CCY2", value.get(3).getText());
            oneResult.put("DEADLINE", value.get(4).findElement(By.tagName("input")).getAttribute("value").replace(",", ""));
        }
        return oneResult;
    }
    
    public Map<String, Object> getExpectResult(Map<String, String> values) {    
        int rowId = Integer.parseInt(values.get("rowID"));
        List<WebElement> referenceData = new ArrayList<WebElement>();  
        Map<String, Object> oneResult = new HashMap<String, Object>();    
        if (waitingElement(By.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
            referenceData = driver.findElements(By.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
            List<WebElement> value = referenceData.get(rowId).findElements(By.tagName("td"));                    
            oneResult.put("PRICING_SERVICE", value.get(1).getText());
            oneResult.put("CCY1", value.get(2).getText());
            oneResult.put("CCY2", value.get(3).getText());
            oneResult.put("DEADLINE", values.get("DEADLINE"));
        }
        return oneResult;
    }
    
    public Map<String, Object> getOriginalResult(Map<String, String> values, String[] original) {    
        int rowId = Integer.parseInt(values.get("rowID"));
        List<WebElement> referenceData = new ArrayList<WebElement>();  
        Map<String, Object> oneResult = new HashMap<String, Object>();    
        if (waitingElement(By.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
            referenceData = driver.findElements(By.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
            List<WebElement> value = referenceData.get(rowId).findElements(By.tagName("td"));                    
            oneResult.put("PRICING_SERVICE", value.get(1).getText());
            oneResult.put("CCY1", value.get(2).getText());
            oneResult.put("CCY2", value.get(3).getText());
            oneResult.put("DEADLINE", original[0]);
        }
        return oneResult;
    }
    
    public void addDeadlineIDFService(OrderTolerance addReocrds) throws Throwable   {        
        String pricingService = addReocrds.getPricingService();
        String ccy1 = addReocrds.getCCY1();
        String ccy2 = addReocrds.getCCY2();
        String cutoff = addReocrds.getDeadline();
        
        String[] inputNames = { "TARGET", "PARAMS" };
        String[] inputs = { "CURRENCY_PAIR_CUTOFF_ADD", "" };
        String params = "{\"currencyPairGroup\":{\"ccy1\":\"" + ccy1 + "\"," + "\"ccy2\":\"" + ccy2
                + "\"," + "\"pricingServiceId\":\"" + pricingService + "\"},\"cutoff\":" + cutoff + "}";
        inputs[1] = params;
        if (currentUrl.toLowerCase().contains("cloud")) {           
            BaseClientUtil.getInstance().sendIdfRequest("913130000", inputNames, inputs);            
        }else {
            navigateToSpecificUrl(BaseClientUtil.getInstance().sendUrlRequest("913130000", inputNames, inputs));
            Thread.sleep(1000);
            navigateToSpecificUrl(currentUrl);           
        }        
    }    
}