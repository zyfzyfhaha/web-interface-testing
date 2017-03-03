package com.ssc.ssgm.fx.refdata;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;

/**
 * cucumber auto test suit.
 * 
 * @author e532258
 */
@RunWith(Cucumber.class)
@CucumberOptions(

tags = { "@QA" }, features = {

"src/test/resources/storys"

}, monochrome = true, format = { "pretty", "html:target/cucumber",
		"json:target/cucumber/test-report.json",
		"junit:target/cucumber/test-report.xml" })
public class RunCucumberTest {
	private static Date startTime;
	public static int failedTotal = 0;
	public static int successTotal = 0;

	private static Logger logger = Logger.getLogger(RunCucumberTest.class);

	@BeforeClass
	public static void init() throws Exception {
		startTime = new Date();
		System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
	}

	@AfterClass
	public static void tearDown() throws Exception {
		Date endTime = new Date();

		logger.info("Duration: " + (endTime.getTime() - startTime.getTime()));
		logger.info("successTotal: " + successTotal);
		logger.info("failedTotal: " + failedTotal);
		logger.info("total: " + (successTotal + failedTotal));
		BaseWebDriver.quit();
	}
}
