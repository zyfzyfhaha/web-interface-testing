package com.ssc.ssgm.fx.refdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import com.ssc.ssgm.fx.refdata.base.BaseClientUtil;
import com.ssc.ssgm.fx.refdata.base.BaseTestUtil;
import com.ssc.ssgm.fx.refdata.base.Constants;
import com.ssc.ssgm.fx.refdata.base.JsonParse;
import com.ssc.ssgm.fx.refdata.base.SpringContextHolder;
import com.ssc.ssgm.fx.refdata.dao.OrderDAO;
import com.ssc.ssgm.fx.refdata.model.OrderTolerance;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.CurrencyPairCutoffPage;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AutoExecutionStepDefs {
    protected static final Logger logger = Logger.getLogger(AutoExecutionStepDefs.class);
    private CurrencyPairCutoffPage cutoffPage = PageFactory.initElements(CurrencyPairCutoffPage.getWebDriver(), CurrencyPairCutoffPage.class);  
    private List<Object[]> currentCurrencyPair;
    private String[] currentMapping;
    private String[] original;
    private List<OrderTolerance> idfQueryResults;
    private List<OrderTolerance> uiQueryResults;
    private List<Map<String, String>> editDeadlineLists = new ArrayList<Map<String,String>>();
    private List<OrderTolerance> addDeadlineCurrencyPair = new ArrayList<OrderTolerance>();
    private OrderDAO orderDao = (OrderDAO) SpringContextHolder.getBean(OrderDAO.class);
    private JsonParse jsonParse = new JsonParse();

    @Given("^Navigate to Currency Pair Deadline Page$")
    public void navigate_to_Cutoff_Page() throws Throwable {
        cutoffPage.openCutoffPage();
    }
    
    @Given("^User Prepare Add Currency Pair Cutoff data$")
    public void user_Prepare_Add_Cutoff_data(DataTable inputs) throws Throwable {
        Map<String, String> values = inputs.asMap(String.class, String.class);
        cutoffPage.setPageValues(values);
        currentCurrencyPair = BaseTestUtil.getCurrecncyPairFromMap(values);
    }
    
    @When("^there is no same Deadline in database$")
    public void there_is_no_same_deadline_in_database() throws Throwable {
        orderDao.deleteCurrencyPairCutoff(currentCurrencyPair);
    }
    
    @Given("^user prepare currency pair deadline query data$")
    public void prepare_deadline_criteria(DataTable inputs) throws Throwable {
        Map<String, String> values = inputs.asMap(String.class, String.class);
        String[] orderedKeys = {"Pricing Service","CCY1","CCY2"};
        currentMapping = BaseStepdefs.trasferMapValue(values, orderedKeys);
        cutoffPage.selectQueryPricingService(values.get("Pricing Service"));
        cutoffPage.setQueryCCY1(values.get("CCY1"));
        cutoffPage.setQueryCCY2(values.get("CCY2"));
    }
    
    @Then("^Verify Currency Pair Deadline Query IDF Service and UI results are equal$")
    public void verify_deadline_query_result() throws Throwable {
        List<Map<String, Object>> resultList = getDeadlineQueryIDFService();
        int totalUiNumber = cutoffPage.getTotalQueryNumbers();
        
        String errorMsg = resultList.get(0).get(Constants.IDF_OUTPUT_ERROR).toString();
        if (errorMsg == null || errorMsg.equals("")) {
            Assert.assertTrue("IDF service results number is not equal UI.", resultList.size()==totalUiNumber);
            uiQueryResults = cutoffPage.getDeadlineUIResult();            
            parseJsonWithUI(resultList);
        } else {
            logger.error("Throwed IDF error message:"+ resultList.get(0).get(Constants.IDF_OUTPUT_ERROR));          
        }
    }
    
    public void parseJsonWithUI(List<Map<String, Object>> resultList) {
        List<Map<String, Object>> anotherList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < resultList.size(); i++) {
            Map<String, Object> jsonResult = jsonParse.parseJson(resultList.get(i).get("JSON").toString());
            anotherList.add(jsonResult);
        }
        idfQueryResults = cutoffPage.getOrderList(anotherList);
        for (int i = 0; i < idfQueryResults.size(); i++) {
            Assert.assertTrue("IDF result contents not equal UI contents.", idfQueryResults.get(i).equals(uiQueryResults.get(i))); 
        }       
    }
    
    public List<OrderTolerance> getOrderList(List<Map<String, Object>> orderMaps) {       
        List<OrderTolerance> orderList = new ArrayList<OrderTolerance>();        
        for (int i = 0; i < orderMaps.size(); i++) {
            orderList.add(new OrderTolerance(orderMaps.get(i)));
        }
        return orderList;
    }
    
    private List<Map<String, Object>> getDeadlineQueryIDFService() throws Throwable  {
        String pricingService = currentMapping[0];
        String ccy1 = currentMapping[1];
        String ccy2 = currentMapping[2];
        
        String[] inputNames = { "TARGET", "PARAMS" };
        String[] inputs = { "CURRENCY_PAIR_CUTOFF_QUERY", "" };
        String params = "{\"currencyPairGroup\":{\"ccy1\":\"" + ccy1 + "\", " + "\"ccy2\":\"" + ccy2
                + "\", " + "\"pricingServiceId\":\"" + pricingService + "\"}}";
        inputs[1] = params;
        List<Map<String, Object>> resultList = BaseClientUtil.getInstance().sendIdfRequest("913130000", inputNames, inputs);
        return resultList;       
    }
    
    @When("^user select currency pair to delete for below data$")
    public void select_currencyPair(Map<String, String> values) {        
        if (uiQueryResults == null || uiQueryResults.size()==0) {
            uiQueryResults = cutoffPage.getDeadlineUIResult(); 
        }
        for (int i = 0; i < uiQueryResults.size(); i++) {
            String UIKey1 = uiQueryResults.get(i).getPricingService() + "_" + uiQueryResults.get(i).getCCY1() + "_" +uiQueryResults.get(i).getCCY2();
            String UIKey2 = uiQueryResults.get(i).getPricingService() + "_" + uiQueryResults.get(i).getCCY2() + "_" +uiQueryResults.get(i).getCCY1();
            if (UIKey1.equalsIgnoreCase(values.get("KEY"))||UIKey2.equalsIgnoreCase(values.get("KEY"))) {
                addDeadlineCurrencyPair.add(uiQueryResults.get(i));   
                cutoffPage.selectDeleteRecord(UIKey1);               
            }          
        }       
    }
    
    @When("^user select all currency pair to delete$")
    public void select_all_currencyPair() {        
        cutoffPage.selectAllDeleteRecord();                   
    }
       
    @When("^pop up confirm message after click delete$")
    public void delete_currencyPair(Map<String, String> values) throws Exception {        
        cutoffPage.clickDeleteButton();
        cutoffPage.deleteConfirm(values.get("DELETE"));
    }
       
    @When("^pop up error message after click delete$")
    public void no_currency_selected(Map<String, String> values) {        
        cutoffPage.clickDeleteButton(); 
        String actualResult = cutoffPage.queryResult();                 
        String expectResult = values.get("Result");
        Assert.assertEquals("Result should be " +expectResult + ", but actual is " + actualResult,expectResult, actualResult);
    }
    
    @When("^pop up error message after confirm ok$")
    public void no_privileged_to_delete(Map<String, String> values) {        
        String actualResult = cutoffPage.queryResult();                 
        String expectResult = values.get("Result");
        Assert.assertEquals("Result should be " +expectResult + ", but actual is " + actualResult,expectResult, actualResult);
    }
    
    @And("^verify currency pair are deleted successfully$")
    public void verify_delete(DataTable expectData){
        List<List<String>> keyLists = expectData.raw();
        for (int i = 0; i < keyLists.size(); i++) {
            String[] value = keyLists.get(i).get(1).split("_");
            cutoffPage.selectQueryPricingService(value[0]);
            cutoffPage.setQueryCCY1(value[1]);
            cutoffPage.setQueryCCY2(value[2]);
            cutoffPage.getSearchButton();
            String expectValue = "No record is found.";
            String actualValue = cutoffPage.getErrorMsg();
            BaseTestUtil.assertEqual("error message", expectValue, actualValue);           
        }
    }
      
    @And("^verify currency pair are deleted$")
    public void verify_deleteAll(){
        String expectValue = "No record is found.";
        String actualValue = cutoffPage.getErrorMsg();
        BaseTestUtil.assertEqual("error message", expectValue, actualValue);  
        
    } 
    
    @And("^verify currency pair are not deleted$")
    public void verify_notdelete(DataTable expectData){
        List<List<String>> keyLists = expectData.raw();
        for (int i = 0; i < keyLists.size(); i++) {
            String[] value = keyLists.get(i).get(1).split("_");
            cutoffPage.selectQueryPricingService(value[0]);
            cutoffPage.setQueryCCY1(value[1]);
            cutoffPage.setQueryCCY2(value[2]);
            cutoffPage.getSearchButton();
            uiQueryResults = cutoffPage.getDeadlineUIResult();
            Assert.assertTrue("UI results number is not one.", uiQueryResults.size()==1);
            String UIkey1 = uiQueryResults.get(0).getPricingService() + "_" + uiQueryResults.get(0).getCCY1() + "_" + uiQueryResults.get(0).getCCY2();
            String UIkey2 = uiQueryResults.get(0).getPricingService() + "_" + uiQueryResults.get(0).getCCY2() + "_" + uiQueryResults.get(0).getCCY1();

            Assert.assertTrue("UI results is not right.",keyLists.get(i).get(1).equalsIgnoreCase(UIkey1)||keyLists.get(i).get(1).equalsIgnoreCase(UIkey2));
        }
    }
    
    @And("^user add some currency pair$")
    public void add_records(List<Map<String, String>> dataList) throws Throwable {
        for (int i = 0; i < dataList.size(); i++) {         
            OrderTolerance oneRecord = new OrderTolerance();           
            oneRecord.setPricingServiceID(dataList.get(i).get("Pricing Service"));         
            oneRecord.setCCY1(dataList.get(i).get("CCY1"));
            oneRecord.setCCY2(dataList.get(i).get("CCY2"));
            oneRecord.setDeadline(dataList.get(i).get("Cutoff"));
            cutoffPage.addDeadlineIDFService(oneRecord);
        }
    }
    
    @And("^user modify deadline for below data$")
    public void modify_currency_pair_deadline(Map<String, String> values) {
        boolean exists = false;
        Map<String, String> deadline = new HashMap<String, String>();
        deadline.put("DEADLINE", values.get("DEADLINE"));
        if (uiQueryResults == null || uiQueryResults.size()==0) {
            uiQueryResults = cutoffPage.getDeadlineUIResult();
        }
        for (int i = 0; i < uiQueryResults.size(); i++) {
            String UIKey1 = uiQueryResults.get(i).getPricingService() + "_" + uiQueryResults.get(i).getCCY1() + "_" +uiQueryResults.get(i).getCCY2();
            String UIKey2 = uiQueryResults.get(i).getPricingService() + "_" + uiQueryResults.get(i).getCCY2() + "_" +uiQueryResults.get(i).getCCY1();           
            if (UIKey1.equalsIgnoreCase(values.get("KEY"))||UIKey2.equalsIgnoreCase(values.get("KEY"))) {
                original = new String[2];
                original[0] = uiQueryResults.get(i).getDeadline();
                cutoffPage.modifyDeadline(i, values);
                deadline.put("rowID", String.valueOf(i));  
                editDeadlineLists.add(deadline);
                exists = true;
            }          
        }       
        Assert.assertTrue("error message: Test data "+ values.get("KEY") + "is not found.", exists);
    }
        
    @And("^verify new deadline are saved successfully after click save$")
    public void verify_modify_deadline() {
        cutoffPage.saveUpdateResult();
        for (int i = 0; i < editDeadlineLists.size(); i++) {
            OrderTolerance actualResult =  new OrderTolerance(cutoffPage.getActualResult(editDeadlineLists.get(i).get("rowID")) );
            OrderTolerance expectResult =  new OrderTolerance(cutoffPage.getExpectResult(editDeadlineLists.get(i)));
            Assert.assertTrue("Error after modify deadline value.", expectResult.equals(actualResult));   
        }
    }
    
    @And("^verify deadline value are not changed$")
    public void saveFailed(Map<String, String> values) {
        cutoffPage.getSearchButton();
        cutoffPage.waitAfterSearch();
        Map<String, Object> currentResults = cutoffPage.getActualResult("0");
        String currentDeadline = currentResults.get("DEADLINE").toString();
        BaseTestUtil.assertEqual("Error after modify deadline value.", original[0], currentDeadline);
    }
       
    @Then("^user get error message after click save$")
    public void verifyUpdateErrorResult(Map<String, String> values) throws Throwable {
        String expectMsg = values.get("Result");       
        String actualValue = cutoffPage.saveErrorResult();
        BaseTestUtil.assertEqual("update error result", expectMsg, actualValue);
    }
}