package com.ssc.ssgm.fx.refdata.model.ui.selenium;

import java.math.BigDecimal;
import java.security.PublicKey;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.ssc.ssgm.fx.refdata.base.BaseClientUtil;
import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;
import com.ssc.ssgm.fx.refdata.base.Constants;
import com.ssc.ssgm.fx.refdata.model.CurrencyPairGroup;
import com.ssc.ssgm.fx.refdata.model.JsonOrderNormalSize;
import com.ssc.ssgm.fx.refdata.model.OrderTolerance;

public class NormalSizeQueryPage extends BaseWebDriver {
	// Pricing Service|IM | FUND | CCY1 | CCY2
	@FindBy(xpath = "//div[@id='gwt-debug-pricingServiceDropdown']/button")
	private WebElement ddl_PricingService;
	@FindBy(xpath = "//span[@id='gwt-debug-imInput']//input")
	private WebElement input_im_id;
	@FindBy(xpath = "//span[@id='gwt-debug-fundInput']//input")
	private WebElement input_fund_id;
	@FindBy(xpath = "//div[@id='gwt-debug-ccy1Input']/button")
	private WebElement ddl_ccy1;
	@FindBy(xpath = "//div[@id='gwt-debug-ccy2Input']/button")
	private WebElement ddl_ccy2;
	@FindBy(id = "gwt-debug-searchButton")
	private WebElement btn_search;
	@FindBy(id = "gwt-debug-Normal-Order-Size")
	private WebElement btn_navigate;
	@FindBy(id = "gwt-debug-filldownButton")
	private WebElement btn_filldown;
	@FindBy(xpath = "//span[contains(.,'Bulk Update')]")
	private WebElement btn_bulkupdate;
	
	private String currentUrl = "";

	private Map<String, WebElement> getInputBoxMap() {
		Map<String, WebElement> inputBoxMap = new HashMap<String, WebElement>();
		inputBoxMap.put("IM", input_im_id);
		inputBoxMap.put("FUND", input_fund_id);
		return inputBoxMap;
	}

	public void setPageValues(Map<String, String> values) {
		switchToFrame(Constants.NORMAL_SIZE_FRAME);
		selectDropDownValue(ddl_PricingService, values.get("Pricing Service"));
		selectDropDownValue(ddl_ccy1, values.get("CCY1"));
		selectDropDownValue(ddl_ccy2, values.get("CCY2"));
		Map<String, WebElement> inputMap = getInputBoxMap();
		setPageInputs(values, inputMap);
	}

	public void navigateToOrderNormalSizePage() {
		waitingElement(By.id("gwt-debug-Normal-Order-Size"));
		btn_navigate.click();
		currentUrl = getCurrentUrl();
		switchToFrame(Constants.NORMAL_SIZE_FRAME);
		waitingElement(By.xpath("//button[@class='cdtAddItem']"));
	}

	public List<String> getUIcolumnFields() {
		List<WebElement> referenceData = new ArrayList<WebElement>();
		List<String> UIFields = new ArrayList<String>();
		if (waitingElement(By
				.xpath("//div[@class='dataGridWidget']/div[1]//tr"))) {
			referenceData = driver.findElements(By
					.xpath("//div[@class='dataGridWidget']/div[1]//tr"));
			for (int i = 0; i < referenceData.size(); i++) {
//				List<WebElement> value = referenceData.get(i).findElements(
//						By.tagName("th"));
				List<WebElement> value = referenceData.get(i).findElements(
						By.tagName("span"));
				UIFields.add(value.get(0).getText());
				UIFields.add(value.get(1).getText());
				UIFields.add(value.get(2).getText());
				UIFields.add(value.get(3).getText());
				UIFields.add(value.get(4).getText());
				UIFields.add(value.get(5).getText());
				UIFields.add(value.get(6).getText());
				UIFields.add(value.get(7).getText());
				UIFields.add(value.get(8).getText());
				UIFields.add(value.get(9).getText());
//				UIFields.add(value.get(10).getText());
				UIFields.add(value.get(10).getAttribute("title"));
			}
			return UIFields;
		}
		return UIFields;
	}

	public void modifyNormalSize(int rowId, Map<String, String> values) {
		List<WebElement> referenceData = new ArrayList<WebElement>();
		if (waitingElement(By
				.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
			referenceData = driver.findElements(By
					.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
			List<WebElement> value = referenceData.get(rowId).findElements(
					By.tagName("td"));
			WebElement ccy1normalSize = value.get(6).findElement(
					By.tagName("input"));
			WebElement ccy2normalSize = value.get(7).findElement(
					By.tagName("input"));
			setInputValue(ccy1normalSize, values.get("CCY1_NORMAL_SIZE"));
			setInputValue(ccy2normalSize, values.get("CCY2_NORMAL_SIZE"));
		}
	}

	public Map<String, Object> getActualResult(String rowID) {
		int rowId = Integer.parseInt(rowID);
		List<WebElement> referenceData = new ArrayList<WebElement>();
		Map<String, Object> oneResult = new HashMap<String, Object>();
		if (waitingElement(By
				.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
			referenceData = driver.findElements(By
					.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
			List<WebElement> value = referenceData.get(rowId).findElements(
					By.tagName("td"));
			oneResult.put("PRICING_SERVICE", value.get(1).getText());
			oneResult.put("IM", value.get(2).getText());
			oneResult.put("FUND", value.get(3).getText());
			oneResult.put("CCY1", value.get(4).getText());
			oneResult.put("CCY2", value.get(5).getText());
			oneResult.put(
					"CCY1_NORMAL_SIZE",
					value.get(6).findElement(By.tagName("input"))
							.getAttribute("value"));
			oneResult.put(
					"CCY2_NORMAL_SIZE",
					value.get(7).findElement(By.tagName("input"))
							.getAttribute("value"));
			oneResult.put("AUTO_CALCULATE", value.get(8).getText());
			
			
		}
		return oneResult;
	}

	public Map<String, Object> getExpectResult(Map<String, String> values) {
		int rowId = Integer.parseInt(values.get("rowID"));
		List<WebElement> referenceData = new ArrayList<WebElement>();
		Map<String, Object> oneResult = new HashMap<String, Object>();
		if (waitingElement(By
				.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
			referenceData = driver.findElements(By
					.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
			List<WebElement> value = referenceData.get(rowId).findElements(
					By.tagName("td"));
			oneResult.put("PRICING_SERVICE", value.get(1).getText());
			oneResult.put("IM", value.get(2).getText());
			oneResult.put("FUND", value.get(3).getText());
			oneResult.put("CCY1", value.get(4).getText());
			oneResult.put("CCY2", value.get(5).getText());
			oneResult.put("CCY1_NORMAL_SIZE", values.get("CCY1_NORMAL_SIZE"));
			oneResult.put("CCY2_NORMAL_SIZE", values.get("CCY2_NORMAL_SIZE"));
			String auto_calculate = "";
			if(values.get("CCY1_NORMAL_SIZE").toString().equals("0")&&values.get("CCY2_NORMAL_SIZE").toString().equals("0")){
				auto_calculate="N";
			}else{
				auto_calculate = "Y";
			}
			oneResult.put("AUTO_CALCULATE", auto_calculate);

		}
		return oneResult;
	}

	public Map<String, Object> getOriginalResult(Map<String, String> values,
			String[] original) {
		int rowId = Integer.parseInt(values.get("rowID"));
		List<WebElement> referenceData = new ArrayList<WebElement>();
		Map<String, Object> oneResult = new HashMap<String, Object>();
		if (waitingElement(By
				.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
			referenceData = driver.findElements(By
					.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
			List<WebElement> value = referenceData.get(rowId).findElements(
					By.tagName("td"));
			oneResult.put("PRICING_SERVICE", value.get(1).getText());
			oneResult.put("IM", value.get(2).getText());
			oneResult.put("FUND", value.get(3).getText());
			oneResult.put("CCY1", value.get(4).getText());
			oneResult.put("CCY2", value.get(5).getText());
			oneResult.put("CCY1_NORMAL_SIZE", original[0]);
			oneResult.put("CCY2_NORMAL_SIZE", original[1]);
		}
		return oneResult;
	}

	public void saveNormalSize() {
		Actions actions = new Actions(driver);
		WebElement saveButton = driver.findElement(By
				.xpath("//button[@class='cdtBlueBarSubmitButton']"));
		actions.moveToElement(saveButton);
		saveButton.click();
	}

	public List<OrderTolerance> getOrderList(List<Map<String, Object>> orderMaps) {
		List<OrderTolerance> orderList = new ArrayList<OrderTolerance>();
		for (int i = 0; i < orderMaps.size(); i++) {
			orderList.add(new OrderTolerance(orderMaps.get(i)));
		}
		return orderList;
	}

	public List<JsonOrderNormalSize> getOrderNormalSizeList() throws ParseException {
		List<WebElement> referenceData = new ArrayList<WebElement>();
		List<JsonOrderNormalSize> pageResults = new ArrayList<JsonOrderNormalSize>();
		if (waitingElement(By
				.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
			referenceData = driver.findElements(By
					.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
			for (int i = 0; i < referenceData.size(); i++) {
				List<WebElement> value = referenceData.get(i).findElements(
						By.tagName("td"));
				JsonOrderNormalSize oneQuery = new JsonOrderNormalSize();
				CurrencyPairGroup cpg = new CurrencyPairGroup();
				cpg.setPricingServiceId(value.get(1).getText());
				oneQuery.setIm(value.get(2).getText());
				oneQuery.setFund(value.get(3).getText());
				cpg.setCcy1(value.get(4).getText());
				cpg.setCcy2(value.get(5).getText());
				oneQuery.setCurrencyPairGroup(cpg);
				oneQuery.setCcy1NormalSize(new BigDecimal(value.get(6).findElement(By.tagName("input")).getAttribute("value").replace(",", "")));
				oneQuery.setCcy2NormalSize(new BigDecimal(value.get(7).findElement(By.tagName("input")).getAttribute("value").replace(",", "")));
				oneQuery.setAutoCalculate(value.get(8).getText());
				oneQuery.setComment(value.get(9).findElement(By.tagName("input"))
						.getAttribute("value"));
				oneQuery.setLastUpdatedById(value.get(10).getText());
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript(
						"arguments[0].focus(); arguments[0].blur(); return true",
						value.get(11));
				String updatedDatetime = value.get(11).getText();
				DateFormat format = new SimpleDateFormat(
						"MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
				Date date = format.parse(updatedDatetime);
				oneQuery.setLastUpdatedDttm(date.getTime());
				pageResults.add(oneQuery);
			}
			return pageResults;
		}
		return pageResults;
	}

	public List<OrderTolerance> getEditNormalSize() {
		return getOrderList(getOnePageResults());
	}

	public List<OrderTolerance> getUIResult() {
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
		} while (onePageNum < totalNum);
		return uiQueryResults;
	}

	public List<JsonOrderNormalSize> getUiNormalSize() throws ParseException {
		int onePageNum = getOnePageNumbers();
		int totalNum = getTotalQueryNumbers();
		List<JsonOrderNormalSize> uiQueryResults = new ArrayList<JsonOrderNormalSize>();
		do {
			onePageNum = getOnePageNumbers();
			// List<JsonOrderNormalSize> pageResult =
			// getOrderNormalSizeList(getOnePageResults());
			List<JsonOrderNormalSize> pageResult = getOrderNormalSizeList();
			for (int i = 0; i < pageResult.size(); i++) {
				uiQueryResults.add(pageResult.get(i));
			}
			if (onePageNum != totalNum) {
				gotoNextQueryPage();
			}
		} while (onePageNum < totalNum);
		return uiQueryResults;
	}

	public List<Map<String, Object>> getOnePageResults() {
		List<WebElement> referenceData = new ArrayList<WebElement>();
		List<Map<String, Object>> pageResults = new ArrayList<Map<String, Object>>();
		if (waitingElement(By
				.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
			referenceData = driver.findElements(By
					.xpath("//div[@class='dataGridWidget']/div[3]//tr"));
			for (int i = 0; i < referenceData.size(); i++) {
				List<WebElement> value = referenceData.get(i).findElements(
						By.tagName("td"));
				Map<String, Object> oneQuery = new HashMap<String, Object>();
				oneQuery.put("PRICING_SERVICE", value.get(1).getText());
				oneQuery.put("IM", value.get(2).getText());
				oneQuery.put("FUND", value.get(3).getText());
				oneQuery.put("CCY1", value.get(4).getText());
				oneQuery.put("CCY2", value.get(5).getText());
				oneQuery.put("CCY1_NORMAL_SIZE",
						value.get(6).findElement(By.tagName("input"))
								.getAttribute("value"));
				oneQuery.put("CCY2_NORMAL_SIZE",
						value.get(7).findElement(By.tagName("input"))
								.getAttribute("value"));
				oneQuery.put("AUTO_CALCULATE", value.get(8).getText());
				oneQuery.put("COMMENT",
						value.get(9).findElement(By.tagName("input"))
								.getAttribute("value"));

				oneQuery.put("LAST_MODIFY_BY", value.get(10).getText());
				// oneQuery.put("LAST_MODIFY_TIME", value.get(11).getText());
				pageResults.add(oneQuery);
			}
			return pageResults;
		}
		return pageResults;
	}

	private WebElement getOrderNormalSizeCurrency(String type) {
		WebElement targetNS;
		if ("CCY1".equals(type)) {
			targetNS = ddl_ccy1;
		} else {
			targetNS = ddl_ccy2;
		}
		return targetNS;
	}

	public List<String> getOrderNormalSizeCurrencyList(String type) {
		WebElement targetNS = getOrderNormalSizeCurrency(type);
		return getDropDownValueList(targetNS);
	}

	public void setOrderNormalSizeCurrencyValue(String type, String selectValue) {
		WebElement targetNS = getOrderNormalSizeCurrency(type);
		selectDropDownValue(targetNS, selectValue);
	}

	public String getOrderNormalSizeCurrencySelectedValue(String type) {
		WebElement targetNS = getOrderNormalSizeCurrency(type);
		String targtValue = targetNS.getText();
		return targtValue;
	}

	public void clickAllCheckBox() {
		Actions actions = new Actions(driver);
		WebElement select_all_checkboxElement = driver.findElement(By.xpath("//tr[@__gwt_header_row='0']//input[@type='checkbox']"));
		actions.moveToElement(select_all_checkboxElement);
		driver.navigate().refresh();
//		new WebDriverWait(driver, 15).until(
//		ExpectedConditions.presenceOfElementLocated(By.xpath("//tr[@__gwt_header_row='0']//input[@type='checkbox']"))
//		);

		select_all_checkboxElement.click();
	}
	
	public void singleCheckBox(String num) {
		Actions actions = new Actions(driver);
		WebElement select_single_checkboxElement = driver.findElement(By.xpath("//tr[@__gwt_row='" + num +"']/input[@type='checkbox']"));
		actions.moveToElement(select_single_checkboxElement);
		select_single_checkboxElement.click();
	}
	
	public void clickFilldownButton() {
		Actions actions = new Actions(driver);
		actions.moveToElement(btn_filldown);
		btn_filldown.click();
	}
	
	
	public void clickSearchButton() {
		Actions actions = new Actions(driver);
		actions.moveToElement(btn_search);
		btn_search.click();
	}
	
	public void clickBulkUpdateButton() {
		Actions actions = new Actions(driver);
		actions.moveToElement(btn_bulkupdate);
		btn_bulkupdate.click();
	}
	
	
	public void addNormalSizeIDFService(OrderTolerance addReocrds)
			throws Throwable {
		String pricingService = addReocrds.getPricingService();
		String ccy1 = addReocrds.getCCY1();
		String ccy2 = addReocrds.getCCY2();
		String custId = addReocrds.getFund();

		// String[] inputNames = { "TARGET", "PARAMS" };
		String[] inputNames = { "USER", "JSON" };
		// String[] inputs = { "NORMAL_ORDER_SIZE_ADD", "" };
		String[] inputs = { "NORMAL_ORDER_SIZE_ADD_TEST", "" };
		String params = "{\"currencyPairGroup\":{\"ccy1\":\"" + ccy1 + "\", "
				+ "\"ccy2\":\"" + ccy2 + "\", " + "\"pricingServiceId\":\""
				+ pricingService + "\"},";
		params = params + "\"custId\":" + custId
				+ ", \"ccy1NormalSize\":1200,\"ccy2NormalSize\":2000}";
		inputs[1] = params;
		if (currentUrl.toLowerCase().contains("cloud")) {
			// BaseClientUtil.getInstance().sendIdfRequest("913130000",
			// inputNames, inputs);
			BaseClientUtil.getInstance().sendIdfRequest("913120014",
					inputNames, inputs);
		} else {
			// navigateToSpecificUrl(BaseClientUtil.getInstance().sendUrlRequest("913130000",
			// inputNames, inputs));
			navigateToSpecificUrl(BaseClientUtil.getInstance().sendUrlRequest(
					"913120014", inputNames, inputs));
			Thread.sleep(1000);

		}
		navigateToSpecificUrl(currentUrl);
		switchToFrame(Constants.NORMAL_SIZE_FRAME);
		waitingElement(By.xpath("//button[@class='cdtAddItem']"));
	}
}
