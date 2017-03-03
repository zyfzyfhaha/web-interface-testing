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
import com.ssc.ssgm.fx.refdata.dao.PricingServiceConfigurationDao;
import com.ssc.ssgm.fx.refdata.model.PricingServiceConfiguration;
import com.ssc.ssgm.fx.refdata.model.PricingServiceProperty;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.PricingServiceConfigurationPage;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class PricingServiceConfigurationStepDefs {
	private PricingServiceConfigurationPage pricingServicePage = PageFactory
			.initElements(PricingServiceConfigurationPage.getWebDriver(),
					PricingServiceConfigurationPage.class);
	private PricingServiceConfigurationDao pricingServiceDao = (PricingServiceConfigurationDao) SpringContextHolder
			.getBean(PricingServiceConfigurationDao.class);
	protected static final Logger LOGGER = Logger
			.getLogger(PricingServiceConfigurationStepDefs.class);

	private List<PricingServiceConfiguration> pscExpectedDB;
	private List<PricingServiceConfiguration> pscExpectedUI;
	private List<PricingServiceConfiguration> pscActualUI;

	@When("^User navigates to Prcing Service Page$")
	public void user_navigates_to_Prcing_Service_Page() throws Throwable {
		pricingServicePage.open_Pricing_Service_Page();
	}

	@Then("^All Pricing Service and the detailed configuration are displayed order by PRICING_SERVICE_ID and LAST MODIFY TIME is in user time zone$")
	public void all_Pricing_Service_and_the_detailed_configuration_are_displayed_order_by_PRICING_SERVICE_ID(
			List<Map<String, String>> args) throws Throwable {

		// Get pricing service configuration from DB
		// Get pricing service list from DB
		pscExpectedDB = getPricingServiceConfigurationFromDB();

		// Get pricing service configuration from UI

		pscActualUI = pricingServicePage.getPricingServiceConfigurationFromUI();

		LOGGER.info("Pricing Service Configuration Expected: "
				+ pscExpectedDB.toString());
		LOGGER.info("Pricing Service Configuration Actual: "
				+ pscActualUI.toString());

		Assert.assertEquals("Pricing Service Configuration", pscExpectedDB,
				pscActualUI);
	}

	private List<PricingServiceConfiguration> getPricingServiceConfigurationFromDB() {
		pscExpectedDB = pricingServiceDao.getPricingServiceFromDB();
		// Get pricing service property list from DB
		List<PricingServiceConfiguration> psp = pricingServiceDao
				.getPricingServiceConfigurationFromDB();

		for (PricingServiceConfiguration psc : pscExpectedDB) {
			for (PricingServiceConfiguration p : psp) {
				if (psc.getPricingServiceId().equals(p.getPricingServiceId())) {

					long updatedDttm = psc.getUpdatedDatetime();
					long updatedDttmProperty = p.getProperties().get(0)
							.getUpdatedDatetime();
					String updatedByProperty = p.getProperties().get(0)
							.getUpdatedBy();
					if (updatedDttm == 0L || updatedDttm < updatedDttmProperty) {
						psc.setUpdatedDatetime(updatedDttmProperty);
					}

					String updatedBy = psc.getUpdatedBy();
					if (updatedBy == null || updatedDttm < updatedDttmProperty) {
						psc.setUpdatedBy(updatedByProperty);
					}

					List<PricingServiceProperty> pspList = psc.getProperties();
					if (pspList == null) {
						pspList = new ArrayList<PricingServiceProperty>();
					}
					PricingServiceProperty property = new PricingServiceProperty();
					property.setPropertyName(p.getProperties().get(0)
							.getPropertyName());
					property.setPropertyValue(p.getProperties().get(0)
							.getPropertyValue());
					pspList.add(property);
					psc.setProperties(pspList);
				}

			}
		}
		return pscExpectedDB;
	}

	@When("^User update pricing service property value$")
	public void user_update_pricing_service_property_value(
			List<Map<String, String>> args) throws Throwable {

		// Get the UI result before updating
		pscExpectedUI = pricingServicePage
				.getPricingServiceConfigurationFromUI();

		// Get the value to be updated
		String hedgeBrokerCode = args.get(0).get("HEDGE_BROKER_CODE");
		String property = "HEDGE TRADE BROKER CODE";

		if (StringUtils.isNotBlank(hedgeBrokerCode)) {
			List<String> valueList = new ArrayList<String>(
					Arrays.asList(hedgeBrokerCode.split(",")));

			for (String v : valueList) {
				v = v.trim();
				String pricing_service = v.substring(0, v.indexOf("-")).trim();
				String value_to_update = v.substring(
						pricing_service.length() + 1).trim();

				// update pricing service validation in UI
				pricingServicePage.update_Pricing_Service_Configuration(
						pricing_service, property, value_to_update);

				// Get Configurations no need to update
				for (PricingServiceConfiguration p : pscExpectedUI) {
					if (StringUtils.isNotBlank(p.getPricingServiceId()) && p.getPricingServiceId().equals(pricing_service)) {
						p = setPricingServiceConfigurationAsNull(p);

					}
				}
			}
		}
	}

	private PricingServiceConfiguration setPricingServiceConfigurationAsNull(
			PricingServiceConfiguration p) {
		p.setFullName(null);
		p.setPricingServiceId(null);
		p.setProperties(null);
		p.setShortName(null);
		p.setUpdatedBy(null);
		p.setUpdatedDatetime(0L);
		return p;
	}

	@When("^User click pricing service configuration Save button$")
	public void user_click_pricing_service_configuration_Save_button()
			throws Throwable {
		pricingServicePage.click_Pricing_Service_Configuration_save_button();

	}

	@Then("^Updated Pricing Service Configuration are displayed with updated value with LAST MODIFY TIME in user time zone and other Pricing Service Configuration are not updated$")
	public void updated_Pricing_Service_Configuration_are_displayed_with_updated_value_and_other_Pricing_Service_Configuration_are_not_updated(
			List<Map<String, String>> args) throws Throwable {

		// Get Actual Pricing Service Validation from UI
		pscActualUI = pricingServicePage.getPricingServiceConfigurationFromUI();

		pscExpectedDB = getPricingServiceConfigurationFromDB();
		LOGGER.info("Pricing Service Configuration Expected: "
				+ pscExpectedDB.toString());
		LOGGER.info("Pricing Service Configuration Actual:  "
				+ pscActualUI.toString());
		Assert.assertEquals("Pricing Service Configuration after Update",
				pscExpectedDB, pscActualUI);

		// Get the UI result of pricing service configuration not updated
		String hedgeBrokerCode = args.get(0).get("HEDGE_BROKER_CODE");

		if (StringUtils.isNotBlank(hedgeBrokerCode)) {
			List<String> valueList = new ArrayList<String>(
					Arrays.asList(hedgeBrokerCode.split(",")));

			for (String v : valueList) {
				v = v.trim();
				String pricing_service = v.substring(0, v.indexOf("-")).trim();
				// Get Configurations no need to update
				for (PricingServiceConfiguration p : pscActualUI) {
					if (StringUtils.isNotBlank(p.getPricingServiceId()) && p.getPricingServiceId().equals(pricing_service)) {
						p = setPricingServiceConfigurationAsNull(p);
					}
				}
			}

			LOGGER.info("Pricing Service Configuration Expected: "
					+ pscExpectedUI.toString());
			LOGGER.info("Pricing Service Configuration Actual:  "
					+ pscActualUI.toString());
			Assert.assertEquals("Pricing Service Configuration without Update",
					pscExpectedUI, pscActualUI);

		}
	}
}
