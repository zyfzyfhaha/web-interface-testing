package com.ssc.ssgm.fx.refdata.model.ui.selenium;

import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;
import com.ssc.ssgm.fx.refdata.base.Constants;
import com.ssc.ssgm.fx.refdata.model.FundCustId;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class HedgeTradeConfigPage extends BaseWebDriver {

	protected static final Logger LOGGER = Logger
			.getLogger(HedgeTradeConfigPage.class);

	@FindBy(xpath = "//div[@id='gwt-debug-Hedge-Trade']")
	private WebElement btn_Subscription;

	@FindBy(xpath = "//span[@id='InstSearchPanel-Fund-Input']/input")
	private WebElement inst_Search;

	@FindBy(xpath = "//span[@id='FundSearchPanel-Fund-Input']/input")
	private WebElement fund_Search;

	@FindBy(xpath = "//button[@id='gwt-debug-fundSearchButton']")
	private WebElement btn_searchFund;

	@FindBy(xpath = "//button[@id='gwt-debug-instSearchButton']")
	private WebElement btn_searchInst;

	public void openHedgeTradeSubscriptionPage() {

		// Click Subscirption in REFUI Homepage
		btn_Subscription.click();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Switch to Hedge Trade Configuration Frame
		waitLoadingEnd();
		switchToFrame(Constants.HEDGE_TRADE_FRAME);

	}

	public void input_Fund_Name(String fundName) {
		if (fundName == null) {
			return;
		}
		searchFund(fundName);
		selectFund(fundName);
	}

	public void searchFund(String searchValue) {

		setInputValue(fund_Search, searchValue);
		btn_searchFund.click();
		while (!btn_searchFund.isDisplayed()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e, e);
			}
		}
	}

	public void selectFund(String fundShortName) {
		waitingElement(By.xpath("//table[@id='FundSearchPanel-Fund-List']//tr"));
		List<WebElement> items = driver
				.findElements(By
						.xpath("//table[@id='FundSearchPanel-Fund-List']//tr/td[2]/div"));
		for (WebElement item : items) {
			if (item.getText().equals(fundShortName)) {
				item.click();
				break;
			}
		}
	}

	public void input_Instrument_Name(String instrumentName) {
		if (instrumentName == null) {
			return;
		}
		searchInst(instrumentName);
		selectInst(instrumentName);

	}

	public void searchInst(String searchValue) {

		setInputValue(inst_Search, searchValue);
		btn_searchInst.click();
		while (!btn_searchInst.isDisplayed()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error(e, e);
			}
		}
	}

	public void selectInst(String instName) {
		waitingElement(By.xpath("//table[@id='InstSearchPanel-Fund-List']//tr"));
		List<WebElement> items = driver
				.findElements(By
						.xpath("//table[@id='InstSearchPanel-Fund-List']//tr/td[2]/div"));
		for (WebElement item : items) {
			if (item.getText().equals(instName)) {
				item.click();
				break;
			}
		}
	}

	public void click_Subscribe_button() {
		findElementById("hcSubBtn").click();
		waitLoadingEnd();
	}

	public String getCustId() {
		String custId = findElementById("hc_editor_custId").getText();
		return custId;
	}

	public String getInstrumentName() {
		String instName = findElementById("hc_editor_instId").getText();
		return instName;

	}

	public boolean fund_Booking_Code_Exist() {
		boolean fund_Booking_code_Exist = findElementByWrapperAndTagName(
				"hc_editor_fund_bk_code", "input").isDisplayed();
		return fund_Booking_code_Exist;
	}

	public String getPanelHeaderName() {
		WebElement panel = driver.findElement(By
				.cssSelector(".gwt-InlineLabel"));
		String panelHeader = panel.getText();
		// String panelHeader = findElementByXpath(
		// "//span[@title='HEDGE TRADE SUBSCRIPTION']").getText();
		return panelHeader;
	}

	public boolean add_Button_Exist() {
		boolean add_Button_Exist = findElementByXpath(
				"//button[@class='cdtAddItem']").isDisplayed();
		return add_Button_Exist;
	}

	public void select_Next_Available_Tenor_Type(WebElement hedgeTable,
			String string) {

		List<WebElement> trList = hedgeTable.findElements(By.tagName("tr"));
		WebElement next_Available_Tenor_Drop_Down = trList.get(4).findElement(
				By.tagName("button"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"arguments[0].focus(); arguments[0].blur(); return true",
				next_Available_Tenor_Drop_Down);
		selectDropDownValue(next_Available_Tenor_Drop_Down, string);

	}

	// public void input_Tenor_Value(String tenorValue) {
	// // Get all hedge config tables
	// List<WebElement> hedgeTables =
	// findElementListByXpath("//table[@class='hc-editor-table hc-detail-table']");
	//
	// // Get the last hedge table which is latest added
	// WebElement lastHedgeTable = hedgeTables.get(hedgeTables.size() - 1);
	// // List<WebElement> trList =
	// lastHedgeTable.findElements(By.tagName("tr"));
	// // WebElement tenorInput =
	// trList.get(5).findElement(By.tagName("input"));
	//
	// // setInputValue(tenorInput, tenorValue);
	//
	// }

	public void click_add_Button() {
		findElementByXpath("//button[@class='cdtAddItem']").click();
		waitLoadingEnd();
	}

	public String get_Booking_Code() {
		String bookingCode = findElementByWrapperAndTagName(
				"hc_editor_fund_bk_code", "input").getText();
		return bookingCode;
	}

	public void input_Fund_Booking_Code(String bookingCode) {

		WebElement fundBookingCode = driver.findElement(By
				.cssSelector(".hc-editor-fund-bk-code input"));
		// setInputValue(
		// findElementByWrapperAndTagName("hc_editor_fund_bk_code",
		// "input"), bookingCode);
		setInputValue(fundBookingCode, bookingCode);
	}

	public String getCcy1(WebElement hedge) {

		List<WebElement> trList = hedge.findElements(By.tagName("tr"));
		WebElement ccy1Element = trList.get(1)
				.findElement(By.tagName("button"));
		String ccy1 = ccy1Element.getText();
		return ccy1;
	}

	public String getCcy2(WebElement hedge) {

		List<WebElement> trList = hedge.findElements(By.tagName("tr"));
		WebElement ccy2Element = trList.get(2)
				.findElement(By.tagName("button"));

		String ccy2 = ccy2Element.getText();
		return ccy2;
	}

	public void input_Percentage(WebElement hedge, String percentage) {

		WebElement percentageElement = hedge.findElement(By
				.cssSelector(".hc-editor-field[name=percentage] input"));

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript(

		"arguments[0].focus(); arguments[0].blur(); return true",

		percentageElement);
		
		percentageElement.click();

		if (!StringUtils.isBlank(percentage)) {
			setInputValue(percentageElement, percentage);
		}

	}

	public String getPercentage(WebElement hedge) {

		WebElement percentageElement = hedge.findElement(By
				.cssSelector(".hc-editor-field[name=percentage] input"));
		String percentage = percentageElement.getAttribute("value");
		return percentage;
	}

	public void select_CCY1_CCY2(WebElement hedgeTable, String ccy1, String ccy2) {

		List<WebElement> trList = hedgeTable.findElements(By.tagName("tr"));
		WebElement ccy1Element = trList.get(1)
				.findElement(By.tagName("button"));
		WebElement ccy2Element = trList.get(2)
				.findElement(By.tagName("button"));

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript(

		"arguments[0].focus(); arguments[0].blur(); return true",

		ccy1Element);

		if (!StringUtils.isBlank(ccy1)) {
			selectDropDownValue(ccy1Element, ccy1);
		}
		
		js.executeScript(

		"arguments[0].focus(); arguments[0].blur(); return true",

		ccy2Element);

		if (!StringUtils.isBlank(ccy2)) {
			selectDropDownValue(ccy2Element, ccy2);
		}

	}

	public WebElement inspectElement(WebElement parent, By by) {
		List<WebElement> targets = inspectElements(parent, by);
		if (targets != null && !targets.isEmpty()) {
			return parent.findElement(by);
		} else {
			return null;
		}
	}

	public List<WebElement> inspectElements(By by) {
		return driver.findElements(by);
	}

	public List<WebElement> inspectElements(WebElement parent, By by) {
		return parent.findElements(by);

	}

	public boolean hedge_Table_Exist() {
		// Get all hedge config tables
		List<WebElement> hedgeTables = findElementListByXpath("//table[@class='hc-editor-table hc-detail-table']");
		if (hedgeTables.size() == 0) {
			return false;
		}
		return true;
	}

	public String getTenorValue(WebElement hedgeTable) {

		List<WebElement> rows = hedgeTable.findElements(By
				.xpath("//tr[@class='hc-editor-tenor']"));

		StringBuffer tenors = new StringBuffer();
		for (int i = 0; i < rows.size(); i++) {
			if (i > 0) {
				tenors.append(",");
			}
			WebElement tenorInput = rows.get(i)
					.findElement(By.tagName("input"));
			tenors.append(tenorInput.getAttribute("value"));

		}

		return tenors.toString();
	}

	public void input_Tenor(WebElement hedgeTable, List<String> tenorDates)
			throws InterruptedException {

		int rowToAdd = tenorDates.size() - 1;
		/*
		 * for (int i = 0; i < tenorDates.size(); i++) { List<WebElement> rows =
		 * hedgeTable.findElements(By.xpath("//tr[@class='hc-editor-tenor']"));
		 * WebElement tenorInput = rows.get(i).findElement(By.tagName("input"));
		 * tenorInput.click(); setInputValue(tenorInput, tenorDates.get(i)); if
		 * (rowToAdd > 0) {
		 * rows.get(i).findElements(By.tagName("button")).get(0).click();
		 * rowToAdd--; } }
		 */
		// System.out.println("data-panel-id:" +
		// hedgeTable.getAttribute("data-panel-id"));
		// System.out.println("data-panel-id:" +
		// hedgeTable.getAttribute("data-panel-id"));
		for (int i = 0; i < tenorDates.size(); i++) {
			List<WebElement> plusIcons = hedgeTable.findElements(By
					.cssSelector(".hc-editor-tenor-button-plus"));
			String selector = plusIcons.isEmpty() ? ".hc-editor-tenor-day input"
					: ".hc-editor-tenor-date input";
			List<WebElement> inputs = hedgeTable.findElements(By
					.cssSelector(selector));
			WebElement tenorInput = inputs.get(inputs.size() - 1);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].focus(); arguments[0].blur(); return true",
					tenorInput);

			tenorInput.click();
			tenorInput.clear();
			// setInputValue(tenorInput, tenorDates.get(i));
			tenorInput.sendKeys(tenorDates.get(i));

			if (rowToAdd > 0) {
				plusIcons.get(plusIcons.size() - 1).click();
				rowToAdd--;
			}
		}

	}

	public List<WebElement> getHedgeTables() {
		List<WebElement> hedgeTables = driver.findElements(By
				.cssSelector(".hc-detail-table"));
		return hedgeTables;
	}

	public boolean percentage_Sign_Exist(WebElement hedgeTable) {
		WebElement percentage = hedgeTable.findElement(By
				.cssSelector(".hc-editor-field[name=percentage]"));
		String percentageSing = percentage.getText();
		if (percentageSing.equals("%")) {
			return true;

		}
		return false;
	}

	public void click_save_Button() {

		WebElement saveButton = findElementByXpath("//button[@class='cdtBlueBarSubmitButton']");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"arguments[0].focus(); arguments[0].blur(); return true",
				saveButton);

		saveButton.click();
		waitLoadingEnd();
	}

	public void clearHedgeSubscription() {
		List<WebElement> closeButtonList = driver.findElements(By
				.cssSelector(".hc-editor-close"));
		for (WebElement close : closeButtonList) {
			close.click();
		}
		waitLoadingEnd();
	}

	public String getInvalidErrorMsg() {
		WebElement error = driver
				.findElement(By
						.xpath("//div[@class='gwt-HTML',@style='left: 0px; top: 0px; right: 0px; bottom: 0px; margin-top: 10px; margin-right: 10px; margin-bottom: 10px; margin-left: 0px; position: absolute;']"));
		return error.getText();

	}

	public List<FundCustId> getFundCustIdFromUI() {
		List<FundCustId> fundCustId = new ArrayList<FundCustId>();
		WebElement fundList = driver.findElement(By
				.id("FundSearchPanel-Fund-List"));

		try {
			List<WebElement> fundRows = fundList.findElements(By
					.cssSelector(".contentDividerHeader"));

			for (int i = 0; i < fundRows.size(); i++) {

				FundCustId fc = new FundCustId();
				fc.setFund(fundRows.get(i).getText());
				fc.setCustIds(fundRows.get(i + 1).getText());
				i++;
				fundCustId.add(fc);
			}

		} catch (NoSuchElementException e) {

		}

		return fundCustId;
	}

	public List<String> getInstrumentFromUI() {
		List<String> instrumentList = new ArrayList<String>();
		WebElement instrumentElements = driver.findElement(By
				.id("InstSearchPanel-Fund-List"));
		try {

			List<WebElement> instrumentRows = instrumentElements
					.findElements(By.cssSelector(".contentDividerHeader"));
			for (WebElement i : instrumentRows) {
				LOGGER.info(i.getText());
				instrumentList.add(i.getText());

			}
		} catch (NoSuchElementException e) {

		}

		return instrumentList;
	}

	public String getFund() {
		String fund = findElementById("hc_editor_fund").getText();
		return fund;
	}

	public Boolean saveButtonExist() {
		WebElement saveButton = driver.findElement(By
				.cssSelector(".cdtBlueBarSubmitButton"));
		Boolean exist = saveButton.isDisplayed();
		return exist;
	}

	public void select_IMM_Tenor(WebElement newHedgeTable,
			List<String> tenorList) {
		List<WebElement> imm_dates = newHedgeTable.findElements(By
				.cssSelector(".hc-editor-tenor-imm div span"));
		for (WebElement imm : imm_dates) {
			// LOGGER.info(imm.getText());
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].focus(); arguments[0].blur(); return true",
					imm);
			String imm_date = imm.getText().toUpperCase();
			if (tenorList.contains(imm_date)) {
				imm.click();
			}
		}

		// for (String imm : tenorList) {
		// WebElement imm_check_box = newHedgeTable.findElement(By.id(imm));
		// imm_check_box.clear();
		// imm_check_box.click();
		// }

	}

	public boolean click_copy_icon() {
		WebElement copy = findElementByXpath("//button[@class='cdtCopyButton']");
		if (copy.isDisplayed()) {
			copy.click();
			waitLoadingEnd();
			return true;
		} else {
			return false;
		}

	}

	public void select_instrument_to_be_copied_from(String instrument_Name) {
		WebElement instrument_dropdown = driver.findElement(By
				.id("instrument_dropdown"));
		selectDropDownValue(instrument_dropdown, instrument_Name);
		waitLoadingEnd();
	}

	public void click_Apply_Button() {
		// WebElement apply = driver.findElement(By.id("hcSubBtn"));
		List<WebElement> buttonList = driver.findElements(By
				.cssSelector(".GDQ5Q2QCO"));
		WebElement apply = buttonList.get(0);
		apply.click();
		waitLoadingEnd();
	}

	public List<String> get_Tenor_Warning() {
		List<WebElement> warning = driver.findElements(By
				.cssSelector(".hc-warning-msg"));
		List<String> warnig_Test = new ArrayList<>();
		for (WebElement w : warning) {
			warnig_Test.add(w.getText());
		}
		return warnig_Test;
	}

	public void select_Month_End_Tenor(WebElement newHedgeTable,
			List<String> tenorList) {
		List<WebElement> month_end = newHedgeTable.findElements(By
				.cssSelector(".hc-editor-tenor-me div span"));
		for (WebElement month : month_end) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].focus(); arguments[0].blur(); return true",
					month);
			// LOGGER.info(imm.getText());
			String imm_date = month.getText().toUpperCase();
			if (!tenorList.contains(imm_date)) {
				month.click();
			}
		}

		// for (String imm : tenorList) {
		// WebElement imm_check_box = newHedgeTable.findElement(By.id(imm));
		// imm_check_box.clear();
		// imm_check_box.click();
		// }
	}

	public void select_3_Months_End_Date(WebElement newHedgeTable,
			List<String> tenorList) {
		List<WebElement> month_end = newHedgeTable.findElements(By
				.cssSelector(".hc-editor-tenor-me div span"));
		for (WebElement month : month_end) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].focus(); arguments[0].blur(); return true",
					month);
			// LOGGER.info(imm.getText());
			String select_3_Months_End_Date = month.getText();
			if (!tenorList.contains(select_3_Months_End_Date)) {
				month.click();
			}

		}
	}

	public void select_6_Months_End_Date(WebElement newHedgeTable,
			List<String> tenorList) {
		List<WebElement> month_end = newHedgeTable.findElements(By
				.cssSelector(".hc-editor-tenor-me div span"));
		for (WebElement month : month_end) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].focus(); arguments[0].blur(); return true",
					month);
			// LOGGER.info(imm.getText());
			String select_6_Months_End_Date = month.getText();
			if (!tenorList.contains(select_6_Months_End_Date)) {
				month.click();
			}

		}
	}

	public void select_9_Months_End_Date(WebElement newHedgeTable,
			List<String> tenorList) {
		List<WebElement> month_end = newHedgeTable.findElements(By
				.cssSelector(".hc-editor-tenor-me div span"));
		for (WebElement month : month_end) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].focus(); arguments[0].blur(); return true",
					month);
			// LOGGER.info(imm.getText());
			String select_9_Months_End_Date = month.getText();
			if (!tenorList.contains(select_9_Months_End_Date)) {
				month.click();
			}

		}
	}

	public void select_1_Year_Date(WebElement newHedgeTable,
			List<String> tenorList) {
		List<WebElement> month_end = newHedgeTable.findElements(By
				.cssSelector(".hc-editor-tenor-me div span"));
		for (WebElement month : month_end) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].focus(); arguments[0].blur(); return true",
					month);
			// LOGGER.info(imm.getText());
			String select_1_Year_Date = month.getText();
			if (!tenorList.contains(select_1_Year_Date)) {
				month.click();
			}

		}
	}

}
