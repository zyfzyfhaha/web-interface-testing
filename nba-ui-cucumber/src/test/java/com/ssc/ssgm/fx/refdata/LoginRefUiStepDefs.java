package com.ssc.ssgm.fx.refdata;

import org.junit.Assert;

import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;

import cucumber.api.java.en.Given;

public class LoginRefUiStepDefs {
	
	private BaseWebDriver driver = new BaseWebDriver();
	
	@Given("^User login to Reference Data Management$")
    public void init() {
		driver.init();
		boolean result = driver.login();
		Assert.assertTrue("Login Failed!", result);
	}

}
