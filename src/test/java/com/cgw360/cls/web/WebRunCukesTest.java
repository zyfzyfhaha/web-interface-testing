package com.cgw360.cls.web;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/**
 * cucumber auto test suit.
 * 
 * @author zyfff
 */
@RunWith(Cucumber.class)
@CucumberOptions(tags = {"@test"}, features = {
		
//		"src/test/resources/features"

		"src/test/resources/features/address information/getProvince.feature"
//		"src/test/resources/features/address information/getCityByProvince.feature"
//		"src/test/resources/features/address information/getCountyByCity.feature"

		}, monochrome = true, format = {
        "pretty", "html:target/cucumber",
        "json:target/cucumber/test-report.json",
        "junit:target/cucumber/test-report.xml" })

public class WebRunCukesTest {

	@BeforeClass
	public static void setUp() throws Exception {

	}

	@AfterClass
	public static void tearDown() throws Exception {

	}
	
}
