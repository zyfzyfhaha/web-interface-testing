package com.ssc.ssgm.fx.refdata;

import java.lang.reflect.Type;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.cloakware.cspm.a.a.a;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ssc.ssgm.fx.refdata.base.BaseTestUtil;
import com.ssc.ssgm.fx.refdata.base.Constants;
import com.ssc.ssgm.fx.refdata.base.BaseClientUtil;
import com.ssc.ssgm.fx.refdata.base.Config;
import com.ssc.ssgm.fx.refdata.base.JsonParse;
import com.ssc.ssgm.fx.refdata.base.SpringContextHolder;
import com.ssc.ssgm.fx.refdata.model.FundCustId;
import com.ssc.ssgm.fx.refdata.model.JsonOrderNormalSize;
import com.ssc.ssgm.fx.refdata.model.OrderTolerance;
import com.ssc.ssgm.fx.refdata.model.QueryFundByIm;
import com.ssc.ssgm.fx.refdata.model.QueryIm;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.BulkUpdate;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.NormalSizeQueryPage;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.OrderNormalSizeAddPanel;
import com.ssc.ssgm.fx.refdata.dao.*;
import com.thoughtworks.selenium.webdriven.commands.Click;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class OrderNormalSizeStepDefs {
	protected static final Logger logger = Logger.getLogger(Config.class);
	private JsonParse jsonParse = new JsonParse();
	private NormalSizeQueryPage queryPage = PageFactory.initElements(
			NormalSizeQueryPage.getWebDriver(), NormalSizeQueryPage.class);
	private OrderNormalSizeAddPanel normalSizeAddPanel = PageFactory
			.initElements(OrderNormalSizeAddPanel.getWebDriver(),
					OrderNormalSizeAddPanel.class);
	private BulkUpdate bulkUpdate = PageFactory.initElements(BulkUpdate.getWebDriver(), BulkUpdate.class);
	private String[] currentMapping;
	private String[] original;

	private List<OrderTolerance> uiQueryResults;
	private List<JsonOrderNormalSize> uiQueryNormalSizeResults;
	private List<JsonOrderNormalSize> idfQueryNormalSizeResults;

	private List<OrderTolerance> idfQueryResults;

	private OrderTolerance currentTolerance;
	private List<Map<String, String>> editNormalSize = new ArrayList<Map<String, String>>();
	private OrderDAO orderDao = (OrderDAO) SpringContextHolder
			.getBean(OrderDAO.class);

	private InstrumentStepDefs instrumentStep = new InstrumentStepDefs();

	@And("^user prepare normal size query data$")
	public void user_Prepare_Add_Trader_Id_mapping_data(DataTable inputs)
			throws Throwable {
		Map<String, String> values = inputs.asMap(String.class, String.class);
		String[] orderedKeys = { "Pricing Service", "IM", "FUND", "CCY1",
				"CCY2" };
		currentMapping = BaseStepdefs.trasferMapValue(values, orderedKeys);
		queryPage.setPageValues(values);
	}

	@When("^navigate to Order Normal Size Page$")
	public void gotoNormalSizeOrderPage() {
		queryPage.navigateToOrderNormalSizePage();
	}

	@And("navigate to Bulk Update Page")
	public void clickBulkUpdateButton() {
		queryPage.clickBulkUpdateButton();
	}
	
	@When("^user enter into Normal Size page successful$")
	public void verifyNormalSizeScreen() {
		String text = queryPage.getPageHeaderName();
		Assert.assertTrue("Not navigate to Normal Size Page.",
				text.equalsIgnoreCase("Normal Order Size"));
	}

	@Then("^the Add Normal Size panel displayed$")
	public void checkNormalSizeAdd() {
		Assert.assertTrue("Normal Size Add panel not found",
				normalSizeAddPanel.isPanelDisplayed());
	}

	@And("^select Pricing Serivice (\\w+)$")
	public void select_pricingService(String pricingService) {
		if (pricingService.contains("Select")) {
			return;
		}
		List<WebElement> elemlList = normalSizeAddPanel.getPricingServiceList();
		for (int i = 1; i < elemlList.size(); i++) {
			if (elemlList.get(i).getText().contains(pricingService)) {
				logger.info("the select pricing service value is : "
						+ elemlList.get(i).getText());
				elemlList.get(i).click();
				break;
			}
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@And("^the page include fund and currency pair table$")
	public void check_include_elements() {
		boolean isExistFund = normalSizeAddPanel.isFundDisplayed();
		boolean isExistCCY1 = normalSizeAddPanel.getNormalSizeTable("CCY1");
		boolean isExistCCY2 = normalSizeAddPanel.getNormalSizeTable("CCY2");
		Assert.assertTrue("Currency pair form is not exist.", isExistFund
				&& isExistCCY1 && isExistCCY2);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Then("^the value of pricing service list in normal size should be$")
	public void checkNormalSizePricingService(List<String> expectValues) {
		List<String> actualList = normalSizeAddPanel
				.getPricingServiceValueList();
		actualList.remove(actualList.get(0));
		// for (int i = 0; i < expectValues.size(); i++) {
		// String expectKey = expectValues.get(i);

		// Assert.assertTrue("Value not found: " +
		// expectKey,actualList.contains(expectKey));

		// }

		List<String> expectedList = instrumentStep
				.getPricingServiceValueListFromDB();

		Assert.assertEquals("Pricing Service List: ", expectedList, actualList);
	}

	@When("^user close the add page$")
	public void closeAddPage() {
		queryPage.closeAddPanel();
	}

	@And("^there is no same record in database for normal size$")
	public void cleanDBforOrderTolerance() {
		orderDao.deleteOrderNormalSize(currentTolerance);
	}

	@When("^user input fund and normal_size for currency pair$")
	public void selectFundAndPricingService(Map<String, String> values) {
		currentTolerance = new OrderTolerance();
		currentTolerance.setPricingServiceID(values.get("Pricing service"));
		currentTolerance.setFund(values.get("FUND"));
		currentTolerance.setCCY1(values.get("CCY1"));
		currentTolerance.setCCY2(values.get("CCY2"));
		currentTolerance.setCCY1NORMALSIZE(values.get("CCY1_NormalSize"));
		currentTolerance.setCCY2NORMALSIZE(values.get("CCY2_NormalSize"));

		normalSizeAddPanel.selectPricingService(values.get("Pricing service"));
		normalSizeAddPanel.setFund(values.get("FUND"));
		normalSizeAddPanel.setCCY1(values.get("CCY1"));
		normalSizeAddPanel.setCCY1NormalSize(values.get("CCY1_NormalSize"));
		normalSizeAddPanel.setCCY2(values.get("CCY2"));
		normalSizeAddPanel.setCCY2NormalSize(values.get("CCY2_NormalSize"));
	}

	@And("^Verify Normal Size Query IDF Service and UI results are equal$")
	public void verify_NormalSize_Query_result() throws Throwable {
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("PRICING SERVICE", currentMapping[0]);
		paraMap.put("IM", currentMapping[1]);
		paraMap.put("FUND", currentMapping[2]);
		paraMap.put("CCY1", currentMapping[3]);
		paraMap.put("CCY2", currentMapping[4]);
		// uiQueryResults = queryPage.getUIResult();
		uiQueryNormalSizeResults = queryPage.getUiNormalSize();

		List<Map<String, Object>> resultList = getNormalQueryIDFService(paraMap);
		String jsonResponse = resultList.get(0).get(Constants.IDF_OUTPUT_JSON)
				.toString();
		String errorMsg = resultList.get(0).get(Constants.IDF_OUTPUT_ERROR)
				.toString();
		if (errorMsg == null || errorMsg.equals("")) {
			// Assert.assertTrue("IDF service results number is not equal UI.",
			// resultList.size() == uiQueryResults.size());
			// parseJsonWithUI(resultList);
			idfQueryNormalSizeResults = getOrderNormalSizeFromJson(jsonResponse);
			for(JsonOrderNormalSize s : idfQueryNormalSizeResults){
				s.setCustId(null);
				s.getCurrencyPairGroup().setGroupId(null);
				
			}
			Assert.assertEquals("Order Normal Size: ",
					idfQueryNormalSizeResults, uiQueryNormalSizeResults);
		} else {
			logger.error("Throwed IDF error message:"
					+ resultList.get(0).get(Constants.IDF_OUTPUT_ERROR));
		}
	}

	private List<JsonOrderNormalSize> getOrderNormalSizeFromJson(
			String jsonResponse) {

		Gson gson = new Gson();
		Type collectionType = new TypeToken<List<JsonOrderNormalSize>>() {
		}.getType();

		List<JsonOrderNormalSize> orderNormalSizeList = gson.fromJson(
				jsonResponse, collectionType);

		return orderNormalSizeList;

	}

	@And("^UI columns include below fields$")
	public void verify_columns(DataTable expectData) {
		List<List<String>> expectFields = expectData.raw();
		List<List<String>> actualFields = new ArrayList<List<String>>();

		List<String> Fields = queryPage.getUIcolumnFields();

		for (int i = 0; i < expectFields.size(); i++) {
			List<String> fieldCheck = new ArrayList<String>();
			fieldCheck.add(Fields.get(i));
			actualFields.add(fieldCheck);
		}
		expectData.diff(actualFields);
	}

	public void parseJsonWithUI(List<Map<String, Object>> resultList) {
		List<Map<String, Object>> anotherList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < resultList.size(); i++) {
			Map<String, Object> jsonResult = jsonParse.parseJson(resultList
					.get(i).get("JSON").toString());
			anotherList.add(jsonResult);
		}
		idfQueryResults = queryPage.getOrderList(anotherList);
		for (int i = 0; i < idfQueryResults.size(); i++) {
			Assert.assertTrue("IDF result contents not equal UI contents.",
					idfQueryResults.get(i).equals(uiQueryResults.get(i)));
		}
	}

	public List<Map<String, Object>> getNormalQueryIDFService(
			Map<String, String> values) throws Throwable {
		// String[] inputNames = { "TARGET", "PARAMS" };
		// String[] inputs = { "NORMAL_ORDER_SIZE_QUERY", "" };
		String[] inputNames = { "USER", "JSON" };
		String[] inputs = { "NORMAL_ORDER_SIZE_QUERY_TEST", "" };
		String params = "{\"currencyPairGroup\":{\"ccy1\":\""
				+ values.get("CCY1") + "\", " + "\"ccy2\":\""
				+ values.get("CCY2") + "\", " + "\"pricingServiceId\":\""
				+ values.get("PRICING SERVICE") + "\"},";
		params = params + "\"im\":\"" + values.get("IM") + "\","
				+ "\"fund\":\"" + values.get("FUND") + "\"}";
		inputs[1] = params;
		// List<Map<String, Object>> resultList = BaseClientUtil.getInstance()
		// .sendIdfRequest("913130000", inputNames, inputs);
		List<Map<String, Object>> resultList = BaseClientUtil.getInstance()
				.sendIdfRequest("913120004", inputNames, inputs);
		return resultList;
	}

	@And("^user can get result after click search$")
	public void checkQueryResult(Map<String, String> values) {
		String actualResult = queryPage.queryResult();
		String expectResult = values.get("Result");
		BaseTestUtil.assertEqual("Search result error.", expectResult,
				actualResult);
	}

	@And("^user modify normal size for below data$")
	public void modify_normal_size(Map<String, String> values) {
		boolean exists = false;
		Map<String, String> normalSizeValues = new HashMap<String, String>();
		normalSizeValues
				.put("CCY1_NORMAL_SIZE", values.get("CCY1_NORMAL_SIZE"));
		normalSizeValues
				.put("CCY2_NORMAL_SIZE", values.get("CCY2_NORMAL_SIZE"));

		if (uiQueryResults == null || uiQueryResults.size() == 0) {
			uiQueryResults = queryPage.getEditNormalSize();
		}
		for (int i = 0; i < uiQueryResults.size(); i++) {
			String UIKey1 = uiQueryResults.get(i).getPricingService() + "_"
					+ uiQueryResults.get(i).getCCY1() + "_"
					+ uiQueryResults.get(i).getCCY2();
			String UIKey2 = uiQueryResults.get(i).getPricingService() + "_"
					+ uiQueryResults.get(i).getCCY2() + "_"
					+ uiQueryResults.get(i).getCCY1();
			if (UIKey1.equalsIgnoreCase(values.get("KEY"))
					|| UIKey2.equalsIgnoreCase(values.get("KEY"))) {
				original = new String[2];
				original[0] = uiQueryResults.get(i).getCCY1NORMALSIZE();
				original[1] = uiQueryResults.get(i).getCCY2NORMALSIZE();
				queryPage.modifyNormalSize(i, values);
				normalSizeValues.put("rowID", String.valueOf(i));
				editNormalSize.add(normalSizeValues);
				exists = true;
			}
		}
		Assert.assertTrue("error message: Test data " + values.get("KEY")
				+ " is not found.", exists);
	}

	@And("^user click save button$")
	public void save_normal_size() {
		queryPage.saveNewTolerance();
	}

	@And("^verify new normal sizes are saved successfully after click save$")
	public void verify_modify_normalSize() {
		queryPage.saveUpdateResult();
		for (int i = 0; i < editNormalSize.size(); i++) {
			OrderTolerance actualResult = new OrderTolerance(
					queryPage.getActualResult(editNormalSize.get(i)
							.get("rowID")));
			OrderTolerance expectResult = new OrderTolerance(
					queryPage.getExpectResult(editNormalSize.get(i)));
			logger.info("Expected Result: " + expectResult.toString());
			logger.info("Actual Result" + actualResult.toString());
			Assert.assertTrue("Error after modify normal size.",
					expectResult.equals(actualResult));
		}
	}

	@And("^verify normal size values are not changed$")
	public void saveFailed() {
		for (int i = 0; i < editNormalSize.size(); i++) {
			OrderTolerance actualResult = new OrderTolerance(
					queryPage.getActualResult(editNormalSize.get(i)
							.get("rowID")));
			OrderTolerance expectResult = new OrderTolerance(
					queryPage.getOriginalResult(editNormalSize.get(i), original));
			Assert.assertTrue("Error after modify normal size.",
					expectResult.equals(actualResult));
		}
	}

	@Then("User input below value in fund field")
	public void searchFund(Map<String, String> values) {
		if (values.containsKey("addition")) {
			String addValue = values.get("addition");
			normalSizeAddPanel.additionForFund(addValue);
			return;
		}
		String searchValue = values.get("input");
		normalSizeAddPanel.searchFund(searchValue);
	}

	@Then("^a list of search result will be listed$")
	public void a_list_of_search_result_will_be_listed(DataTable expectList)
			throws Throwable {
		List<String[]> actualList = normalSizeAddPanel.getFundSearchList();
		expectList.diff(actualList);
	}

	@Then("^click on the value in the list$")
	public void click_on_the_value_in_the_list(Map<String, String> values)
			throws Throwable {
		String fundShortName = values.get("Value");
		normalSizeAddPanel.selectFund(fundShortName);
	}

	@Then("^the fund field will be set as (.*)$")
	public void the_fund_field_will_be_set_as(String expectValue)
			throws Throwable {
		String actualValue = normalSizeAddPanel.getFundValue();
		BaseTestUtil.assertEqual("fund", expectValue, actualValue);
	}

	@Then("^user can get normal size result after click Save$")
	public void verifyUpdateResult(Map<String, String> values) throws Throwable {
		queryPage.saveUpdateResult();
	}

	@And("^the save button is (\\w+)$")
	public void verifySaveButtonStatus(String expectStatus) {
		String actualStatus = "disabled";
		if (normalSizeAddPanel.isSaveButtonEnable()) {
			actualStatus = "enabled";
		}
		BaseTestUtil.assertEqual("Error message is not right.", expectStatus,
				actualStatus);
	}

	@And("^user add normal order size data$")
	public void add_records(List<Map<String, String>> dataList)
			throws Throwable {
		for (int i = 0; i < dataList.size(); i++) {
			OrderTolerance oneRecord = new OrderTolerance();
			oneRecord.setPricingServiceID(dataList.get(i)
					.get("Pricing Service"));
			oneRecord.setCCY1(dataList.get(i).get("CCY1"));
			oneRecord.setCCY2(dataList.get(i).get("CCY2"));
			oneRecord.setFund(dataList.get(i).get("CustID"));
			queryPage.addNormalSizeIDFService(oneRecord);
		}
	}
	
	@When("^user click search to query$")
	public void clickSearchButton() throws Throwable{
		queryPage.clickSearchButton();
	}
	
	@And("^click the all-check check box$")
	public void clickCheckAllBox()throws Throwable{
	queryPage.clickAllCheckBox();
	}
	
	@When("^click filldown button and save button$")
	public void clickFilldownButton() throws Throwable{
	queryPage.clickFilldownButton();
	queryPage.saveUpdateResult();
	}
	
	@And("^ccy1 size and ccy2 size of all left records below the first record are updated$")
	public void checkFilldown(Map<String, String> values) {
		boolean exists = false;
		Map<String, String> normalSizeValues = new HashMap<String, String>();
		if (uiQueryResults == null || uiQueryResults.size() == 0) {
			uiQueryResults = queryPage.getEditNormalSize();
		}
		for (int i = 0; i < uiQueryResults.size(); i++) {
			String UIKey1 = uiQueryResults.get(i).getPricingService() + "_"
					+ uiQueryResults.get(i).getCCY1() + "_"
					+ uiQueryResults.get(i).getCCY2();
			String UIKey2 = uiQueryResults.get(i).getPricingService() + "_"
					+ uiQueryResults.get(i).getCCY2() + "_"
					+ uiQueryResults.get(i).getCCY1();
			if (UIKey1.equalsIgnoreCase(values.get("KEY"))
					|| UIKey2.equalsIgnoreCase(values.get("KEY"))) {
				original = new String[2];
				original[0] = uiQueryResults.get(i).getCCY1NORMALSIZE();
				original[1] = uiQueryResults.get(i).getCCY2NORMALSIZE();
				queryPage.modifyNormalSize(i, values);
				normalSizeValues.put("rowID", String.valueOf(i));
				editNormalSize.add(normalSizeValues);
				exists = true;
			}
		}
		Assert.assertTrue("error message: Test data " + values.get("KEY")
				+ " is not found.", exists);
	}
	
	
	@When("Input a valid IM name which exsit in DB and click the IM search button")
	public void searchIM(final Map<String, String> args)throws Throwable{
		String imNameString = args.get("IM_NAME");
		bulkUpdate.searchIM(imNameString);
	}
	
	
	@When("Input a valid IM name which not exsit in DB click the IM search button") 
	public void searchIMNotExistInDb(final Map<String, String> args)throws Throwable{
		String imNameString = args.get("IM_NAME");
		bulkUpdate.searchIM(imNameString);
	}
	
	@And("^select SFX pricing service$")
	public void selectSfxPricingService()throws Throwable{
		bulkUpdate.getPricingServiceList();
		bulkUpdate.selectPricingService("SFX");
	} 
	
	
	@When("^Input a valid Fund name which NOT exsit in DB and click the Fund search button$")
	public void searchFundNotExistInDb(final Map<String, String> args)throws Throwable{
		String fundNameString = args.get("FUND_NAME");
		bulkUpdate.searchFund(fundNameString);
	}
	
	
	@Then("^No IM search results, no drop downlist, nothing happened$")
	public void noImSearchOut()throws Throwable{
		Assert.assertTrue(bulkUpdate.isImDropdownListNotExist());
	}
	
	
	@Then("^No fund search results, no drop downlist, nothing happened$")
	public void noFundSearchOut()throws Throwable{
		Assert.assertTrue(bulkUpdate.isFundDropdownListNotExist());
	}
	
	
	@Given("Select valid IM and click the Fund search button")
	public void selectImAndSearchFund(final Map<String, String> args)throws Throwable {
		String imNameString = args.get("IM_NAME");
		String fundNameString = args.get("FUND_NAME");
		bulkUpdate.searchIM(imNameString);
		bulkUpdate.selectIm(imNameString);
		bulkUpdate.searchFund(fundNameString);
		
	}
	
	
	
	@Given("^Select a valid IM and valid Fund$")
	public void selectImAndFund(final Map<String, String> args)throws Throwable {
		String imNameString = args.get("IM_NAME_1");
		String fundNameString = args.get("FUND_NAME");
		bulkUpdate.searchIM(imNameString);
		bulkUpdate.selectIm(imNameString);
		bulkUpdate.searchFund(fundNameString);
		List<QueryFundByIm> actualResultsFromUi = bulkUpdate.getFundAndIdFromUI();
		for(QueryFundByIm a : actualResultsFromUi){
		bulkUpdate.clickFund(a.getFund());
		}
		
	}
	
	
	@Then("^Select another valid IM$")
	public void selectAnotherIm(final Map<String, String> args)throws Throwable{
		String imNameString = args.get("IM_NAME_2");
		bulkUpdate.searchIM(imNameString);
		bulkUpdate.selectIm(imNameString);
	}
	
	
	@Then("^The selected fund in textbox should be cleared$")
	public void clearFund()throws Throwable{
			Assert.assertTrue(bulkUpdate.isFundDeleted());
		} 
		
	
	
	
	@When("^User clicked some fund in dropdown list$")
	public void selectFund()throws Throwable{
		List<QueryFundByIm> actualResultsFromUi = bulkUpdate.getFundAndIdFromUI();
		for(QueryFundByIm a : actualResultsFromUi){
			bulkUpdate.clickFund(a.getFund());
		}
	}
	
	
	@Then("^Fund name should be displayed properly in the textbox$")
		public void checkSelectedFund(){
		List<QueryFundByIm> actualResultsFromUi = bulkUpdate.getFundAndIdFromUI();
		for(QueryFundByIm a : actualResultsFromUi){
			Assert.assertTrue(bulkUpdate.selectedFund(a.getFund()));
		}	
	}
	

	@When("^Selected the fund already exist in textbox$")
	public void selectDuplicateFund()throws Throwable{
		List<QueryFundByIm> actualResultsFromUi = bulkUpdate.getFundAndIdFromUI();
		for(QueryFundByIm a : actualResultsFromUi){
			bulkUpdate.clickFund(a.getFund());
		}
	}
	
	@Then("No fund added to textbox")
	public void NoDuplicateFundAdded(final Map<String, String> args)throws Throwable{
		List<QueryFundByIm> actualResultsFromUi = bulkUpdate.getFundAndIdFromUI();
		Assert.assertEquals(actualResultsFromUi.size(),bulkUpdate.selectedFundCount());
	}
	
	
	@When("^Delete the fund in textbox$")
	public void deleteFund()throws Throwable{
		bulkUpdate.deleteFund();
	}
	
	@Then("^The fund should be deleted$")
	public void isFundDeleted()throws Throwable{
		Assert.assertTrue(bulkUpdate.isFundDeleted());
	}
	
	
	
	
	
	@Then("^IM search results should be listed in dropdown list$")
	public void checkImDropdownList(final Map<String, String> args)throws Throwable{
		String imNameString = args.get("IM_NAME");
		List<QueryIm> actualResultsFromUi = bulkUpdate.getImAndIdFromUI();
		List<QueryIm> expectedResultsFromService = getImAndIdFromNBAService(imNameString);
		Assert.assertEquals(expectedResultsFromService, actualResultsFromUi);
	}
	
	@Given("Select a valid IM")
	public void selectValidIm(final Map<String, String> args)throws Throwable{
		String imNameString = args.get("IM_NAME");
		bulkUpdate.searchIM(imNameString);
		bulkUpdate.selectIm(imNameString);
	}
	

	
	
	
	
	@When("^Click the Fund search button$")
	public void clickFundSearch()throws Throwable{
		bulkUpdate.clickSearchFund();
	}

	
	
	@When("Input a valid Fund name which exsit in DB and click the Fund search button")
	public void searchValidFund(final Map<String, String> args)throws Throwable{
		String fundNameString = args.get("FUND_NAME");
		bulkUpdate.searchFund(fundNameString);
	}
	
	
	@Then("^Fund search results should be listed in dropdown list$")
	public void checkFundDropdownList(final Map<String, String> args)throws Throwable{
		String fundNameString = args.get("FUND_NAME");
		String imIdString = args.get("IM_ID");
		List<QueryFundByIm> actualResultsFromUi = bulkUpdate.getFundAndIdFromUI();
		List<QueryFundByIm> expectedResultsFromService = getFundCustIdFromNBAService(fundNameString, imIdString);
		Assert.assertEquals(expectedResultsFromService, actualResultsFromUi);
	}
	
	
	// {queryId:9, im='UBS AG ZUR'}
	
	private List<QueryIm> getImAndIdFromNBAService(String im) throws Throwable {

		List<QueryIm> imList = new ArrayList<QueryIm>();
		StringBuffer inputJson = new StringBuffer();
		inputJson.append("{queryId:9,im='").append(im).append("'}");
		String userName = "QueryImTest";
		List<Object[]> result = BaseClientUtil.getInstance().callNbaIdfService(
				"913129001", inputJson.toString(), userName);
		String outputJson = (result.get(0))[0].toString();
		String err = (result.get(0))[1].toString();

		if (err.contains("No record is found")) {
			return imList;
		}
		if (StringUtils.isBlank(err) && StringUtils.isNotBlank(outputJson)) {

			imList = getImFromJson(outputJson);
		}

		return imList;
	}
	
	
	
//	{queryId:10,imId=[9164357, 9166122], fund="C"}

	private List<QueryFundByIm> getFundCustIdFromNBAService(String fund, String ImId)
			throws Throwable {

		List<QueryFundByIm> fundList = new ArrayList<QueryFundByIm>();
		StringBuffer inputJson = new StringBuffer();
		inputJson.append("{queryId:10,imId=[").append(ImId).append("],fund=\"").append(fund).append("\"}");
		String userName = "getFundCustIdByImTest";
		List<Object[]> result = BaseClientUtil.getInstance().callNbaIdfService(
				"913129001", inputJson.toString(), userName);
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
	
	
	private List<QueryIm> getImFromJson(String Json) {
		Type collectionType = new TypeToken<List<QueryIm>>() {
		}.getType();
		Gson gson = new Gson();
		List<QueryIm> im = gson.fromJson(Json, collectionType);
		return im;
	}
	
	
	private List<QueryFundByIm> getFundsFromJson(String Json) {
		Type collectionType = new TypeToken<List<QueryFundByIm>>() {
		}.getType();
		Gson gson = new Gson();
		List<QueryFundByIm> im = gson.fromJson(Json, collectionType);
		return im;
	}
	
}
