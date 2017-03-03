package com.ssc.ssgm.fx.refdata.model.ui.selenium;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;
import com.ssc.ssgm.fx.refdata.base.Constants;
import com.ssc.ssgm.fx.refdata.model.PricingServiceConfiguration;
import com.ssc.ssgm.fx.refdata.model.PricingServiceProperty;

public class PricingServiceConfigurationPage extends BaseWebDriver {

	protected static final Logger LOGGER = Logger
			.getLogger(PricingServiceConfigurationPage.class);

	@FindBy(id = "gwt-debug-Pricing-Service")
	private WebElement pricing_Service_feature;

	public void open_Pricing_Service_Page() {

		pricing_Service_feature.click();
		switchToFrame(Constants.PRICING_SERVICE_FRAME);

	}

	public List<PricingServiceConfiguration> getPricingServiceConfigurationFromUI()
			throws ParseException {
		// Get column number of each validation field
		Map<String, Integer> colMap = get_col_map();

		// Even Rows
		List<PricingServiceConfiguration> pscEvenList = new ArrayList<PricingServiceConfiguration>();
		List<WebElement> configEvenRows = driver.findElements(By
				.cssSelector(".dataGridEvenRow"));

		pscEvenList = get_Configuration_from_UI(colMap, configEvenRows);

		// Odd Rows
		List<PricingServiceConfiguration> pscOddList = new ArrayList<PricingServiceConfiguration>();
		List<WebElement> configOddRows = driver.findElements(By
				.cssSelector(".dataGridOddRow"));

		pscOddList = get_Configuration_from_UI(colMap, configOddRows);

		List<PricingServiceConfiguration> pscList = new ArrayList<PricingServiceConfiguration>();
		for (Integer i = 0; i < pscEvenList.size(); i++) {

			if (pscEvenList.size() > i) {
				pscList.add(pscEvenList.get(i));
			}

			if (pscOddList.size() > i) {
				pscList.add(pscOddList.get(i));
			}

		}

		return pscList;
	}

	private List<PricingServiceConfiguration> get_Configuration_from_UI(
			Map<String, Integer> colMap, List<WebElement> configEvenRows)
			throws ParseException {

		List<PricingServiceConfiguration> pscList = new ArrayList<PricingServiceConfiguration>();
		if (configEvenRows.size() > 0) {
			for (WebElement row : configEvenRows) {
				PricingServiceConfiguration psc = new PricingServiceConfiguration();

				List<PricingServiceProperty> psp = new ArrayList<PricingServiceProperty>();

				List<WebElement> configColumns = row.findElements(By
						.cssSelector(".dataGridCell"));

				for (Integer colNo = 0; colNo < configColumns.size(); colNo++) {

					WebElement columnDiv = configColumns.get(colNo)
							.findElement(By.tagName("div"));

					String columnValue = columnDiv.getText();

					if (colNo == colMap.get("PRICING SERVICE")) {
						psc.setPricingServiceId(columnValue);
					}

					if (colNo == colMap.get("PRICING SERVICE NAME")) {
						psc.setFullName(columnValue);
					}

					if (colNo == colMap.get("HEDGE TRADE BROKER CODE")) {

						WebElement columnInput = columnDiv.findElement(By
								.tagName("input"));
						String columnInputValue = columnInput
								.getAttribute("value");

						PricingServiceProperty p = new PricingServiceProperty();
						p.setPropertyName("Hedge Trade Broker Code");
						if (StringUtils.isNotBlank(columnInputValue)) {
							p.setPropertyValue(columnInputValue);
						}

						psp.add(p);
						psc.setProperties(psp);
					}

					if (colNo == colMap.get("LAST MODIFY BY")) {
						psc.setUpdatedBy(columnValue);
					}

					if (colNo == colMap.get("LAST MODIFY TIME")) {
						columnValue = columnValue.trim().substring(0, 19);
						DateFormat format = new SimpleDateFormat(
								"MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
						Date date = format.parse(columnValue);
						LOGGER.info("Date from UI: " + date + " long value "
								+ date.getTime());
						psc.setUpdatedDatetime(date.getTime());
					}
				}

				pscList.add(psc);
			}
		}

		return pscList;
	}


	public void update_Pricing_Service_Configuration(String pricing_service,
			String property, String value_to_update) {

		// Get column number of each validation field
		Map<String, Integer> colMap = get_col_map();

		// Search Even Rows and update
		List<WebElement> configEvenRows = driver.findElements(By
				.cssSelector(".dataGridEvenRow "));

		update_pricing_service_config_value(colMap, configEvenRows,
				pricing_service, property, value_to_update);

		// Search Odd Rows and update
		List<WebElement> configOddRows = driver.findElements(By
				.cssSelector(".dataGridOddRow"));

		update_pricing_service_config_value(colMap, configOddRows,
				pricing_service, property, value_to_update);
	}

	private void update_pricing_service_config_value(
			Map<String, Integer> colMap, List<WebElement> configRows,
			String pricing_service, String property, String value_to_update) {
		if (configRows.size() > 0) {
			for (WebElement row : configRows) {
				List<WebElement> configColumns = row.findElements(By
						.cssSelector(".dataGridCell"));
				
				String pricing_service_ui = "";

				for (Integer colNo = 0; colNo < configColumns.size(); colNo++) {

					WebElement columnDiv = configColumns.get(colNo)
							.findElement(By.tagName("div"));

					String columnValue = columnDiv.getText();

					if (colNo == colMap.get("PRICING SERVICE")) {
						pricing_service_ui = columnValue;
					}
					if (colNo == colMap.get("HEDGE TRADE BROKER CODE")) {
						if (pricing_service_ui.equals(pricing_service)) {
							WebElement columnInput = columnDiv.findElement(By
									.tagName("input"));
							columnInput.click();
							setInputValue(columnInput, value_to_update);
						}
					}
				}

			}
		}
	}

	public void click_Pricing_Service_Configuration_save_button() {
		WebElement save_button = driver.findElement(By
				.id("gwt-debug-saveButton"));

		save_button.click();
		waitLoadingEnd();

	}
}
