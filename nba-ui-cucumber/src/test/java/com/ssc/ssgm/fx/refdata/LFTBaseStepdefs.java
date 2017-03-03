package com.ssc.ssgm.fx.refdata;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;

import com.ssc.ssgm.fx.refdata.base.BaseTestUtil;
import com.ssc.ssgm.fx.refdata.model.ui.leanft.RefUIBaseUtil;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LFTBaseStepdefs {

	private static final Logger logger = Logger.getLogger(LFTBaseStepdefs.class);
    private RefUIBaseUtil lftUtil = new RefUIBaseUtil();
    
    @Given("^user login to Reference UI home page$")
    public void openHomePage(){
        lftUtil.initDriver();
        boolean result = lftUtil.login();
        Assert.assertTrue("Login Failed!", result);
        logger.info("LFT Logined.");
    }
    
    @And("^user click the first row record$")
    public void clickFirstRecord(){
    	lftUtil.clickRowDetail(1);
    }
    
    @And("^user click the first row record for edit$")
    public void clickFirstRecordAndEdit(){
        lftUtil.clickRowDetail(1);
        lftUtil.ActiveEdit();
    }
    
    @When("^open add panel$")
    public void openAddPanelByLFT() throws Exception {
    	lftUtil.openAddPanel();
    }
    
    @Then("^popup the error message$")
    public void verifyErrorMsg(Map<String, String> values) throws Throwable {
    	String expectValue = values.get("Error");
        String actualValue = lftUtil.getErrorMsg();
        BaseTestUtil.assertEqual("error message", expectValue, actualValue);
    }
}
