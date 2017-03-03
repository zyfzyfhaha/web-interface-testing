package com.ssc.ssgm.fx.refdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import com.ssc.ssgm.fx.refdata.base.BaseClientUtil;
import com.ssc.ssgm.fx.refdata.base.BaseTestUtil;
import com.ssc.ssgm.fx.refdata.base.Config;
import com.ssc.ssgm.fx.refdata.base.Constants;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.CurrencyPairCutoffPage;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.NormalSizeQueryPage;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.OrderNormalSizeAddPanel;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.OrderValidationAddPanel;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.OrderValidationPage;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CurrencyPairQueryStepDefs {

    private NormalSizeQueryPage queryPage = PageFactory.initElements(NormalSizeQueryPage.getWebDriver(),
            NormalSizeQueryPage.class);
    private OrderNormalSizeAddPanel normalSizeAddPanel = PageFactory.initElements(
            OrderNormalSizeAddPanel.getWebDriver(), OrderNormalSizeAddPanel.class);
    private CurrencyPairCutoffPage cutoffPage = PageFactory.initElements(CurrencyPairCutoffPage.getWebDriver(),
            CurrencyPairCutoffPage.class);
    private OrderValidationPage orderPage = PageFactory.initElements(OrderValidationPage.getWebDriver(),
            OrderValidationPage.class);
    private OrderValidationAddPanel orderAddPanel = PageFactory.initElements(OrderValidationAddPanel.getWebDriver(),
            OrderValidationAddPanel.class);
    
    private List<String> CCYListValues;
    
    protected static final Logger logger = Logger.getLogger(Config.class);

    @Given("^user logins to Reference data config page$")
    public void init() throws Throwable {
        queryPage.init();
        boolean result = queryPage.login();
        Assert.assertTrue("Login Failed!", result);
    }

    @Given("^user navigates to (.*)$")
    public void gotoNormalSizePage(String page) {
        switch (page) {
        case "Currency Pair Deadline Page":
            cutoffPage.openCutoffPage();
            break;
        case "Order Normal Size Page":
            queryPage.navigateToOrderNormalSizePage();
            break;
        case "Order Size Tolerance Page":
            orderPage.navigateToOrderPage();
            break;
        }
    }
    
    @When("^user clicks (\\w+) drop down list of (.*)$")
    public void openCcyList(String type, String page) {
        switch (page) {
        case "Currency Pair Deadline":
            CCYListValues = cutoffPage.getCurrencyDDLList(type, false);
            break;
        case "Currency Pair Deadline Add Panel":
            CCYListValues = cutoffPage.getCurrencyDDLList(type, true);
            break;
        case "Order Normal Size":
            CCYListValues = queryPage.getOrderNormalSizeCurrencyList(type);
            break;
        case "Order Normal Size Add Panel":
            CCYListValues = normalSizeAddPanel.getAddOrderNormalSizeCurrencyList(type);
            break;
        case "Order Size Tolerance":
            CCYListValues = orderPage.getOrderSizeToleranceCurrencyList(type);
            break;
        case "Order Size Tolerance Add Panel":
            CCYListValues = orderAddPanel.getAddOrderSizeToleranceCurrencyList(type);
            break;
        }
    }

    @Then("^user sees the size of list > (\\d+)$")
    public void seeCcyValueList(int expectNum) throws Throwable {
        Assert.assertEquals(true, CCYListValues.size() > expectNum);
    }

    @And("^the value of currency list are same as service result$")
    public void compairWithIDFResults() throws Throwable {
        List<String> CCYAimList = getCcyPairQueryIDFService();
        BaseTestUtil.assertListEqual(CCYAimList, CCYListValues);

    }

    @And("^user clicks a (\\w+) value of (.*)$")
    public void clicks_ccy_value(String type, String page, Map<String, String> values) throws Throwable {
        String ccyselectValue = values.get("value");

        switch (page) {
        case "Currency Pair Deadline":
            cutoffPage.setCurrencyDDLValue(type, false, ccyselectValue);
            break;
        case "Currency Pair Deadline Add Panel":
            cutoffPage.setCurrencyDDLValue(type, true, ccyselectValue);
            break;
        case "Order Normal Size":
            queryPage.setOrderNormalSizeCurrencyValue(type, ccyselectValue);
            break;
        case "Order Normal Size Add Panel":
            normalSizeAddPanel.setAddOrderNormalSizeCurrencyValue(type, ccyselectValue);
            break;
        case "Order Size Tolerance":
            orderPage.setOrderSizeToleranceCurrencyValue(type, ccyselectValue);
            break;
        case "Order Size Tolerance Add Panel":
            orderAddPanel.setAddOrderSizeToleranceCurrencyValue(type, ccyselectValue);
            break;
        }
    }

    @Then("^the (\\w+) value of (.*) is selected$")
    public void ccyValueSelected(String type, String page, Map<String, String> values) {
        String ccyValue = values.get("value");
        String ccySelectedValue;
        switch (page) {
        case "Currency Pair Deadline":
            ccySelectedValue = cutoffPage.getCurrencyDDLSelectedValue(type, false);            
            break;
        case "Currency Pair Deadline Add Panel":
            ccySelectedValue = cutoffPage.getCurrencyDDLSelectedValue(type, true);
            break;
        case "Order Normal Size":
            ccySelectedValue = queryPage.getOrderNormalSizeCurrencySelectedValue(type);
            break;
        case "Order Normal Size Add Panel":
            ccySelectedValue = normalSizeAddPanel.getAddOrderNormalSizeCurrencySelectedValue(type);
            break;
        case "Order Size Tolerance":
            ccySelectedValue = orderPage.getOrderSizeToleranceCurrencySelectedValue(type);
            break;
        case "Order Size Tolerance Add Panel":
            ccySelectedValue = orderAddPanel.getAddOrderSizeToleranceCurrencySelectedValue(type);
            break;
        default:
            ccySelectedValue = "error";
        }
        Assert.assertEquals(ccyValue, ccySelectedValue);
    }

    public List<String> getCcyPairQueryIDFService() throws Throwable {
        List<String> jsonList = new ArrayList<String>();
        jsonList.add("- Select A Currency -");
        String[] inputNames = { "TARGET", "PARAMS" };
        String[] inputs = { "CURRENCY_LIST_QUERY", "" };
        String params = "{[]}";
        inputs[1] = params;
        List<Map<String, Object>> resultList = BaseClientUtil.getInstance().sendIdfRequest("913130000", inputNames,
                inputs);
        if (resultList.get(0).get(Constants.IDF_OUTPUT_ERROR).toString().isEmpty()) {
            for (Map<String, Object> result : resultList) {
                String temp = result.get("JSON").toString().replace("\"", "");
                jsonList.add(temp);
            }
        } else {
            String error = resultList.get(0).get(Constants.IDF_OUTPUT_ERROR).toString().replace("\"", "");
            jsonList.add(error);
            logger.info(error);
        }
        return jsonList;
    }

}
