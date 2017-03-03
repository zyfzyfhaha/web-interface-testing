package com.ssc.ssgm.fx.refdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.support.PageFactory;

import com.ssc.ssgm.fx.refdata.base.Config;
import com.ssc.ssgm.fx.refdata.base.SpringContextHolder;
import com.ssc.ssgm.fx.refdata.dao.PricingServiceValidationDao;
import com.ssc.ssgm.fx.refdata.model.PricingServiceValidation;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.PricingServiceValidationPage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PricingServiceValidationStepDefs {
	private PricingServiceValidationPage validation = PageFactory.initElements(
			PricingServiceValidationPage.getWebDriver(),
			PricingServiceValidationPage.class);
	private PricingServiceValidationDao psvDao = (PricingServiceValidationDao) SpringContextHolder
			.getBean(PricingServiceValidationDao.class);
	protected static final Logger LOGGER = Logger
			.getLogger(PricingServiceValidationStepDefs.class);

	private String validation_criteria;
	private String pricing_service_criteria;
	private List<PricingServiceValidation> psvDBExpected;
	private List<PricingServiceValidation> psvUIActual;

	@Given("^User navigates to OMS VALIDATION VALIDATION Page$")
	public void user_navigates_to_OMS_VALIDATION_VALIDATION_Page()
			throws Throwable {
		validation.open_OMS_Validation_Page();
	}

	@Given("^User select select a Pricing service and a Validation$")
	public void user_select_Validation_select_Pricing_Service(
			List<Map<String, String>> args) throws Throwable {
		pricing_service_criteria = args.get(0).get("PRICING SERVICE");
		validation_criteria = args.get(0).get("VALIDATION");

		// Select Pricing Service
		validation.select_pricing_service(pricing_service_criteria);
		// Select Validation
		validation.select_validation(validation_criteria);

	}

	@When("^User click Search button$")
	public void click_Search_button() throws Throwable {
		validation.click_search_button();
	}

	@Then("^All matched pricing service validation are displayed and order by Pricing Service and Validation$")
	public void all_matched_pricing_service_validation_are_displayed(
			List<Map<String, String>> args) throws Throwable {

		// Get Expected Pricing Service Validation from DB
		psvDBExpected = psvDao.get_validation_by_pricing_service_and_desc(
				pricing_service_criteria, validation_criteria);

		// Get Actual Pricing Service Validation from UI
		psvUIActual = validation.get_validation_from_UI();

		// set last updated date time as null, which will be tested by manual
		for (PricingServiceValidation v : psvUIActual) {
			v.setLastUpdatedDttm(null);
		}

		LOGGER.info("psvDBExpected: " + psvDBExpected.toString());
		LOGGER.info("psvUIActual: " + psvUIActual.toString());
		Assert.assertEquals("Pricing Service Validaiton", psvDBExpected,
				psvUIActual);
	}

	@When("^User update value$")
	public void user_update_value(List<Map<String, String>> args)
			throws Throwable {

		// Get the UI result before updating
		psvDBExpected = validation.get_validation_from_UI();
		;

		// Get the value to be updated
		String value = args.get(0).get("VALUE");
		if (StringUtils.isNotBlank(value)) {
			List<String> valueList = new ArrayList<String>(Arrays.asList(value
					.split(",")));

			for (String v : valueList) {
				v = v.trim();
				String validation_desc = v.substring(0, v.indexOf("-")).trim();
				String v1 = v.substring(validation_desc.length() + 1).trim();
				String pricing_service = v1.substring(0, v1.indexOf("-"))
						.trim();
				String value_to_update = v1.substring(
						pricing_service.length() + 1).trim();

				// update pricing service validation in UI
				validation.update_Pricing_Service_Validation(validation_desc,
						pricing_service, value_to_update);

				// Update Expected Result after update
				for (PricingServiceValidation psv : psvDBExpected) {
					if (psv.getValidationDesc().equals(validation_desc)
							&& psv.getPricingServiceId()
									.equals(pricing_service)) {
						psv.setValue(value_to_update.toUpperCase().trim());
						Config config = new Config();
						psv.setLastUpdatedById(config.getProperty("UifuserId")
								.trim().toUpperCase());
					}

				}

			}

		}

	}

	@Then("^Updated Pricing Service Validation are displayed with updated value and other Pricing Service validation are not updated$")
	public void updated_Pricing_Service_Validation_are_displayed_with_updated_value(
			List<Map<String, String>> args) throws Throwable {
		// Get Actual Pricing Service Validation from UI
		psvUIActual = validation.get_validation_from_UI();

		LOGGER.info("psvDBExpected" + psvDBExpected.toString());
		LOGGER.info("psvUIActual " + psvUIActual.toString());

		Assert.assertEquals("Pricing Service Validaiton Update", psvDBExpected,
				psvUIActual);
	}

	@When("^User click Validation Save button$")
	public void user_click_validation_save_button() throws Throwable {

		validation.click_validation_save_button();

	}

}
