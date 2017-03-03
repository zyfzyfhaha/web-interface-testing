package com.ssc.ssgm.fx.refdata.model.ui.selenium;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;
import com.ssc.ssgm.fx.refdata.base.Constants;
import com.ssc.ssgm.fx.refdata.model.PricingServiceValidation;

public class PricingServiceValidationPage extends BaseWebDriver {

	protected static final Logger LOGGER = Logger
			.getLogger(PricingServiceValidationPage.class);

	@FindBy(id = "gwt-debug-Validation")
	private WebElement validation_feature;

	public void open_OMS_Validation_Page() {

		validation_feature.click();
		switchToFrame(Constants.VALIDATION_FRAME);

	}

	public void select_validation(String validation_desc) {

		if (StringUtils.isNotBlank(validation_desc)) {
			WebElement validation_dropdown = driver.findElement(By
					.cssSelector(".vc-validation-dropdown"));
			selectDropDownValue(validation_dropdown, validation_desc);
		}

	}

	public void select_pricing_service(String pricingService) {
		if (StringUtils.isNotBlank(pricingService)) {

			WebElement pricing_service_dropdown = driver.findElement(By
					.cssSelector(".vc-pricing-service-id-dropdown"));
			selectDropDownValue(pricing_service_dropdown, pricingService);
		}
	}

	public void click_search_button() {

		WebElement search_button = driver.findElement(By
				.id("gwt-debug-searchButton"));
		search_button.click();
		waitLoadingEnd();

	}

	public List<PricingServiceValidation> get_validation_from_UI() {

		// Get column number of each validation field
		Map<String, Integer> colMap = get_col_map();

		// Even Rows
		List<PricingServiceValidation> psvEvenList = new ArrayList<PricingServiceValidation>();
		List<WebElement> validationEvenRows = driver.findElements(By
				.cssSelector(".dataGridEvenRow "));

		psvEvenList = get_validation_from_UI(colMap, validationEvenRows);

		// Odd Rows
		List<PricingServiceValidation> psvOddList = new ArrayList<PricingServiceValidation>();
		List<WebElement> validationOddRows = driver.findElements(By
				.cssSelector(".dataGridOddRow"));

		psvOddList = get_validation_from_UI(colMap, validationOddRows);

		List<PricingServiceValidation> psvList = new ArrayList<PricingServiceValidation>();
		for (Integer i = 0; i < psvEvenList.size(); i++) {

			if (psvEvenList.size() > i) {
				psvList.add(psvEvenList.get(i));
			}

			if (psvOddList.size() > i) {
				psvList.add(psvOddList.get(i));
			}

		}

		return psvList;
	}

//	private Map<String, Integer> get_col_map() {
//		Map<String, Integer> colMap = new LinkedHashMap<String, Integer>();
//		// Get the column no
//		Integer prcingSrviceColNo = 0;
//		Integer validationColNo = 0;
//		Integer propertyColNo = 0;
//		Integer valueColNo = 0;
//		Integer updateByIdColNo = 0;
//		List<WebElement> headerCol = driver.findElements(By
//				.cssSelector(".dataGridHeader span"));
//		Integer index = 1;
//		for (WebElement h : headerCol) {
//			String colValue = h.getText().trim().toUpperCase();
//			switch (colValue) {
//			case "PRICING SERVICE":
//				prcingSrviceColNo = index;
//				colMap.put("PRICING SERVICE", prcingSrviceColNo);
//				break;
//			case "VALIDATION":
//				validationColNo = index;
//				colMap.put("VALIDATION", validationColNo);
//				break;
//			case "PROPERTY":
//				propertyColNo = index;
//				colMap.put("PROPERTY", propertyColNo);
//				break;
//			case "VALUE":
//				valueColNo = index;
//				colMap.put("VALUE", valueColNo);
//				break;
//			case "LAST MODIFY BY":
//				updateByIdColNo = index;
//				colMap.put("LAST MODIFY BY", updateByIdColNo);
//				break;
//			}
//
//			index++;
//
//		}
//		return colMap;
//	}

	private List<PricingServiceValidation> get_validation_from_UI(
			Map<String, Integer> map, List<WebElement> validationEvenRows) {

		List<PricingServiceValidation> psvList = new ArrayList<PricingServiceValidation>();
		if (validationEvenRows.size() > 0) {
			for (WebElement row : validationEvenRows) {
				PricingServiceValidation psv = new PricingServiceValidation();
				List<WebElement> validationColumns = row.findElements(By
						.cssSelector(".dataGridCell"));
				Integer colNo = 1;
				for (WebElement column : validationColumns) {

					WebElement columnDiv = column
							.findElement(By.tagName("div"));

					String columnValue = columnDiv.getText();

					if (colNo == map.get("VALIDATION")) {
						psv.setValidationDesc(columnValue);
					}
					if (colNo == map.get("PRICING SERVICE")) {
						psv.setPricingServiceId(columnValue);
					}
					if (colNo == map.get("PROPERTY")) {
						psv.setProperty(columnValue);
					}

					if (colNo == map.get("VALUE")) {
						WebElement columnInput = columnDiv.findElement(By
								.tagName("input"));
						String columnInputValue = columnInput
								.getAttribute("value");
						psv.setValue(columnInputValue);
					}

					if (colNo == map.get("LAST MODIFY BY")) {
						psv.setLastUpdatedById(columnValue);
					}

					colNo++;
				}
				psvList.add(psv);
			}
		}

		return psvList;
	}

	public void update_Pricing_Service_Validation(String validation_desc,
			String pricing_service, String value_to_update) {

		// Get column number of each validation field
		Map<String, Integer> colMap = get_col_map();

		// Search Even Rows and update
		List<WebElement> validationEvenRows = driver.findElements(By
				.cssSelector(".dataGridEvenRow "));

		update_validation_value(colMap, validationEvenRows, validation_desc,
				pricing_service, value_to_update);

		// Search Odd Rows and update
		List<WebElement> validationOddRows = driver.findElements(By
				.cssSelector(".dataGridOddRow"));

		update_validation_value(colMap, validationOddRows, validation_desc,
				pricing_service, value_to_update);

	}

	private void update_validation_value(Map<String, Integer> map,
			List<WebElement> validationRows, String validation_desc,
			String pricing_service, String value_to_update) {

		if (validationRows.size() > 0) {
			for (WebElement row : validationRows) {
				List<WebElement> validationColumns = row.findElements(By
						.cssSelector(".dataGridCell"));
				Integer colNo = 1;
				String validation_desc_ui = "";
				String pricing_service_ui = "";
				for (WebElement column : validationColumns) {

					WebElement columnDiv = column
							.findElement(By.tagName("div"));

					String columnValue = columnDiv.getText();

					if (colNo == map.get("VALIDATION")) {
						validation_desc_ui = columnValue;
					}
					if (colNo == map.get("PRICING SERVICE")) {
						pricing_service_ui = columnValue;
					}
					if (colNo == map.get("VALUE")) {
						if (validation_desc_ui.equals(validation_desc)
								&& pricing_service_ui.equals(pricing_service)) {
							WebElement columnInput = columnDiv.findElement(By
									.tagName("input"));
							columnInput.click();
							setInputValue(columnInput, value_to_update);
						}
					}

					colNo++;
				}
			}
		}
	}

	public void click_validation_save_button() {
		WebElement save_button = driver.findElement(By
				.id("gwt-debug-saveButton"));

		save_button.click();
		waitLoadingEnd();
	}
}
