package com.ssc.ssgm.fx.refdata;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.springframework.stereotype.Repository;

import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;
import com.ssc.ssgm.fx.refdata.base.SpringContextHolder;
import com.ssc.ssgm.fx.refdata.dao.InstrumentDao;
import com.ssc.ssgm.fx.refdata.dao.PricingServiceDao;
import com.ssc.ssgm.fx.refdata.model.CurrencyPairGroup;
import com.ssc.ssgm.fx.refdata.model.InstrumentProfile;
import com.ssc.ssgm.fx.refdata.model.PricingService;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.InstrumentAddPanel;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.InstrumentPage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class InstrumentStepDefs {

	private BaseWebDriver baseDriver = new BaseWebDriver();

	private InstrumentPage instrumentPage = PageFactory.initElements(
			InstrumentPage.getWebDriver(), InstrumentPage.class);
	private InstrumentAddPanel instrumentAddPanel = PageFactory.initElements(
			InstrumentAddPanel.getWebDriver(), InstrumentAddPanel.class);

	private InstrumentDao instrumentDao = (InstrumentDao) SpringContextHolder
			.getBean(InstrumentDao.class);

	private PricingServiceDao pricingServiceDao = (PricingServiceDao) SpringContextHolder
			.getBean(PricingServiceDao.class);
	protected static final Logger LOGGER = Logger
			.getLogger(InstrumentStepDefs.class);

	private InstrumentProfile instrumentUIActual;
	private InstrumentProfile instrumentUIExpected;
	private String instrumentNameRandom;

	@Given("^User navigates to INSTRUMENT Page$")
	public void user_navigates_to_INSTRUMENT_Page() throws Throwable {
		instrumentPage.open_Instrument_Page();
	}

	@Given("^User enter Pricing Service, Rate Source, Location and Instrument Name$")
	public void user_enter_Pricing_Service_Rate_Source_Location_and_Instrument_Name(
			List<Map<String, String>> args) throws Throwable {
		String instrumentName = args.get(0).get("INSTRUMENT_NAME");
		String pricingServiceId = args.get(0).get("PRICING_SERVICE_ID");
		String rateSourceId = args.get(0).get("RATE_SOURCE_ID");
		String location = args.get(0).get("PHY_LOC_ID");
		instrumentPage.input_Query_Instrument_Criteria(instrumentName,
				pricingServiceId, rateSourceId, location);

	}

	@When("^User click Instrument Search button$")
	public void user_click_Instrument_Search_button() throws Throwable {

		baseDriver.getSearchButton();
		baseDriver.waitAfterSearch();

	}

	@Then("^All matched Instrument Profile order by Pricing Service and Instrument Name are displayed in pages with 25 records at most per page$")
	public void all_matched_Instrument_Profile_order_by_Pricing_Service_and_Instrument_Name_are_displayed_in_pages_with_records_at_most_per_page(
			List<Map<String, String>> args) throws Throwable {

		String instrumentName = args.get(0).get("INSTRUMENT_NAME");
		String pricingServiceId = args.get(0).get("PRICING_SERVICE_ID");
		String rateSourceId = args.get(0).get("RATE_SOURCE_ID");
		String location = args.get(0).get("PHY_LOC_ID");

		// get instrument from DB
		List<InstrumentProfile> instrumentDB = instrumentDao
				.getInstrumentFromDB(instrumentName, pricingServiceId,
						rateSourceId, location);

		// get instrument from UI
		List<InstrumentProfile> instrumentUI = instrumentPage
				.getInstrumentFromUI();

		Assert.assertEquals("Instrument Profile List DB V.S. UI: ",
				instrumentDB, instrumentUI);

	}

	@When("^User enter specific page number$")
	public void user_enter_specific_page_number(List<Map<String, String>> args)
			throws Throwable {
		String page = args.get(0).get("PAGE");
		instrumentPage.navigateToSpecificPage(page);

	}

	@Then("^All matched Instrument Profile order by Pricing Service and Instrument Name are displayed in the sepcific page$")
	public void all_matched_Instrument_Profile_order_by_Pricing_Service_and_Instrument_Name_are_displayed_in_the_sepcific_page(
			List<Map<String, String>> args) throws Throwable {
		String instrumentName = args.get(0).get("INSTRUMENT_NAME");
		String pricingServiceId = args.get(0).get("PRICING_SERVICE_ID");
		String rateSourceId = args.get(0).get("RATE_SOURCE_ID");
		String location = args.get(0).get("PHY_LOC_ID");

		int pageNum = instrumentPage.getCurrentPageNum();

		// get instrument from DB
		List<InstrumentProfile> instrumentDB = instrumentDao
				.getInstrumentFromDB(instrumentName, pricingServiceId,
						rateSourceId, location);
		List<InstrumentProfile> ipExpected = new ArrayList<InstrumentProfile>();
		int size = instrumentDB.size() / 25 > pageNum ? pageNum * 25
				: (pageNum - 1) * 25 + instrumentDB.size() % 25;
		for (int i = 25 * (pageNum - 1); i < size; i++) {
			ipExpected.add(instrumentDB.get(i));
		}
		// get instrument from UI
		List<InstrumentProfile> instrumentUI = instrumentPage
				.getInstrumentOfOnePageFromUI(pageNum);

		Assert.assertEquals("Instrument of current page: ", ipExpected,
				instrumentUI);

	}

	@When("^User click column header to sort Instrument Profile$")
	public void user_click_column_header_to_sort_Instrument_Profile(
			List<Map<String, String>> args) throws Throwable {
		String sortBy = args.get(0).get("SORT_BY");
		String order = args.get(0).get("ORDER");

		instrumentPage.sortInstrument(sortBy, order);

	}

	@Then("^All matched Instrument Profile are sorted and displayed in pages with 25 records at most per page$")
	public void all_matched_Instrument_Profile_are_sorted_and_displayed_in_pages_with_records_at_most_per_page(
			List<Map<String, String>> args) throws Throwable {
		String instrumentName = args.get(0).get("INSTRUMENT_NAME");
		String pricingServiceId = args.get(0).get("PRICING_SERVICE_ID");
		String rateSourceId = args.get(0).get("RATE_SOURCE_ID");
		String location = args.get(0).get("PHY_LOC_ID");
		String sortBy = args.get(0).get("SORT_BY");
		String order = args.get(0).get("ORDER");

		// get instrument from DB
		List<InstrumentProfile> instrumentDB = instrumentDao
				.getInstrumentFromDB(instrumentName, pricingServiceId,
						rateSourceId, location, sortBy, order);

		// get instrument from UI
		List<InstrumentProfile> instrumentUI = instrumentPage
				.getInstrumentFromUI();

		Assert.assertEquals("Instrument Profile List DB V.S. UI: ",
				instrumentDB, instrumentUI);

	}

	@Given("^User click add instrument profile button and navigate to add instrument panel$")
	public void user_click_add_instrument_profile_button() throws Throwable {
		instrumentPage.openAddPanel();
		Assert.assertTrue("Add panel not found",
				instrumentAddPanel.isPanelDisplayed());

	}

	@Given("^Input instrument profile details below$")
	public void input_instrument_profile_details_below(
			List<Map<String, String>> args) throws Throwable {

		// Clear test instrument before insert

		// add instrument
		for (int i = 0; i < args.size(); i++) {
			String instrumentName = args.get(i).get("INSTRUMENT_NAME").trim()
					.toUpperCase();
			instrumentNameRandom = getInstrumentRandon(instrumentName);
			
			String pricingServiceId = args.get(i).get("PRICING_SERVICE_ID")
					.trim().toUpperCase();
			String phyLocId = args.get(i).get("PHY_LOC_ID").trim()
					.toUpperCase();
			String rateSourceId = args.get(i).get("RATE_SOURCE_ID").trim()
					.toUpperCase();
			String locationTime = args.get(i).get("LOCATION_TIME(HHMM)").trim()
					.toUpperCase();
			String ccyPairList = args.get(i).get("CCY1_CCY2").trim()
					.toUpperCase();

			instrumentAddPanel.addInstrument(instrumentNameRandom, pricingServiceId,
					phyLocId, rateSourceId, locationTime, ccyPairList);

		}

	}

	private String getInstrumentRandon(String instrumentName) {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(
				"yyMMddHHmmss",
				Locale.ENGLISH);
		return instrumentName+" "+df.format(date);
	}

	@When("^User click add instrument profile save button$")
	public void user_click_add_instrument_profile_save_button()
			throws Throwable {

	}

	@Then("^Message box with Info Instrument is created successfully pops up$")
	public void message_box_with_Info_Instrument_is_created_successfully_pops_up(
			Map<String, String> values) throws Throwable {

		new BaseStepdefs().checkSaveResult(values);

	}
	
	@Then("^Message box with error message pops up$")
	public void message_box_with_error_message_pops_up(
			Map<String, String> values) throws Throwable {

		new BaseStepdefs().checkSaveResult(values);

	}

	@Then("^The instrument is saved successfully and can be viewed in UI$")
	public void the_instrument_is_saved_successfully(
			List<Map<String, String>> args) throws Throwable {
		// Check instrument details in UI
		String pricingServiceId = args.get(0).get("PRICING_SERVICE_ID");
		String rateSourceId = args.get(0).get("RATE_SOURCE_ID");
		String location = args.get(0).get("PHY_LOC_ID");
		instrumentPage.input_Query_Instrument_Criteria(instrumentNameRandom,
				pricingServiceId, rateSourceId, location);
		
		user_click_Instrument_Search_button();

		// Get instrument profile expected
		InstrumentProfile ipExpected = new InstrumentProfile();
		ipExpected.setInstrumentName(instrumentNameRandom);
		ipExpected.setPricingServiceId(pricingServiceId);
		ipExpected.setRateSource(rateSourceId);
		ipExpected.setPhyLocId(location);
		ipExpected.setLocationTime(Integer.valueOf(args.get(0).get(
				"LOCATION_TIME(HHMM)")));

		List<CurrencyPairGroup> ccyPairExpected = new ArrayList<CurrencyPairGroup>();
		List<String> ccyPairs = new ArrayList<>(Arrays.asList(args.get(0)
				.get("CCY1_CCY2").split(",")));
		for (int i = 0; i < ccyPairs.size(); i++) {
			String ccy1 = ccyPairs.get(i).trim().substring(0, 3);
			String ccy2 = ccyPairs.get(i).trim().substring(3, 6);
			CurrencyPairGroup ccyPair = new CurrencyPairGroup();
			ccyPair.setCcy1(ccy1);
			ccyPair.setCcy2(ccy2);
			ccyPairExpected.add(ccyPair);
		}

		// Get instrument profile Actual from UI
		user_Click_Detail_button();
		Thread.sleep(10000);

		// Get Instrument from detail panel
		instrumentUIActual = instrumentPage.getInstrumentFromDetailPanel();

		// Get currency pair list from detail panel
		List<CurrencyPairGroup> ccyPairListActual = instrumentPage
				.getCcyPairList();

		LOGGER.info("Instrument Expected: " + ipExpected.toString()
				+ ". Currency Pair: " + ccyPairExpected.toString());
		LOGGER.info("Instrument Actual: " + instrumentUIActual.toString()
				+ ". Currency Pair: " + ccyPairListActual.toString());

		Assert.assertEquals("Instrument Profile: ", ipExpected,
				instrumentUIActual);

		Boolean compareResult = compareCcyPair(ccyPairExpected,
				ccyPairListActual);
		Assert.assertTrue("Currency pair is NOT displayed as expected. ", compareResult);

	}

	@Then("^The value of pricing service list should be the combination of pricing Service id and full name$")
	public void the_value_of_pricing_service_list_should_be_the_combination_of_pricing_Service_short_name_and_full_name()
			throws Throwable {
		// Get Pricing Service List from Add Instrument Panel
		List<String> actualList = instrumentAddPanel
				.getPricingServiceValueList();
		List<String> expectedList = getPricingServiceValueListFromDB();

		Assert.assertEquals("Pricing Service List: ", expectedList, actualList);
	}

	public List<String> getPricingServiceValueListFromDB() {
		List<PricingService> psList = pricingServiceDao
				.getPricingServiceFromDB();
		List<String> psValueList = new ArrayList<String>();
		for (PricingService ps : psList) {
			psValueList.add(ps.getPricingServiceId() + " " + ps.getFullName());
		}
		return psValueList;
	}

	@When("^User Click Detail button$")
	public void user_Click_Detail_button() throws Throwable {
		// Get instrument from UI
		List<InstrumentProfile> instrumentUIExpectedList = instrumentPage
				.getInstrumentOfOnePageFromUI(1);
		instrumentUIExpected = instrumentUIExpectedList.get(0);
		instrumentUIExpected.setUpdatedBy(null);
		instrumentUIExpected.setUpdatedDatetime(null);

		instrumentPage.click_Detail_Button();

	}

	@Then("^The instrument profile details are displayed$")
	public void the_instrument_profile_details_are_displayed(
			List<Map<String, String>> args) throws Throwable {
		Thread.sleep(10000);
		String instrumentName = args.get(0).get("INSTRUMENT_NAME");
		// Get instrument details from DB
		// Get currency pair list
		List<CurrencyPairGroup> ccyPairListExapected = instrumentDao
				.getCcyPairList(instrumentName);

		// Get Instrument from detail panel
		instrumentUIActual = instrumentPage.getInstrumentFromDetailPanel();

		// Get currency pair list from detail panel
		List<CurrencyPairGroup> ccyPairListActual = instrumentPage
				.getCcyPairList();
		Assert.assertEquals("Instrument Profile", instrumentUIExpected,
				instrumentUIActual);

		Boolean ccyPairCompareResult = compareCcyPair(ccyPairListExapected,
				ccyPairListActual);

		LOGGER.info("Currency Pair List Expected Size: "
				+ ccyPairListExapected.size() + ". Details: "
				+ ccyPairListExapected.toString());
		LOGGER.info("Currency Pair List Actual Size: "
				+ ccyPairListActual.size() + ". Details: "
				+ ccyPairListActual.toString());
		Assert.assertTrue("Currency Pair List are not correct: ",
				ccyPairCompareResult);

	}

	private Boolean compareCcyPair(
			List<CurrencyPairGroup> ccyPairListExapected,
			List<CurrencyPairGroup> ccyPairListActual) {
		Assert.assertEquals("Currency Pair Size: ",
				ccyPairListExapected.size(), ccyPairListActual.size());

		for (CurrencyPairGroup ccyPair : ccyPairListExapected) {
			if (!ccyPairListActual.contains(ccyPair)) {
				return false;
			}
		}
		return true;
	}
}
