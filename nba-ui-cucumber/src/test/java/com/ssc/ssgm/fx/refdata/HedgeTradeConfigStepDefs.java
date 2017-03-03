package com.ssc.ssgm.fx.refdata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ssc.ssgm.fx.refdata.model.HedgeProperty;
import com.ssc.ssgm.fx.refdata.base.BaseClientUtil;
import com.ssc.ssgm.fx.refdata.base.SpringContextHolder;
import com.ssc.ssgm.fx.refdata.dao.BaseDBUtil;
import com.ssc.ssgm.fx.refdata.dao.EditHedgeTradeConfigDao;
import com.ssc.ssgm.fx.refdata.dao.HedgeTradeDao;
import com.ssc.ssgm.fx.refdata.model.FundCustId;
import com.ssc.ssgm.fx.refdata.model.FundInstHedge;
import com.ssc.ssgm.fx.refdata.model.InstrumentProfile;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.HedgeTradeConfigPage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HedgeTradeConfigStepDefs {
	private HedgeTradeConfigPage hedgeTradeConfigPage = PageFactory
			.initElements(HedgeTradeConfigPage.getWebDriver(),
					HedgeTradeConfigPage.class);
	private HedgeTradeDao htDao = (HedgeTradeDao) SpringContextHolder
			.getBean(HedgeTradeDao.class);
	private EditHedgeTradeConfigDao editHedgeTradeConfigDao = (EditHedgeTradeConfigDao) SpringContextHolder
			.getBean(EditHedgeTradeConfigDao.class);
	

	private List<WebElement> hedgeTables;
	private WebElement newHedgeTable;
	private List<FundInstHedge> hedgeExpected;
	private List<FundInstHedge> hedgeActual;
	protected static final Logger LOGGER = Logger
			.getLogger(HedgeTradeConfigStepDefs.class);
//	private List<HedgeProperty> hedgePropertyBeforeUpdate = new ArrayList<HedgeProperty>();
//	private List<HedgeProperty> hedgePropertyAfterUpdate = new ArrayList<HedgeProperty>();
//	private List<HedgeProperty> hedgePropertyForAdt = new ArrayList<HedgeProperty>();
//	private List<HedgeProperty> hedgePropertyActualAdt = new ArrayList<HedgeProperty>(); 
//	private List<String> xrefFundInstrumentDetailIdList = new ArrayList<String>();
//	private String xrefFundInstrumentDetailId = "";
//	private String userNameString = "e513425";

	@Given("^User navigates to Client Profile Hedge Trade Subscription Page$")
	public void user_navigates_to_Client_Profile_Subscription_Page()
			throws Throwable {
		hedgeTradeConfigPage.openHedgeTradeSubscriptionPage();
	}

	@When("^Input valid FUND and INSTRUMENT_NAME$")
	public void input_valid_CUST_ID_and_INSTRUMENT_NAME(
			List<Map<String, String>> args) throws Throwable {

		String fund = args.get(0).get("FUND");
		String instrumentName = args.get(0).get("INSTRUMENT_NAME");

		hedgeTradeConfigPage.input_Fund_Name(fund);
		LOGGER.info("Type in fund: " + fund);

		hedgeTradeConfigPage.input_Instrument_Name(instrumentName);
		LOGGER.info("Type in instrument name: " + instrumentName);

	}

	@When("^Click the Subscribe button$")
	public void click_the_Subscribe_button() throws Throwable {
		hedgeTradeConfigPage.click_Subscribe_button();
		Thread.sleep(3000);
	}

	@Then("^System is navigated to HEDGE TRADE SUBSCRIPTION page$")
	public void system_is_navigated_to_HEDGE_TRADE_SUBSCRIPTION_page()
			throws Throwable {

		//
		// String pageHeaderName = hedgeTradeConfigPage.getPanelHeaderName();
		// LOGGER.info("Hedge trade subscription page header: " +
		// pageHeaderName);
		// Assert.assertEquals("Page Header: ", "HEDGE TRADE SUBSCRIPTION",
		// pageHeaderName);
		// That Subscription Save Button existed means HEDGE TRADE SUBSCRIPTION
		// Page
		Boolean save_Button_Exist = hedgeTradeConfigPage.saveButtonExist();
		Assert.assertTrue("HEDGE TRADE SUBSCRIPTION Page is loaded: ",
				save_Button_Exist);

	}

	@And("^Fund is FUND input input earlier")
	public void fund_is_FUND(List<Map<String, String>> args) {
		String fundExpected = args.get(0).get("FUND").trim();
		String fundActual = hedgeTradeConfigPage.getFund().replace("Fund:", "")
				.trim();
		LOGGER.info("Fund displayed is: " + fundActual);
		Assert.assertEquals("Fund: ", fundExpected, fundActual);
	}

	@Then("^Customer ID is CUST_ID input earlier$")
	public void customer_ID_is_CUST_ID(List<Map<String, String>> args)
			throws Throwable {
		String custIdExpected = args.get(0).get("CUST_ID").trim();
		String cuttIdActual = hedgeTradeConfigPage.getCustId()
				.replace("Customer ID:", "").trim();
		LOGGER.info("Cust id displayed is: " + cuttIdActual);
		Assert.assertEquals("Cust id: ", custIdExpected, cuttIdActual);

	}

	@Then("^Instrument Name is INSTRUMENT_NAME input earlier$")
	public void instrument_Name_is_INSTRUMENT_NAME(
			List<Map<String, String>> args) throws Throwable {
		String instNameExpected = args.get(0).get("INSTRUMENT_NAME").trim();
		String instNameActual = hedgeTradeConfigPage.getInstrumentName()
				.replace("Instrument Name:", "").trim();
		LOGGER.info("Instrument name displayed is: " + instNameActual);
		Assert.assertEquals("Instrument Name: ", instNameExpected,
				instNameActual);
	}

	@And("Textbox of Fund Booking Code is displayed$")
	public void textbox_of_Fund_Booking_Code_Is_Displayed() throws Throwable {
		boolean result = hedgeTradeConfigPage.fund_Booking_Code_Exist();
		Assert.assertEquals("Textbox of Fund Booking Code is displayed ", true,
				result);
	}

	@And("Add button is displayed$")
	public void add_button_is_displayed() {
		boolean result = hedgeTradeConfigPage.add_Button_Exist();
		Assert.assertEquals("Textbox of Fund Booking Code is displayed ", true,
				result);
	}

	@Given("^User select Interval Days from Next Available Tenors dropdown list$")
	public void user_select_Interval_Days_from_Next_Available_Tenors_dropdown_list()
			throws Throwable {
		// Select Interval Days from drop down list
		hedgeTradeConfigPage.select_Next_Available_Tenor_Type(newHedgeTable,
				"Interval Days");
	}

	@When("^Input Tenor below$")
	public void input_valid_Tenor_Value(List<Map<String, String>> args)
			throws Throwable {
		String tenor = args.get(0).get("TENOR");
		List<String> tenorList = new ArrayList<String>(Arrays.asList(tenor
				.split(",")));
		hedgeTradeConfigPage.input_Tenor(newHedgeTable, tenorList);
		LOGGER.info("Type in tenor: " + tenor);
	}

	@Then("^Tenor is entered successfully$")
	public void tenor_is_entered_successfully(List<Map<String, String>> args)
			throws Throwable {
		String tenorValueExpected = args.get(0).get("TENOR").toString();
		String tenorValueActual = hedgeTradeConfigPage
				.getTenorValue(newHedgeTable);
		LOGGER.info("Tenor value displayed is: " + tenorValueActual);
		Assert.assertEquals("Tenor value: ", tenorValueExpected,
				tenorValueActual);
	}

	@Given("User click Add button for Hedge Config$")
	public void click_Add_button_for_Hedge_Config() {
		// Click add button
		hedgeTradeConfigPage.click_add_Button();

		// Get all Hedge Configuration tables
		hedgeTables = hedgeTradeConfigPage.getHedgeTables();

		// Get the new Hedge Configuration table added just now
		newHedgeTable = hedgeTables.get(hedgeTables.size() - 1);

	}

	@Then("^Fund Booking Code below is displayed$")
	public void fund_Booking_Code_below_is_displayed(
			List<Map<String, String>> args) throws Throwable {
		String fund_Booking_Code_Expected = args.get(0)
				.get("FUND_BOOKING_CODE");
		String fund_Booking_Code_Actual = hedgeTradeConfigPage
				.get_Booking_Code();
		
		Assert.assertEquals("Fund Booking Code: ",fund_Booking_Code_Expected, fund_Booking_Code_Actual);
	}

	@Then("^Hedge Config below is displayed$")
	public void hedge_Config_below_is_displayed(List<Map<String, String>> args)
			throws Throwable {

		// Get expected hedge
		String key4Ccy1 = "CCY1";
		String key4Ccy2 = "CCY2";
		String key4Pct = "PERCENTAGE";
		String key4Tenor = "TENORS";
		String key4Type = "NEXT_AVAILABLE_TENOR";

		// Get actual hedge from UI
		List<Map<String, String>> webConfigs = inspectConfig();
		for (Map<String, String> map : args) {
			String[] keys = new String[] { key4Ccy1, key4Ccy2, key4Pct,
					key4Tenor, key4Type };

			LOGGER.info("expected  : " + map);
			boolean foundOne = false;
			for (Map<String, String> webMap : webConfigs) {
				boolean matched = true;
				for (String k : keys) {
					matched = StringUtils.equals(map.get(k), webMap.get(k));
					if (!matched) {
						matched = false;
						break;
					}
				}

				if (matched) {
					foundOne = true;
					LOGGER.info("candidates: " + webMap
							+ (foundOne ? " matched" : " not matched"));
					break;
				} else {
					LOGGER.info("candidates: " + webMap
							+ (foundOne ? " matched" : " not matched"));
				}
			}
			Assert.assertTrue("Not matched", foundOne);
		}

	}

	private List<Map<String, String>> inspectConfig() {

		List<Map<String, String>> webConfigs = new ArrayList<Map<String, String>>();
		List<WebElement> editors = hedgeTradeConfigPage.inspectElements(By
				.cssSelector(".hc-editor-table"));
		for (WebElement editor : editors) {
			Map<String, String> webMap = new LinkedHashMap<String, String>();
			webMap.put(
					"CCY1",
					getText(getDropdownElement(editor,
							".hc-editor-field[name=ccy1] span")));
			webMap.put(
					"CCY2",
					getText(getDropdownElement(editor,
							".hc-editor-field[name=ccy2] span")));
			webMap.put(
					"PERCENTAGE",
					getInputValue(getInputElement(editor,
							".hc-editor-field[name=percentage] input")));
			List<String> tenors = new ArrayList<String>();

			String tenorDay = getInputValue(getInputElement(editor,
					".hc-editor-tenor-day input"));

			if (tenorDay != null) {
				tenors.add(tenorDay);
			}

			List<WebElement> dates = hedgeTradeConfigPage.inspectElements(
					editor, By.cssSelector(".hc-editor-tenor-date input"));
			if (dates != null) {
				for (WebElement dt : dates) {
					tenors.add(getInputValue(dt));
				}
			}

			List<WebElement> imm_dates_checkBox = hedgeTradeConfigPage
					.inspectElements(editor,
							By.cssSelector(".hc-editor-tenor-imm div input"));

			if (imm_dates_checkBox != null) {
				for (WebElement imm_check : imm_dates_checkBox) {
					String imm_check_value = imm_check.getAttribute("CHECKED");
					String imm_value = imm_check.getAttribute("value");

					if (imm_check_value != null
							&& imm_check_value.toUpperCase().equals("TRUE")) {
						tenors.add(imm_value.toUpperCase().trim());

					}
				}

			}

			List<WebElement> month_end_checkbox = hedgeTradeConfigPage
					.inspectElements(editor,
							By.cssSelector(".hc-editor-tenor-me div input"));

			if (month_end_checkbox != null) {
				for (WebElement me : month_end_checkbox) {
					String me_check_value = me.getAttribute("CHECKED");
					String me_value = me.getAttribute("value");

					if (me_check_value != null
							&& me_check_value.toUpperCase().equals("TRUE")) {
						tenors.add(me_value.toUpperCase().trim());

					}
				}

			}

			webMap.put("TENORS", StringUtils.join(tenors.iterator(), ","));

			webMap.put(
					"NEXT_AVAILABLE_TENOR",
					getText(getDropdownElement(editor,
							".hc-editor-field[name=tenorType] span")));
			webConfigs.add(webMap);
		}

		return webConfigs;
	}

	private String getText(WebElement ele) {
		if (ele != null) {
			return ele.getText();
		}
		return null;
	}

	private String getInputValue(WebElement input) {
		if (input != null) {
			return input.getAttribute("value");
		}
		return null;
	}

	private WebElement getDropdownElement(WebElement parent, String cssPath) {
		return hedgeTradeConfigPage.inspectElement(parent,
				By.cssSelector(cssPath));
	}

	private WebElement getInputElement(WebElement parent, String cssPath) {
		return hedgeTradeConfigPage.inspectElement(parent,
				By.cssSelector(cssPath));
	}

	@Then("^No Hedge Trade Configuration is displayed$")
	public void no_Hedge_Trade_Configuration_is_displayed() throws Throwable {
		boolean hedgeTableExist = hedgeTradeConfigPage.hedge_Table_Exist();
		Assert.assertEquals("Hedge Trade Subscription Exist: ", false,
				hedgeTableExist);
	}

	
//	@Given("^Prepare Hedge_Property Data$")
//	public void prepareHedgePropertyData(List<Map<String, String>> args)throws Throwable{
//		htDao.deleteSql("Story156542");
//		htDao.addSql("Story156542");
//		
//		int count = args.size();
//		
//		int i;
//		for(i = 0; i < count; i++ )
//		{
//			xrefFundInstrumentDetailId = args.get(i).get("XREF_FUND_INSTRUMENT_DETAIL_ID");
//			xrefFundInstrumentDetailIdList.add(xrefFundInstrumentDetailId);
//		}
//		
//		
//		
//		hedgePropertyBeforeUpdate = editHedgeTradeConfigDao.getHedgePropertyFromDb(xrefFundInstrumentDetailIdList);
//		hedgePropertyForAdt = editHedgeTradeConfigDao.getHedgePropertyFromDb(xrefFundInstrumentDetailIdList);
//		
//		
//
//	}
	
	@When("^Input Fund Booking Code for hedge trade$")
	public void input_WSS_Booking_Code_for_hedge_trade(
			List<Map<String, String>> args) throws Throwable {
		String bookingCode = args.get(0).get("FUND_BOOKING_CODE");

		hedgeTradeConfigPage.input_Fund_Booking_Code(bookingCode);
		LOGGER.info("Type in fund booking code: " + bookingCode);

	}

	@When("^Select CCY1 and CCY2$")
	public void select_CCY(List<Map<String, String>> args) throws Throwable {
		String ccy1 = args.get(0).get("CCY1");
		String ccy2 = args.get(0).get("CCY2");
		hedgeTradeConfigPage.select_CCY1_CCY2(newHedgeTable, ccy1, ccy2);
		LOGGER.info("Type in currency pair. CCY1: " + ccy1 + ", CCY2: " + ccy2);

	}

	@Then("^CCY1 and CCY2 is selected successfully$")
	public void ccy_is_selected_successfully(List<Map<String, String>> args)
			throws Throwable {
		String ccy1Expected = args.get(0).get("CCY1");
		String ccy2Expected = args.get(0).get("CCY2");
		String ccy1Actual = hedgeTradeConfigPage.getCcy1(newHedgeTable);
		String ccy2Actual = hedgeTradeConfigPage.getCcy2(newHedgeTable);
		Assert.assertEquals("CCY1 selected: ", ccy1Expected, ccy1Actual);
		Assert.assertEquals("CCY2 selected", ccy2Expected, ccy2Actual);

	}

	@When("^Input Percentage$")
	public void input_Percentage(List<Map<String, String>> args)
			throws Throwable {
		String percentage = args.get(0).get("PERCENTAGE");
		hedgeTradeConfigPage.input_Percentage(newHedgeTable, percentage);
		LOGGER.info("Type in percentage: " + percentage);
	}

	@Then("^Percentage is enetered successfully$")
	public void percentage_Is_entered_successfully(
			List<Map<String, String>> args) {
		String percentageExpected = args.get(0).get("PERCENTAGE");
		String percentageActual = hedgeTradeConfigPage
				.getPercentage(newHedgeTable);
		Assert.assertEquals("Percentage is entered: ", percentageExpected,
				percentageActual);

	}

	@Given("^User select Specific Date from Next Available Tenors dropdown list$")
	public void user_select_Specific_Date_from_Next_Available_Tenors_dropdown_list()
			throws Throwable {
		hedgeTradeConfigPage.select_Next_Available_Tenor_Type(newHedgeTable,
				"Specific Date");
	}

	@And("^Hedge Trade Subscirption below are existed in DB$")
	public void hedge_Trade_Subscription_Below_Are_Existed_In_DB(
			List<Map<String, String>> args) {
		// htDao.deleteHedgeSubscriptionByCustIdInstrument("sql/DeleteHedgeTradeSubscription.sql",
		// "'9112219'", "'%_NBA_152468'");

	}

	@And("^Percentage sign is displayed in UI$")
	public void percentage_Sign_Is_Displayed_In_UI() {
		boolean percentageSingExist = hedgeTradeConfigPage
				.percentage_Sign_Exist(newHedgeTable);
		Assert.assertEquals("Percentage sign is displayed ", true,
				percentageSingExist);
	}

	@Then("^Error message is displayed$")
	public void error_message_is_displayed(List<Map<String, String>> args)
			throws Throwable {
		// TODO
	}

	@Given("^No hedge trade subscription is displayed$")
	public void no_hedge_trade_subscription_is_displayed() throws Throwable {
		// Get all Hedge Configuration tables
		hedgeTables = hedgeTradeConfigPage.getHedgeTables();
		Assert.assertEquals("No hedge trade subscription", 0,
				hedgeTables.size());

	}

	@Given("^User input hedge trade subscription below$")
	public void user_input_hedge_trade_subscription_below(
			List<Map<String, String>> args) throws Throwable {
		// Delete all currency pairs
		hedgeTradeConfigPage.clearHedgeSubscription();
		for (int i = 0; i < args.size(); i++) {
			String ccy1 = args.get(i).get("CCY1");
			String ccy2 = args.get(i).get("CCY2");
			String percentage = args.get(i).get("PERCENTAGE");
			String tenorType = args.get(i).get("NEXT_AVAILABLE_TENOR");
			String tenorValue = args.get(i).get("TENORS");
			// Click add button
			hedgeTradeConfigPage.click_add_Button();

			// Get all Hedge Configuration tables
			hedgeTables = hedgeTradeConfigPage.getHedgeTables();

			// Get the new Hedge Configuration table added just now
			newHedgeTable = hedgeTables.get(hedgeTables.size() - 1);
			// System.out.printf("DATA-PANEL-ID:%s",
			// newHedgeTable.getAttribute("data-panel-id"));

			// input CCY1, CCY2
			hedgeTradeConfigPage.select_CCY1_CCY2(newHedgeTable, ccy1, ccy2);

			// input percentage
			hedgeTradeConfigPage.input_Percentage(newHedgeTable, percentage);

			// input tenor type
			hedgeTradeConfigPage.select_Next_Available_Tenor_Type(
					newHedgeTable, tenorType);
			// input tenor
			List<String> tenorList = new ArrayList<String>(
					Arrays.asList(tenorValue.split(",")));
			if (StringUtils.isNotBlank(tenorValue.trim())) {

				if (tenorType.contains("IMM")) {
					hedgeTradeConfigPage.select_IMM_Tenor(newHedgeTable,
							tenorList);
				} else if (tenorValue.equals("NONE")
						|| tenorValue.contains("END")) {
					hedgeTradeConfigPage.select_Month_End_Tenor(newHedgeTable,
							tenorList);
				} else {
					hedgeTradeConfigPage.input_Tenor(newHedgeTable, tenorList);
				}
			}

		}

	}

	@When("^User click Save button$")
	public void user_click_Save_button() throws Throwable {
		// Click save button
		hedgeTradeConfigPage.click_save_Button();
	}

	@Then("^Hedge Trade Subscription is saved in DB$")
	public void hedge_Trade_Subscription_is_saved_in_DB() throws Throwable {

		// Get expected hedge trade subscription

		// Get actual hedge trade subscription from DB
		String custid = "";
		String instrumentNmae = "";
		String ccy1 = "";
		String ccy2 = "";
		hedgeActual = htDao.getHedgeFromDbByCustId_Inst_CcyPair(custid,
				instrumentNmae, ccy1, ccy2);

		Assert.assertEquals("Hedge Trade subscription", hedgeExpected,
				hedgeActual);
	}

	@Given("^Hedge trade subscription for CUST_ID and INSTRUMENT_NAME below existed in DB$")
	public void hedge_trade_subscription_for_CUST_ID_and_INSTRUMENT_NAME_below_existed_in_DB(
			List<Map<String, String>> args) throws Throwable {

	}

	@Then("^System is navigated to Client Profile Subscription Page$")
	public void system_is_navigated_to_Client_Profile_Subscription_Page()
			throws Throwable {
		Thread.sleep(2000);
	}

	@When("^Delete all hedge subscription$")
	public void delete_All_Hedge_Subscription() {
		// Delete all currency pairs
		hedgeTradeConfigPage.clearHedgeSubscription();
	}

	@And("^User add hedge trade subscription below$")
	public void add_hedge_subscription(List<Map<String, String>> args)
			throws Throwable {

		for (int i = 0; i < args.size(); i++) {
			String ccy1 = args.get(i).get("CCY1");
			String ccy2 = args.get(i).get("CCY2");
			String percentage = args.get(i).get("PERCENTAGE");
			String tenorType = args.get(i).get("NEXT_AVAILABLE_TENOR");
			String tenorValue = args.get(i).get("TENORS");
			// Click add button
			hedgeTradeConfigPage.click_add_Button();

			// Get all Hedge Configuration tables
			hedgeTables = hedgeTradeConfigPage.getHedgeTables();

			// Get the new Hedge Configuration table added just now
			newHedgeTable = hedgeTables.get(hedgeTables.size() - 1);
			// System.out.printf("DATA-PANEL-ID:%s",
			// newHedgeTable.getAttribute("data-panel-id"));

			// input CCY1, CCY2
			hedgeTradeConfigPage.select_CCY1_CCY2(newHedgeTable, ccy1, ccy2);

			// input percentage
			hedgeTradeConfigPage.input_Percentage(newHedgeTable, percentage);

			// input tenor type
			hedgeTradeConfigPage.select_Next_Available_Tenor_Type(
					newHedgeTable, tenorType);

			// input tenor
			if (!StringUtils.isBlank(tenorValue)) {
				List<String> tenorList = new ArrayList<String>(
						Arrays.asList(tenorValue.split(",")));
				if (tenorType.contains("IMM")) {
					hedgeTradeConfigPage.select_IMM_Tenor(newHedgeTable,
							tenorList);
				} else if (tenorValue.equals("NONE")
						|| tenorValue.contains("END")) {
					hedgeTradeConfigPage.select_Month_End_Tenor(newHedgeTable,
							tenorList);
				} else {
					hedgeTradeConfigPage.input_Tenor(newHedgeTable, tenorList);
				}

			}

		}

	}

	@And("^User update hedge trade subscription below$")
	public void update_hedge_subscription(List<Map<String, String>> args)
			throws Throwable {

		for (int i = 0; i < args.size(); i++) {
			String ccy1 = args.get(i).get("CCY1");
			String ccy2 = args.get(i).get("CCY2");
			String percentage = args.get(i).get("PERCENTAGE");
			String tenorType = args.get(i).get("NEXT_AVAILABLE_TENOR");
			String tenorValue = args.get(i).get("TENORS");

			// Get all Hedge Configuration tables
			hedgeTables = hedgeTradeConfigPage.getHedgeTables();

			// currency pair find flag
			boolean flag = false;
			for (int j = 0; j < hedgeTables.size(); j++) {
				// Get the Hedge Configuration one by one
				newHedgeTable = hedgeTables.get(j);
				String ccy1_displayed = getText(newHedgeTable.findElement(By
						.cssSelector(".hc-editor-field[name=ccy1] span")));
				String ccy2_displayed = getText(newHedgeTable.findElement(By
						.cssSelector(".hc-editor-field[name=ccy2] span")));
				if ((ccy1.equals(ccy1_displayed) && ccy2.equals(ccy2_displayed))
						|| (ccy1.equals(ccy2_displayed) && ccy2
								.equals(ccy1_displayed))) {
					flag = true;
					// input percentage
					hedgeTradeConfigPage.input_Percentage(newHedgeTable,
							percentage);
					// input tenor type
					hedgeTradeConfigPage.select_Next_Available_Tenor_Type(
							newHedgeTable, tenorType);
					// input tenor
					List<String> tenorList = new ArrayList<String>(
							Arrays.asList(tenorValue.split(",")));
					if (tenorType.contains("IMM")) {
						hedgeTradeConfigPage.select_IMM_Tenor(newHedgeTable,
								tenorList);
					} else if (tenorValue.equals("NONE")
							|| tenorValue.contains("END")) {
						hedgeTradeConfigPage.select_Month_End_Tenor(
								newHedgeTable, tenorList);
						
					} else if (tenorType.contains("3 Months End Date")){
						hedgeTradeConfigPage.select_3_Months_End_Date(newHedgeTable, tenorList);
						
					}else if (tenorType.contains("6 Months End Date")){
						hedgeTradeConfigPage.select_6_Months_End_Date(newHedgeTable, tenorList);
						
					}else if (tenorType.contains("9 Months End Date")){
						hedgeTradeConfigPage.select_9_Months_End_Date(newHedgeTable, tenorList);
						
					}else if (tenorType.contains("1 Year Date")){
						hedgeTradeConfigPage.select_1_Year_Date(newHedgeTable, tenorList);
						
					}else {
						hedgeTradeConfigPage.input_Tenor(newHedgeTable,
								tenorList);
					}

				}

			}

			Assert.assertTrue("The currency pair to be udpated is not found",
					flag);

		}

	}

	@And("^User delete hedge trade subscription below$")
	public void delete_hedge_subscription(List<Map<String, String>> args)
			throws Throwable {

		for (int i = 0; i < args.size(); i++) {
			String ccy1 = args.get(i).get("CCY1");
			String ccy2 = args.get(i).get("CCY2");

			// Get all Hedge Configuration tables
			hedgeTables = hedgeTradeConfigPage.getHedgeTables();

			// currency pair find flag
			boolean flag = false;
			for (int j = 0; j < hedgeTables.size(); j++) {
				// Get the Hedge Configuration one by one
				newHedgeTable = hedgeTables.get(j);
				String ccy1_displayed = getText(newHedgeTable.findElement(By
						.cssSelector(".hc-editor-field[name=ccy1] span")));
				String ccy2_displayed = getText(newHedgeTable.findElement(By
						.cssSelector(".hc-editor-field[name=ccy2] span")));
				if ((ccy1.equals(ccy1_displayed) && ccy2.equals(ccy2_displayed))
						|| (ccy1.equals(ccy2_displayed) && ccy2
								.equals(ccy1_displayed))) {
					flag = true;

					// click delete button
					newHedgeTable.findElement(
							By.cssSelector(".hc-editor-close")).click();
					;
				}

			}

			Assert.assertTrue("The currency pair to be udpated is not found",
					flag);

		}

	}

	@Then("^Error message is pop up$")
	public void error_msg_is_pop_up(List<Map<String, String>> args) {
		String errorExpected = args.get(0).get("ERROR");
		String errorActual = hedgeTradeConfigPage.getAddResult();
		// Assert.assertEquals("error message", errorExpected, errorActual);
		Assert.assertEquals(errorExpected, errorActual);
	}

	@When("^Input FUND value and click search icon$")
	public void input_FUND_value(List<Map<String, String>> args)
			throws Throwable {

		String fund = args.get(0).get("FUND");

		hedgeTradeConfigPage.searchFund(fund);
		LOGGER.info("Type in fund: " + fund);

	}

	@Then("^All active Funds start with FUND input are displayed in list$")
	public void search_results_all_start_with_FUND_input(
			List<Map<String, String>> args) throws Throwable {
		// Get fund list from back-end service
		String fund = args.get(0).get("FUND");
		List<FundCustId> fundListExpected = getFundCustIdFromNBAService(fund);

		// Get fund list from UI
		List<FundCustId> fundListActual = hedgeTradeConfigPage
				.getFundCustIdFromUI();
		// List<WebElement> items = hedgeTradeConfigPage
		// .findElementListByXpath("//table[@id='FundSearchPanel-Fund-List']//tr/td[2]/div");
		// boolean startWithFund = true;
		//
		// for (WebElement item : items) {
		// if (org.apache.commons.lang.StringUtils.isNotEmpty(item.getText())
		// && !item.getText().startsWith(fund)) {
		// startWithFund = false;
		// break;
		// }
		// }
		//
		// LOGGER.info("start with fund :" + startWithFund);
		// Assert.assertTrue(startWithFund);

		LOGGER.info("Fund CustId expected:" + fundListExpected.toString());
		LOGGER.info("Fund CustId actual:" + fundListActual.toString());

		Assert.assertEquals("Fund CustId List: ", fundListExpected,
				fundListActual);
	}

	private List<FundCustId> getFundCustIdFromNBAService(String fund)
			throws Throwable {

		List<FundCustId> fundList = new ArrayList<FundCustId>();
		StringBuffer inputJson = new StringBuffer();
		inputJson.append("{\"fund\":\"").append(fund).append("\"}");
		String userName = "HedgeTradeSubscriptionTest";
		List<Object[]> result = BaseClientUtil.getInstance().callNbaIdfService(
				"913120008", inputJson.toString(), userName);
		String outputJson = (result.get(0))[0].toString();
		String err = (result.get(0))[1].toString();

		if (err.contains("No record is found")) {
			return fundList;
		}
		if (StringUtils.isBlank(err) && StringUtils.isNotBlank(outputJson)) {

			fundList = getFundsFromJson(outputJson);
		}

		return fundList;
	}

	private List<FundCustId> getFundsFromJson(String Json) {
		Type collectionType = new TypeToken<List<FundCustId>>() {
		}.getType();
		Gson gson = new Gson();
		List<FundCustId> funds = gson.fromJson(Json, collectionType);
		return funds;
	}

	@Then("^All active Funds start with FUND input are displayed in list XXXXXXXXXXXXXX$")
	public void search_result_equals_to_input_fund(
			List<Map<String, String>> args) throws Throwable {

		String fund = args.get(0).get("FUND");
		List<WebElement> items = hedgeTradeConfigPage
				.findElementListByXpath("//table[@id='FundSearchPanel-Fund-List']//tr/td[2]/div");
		boolean equalsToFund = false;
		for (WebElement item : items) {
			if (item.getText().equals(fund)) {
				equalsToFund = true;
				break;
			}
		}
		LOGGER.info("equals to fund: " + equalsToFund);
		Assert.assertTrue(equalsToFund);

	}

	@Then("^No Fund result can be found$")
	public void no_FUND_result_can_be_found() throws Throwable {

		List<WebElement> items = hedgeTradeConfigPage
				.findElementListByXpath("//table[@id='FundSearchPanel-Fund-List']//tr/td[2]/div");
		boolean resultFound = true;
		if (items.size() == 0) {
			resultFound = false;
		}
		LOGGER.info("Fund result can be found" + resultFound);
		Assert.assertFalse(resultFound);
	}

	@When("^Input INSTRUMENT value and click search icon$")
	public void input_INSTRUMENT_value(List<Map<String, String>> args)
			throws Throwable {

		String instrument = args.get(0).get("INSTRUMENT");

		hedgeTradeConfigPage.searchInst(instrument);
		LOGGER.info("Type in instrument: " + instrument);

	}

	@Then("^All Instruments start with INSTRUMENT input are displayed in list$")
	public void search_results_all_start_with_INSTRUMENT_input(
			List<Map<String, String>> args) throws Throwable {
		// Get expected instrument list from back-end service
		String instrument = args.get(0).get("INSTRUMENT");
		List<InstrumentProfile> instruments = getInstrumentFromService(instrument);
		List<String> instrumentExpected = new ArrayList<String>();
		for (InstrumentProfile i : instruments) {
			instrumentExpected.add(i.getInstrumentName());
		}

		// Get actual instrument list from UI
		List<String> instrumentActual = hedgeTradeConfigPage
				.getInstrumentFromUI();

		LOGGER.info("Instrument expected:" + instrumentExpected.toString());
		LOGGER.info("Instrument actual:" + instrumentActual.toString());

		Assert.assertEquals("Instrument List: ", instrumentExpected,
				instrumentActual);
		// List<WebElement> items = hedgeTradeConfigPage
		// .findElementListByXpath("//table[@id='InstSearchPanel-Fund-List']//tr/td[2]/div");
		// boolean startWithFund = true;
		//
		// for (WebElement item : items) {
		// if (org.apache.commons.lang.StringUtils.isNotEmpty(item.getText())
		// && !item.getText().startsWith(instrument)) {
		// startWithFund = false;
		// break;
		// }
		// }
		//
		// LOGGER.info("start with fund :" + startWithFund);
		// Assert.assertTrue(startWithFund);

	}

	private List<InstrumentProfile> getInstrumentFromService(String instrument)
			throws Throwable {
		List<InstrumentProfile> instruments = new ArrayList<InstrumentProfile>();
		StringBuffer inputJson = new StringBuffer();
		inputJson.append("{\"queryId\":").append("2")
				.append(",\"instrumentName\":\"").append(instrument)
				.append("\"}");
		String userName = "HedgeTradeSubscriptionTest";
		List<Object[]> result = BaseClientUtil.getInstance().callNbaIdfService(
				"913129001", inputJson.toString(), userName);
		String outputJson = (result.get(0))[0].toString();
		String err = (result.get(0))[1].toString();
		if (err.contains("No record is found")) {
			return instruments;

		}
		if (StringUtils.isBlank(err) && StringUtils.isNotBlank(outputJson)) {

			instruments = getInstrumentFromJson(outputJson);
		}

		return instruments;
	}

	private List<InstrumentProfile> getInstrumentFromJson(String json) {

		Type collectionType = new TypeToken<List<InstrumentProfile>>() {
		}.getType();
		Gson gson = new Gson();
		List<InstrumentProfile> instrument = gson
				.fromJson(json, collectionType);
		return instrument;
	}

	@Then("^Search result equals to input INSTRUMENT$")
	public void search_result_equals_to_input_INSTRUMENT(
			List<Map<String, String>> args) throws Throwable {

		String instrument = args.get(0).get("INSTRUMENT");
		List<WebElement> items = hedgeTradeConfigPage
				.findElementListByXpath("//table[@id='InstSearchPanel-Fund-List']//tr/td[2]/div");
		boolean equalsToInst = false;
		for (WebElement item : items) {
			if (item.getText().equals(instrument)) {
				equalsToInst = true;
				break;
			}
		}
		LOGGER.info("equals to instrument: " + equalsToInst);
		Assert.assertTrue(equalsToInst);

	}

	@Then("^No INSTRUMENT result can be found$")
	public void no_INSTRUMENT_result_can_be_found() throws Throwable {

		List<WebElement> items = hedgeTradeConfigPage
				.findElementListByXpath("//table[@id='InstSearchPanel-Fund-List']//tr/td[2]/div");
		boolean resultFound = true;
		if (items.size() == 0) {
			resultFound = false;
		}
		LOGGER.info("Fund result can be found" + resultFound);
		Assert.assertFalse(resultFound);
	}

	@And("^Tenor warning message below is dispalyed under Tenor")
	public void tenor_warning_is_displayed_under_tenor(
			List<Map<String, String>> args) {
		String warningExpected = args.get(0).get("WARNING");

		// Get all Hedge Configuration tables
		hedgeTables = hedgeTradeConfigPage.getHedgeTables();

		// Get all warning message
		List<String> warningTexts = hedgeTradeConfigPage.get_Tenor_Warning();

		Assert.assertEquals("Tenor warning count", hedgeTables.size(),
				warningTexts.size());

		for (String w : warningTexts) {

			Assert.assertEquals("Tenor warning", warningExpected, w);

		}

	}
	
//	@And("^The hedge property table should be updated, record 9 should be clean as null$")
//	public void checkHedgeProperty(List<Map<String, String>> args)throws Throwable{
//		for(HedgeProperty a :hedgePropertyBeforeUpdate){
//			
//			if(args.get(0).get("NEXT_AVAILABLE_TENOR").equals("3 Months End Date") || args.get(0).get("NEXT_AVAILABLE_TENOR").equals("6 Months End Date") || args.get(0).get("NEXT_AVAILABLE_TENOR").equals("9 Months End Date") || args.get(0).get("NEXT_AVAILABLE_TENOR").equals("1 Year Date")){
//				if (a.getFundHedgeId().equals(args.get(0).get("XREF_FUND_INSTRUMENT_DETAIL_ID"))){
//					a.setUpdatedBy(userNameString);
//					a.setBenchmarkDate(null);
//					a.setMonthEndDate(null);
//				}
//			}
//		}
//		
//		hedgePropertyAfterUpdate = editHedgeTradeConfigDao.getHedgePropertyFromDb(xrefFundInstrumentDetailIdList);
//		Assert.assertTrue(editHedgeTradeConfigDao.compare2(hedgePropertyBeforeUpdate, hedgePropertyAfterUpdate));
//	}

	
	

	@And("^Click copy icon$")
	public void click_copy_icon() throws InterruptedException {
		boolean click_copy_icon_done = hedgeTradeConfigPage.click_copy_icon();
		Assert.assertTrue("Click copy icon", click_copy_icon_done);
	}

	@Then("^Select the instrument to be copied from and click Apply button$")
	public void select_the_instrument_to_be_copied_from(
			List<Map<String, String>> args) throws Throwable {
		// Select instrument from dropdown list
		String instrument = args.get(0).get("INSTRUMENT_NAME");
		hedgeTradeConfigPage.select_instrument_to_be_copied_from(instrument);
		// Click Apply button
		hedgeTradeConfigPage.click_Apply_Button();

	}

	@Then("^Month End Dates are displayed and checked by default$")
	public void month_End_Dates_are_displayed_and_checked_by_default(
			List<Map<String, String>> args) throws Throwable {
		String monthEndString = args.get(0).get("TENORS");
		List<String> monthEndExpected = new ArrayList<String>(
				Arrays.asList(monthEndString.split(",")));
		List<String> monthEndActual = new ArrayList<String>();

		List<WebElement> editors = hedgeTradeConfigPage.inspectElements(By
				.cssSelector(".hc-editor-table"));

		for (WebElement editor : editors) {
			List<WebElement> month_end_dates_checkBox = hedgeTradeConfigPage
					.inspectElements(editor,
							By.cssSelector(".hc-editor-tenor-me div input"));

			if (month_end_dates_checkBox != null) {
				for (WebElement month : month_end_dates_checkBox) {
					String month_check_value = month.getAttribute("CHECKED");
					String month_value = month.getAttribute("value");

					if (month_check_value != null
							&& month_check_value.toUpperCase().equals("TRUE")) {
						monthEndActual.add(month_value.toUpperCase().trim());

					}
				}

			}
		}

		Assert.assertEquals("Month End Date", monthEndExpected, monthEndActual);

	}

	@Then("^No tenor warning message below is dispalyed under Tenor$")
	public void no_tenor_warning_message_below_is_dispalyed_under_Tenor()
			throws Throwable {

		// Get all warning message
		List<String> warningTexts = hedgeTradeConfigPage.get_Tenor_Warning();

		Assert.assertEquals("Tenor warning count", 0, warningTexts.size());

	}

}