package com.ssc.ssgm.fx.refdata.model.ui.selenium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ssc.ssgm.fx.refdata.base.BaseClientUtil;
import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;
import com.ssc.ssgm.fx.refdata.model.OrderTolerance;
import com.ssc.ssgm.fx.refdata.model.OrderToleranceDetail;

public class OrderValidationPage extends BaseWebDriver {
	
	@FindBy(xpath="//div[@id='gwt-debug-Order-Size-Tolerance']")
    private WebElement btn_navigate;
	
	@FindBy(xpath="//div[@id='gwt-debug-pricingServiceDropdown']/button")
    private WebElement ddl_PricingService;
	@FindBy(xpath="//div[@id='gwt-debug-ccy1Input']/button")
    private WebElement ddl_ccy1;
    @FindBy(xpath="//div[@id='gwt-debug-ccy2Input']/button")
    private WebElement ddl_ccy2;
    
    //Edit Panel
    @FindBy(id="gwt-debug-submitButton")
    private WebElement btn_Edit;
    @FindBy(xpath="//span[@id='ccy1-Minimum']/input")
    private WebElement input_min1;
    @FindBy(xpath="//span[@id='ccy2-Minimum']/input")
    private WebElement input_min2;
    @FindBy(id="gwt-debug-ccy1TiersTable")
    private WebElement table_tiers1;
    @FindBy(id="gwt-debug-ccy2TiersTable")
    private WebElement table_tiers2;
    
    private List<OrderTolerance> uiQueryResults = new ArrayList<OrderTolerance>();
    private String currentUrl=""; 
    
    public void navigateToOrderPage() {
    	btn_navigate.click();
    	currentUrl = getCurrentUrl();
    }
	public List<WebElement> getMenus() {
        List<WebElement> menuList = driver.findElements(By.xpath("//div[starts-with(@class,'featureBox-content')]"));
        return menuList;
    }
    public List<String> getMenuList(){
        List<String> resultList = new ArrayList<String>();
        for (WebElement element : getMenus()) {
            resultList.add(element.getText());
        }
        return resultList;
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
	
	public void setCCY1(String ccy1Value) {
        selectDropDownValue(ddl_ccy1, ccy1Value);
	}
	   
	public void setCCY2(String ccy2Value) {
        selectDropDownValue(ddl_ccy2, ccy2Value);    
	}
	
	public List<Map<String, Object>> getOnePageResults() {    
        List<WebElement> referenceData = new ArrayList<WebElement>();
        List<Map<String, Object>> pageResults = new ArrayList<Map<String,Object>>();   
        if (waitingElement(By.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
            referenceData = driver.findElements(By.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
            for (int i = 0; i < referenceData.size(); i++) {
                List<WebElement> value = referenceData.get(i).findElements(By.tagName("td"));
                Map<String, Object> oneQuery = new HashMap<String, Object>();
                    oneQuery.put("PRICING_SERVICE", value.get(1).getText());
                    oneQuery.put("CCY1", value.get(2).getText());
                    oneQuery.put("CCY2", value.get(3).getText());
                    oneQuery.put("CCY1_MINIMUM", value.get(4).getText());
                    oneQuery.put("CCY2_MINIMUM", value.get(5).getText());
                    pageResults.add(oneQuery);
            }
            return pageResults;
        }
        return pageResults;
    }
    
    public int getPageNumber() {
        WebElement pageNum = driver.findElement(By.xpath("//div[@id='label3']"));
        return Integer.parseInt(pageNum.getText());        
    }
    
    public int getCurrentPageNumber() {
        WebElement totalElement = driver.findElement(By.xpath("//div[@class='gwt-HTML countText']"));
        String[] pageNumbers = totalElement.getText().split(" ")[0].split("-");      
        return Integer.parseInt(pageNumbers[0]);    
    }
    
    public List<WebElement> getAllRecord() {
        List<WebElement> referenceData = new ArrayList<WebElement>();     
        if (waitingElement(By.xpath("//div[@class='dataGridWidget']/div[3]//tr"))) {
            referenceData = driver.findElements(By.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
        }
        return referenceData;
    }

    public void miniPageCCY1Tiers() {
        WebElement startField =  driver.findElement(By.xpath("//table[@id='gwt-debug-ccy1TiersTable']//span[@id='gwt-debug-rangeStartField0']/input"));
        setInputValue(startField, "12");
        WebElement endField =  driver.findElement(By.xpath("//table[@id='gwt-debug-ccy1TiersTable']//span[@id='gwt-debug-rangeEndField0']/input"));
        setInputValue(endField, "666");
        
        WebElement factorField =  driver.findElement(By.xpath("//table[@id='gwt-debug-ccy1TiersTable']//span[@id='gwt-debug-factorField0']/input"));
        setInputValue(factorField,"1.3");
        
        driver.findElement(By.xpath("//button[@id='gwt-debug-submitButton']")).click();
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public void clickDetailBTNforRow(int rowNum){
    	WebElement row = getAllRecord().get(rowNum-1);
    	WebElement btn = row.findElement(By.tagName("i"));
    	btn.click();
    }
    
    public OrderToleranceDetail getDetailData(){
    	String detailTitle = driver.findElement(By.xpath("//div[@id='gwt-debug-editPanel']/div[2]/div/div")).getText();
    	WebElement detailTable = driver.findElements(By.xpath("//div[@id='gwt-debug-editPanel']//table")).get(0);
    	List<WebElement> rows = detailTable.findElements(By.xpath("tbody/tr"));
    	String ccy1 = rows.get(1).findElement(By.xpath("td/div")).getText();
    	String ccy2 = rows.get(6).findElement(By.xpath("td/div")).getText();
    	List<String[]> tier1 = getTableData(table_tiers1);
    	List<String[]> tier2 = getTableData(table_tiers2);
    	String min1 = input_min1.getAttribute("value");
    	String min2 = input_min2.getAttribute("value");
    	OrderToleranceDetail detail = new OrderToleranceDetail(detailTitle, ccy1, ccy2, min1, min2, tier1, tier2);
    	return detail;
    }
    
    private List<String[]> getTableData(WebElement table){
    	List<String[]> tableData = new ArrayList<String[]>();
    	List<WebElement> rows = table.findElements(By.xpath("tbody/tr"));
    	for (WebElement row : rows) {
    		List<WebElement> cells = row.findElements(By.tagName("td"));
    		String[] rowData = new String[3];
    		for (int i = 0; i < 3; i++) {
				rowData[i] = cells.get(i).findElement(By.xpath("span/input")).getAttribute("value");
			}
    		tableData.add(rowData);
		}
    	return tableData;
    }
    
    public List<OrderTolerance> getUIResult() {
        int onePageNum = getOnePageNumbers();
        int totalNum = getTotalQueryNumbers();
        uiQueryResults.clear();
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
    
    public List<OrderTolerance> getOrderList(List<Map<String, Object>> orderMaps) {       
        List<OrderTolerance> orderList = new ArrayList<OrderTolerance>();        
        for (int i = 0; i < orderMaps.size(); i++) {
            orderList.add(new OrderTolerance(orderMaps.get(i)));
        }
        return orderList;
    }
    
    public boolean checkTierTableCellEnable(WebElement tierTable){
    	Boolean checkStatus = true;
    	List<WebElement> rows = tierTable.findElements(By.xpath("tbody/tr"));
    	for (int i = 0; i < rows.size(); i++) {
    		List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
    		for (int j = 0; j < 3; j++) {
    			boolean expectStatus = true;
    			if (i == 0 || (i == rows.size()-1 && j == 1)) {
    				expectStatus = false;
				}
				if (cells.get(j).findElement(By.xpath("span/input")).isEnabled()!=expectStatus) {
					checkStatus = false;
					logger.error("Check tiers table status find cell of " + (i+1) + "," + (j+1) + " enabled is not " +expectStatus);
				}
			}
		}
    	return checkStatus;
    }
    
    public List<String[]> getEditPanelEnabled() {
		List<String[]> result = new ArrayList<String[]>();
		if (input_min1.isEnabled()) {
			result.add(new String[]{"CCY1 Minimum size","enabled"});
		}else {
			result.add(new String[]{"CCY1 Minimum size","disabled"});
		}
		if (checkTierTableCellEnable(table_tiers1)) {
			result.add(new String[]{"CCY1 tolerance","enabled"});
		}else {
			result.add(new String[]{"CCY1 tolerance","disabled"});
		}
		if (input_min2.isEnabled()) {
			result.add(new String[]{"CCY2 Minimum size","enabled"});
		}else {
			result.add(new String[]{"CCY2 Minimum size","disabled"});
		}
		if (checkTierTableCellEnable(table_tiers2)) {
			result.add(new String[]{"CCY2 tolerance","enabled"});
		}else {
			result.add(new String[]{"CCY2 tolerance","disabled"});
		}
		return result;
	}
    
    public void activeEdit() {
		btn_Edit.click();
	}
    
    public void setUpTier(String index, List<Map<String, String>> values){
		WebElement tierTable;
		if (index.equals("CCY1")) {
			tierTable = table_tiers1;
		}else {
			tierTable = table_tiers2;
		}
		List<WebElement> rows = tierTable.findElements(By.tagName("tr"));
		int rowToAdd = values.size()-rows.size();
		for (int i = 0; i < values.size(); i++) {
			List<WebElement> cells = rows.get(i).findElements(By.tagName("input"));
			if (rowToAdd<0 && i!=0) {
				rows.get(i).findElements(By.tagName("button")).get(1).click();
				rowToAdd++;
				rows = tierTable.findElements(By.tagName("tr"));
				cells = rows.get(i).findElements(By.tagName("input"));
			}
			if (i!=0) {
				String startValue = values.get(i).get("Range Start").replace("", "");
				setInputValue(cells.get(0), startValue);
				if (i!=values.size()-1) {
					String endValue = values.get(i).get("Range End").replace("", "");					
				    setInputValue(cells.get(1), endValue);
				}
				setInputValue(cells.get(2), values.get(i).get("Factor"));
			}
			if (rowToAdd>0) {
				rows.get(i).findElements(By.tagName("button")).get(0).click();
				rowToAdd--;
				rows = tierTable.findElements(By.tagName("tr"));
			}
		}
	}
    
    public void setEditPanelMinimumSize(String min1, String min2) {
    	min1 = min1.replace(",", "");
    	min2 = min2.replace(",", "");
		setInputValue(input_min1, min1);
		setInputValue(input_min2, min2);
	}
    
    public void saveUpdate(boolean needWait) {
		btn_Edit.click();
		if (needWait) {
			waitingElementDisappear(By.id("gwt-debug-submitButton"));
		}
	}
    
    private WebElement getOrderSizeToleranceCurrency(String type) {
        WebElement targetOST;
        if ("CCY1".equals(type)) {
            targetOST = ddl_ccy1;
        } else {
            targetOST = ddl_ccy2;
        }
        return targetOST;
    }

    public List<String> getOrderSizeToleranceCurrencyList(String type) {
        WebElement targetOST = getOrderSizeToleranceCurrency(type);
        return getDropDownValueList(targetOST);
    }

    public void setOrderSizeToleranceCurrencyValue(String type, String selectValue) {
        WebElement targetOST = getOrderSizeToleranceCurrency(type);
        selectDropDownValue(targetOST, selectValue);
    }

    public String getOrderSizeToleranceCurrencySelectedValue(String type) {
        WebElement targetOST = getOrderSizeToleranceCurrency(type);
        String targetValue = targetOST.getText();
        return targetValue;
    }
    public void addToleranceIDFService(OrderTolerance addReocrds) throws Throwable {
        String pricingService = addReocrds.getPricingService();
        String ccy1 = addReocrds.getCCY1();
        String ccy2 = addReocrds.getCCY2();

        String[] inputNames = { "TARGET", "PARAMS" };
        String[] inputs = { "ORDER_SIZE_TIERS_ADD", "" };
        String params = "{\"ccy1tiers\":[{\"factor\":0, \"maxSize\":0, \"minSize\":0, \"tierLevel\":0},{\"factor\":0, \"maxSize\":-1, \"minSize\":0, \"tierLevel\":1}],"; 
        params = params + "\"ccy2tiers\":[{\"factor\":0, \"maxSize\":0, \"minSize\":0, \"tierLevel\":0},{\"factor\":0, \"maxSize\":-1, \"minSize\":0, \"tierLevel\":1}],";
        params = params + "\"currencyPairGroup\":{\"ccy1\":\"" + ccy1 + "\"," + "\"ccy2\":\"" + ccy2 + "\","
                + "\"pricingServiceId\":\"" + pricingService + "\"},\"ccy1MinimalSize\":123,\"ccy2MinimalSize\":456}";
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