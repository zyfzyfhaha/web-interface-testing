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

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;
import com.ssc.ssgm.fx.refdata.base.Constants;
import com.ssc.ssgm.fx.refdata.model.CurrencyPairGroup;
import com.ssc.ssgm.fx.refdata.model.InstrumentProfile;
import com.ssc.ssgm.fx.refdata.model.OrderTolerance;
import com.ssc.ssgm.fx.refdata.model.PricingServiceConfiguration;
import com.ssc.ssgm.fx.refdata.model.PricingServiceProperty;

public class InstrumentPage extends BaseWebDriver {

	protected static final Logger LOGGER = Logger
			.getLogger(InstrumentPage.class);

	@FindBy(id = "gwt-debug-Instrument")
	private WebElement instrument_Feature;
	@FindBy(id = "ip-pricing-service-dropdown")
	private WebElement pricingService;
	@FindBy(xpath = "//span[@id='gwt-debug-rateSource']//input")
	private WebElement input_rate_source_id;
	@FindBy(xpath = "//span[@id='gwt-debug-instrumentName']//input")
	private WebElement input_instrument_name_id;
	@FindBy(xpath = "//span[@id='gwt-debug-location']//input")
	private WebElement input_location_id;
	@FindBy(xpath = "//span[@id='input1']//input")
	private WebElement input_page;

	@FindBy(xpath = "//div[@id='gwt-debug-ccy1Input']/button")
	private WebElement ddl_ccy1;

	public void open_Instrument_Page() {
		instrument_Feature.click();
		switchToFrame(Constants.INSTRUMENT_FRAME);

	}

	public void input_Query_Instrument_Criteria(String instrumentName,
			String pricingServiceId, String rateSourceId, String location) {
		// input instrument name
		setInputValue(input_instrument_name_id, instrumentName);

		// select pricing service id
		selectDropDownValue(pricingService, pricingServiceId);

		// input rate source id
		setInputValue(input_rate_source_id, rateSourceId);

		// input location
		setInputValue(input_location_id, location);

	}

	public List<InstrumentProfile> getInstrumentFromUI() throws ParseException {
		// Get column number of each validation field
		Map<String, Integer> colMap = get_col_map();

		int onePageNum = getOnePageNumbers();
		int totalNum = getTotalQueryNumbers();
		List<InstrumentProfile> ipList = new ArrayList<InstrumentProfile>();
		List<InstrumentProfile> ipEvenList = new ArrayList<InstrumentProfile>();
		List<InstrumentProfile> ipOddList = new ArrayList<InstrumentProfile>();
		do {
			onePageNum = getOnePageNumbers();

			// Even Rows
			List<InstrumentProfile> pscEvenList = new ArrayList<InstrumentProfile>();
			List<WebElement> configEvenRows = driver.findElements(By
					.cssSelector(".dataGridEvenRow"));

			pscEvenList = get_Configuration_from_UI(colMap, configEvenRows);
			for (int i = 0; i < pscEvenList.size(); i++) {
				ipEvenList.add(pscEvenList.get(i));
			}

			// Odd Rows
			List<InstrumentProfile> pscOddList = new ArrayList<InstrumentProfile>();
			List<WebElement> configOddRows = driver.findElements(By
					.cssSelector(".dataGridOddRow"));

			pscOddList = get_Configuration_from_UI(colMap, configOddRows);
			for (int i = 0; i < pscOddList.size(); i++) {
				ipOddList.add(pscOddList.get(i));
			}

			if (onePageNum != totalNum) {
				gotoNextQueryPage();
				int rowActual = configEvenRows.size() + configOddRows.size();
				Assert.assertEquals("25 Rows Expected in the page: ", 25,
						rowActual);
			}

			if (onePageNum == totalNum) {
				int rowExpected = totalNum % 25;
				if (rowExpected == 0) {
					rowExpected = 25;
				}
				int rowActual = configEvenRows.size() + configOddRows.size();
				Assert.assertEquals("Rows Expected in last page: ",
						rowExpected, rowActual);
			}
		} while (onePageNum < totalNum);

		for (Integer i = 0; i < ipEvenList.size(); i++) {

			if (ipEvenList.size() > i) {
				ipList.add(ipEvenList.get(i));
			}

			if (ipOddList.size() > i) {
				ipList.add(ipOddList.get(i));
			}

		}

		return ipList;

	}

	private List<InstrumentProfile> get_Configuration_from_UI(
			Map<String, Integer> colMap, List<WebElement> configEvenRows)
			throws ParseException {

		List<InstrumentProfile> ipList = new ArrayList<InstrumentProfile>();
		if (configEvenRows.size() > 0) {
			for (WebElement row : configEvenRows) {
				InstrumentProfile ip = new InstrumentProfile();

				List<WebElement> configColumns = row.findElements(By
						.cssSelector(".dataGridCell"));

				for (Integer colNo = 0; colNo < configColumns.size(); colNo++) {

					WebElement columnDiv = configColumns.get(colNo)
							.findElement(By.tagName("div"));
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript(
							"arguments[0].focus(); arguments[0].blur(); return true",
							columnDiv);

					String columnValue = columnDiv.getText();

					if (colNo == colMap.get("INSTRUMENT")) {
						ip.setInstrumentName(columnValue);

					}
					if (colNo == colMap.get("PRICING SERVICE")) {
						ip.setPricingServiceId(columnValue);
					}

					if (colNo == colMap.get("PRICING SERVICE NAME")) {
						ip.setPricingServiceId(columnValue);
					}

					if (colNo == colMap.get("RATE SOURCE")) {
						ip.setRateSource(columnValue);
					}

					if (colNo == colMap.get("LOCATION")) {
						ip.setPhyLocId(columnValue);
					}

					if (colNo == colMap.get("LOCATION TIME(HHMM)")) {
						ip.setLocationTime(Integer.valueOf(columnValue));
					}

					if (colNo == colMap.get("LAST MODIFY BY")) {
						ip.setUpdatedBy(columnValue);
					}

					if (colNo == colMap.get("LAST MODIFY TIME")) {
						columnValue = columnValue.trim().substring(0, 19);
						DateFormat format = new SimpleDateFormat(
								"MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
						Date date = format.parse(columnValue);
						LOGGER.info("Date from UI: " + date + " long value "
								+ date.getTime());
						ip.setUpdatedDatetime(Long.toString(date.getTime()));
					}
				}

				ipList.add(ip);
			}
		}

		return ipList;
	}

	public void navigateToSpecificPage(String page) {
		int numPerPage = 25;
		int totalNum = getTotalQueryNumbers();
		int totalPageNum = getPageNum(totalNum, numPerPage);
		int pageNum = 0;
		switch (page) {
		case "0":
			pageNum = 0;
			break;
		case "1":
			pageNum = 1;
			break;
		case "MIDDLE":
			pageNum = totalPageNum / 2;
			break;
		case "LAST SECOND":
			pageNum = totalPageNum - 1;
			break;
		case "LAST":
			pageNum = totalPageNum;
			break;

		}
		setInputValue(input_page, Integer.toString(pageNum));
	}

	private int getPageNum(int totalNum, int numPerPage) {
		return totalNum % numPerPage > 0 ? totalNum / numPerPage + 1 : totalNum
				/ numPerPage;

	}

	public int getCurrentPageNum() {
		return Integer.valueOf(input_page.getAttribute("value"));
	}

	public List<InstrumentProfile> getInstrumentOfOnePageFromUI(int pageNum)
			throws ParseException {
		// Get column number of each validation field
		Map<String, Integer> colMap = get_col_map();
		List<InstrumentProfile> ipUI = new ArrayList<InstrumentProfile>();
		// Even Rows
		List<InstrumentProfile> pscEvenList = new ArrayList<InstrumentProfile>();
		List<WebElement> configEvenRows = driver.findElements(By
				.cssSelector(".dataGridEvenRow"));

		pscEvenList = get_Configuration_from_UI(colMap, configEvenRows);

		// Odd Rows
		List<InstrumentProfile> pscOddList = new ArrayList<InstrumentProfile>();
		List<WebElement> configOddRows = driver.findElements(By
				.cssSelector(".dataGridOddRow"));

		pscOddList = get_Configuration_from_UI(colMap, configOddRows);

		int index = pageNum % 2 > 0 ? pscEvenList.size() : pscOddList.size();

		for (Integer i = 0; i < index; i++) {
			if (pageNum % 2 > 0) {
				if (pscEvenList.size() > i) {
					ipUI.add(pscEvenList.get(i));
				}
				if (pscOddList.size() > i) {
					ipUI.add(pscOddList.get(i));
				}

			} else {

				if (pscOddList.size() > i) {
					ipUI.add(pscOddList.get(i));
				}
				if (pscEvenList.size() > i) {
					ipUI.add(pscEvenList.get(i));
				}

			}
		}

		return ipUI;
	}

	public void sortInstrument(String sortBy, String order) {
		// Get the column no
		List<WebElement> headerCol = driver.findElements(By
				.cssSelector(".dataGridHeader span"));

		for (Integer index = 0; index < headerCol.size(); index++) {

			String colValue = headerCol.get(index).getText().trim()
					.toUpperCase();
			String targetHeader = null;
			switch (sortBy) {
			// | PRICING SERVICE | ASC |
			// # | RATE SOURCE | DESC |
			// # | LOCATION | ASC |
			// # | INSTRUMENT NAME | DESC |
			case "PRICING SERVICE":
				targetHeader = "PRICING SERVICE";
				break;
			case "RATE SOURCE":
				targetHeader = "RATE SOURCE";
				break;
			case "LOCATION":
				targetHeader = "LOCATION";
				break;
			case "INSTRUMENT NAME":
				targetHeader = "INSTRUMENT";
				break;
			}
			if (targetHeader.equals(colValue)) {
				if (order.equals("ASC")) {
					headerCol.get(index).click();
					index = headerCol.size();
				}
				if (order.equals("DESC")) {
					headerCol.get(index).click();
					headerCol.get(index).click();
					index = headerCol.size();
				}
			}

		}
	}

	public void click_Detail_Button() {

		// Get column number of each validation field
		Map<String, Integer> colMap = get_col_map();
		//

		List<WebElement> cells = driver.findElements(By
				.cssSelector(".dataGridCell"));

		for (Integer colNo = 0; colNo < cells.size(); colNo++) {

			WebElement columnDiv = cells.get(colNo).findElement(
					By.tagName("div"));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].focus(); arguments[0].blur(); return true",
					columnDiv);

			String columnValue = columnDiv.getText();

			if (colNo == colMap.get("DETAILS")) {
				columnDiv.click();
				colNo = cells.size();
			}

		}
	}

	public InstrumentProfile getInstrumentFromDetailPanel() {

		String instrument = driver.findElement(
				By.id("ip_editor_instrumentName")).getText();
		String instrumentName = instrument.substring(
				instrument.indexOf(":") + 1, instrument.length()).trim();

		String pricingService = driver.findElement(
				By.id("ip_editor_pricingServiceId")).getText();
		String pricingServiceId = pricingService.substring(
				pricingService.indexOf(":") + 1, pricingService.length())
				.trim();

		String rateSource = driver.findElement(By.id("ip_editor_rateSource"))
				.getText();
		String rateSourceId = rateSource.substring(rateSource.indexOf(":") + 1,
				rateSource.length()).trim();

		String location = driver.findElement(By.id("ip_editor_phyLocId"))
				.getText();
		String phyLocId = location.substring(location.indexOf(":") + 1,
				location.length()).trim();

		String locationTime = driver.findElement(
				By.id("ip_editor_locationTime")).getText();
		String locTimeHHMM = locationTime.substring(
				locationTime.indexOf(":") + 1, locationTime.length()).trim();
		int locationTimeHHMM = Integer.valueOf(locTimeHHMM);

		InstrumentProfile instrumentProfile = new InstrumentProfile();
		instrumentProfile.setInstrumentName(instrumentName);
		instrumentProfile.setPricingServiceId(pricingServiceId);
		instrumentProfile.setRateSource(rateSourceId);
		instrumentProfile.setPhyLocId(phyLocId);
		instrumentProfile.setLocationTime(locationTimeHHMM);
		return instrumentProfile;
	}

	public List<CurrencyPairGroup> getCcyPairList() {
		List<WebElement> ccyPairTables = driver.findElements(By
				.cssSelector(".ip-detail-table"));
		List<CurrencyPairGroup> cpList = new ArrayList<CurrencyPairGroup>();
		for (WebElement ccyPair : ccyPairTables) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].focus(); arguments[0].blur(); return true",
					ccyPair);

			CurrencyPairGroup cp = new CurrencyPairGroup();
			// Get ccy1
			WebElement ccy1 = ccyPair.findElement(By
					.cssSelector(".instrument-editor-field[name=ccy1]"));
			js.executeScript(
					"arguments[0].focus(); arguments[0].blur(); return true",
					ccy1);
			cp.setCcy1(ccy1.getText());
			
			// Get ccy2
			WebElement ccy2 = ccyPair.findElement(By
					.cssSelector(".instrument-editor-field[name=ccy2]"));
			js.executeScript(
					"arguments[0].focus(); arguments[0].blur(); return true",
					ccy2);
			cp.setCcy2(ccy2.getText());

			cpList.add(cp);

		}
		return cpList;
	}

}
