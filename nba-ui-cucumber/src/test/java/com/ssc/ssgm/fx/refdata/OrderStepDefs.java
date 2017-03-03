package com.ssc.ssgm.fx.refdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.ssc.ssgm.fx.refdata.base.BaseTestUtil;
import com.ssc.ssgm.fx.refdata.base.BaseClientUtil;
import com.ssc.ssgm.fx.refdata.base.Config;
import com.ssc.ssgm.fx.refdata.base.JsonParse;
import com.ssc.ssgm.fx.refdata.base.SpringContextHolder;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.OrderValidationAddPanel;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.OrderValidationPage;
import com.ssc.ssgm.fx.refdata.model.OrderTolerance;
import com.ssc.ssgm.fx.refdata.model.OrderToleranceDetail;
import com.ssc.ssgm.fx.refdata.dao.*;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class OrderStepDefs {
    protected static final Logger logger = Logger.getLogger(Config.class);
    
    private JsonParse jsonParse = new JsonParse();
	private OrderValidationPage orderPage = PageFactory.initElements(OrderValidationPage.getWebDriver(), OrderValidationPage.class);
	private OrderValidationAddPanel orderAddPanel = PageFactory.initElements(OrderValidationAddPanel.getWebDriver(), OrderValidationAddPanel.class);
	private List<OrderTolerance> dbQueryResults = new ArrayList<OrderTolerance>();
	private List<OrderTolerance> uiQueryResults = new ArrayList<OrderTolerance>();
	private List<OrderTolerance> idfQueryResults = new ArrayList<OrderTolerance>();
	private HashMap<String, String> currentCriteria = new HashMap<String, String>();
	private static final String IDF_OUTPUT_ERROR = "__errorMsg";
	private OrderToleranceDetail orderDetail;
	private OrderToleranceDetail expectOrderDetail = new OrderToleranceDetail();
	private String[] currentMapping;
	
	private String pricingServiceID = null;
	private String CCY1 = null;
	private String CCY2 = null;
	private boolean isSwitchCCY = false;
	
	private OrderTolerance currentTolerance;
	private OrderDAO orderDao = (OrderDAO) SpringContextHolder.getBean(OrderDAO.class);
	
	@Given("^user login to Reference data config page$")
    public void init() {
		orderPage.init();
		boolean result = orderPage.login();
		Assert.assertTrue("Login Failed!", result);
	}	
	@Given("^user login to Reference data page$")
    public void initRole(Map<String, String> values) {
	    String role = values.get("RoleType");
	    orderPage.initRole();
        boolean result = orderPage.roleLogin(role);
        Assert.assertTrue("Login Failed!", result);
        orderPage = PageFactory.initElements(OrderValidationPage.getWebDriver(), OrderValidationPage.class);
        orderAddPanel = PageFactory.initElements(OrderValidationAddPanel.getWebDriver(), OrderValidationAddPanel.class);
    }
	
	@When("^navigate to Order Validation Page$")
    public void gotoOrderPage() {
		orderPage.navigateToOrderPage();
	}
	
	@When("^Navigate to Order Size Tolerance Page$")
    public void gotoOrderSizeTolerancePage() {
        orderPage.navigateToOrderPage();
    }
    
	@Then("^the Add panel displayed$")
    public void checkAdd() {
		Assert.assertTrue("Add panel not found", orderAddPanel.isPanelDisplayed());
	}
		
    @Given("^user prepare order tolerance query data$")
    public void prepare_deadline_criteria(DataTable inputs) throws Throwable {
        Map<String, String> values = inputs.asMap(String.class, String.class);
        String[] orderedKeys = { "Pricing Service", "CCY1", "CCY2" };
        currentMapping = BaseStepdefs.trasferMapValue(values, orderedKeys);
        orderPage.selectPricingService(values.get("Pricing Service"));
        orderPage.setCCY1(values.get("CCY1"));
        orderPage.setCCY2(values.get("CCY2"));
        CCY1 = values.get("CCY1");
        CCY2 = values.get("CCY2");
    }
    
    @And("^the order size tolerance will be queried out successful$")
    public void order_size_tolerance_query_out() throws Throwable{   
        verify_IDFService_result();
    }
    
    @And("^Verify IDF Service and UI results are equal$")
    public void verify_IDFService_result() throws Throwable{         
        int totalUiNumber = orderPage.getTotalQueryNumbers();       
        List<Map<String, Object>> resultList = getResultIDFService();
        String errorMsg = resultList.get(0).get(IDF_OUTPUT_ERROR).toString();
        if (errorMsg == null || errorMsg.equals("")) {
            Assert.assertTrue("IDF service results number is not equal UI. IDF Number: " + resultList.size() + " when UI Number: " + totalUiNumber, resultList.size()==totalUiNumber);
            uiQueryResults = orderPage.getUIResult();
            compareJsonWithUI(resultList);
        } else {
            logger.error("Throwed IDF error message:"+ resultList.get(0).get(IDF_OUTPUT_ERROR));          
        }
    }
    @And("^verify the numbers of IDF service and UI results is equal$")
    public void verify_number_result() throws Throwable{         
        int totalUiNumber = orderPage.getTotalQueryNumbers();       
        List<Map<String, Object>> resultList = getResultIDFService();
        String errorMsg = resultList.get(0).get(IDF_OUTPUT_ERROR).toString();
        if (errorMsg == null || errorMsg.equals("")) {
            Assert.assertTrue("IDF service results number is not equal UI. IDF Number: " + resultList.size() + " when UI Number: " + totalUiNumber, resultList.size()==totalUiNumber);
        } else {
            logger.error("Throwed IDF error message:"+ resultList.get(0).get(IDF_OUTPUT_ERROR));          
        }
    }

    public List<Map<String, Object>> getResultIDFService() throws Throwable{       
        String pricingService = currentMapping[0];
        String ccy1 = currentMapping[1];
        String ccy2 = currentMapping[2];
        String[] inputNames = { "TARGET", "PARAMS"};
        String[] inputs = {"ORDER_SIZE_TIERS_QUERY",""};
        String params = "{\"currencyPairGroup\":{\"ccy1\":\"" + ccy1 + "\", " + "\"ccy2\":\""+ ccy2 +"\", " + "\"pricingServiceId\":\"" + pricingService + "\"}}";
        inputs[1]=params;
        List<Map<String, Object>> resultList = BaseClientUtil.getInstance().sendIdfRequest("913130000",inputNames, inputs);
        return resultList;            
    }

    @And("^Verify IDF Service return error message (.*)$")
    public void verify_IDFService_errMsg(String expectErrMsg) throws Throwable{  
        String uiErrMsg = orderPage.getErrorMsg();
        BaseTestUtil.assertEqual("UI error message is not right.", expectErrMsg, uiErrMsg);
    }
    
    @And("^Verify database and UI results are equal$")
    public void verify_result() throws Throwable{
        orderPage.getUIResult();     
        if (pricingServiceID!=null) {
            currentCriteria.put("PRICING_SERVICE_ID", pricingServiceID);
        }
        if (CCY1!=null&&!CCY1.equals("@Null")) {
            currentCriteria.put("CCY1", uiQueryResults.get(0).getCCY1());
        }
        if (CCY2!=null&&!CCY2.equals("@Null")) {
            currentCriteria.put("CCY2", uiQueryResults.get(0).getCCY2());
        }        
        dbQueryResults = orderDao.getQueryData(currentCriteria);      
        //sortOrderList(uiQueryResults);
        Assert.assertEquals("db result size is not equal UI.", dbQueryResults.size(),orderPage.getTotalQueryNumbers());  
        for (int i = 0; i < uiQueryResults.size() ; i++) {
            if (dbQueryResults.get(i).getCCY1_MINIMUM()==null) {
                dbQueryResults.get(i).setCCY1_MINIMUM("");                
            }
            if (dbQueryResults.get(i).getCCY2_MINIMUM()==null) {
                dbQueryResults.get(i).setCCY2_MINIMUM("");                
            }
            Assert.assertTrue("DB result is not equal UI.db value is " + dbQueryResults.get(i)+" but UI is " + uiQueryResults.get(i), dbQueryResults.get(i).equals(uiQueryResults.get(i)));
        }         
    }
    
    @Then("^the value of pricing service list should be$")
    public void checkPricingService(List<String> expectValues) {
    	List<String> actualList = orderAddPanel.getPricingServiceValueList();
    	for (int i = 0; i < expectValues.size(); i++) {
			String expectKey = expectValues.get(i);
			Assert.assertTrue("Value not found: " + expectKey, actualList.contains(expectKey));
		}
	}
    
    @And("^the default value of first tier and factor should be 0$")
    public void checkDefaultValue() {
    	String[] actualValues = orderAddPanel.getDefaultTierValue("CCY1");
    	for (int i = 0; i < 3; i++) {
			Assert.assertTrue("CCY1 Defauld value is not 0 for index: " + i, actualValues[i].equals("0"));
		}
    	String[] actualValues2 = orderAddPanel.getDefaultTierValue("CCY2");
    	for (int i = 0; i < 3; i++) {
			Assert.assertTrue("CCY2 Defauld value is not 0 for index: " + i, actualValues2[i].equals("0"));
		}
	}
    
    @When("^user select and input Pricing Service and currency$")
    public void selectValueForAdd(Map<String, String> values) {
    	currentTolerance = new OrderTolerance();
    	currentTolerance.setPricingServiceID(values.get("Pricing service"));
    	currentTolerance.setCCY1(values.get("CCY1"));
    	currentTolerance.setCCY2(values.get("CCY2"));
    	orderAddPanel.selectPricingService(values.get("Pricing service"));
    	orderAddPanel.setCCY1(values.get("CCY1"));
    	orderAddPanel.setCCY2(values.get("CCY2"));
    	if (values.containsKey("CCY1 Minimum")) {
    		orderAddPanel.setMinimum(values.get("CCY1 Minimum"));
    		currentTolerance.setCCY1_MINIMUM(values.get("CCY1 Minimum"));
		}else {
			orderAddPanel.setMinimum("1000");
			currentTolerance.setCCY1_MINIMUM("1000");
		}
    	if (values.containsKey("CCY2 Minimum")) {
    		orderAddPanel.setMinimum2(values.get("CCY2 Minimum"));
    		currentTolerance.setCCY2_MINIMUM(values.get("CCY2 Minimum"));
		}else {
			orderAddPanel.setMinimum2("2000");
			currentTolerance.setCCY2_MINIMUM("2000");
		}
	}
    
    @When("^tier is setup for (.*)$")
    public void setUpTier(String index, List<Map<String, String>> values) {
    	orderAddPanel.setUpTier(index, values);
	}
        
    @And("^popup warning message and save failed after click Save$")
    public void saveFailed(Map<String, String> values) {
        String actualResult = orderAddPanel.saveNewTolerance();
        String expectResult = values.get("Result");
        Assert.assertEquals("Result should be " +expectResult + ", but actual is " + actualResult,expectResult, actualResult);
    }
    
    @And("^there is no same record in database$")
    public void cleanDBforOrderTolerance() {
    	orderDao.deleteOrderTolerance(currentTolerance);
	}

    public void compareJsonWithUI(List<Map<String, Object>> resultList) {
        Map<String, Object> jsonResult= new HashMap<String, Object>();
        List<Map<String, Object>> anotherList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < resultList.size(); i++) {
            jsonResult = jsonParse.parseJson(resultList.get(i).get("JSON").toString());
            anotherList.add(jsonResult);
        }
        idfQueryResults = orderPage.getOrderList(anotherList);
        for (int i = 0; i < idfQueryResults.size(); i++) {
            Assert.assertTrue("IDF result contents not equal UI contents.", idfQueryResults.get(i).equals(uiQueryResults.get(i))); 
        }          
    }
    
    @Then("^User input all parameters (.*) and (.*) and (.*)$")
    public void Input_PricingService_and_CCY(String pricingService, String ccy1, String ccy2) {
        pricingServiceID = pricingService;
        CCY1 = ccy1;
        CCY2 = ccy2;
        
        List<WebElement> elemlList = orderPage.getPricingServiceList();
        if (!pricingServiceID.equals("@Null")) {
            for (int i = 1; i < elemlList.size(); i++) {
                if (elemlList.get(i).getText().contains(pricingService)) {
                    logger.info("the select pricing service value is : " + elemlList.get(i).getText());
                    elemlList.get(i).click();
                    break;
                 }
            }  
        }else {
            pricingServiceID="";
        }       
        if (CCY1 == null || CCY1.equals("@Null")) {
            CCY1 = "";
        }else {
            orderPage.setCCY1(CCY1);
        }       
        if (CCY2 == null || CCY2.equals("@Null")) {
            CCY2 = "";
        }else{
            orderPage.setCCY2(ccy2);
        }
    }
    
    @Then("^search for Added tolerance, Minimun size is correct$")
    public void search_for_Added_tolerance_Minimun_size_is_correct(Map<String, String> expectData) throws Throwable {
    	Input_PricingService_and_CCY(currentTolerance.getPricingService(), currentTolerance.getCCY1(), currentTolerance.getCCY2());
    	new BaseStepdefs().clickSearch();
    	List<Map<String, Object>> pageResult = orderPage.getOnePageResults();
    	Assert.assertEquals(expectData.get("Expected CCY1 Minimum"), pageResult.get(0).get("CCY1_MINIMUM"));
    	Assert.assertEquals(expectData.get("Expected CCY2 Minimum"), pageResult.get(0).get("CCY2_MINIMUM"));
    }

    @Then("^the Minimun size in detail panel is also correct$")
    public void the_Minimun_size_in_detail_panel_is_also_correct(Map<String, String> expectData) throws Throwable {
    	orderPage.clickDetailBTNforRow(1);
    	orderDetail = orderPage.getDetailData();
    	Assert.assertEquals(expectData.get("Expected CCY1 Minimum"), orderDetail.getCcy1Minimun());
    	Assert.assertEquals(expectData.get("Expected CCY2 Minimum"), orderDetail.getCcy2Minimun());
    }
    
    @Then("^User click view detail for (\\w+) record$")
    public void openDetail(String condition){
    	orderPage.clickDetailBTNforRow(1);
    	if ("first".equals(condition)) {
			orderDetail = orderPage.getDetailData();
			if (orderDetail.getCcy1().equals(CCY1)) {
				isSwitchCCY = false;
			}else {
				isSwitchCCY = true;
			}
		}	   	  	
    }
    
    @Then("^the value in detail panel is correct$")
    public void checkDetailValue(Map<String, String> expectData){
    	String expectTitle = expectData.get("Title");
    	String expectCCY1 = expectData.get("CCY1");
    	String expectCCY2 = expectData.get("CCY2");
    	Assert.assertEquals("Title not correct", expectTitle, orderDetail.getTitle());
    	Assert.assertEquals("CCY1 not correct", expectCCY1, orderDetail.getCcy1());
    	Assert.assertEquals("CCY2 not correct", expectCCY2, orderDetail.getCcy2());
    }
    
    @Then("^the tire for (\\w+) in detail panel is correct$")
    public void checkDetailTier(String index, DataTable expectData){
    	List<String[]> actualData = new ArrayList<String[]>();
    	actualData.add(new String[]{"Range Start","Range End","Factor"});
    	if (index.equals("CCY1")) {
    		actualData.addAll(orderDetail.getTier1());
		}else if (index.equals("CCY2")) {
			actualData.addAll(orderDetail.getTier2());
		}
    	expectData.diff(actualData);
    }

    @Then("^user view feature boxes$")
    public void Test(DataTable features) throws Throwable {
    	List<List<String>> featureBoxes = features.raw();    	
        List<String> actualFeature = orderPage.getMenuList();
        List<List<String>> expectedFeatureBoxes = new ArrayList<List<String>>();
        expectedFeatureBoxes.add(featureBoxes.get(0));        
        for (int i = 1; i < featureBoxes.size(); i++) {
        	List<String> expectView = new ArrayList<String>();
        	String viewName = featureBoxes.get(i).get(0);
        	expectView.add(viewName);
            if (actualFeature.contains(viewName)) {
                expectView.add("can");                
            }else{
            	expectView.add("cannot");
            } 
            expectedFeatureBoxes.add(expectView);
        }
        features.diff(expectedFeatureBoxes);
    }
        
    @And("^click Edit button for detail panel$")
    public void activeEditTolerance(){
    	orderPage.activeEdit();
    }
    
    @And("^the below cell are editable$")
    public void checkDetailPanelEditActive(DataTable status){
    	List<String[]> actualStatus = orderPage.getEditPanelEnabled();
    	status.diff(actualStatus);
    }
    
    @And("^user update minimum size value$")
    public void setMinimumValueForEdit(Map<String, String> values){
    	String min1 = values.get("CCY1 Minimum size");
    	String min2 = values.get("CCY2 Minimum size");
    	if (isSwitchCCY) {
			String temp = min2;
			min2 = min1;
			min1 = temp;
		}
    	expectOrderDetail.setCcy1Minimun(min1);
    	expectOrderDetail.setCcy2Minimun(min2);
    	orderPage.setEditPanelMinimumSize(min1, min2);
    }
    
    @And("^user update tiers for (\\w+)$")
    public void setTiersForEdit(String index, List<Map<String, String>> values){
    	if (isSwitchCCY) {
			if (index.equals("CCY1")) {
				index = "CCY2";
			}else if (index.equals("CCY2")) {
				index = "CCY1";
			}
		}
    	orderPage.setUpTier(index, values);
    	expectOrderDetail.setTier(index, values);
    }
        
    @And("^save the tolerance should be ok$")
    public void saveEditAndCheckResult(){
    	orderPage.saveUpdate(true);
    	orderPage.waitAfterSearch();
    }
    
    @And("^save the tolerance should get error$")
    public void saveEditAndCheckError(Map<String, String> values){
    	String expectResult = values.get("result");
    	orderPage.saveUpdate(false);
    	String actualResult = orderPage.queryResult();
    	BaseTestUtil.assertEqual("Edit result", expectResult, actualResult);
    }
    
    @And("^the value refreshed should (.*)$")
    public void verifyDetailDataAfterEdit(String condition){
    	OrderToleranceDetail expectDetail = expectOrderDetail;
    	if (!"reflect the update".equals(condition)) {
    		expectDetail = orderDetail;
		}
    	OrderToleranceDetail actualDetail = orderPage.getDetailData();
    	Assert.assertTrue("Detail data after edit is not as expected!", expectDetail.equals(actualDetail));
    }
    
    @And("^user add order tolerance tiers$")
    public void add_records(List<Map<String, String>> dataList) throws Throwable {
        for (int i = 0; i < dataList.size(); i++) {         
            OrderTolerance oneRecord = new OrderTolerance();           
            oneRecord.setPricingServiceID(dataList.get(i).get("Pricing Service"));         
            oneRecord.setCCY1(dataList.get(i).get("CCY1"));
            oneRecord.setCCY2(dataList.get(i).get("CCY2"));
            orderPage.addToleranceIDFService(oneRecord);
        }
    }
}
