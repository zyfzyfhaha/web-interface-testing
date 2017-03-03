package com.ssc.ssgm.fx.refdata.model.ui.selenium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;

public class InstrumentAddPanel extends BaseWebDriver {

	@FindBy(xpath = "//span[@id='InstrumentAdd-instrument-name']//input")
	private WebElement input_instrument_name;

	@FindBy(id = "InstrumentAdd-PS")
	private WebElement pricingService;

	@FindBy(xpath = "//span[@id='InstrumentAdd-location']//input")
	private WebElement input_location;

	@FindBy(xpath = "//span[@id='InstrumentAdd-rate-source']//input")
	private WebElement input_rate_source;

	@FindBy(xpath = "//span[@id='InstrumentAdd-location-time']//input")
	private WebElement input_location_time;

	public boolean isPanelDisplayed() {
		// Save button in Instrument Add Panel
		By locator = By.xpath("//button[@class='cdtBlueBarSubmitButton']");
		return isElementExist(locator);
	}

	public void addInstrument(String instrumentName, String pricingServiceId,
			String phyLocId, String rateSourceId, String locationTime,
			String ccyPairList) {
		// input instrument name
		if (StringUtils.isNotBlank(instrumentName)) {
			setInputValue(input_instrument_name, instrumentName);
		}

		// select pricing service
		if (StringUtils.isNotBlank(pricingServiceId)) {
			selectDropDownValue(pricingService, pricingServiceId);
		}

		// input physical location id
		if (StringUtils.isNotBlank(phyLocId)) {
			setInputValue(input_location, phyLocId);
		}

		// input rate source id
		if (StringUtils.isNotBlank(rateSourceId)) {
			setInputValue(input_rate_source, rateSourceId);
		}

		// input location time
		if (StringUtils.isNotBlank(locationTime)) {
			setInputValue(input_location_time, locationTime);
		}

		// set currency pairs
		if (StringUtils.isNotBlank(ccyPairList)) {
			List<String> ccyPairs = new ArrayList<>(Arrays.asList(ccyPairList
					.split(",")));

			for (int i = 0; i < ccyPairs.size(); i++) {
				String ccy1 = ccyPairs.get(i).trim().substring(0, 3);
				String ccy2 = ccyPairs.get(i).trim().substring(3, 6);

				// Click add currency pair button
				if (i > 0) {
					WebElement addButton = driver.findElement(By
							.id("InstrumentAdd-CCYPAIR-ADD-" + (i - 1)));
					addButton.click();
				}
				WebElement ccy1Dropdown = driver.findElement(By
						.id("InstrumentAdd-CCY1-" + i));
				WebElement ccy2Dropdown = driver.findElement(By
						.id("InstrumentAdd-CCY2-" + i));
				selectDropDownValue(ccy1Dropdown, ccy1);
				selectDropDownValue(ccy2Dropdown, ccy2);
			}
		}

	}

	public List<String> getPricingServiceValueList() {
		List<String> resultList = new ArrayList<String>();
		for (WebElement element : getPricingServiceList()) {
			resultList.add(element.getText());
		}

		// Remove the first record 'Select A Pricing Service'
		resultList.remove(resultList.get(0));
		return resultList;
	}

	public List<WebElement> getPricingServiceList() {
		if (!isElementExist(By.xpath("//div[@class='cdtListView']//a"))) {
			pricingService.click();
		}
		List<WebElement> pricingService = driver.findElements(By
				.xpath("//div[@class='cdtListView']//a"));
		return pricingService;
	}
}
