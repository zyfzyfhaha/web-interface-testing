package com.ssc.ssgm.fx.refdata.base.extension;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.ssc.ssgm.fx.refdata.base.BaseTestUtil;
import com.ssc.ssgm.fx.refdata.base.SeleniumUtil;

public class SeleniumOWT2BaseUtil extends SeleniumUtil {

	@Override
	protected void loginLocal(String userName) {
		WebElement inputName = driver.findElement(By
				.xpath("//input[@name='userId']"));
		inputName.clear();
		inputName.sendKeys(userName);
		WebElement displayName = driver.findElement(By
				.xpath("//input[@name='userName']"));
		displayName.clear();
		displayName.sendKeys("Test");
		WebElement email = driver.findElement(By
				.xpath("//input[@name='userEmail']"));
		email.clear();
		email.sendKeys("test@1.com");
		WebElement loginBTN = driver.findElement(By
				.xpath("//input[@value='Login']"));
		loginBTN.click();
	}

	@Override
	protected boolean checkLogined() {
		//not implemented
    	return false;
	}

	public void waitLoadingEnd() {
		try {
			waitingElement(By.xpath("//div[@class='loadingIndicator']"), 3000);
			waitingElementDisappear(By
					.xpath("//div[@class='loadingIndicator']"));
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.error(e, e);
		}
	}

	public boolean waitSearchLoadingEnd() {
		boolean status = false;
		try {
			waitingElement(By.xpath("//div[@class='cdtDataGridLoadingImage']"),
					3000);
			status = waitingElementDisappear(By
					.xpath("//div[@class='cdtDataGridLoadingImage']"));
			Thread.sleep(500);
		} catch (InterruptedException e) {
			logger.error(e, e);
		}
		return status;
	}

	public void selectDropDownValue(WebElement dropdownlist, String selectValue) {
		waitElementEnable(dropdownlist);
		if (selectValue == null
				|| dropdownlist.findElement(By.tagName("span")).getText()
						.contains(selectValue) || selectValue.equals("@Null")) {
			return;
		}
		dropdownlist.click();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			logger.error(e, e);
		}
		if (!isElementExist(By.xpath("//div[@class='cdtListView']//a"))) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].focus(); arguments[0].click();  return true",
					dropdownlist);
			// dropdownlist.click();
		}
		List<WebElement> ddlElements = driver.findElements(By
				.xpath("//div[@class='cdtListView']//a"));
		logger.info("Selenium: Read dropdownlist find total elements count: "
				+ ddlElements.size());
		for (WebElement element : ddlElements) {
			if (element.getText().contains(selectValue)) {
				element.click();
				return;
			}
		}
		throw new NoSuchElementException(
				"Can't find target value in dropdownlist: " + selectValue);
	}

	public String getDropDownValue(WebElement dropdownlist) {
		waitElementEnable(dropdownlist);
		WebElement ddlElement = dropdownlist.findElement(By.tagName("span"));
		return ddlElement.getAttribute("innerText");
	}

	

	protected void setPageInputs(Map<String, String> values,
			Map<String, WebElement> inputMap) {
		for (Entry<String, String> entry : values.entrySet()) {
			String key = entry.getKey();
			WebElement element = inputMap.get(key);
			if (element != null && !"@Null".equals(entry.getValue())) {
				if (element.getTagName().equalsIgnoreCase("button")) {
					selectDropDownValue(element, entry.getValue());
				} else if (element.getAttribute("type") != null
						&& element.getAttribute("type").equalsIgnoreCase(
								"checkbox")) {
					setCheckBoxValue(element, entry.getValue());
				} else if (element.getAttribute("type") != null
						&& element.getAttribute("type").equalsIgnoreCase(
								"select-one")) {
					selectListBoxValue(element, entry.getValue());
				} else {
					setInputValue(element, entry.getValue());
				}
			}
		}
	}

	public void openAddPanel() {
		if (!waitingElement(By.xpath("//button[@class='cdtAddItem']"))) {
			throw new NoSuchElementException("Add button not found");
		}
		driver.findElement(By.xpath("//button[@class='cdtAddItem']")).click();
	}

	public List<String> getDropDownValueList() {
		List<String> listValue = new ArrayList<String>();
		if (isElementExist(By
				.xpath("//div[starts-with(@class,'cdtListView')]//a"))) {
			List<WebElement> items = driver
					.findElements(By
							.xpath("//div[starts-with(@class,'cdtListView')]/div/div/a"));
			for (WebElement item : items) {
				listValue.add(item.getText());
			}
		}
		return listValue;
	}

	public List<String> getDropDownValueList(WebElement dropdownlist) {
		waitElementEnable(dropdownlist);
		dropdownlist.click();
		if (!isElementExist(By.xpath("//div[@class='cdtListView']//a"))) {
			dropdownlist.click();
		}
		return getDropDownValueList();
	}

	public String getErrorMsg() {
		if (!waitingElement(By.xpath("//div[@class='noDataFound']"))) {
			throw new NoSuchElementException("Element not found");
		}
		return driver.findElement(By.xpath("//div[@class='noDataFound']"))
				.getText();
	}

	
	public void setDateCalendar(WebElement calendarbutton, String date)
			throws ParseException {
		calendarbutton.click();
		if (!waitingElement(By.xpath("//div[@class='cdtDropDownPanel']"))) {
			throw new NoSuchElementException("cdtDropDownPanel not found");
		}
		WebElement previousButton = driver
				.findElement(By
						.xpath("//div[@class='datePickerPreviousButton datePickerPreviousButton-up']"));
		WebElement nextButton = driver
				.findElement(By
						.xpath("//div[@class='datePickerNextButton datePickerNextButton-up']"));
		WebElement MMYYYY = driver.findElement(By
				.xpath("//td[@class='datePickerMonth']"));
		String panelyear = getPanelYear(MMYYYY);
		String panelmonth = getPanelMonth(MMYYYY);
		String[] actdate = date.split("/");
		String actyear = actdate[2];
		String actmonth = actdate[0];
		String actday = actdate[1];

		while (Integer.parseInt(actyear) > Integer.parseInt(panelyear)
				|| Integer.parseInt(actmonth) > Integer.parseInt(panelmonth)) {
			nextButton.click();
			WebElement nextMMYYYY = driver.findElement(By
					.xpath("//td[@class='datePickerMonth']"));
			panelyear = getPanelYear(nextMMYYYY);
			panelmonth = getPanelMonth(nextMMYYYY);
		}
		while (Integer.parseInt(actyear) < Integer.parseInt(panelyear)
				|| Integer.parseInt(actmonth) < Integer.parseInt(panelmonth)) {
			previousButton.click();
			WebElement previousMMYYYY = driver.findElement(By
					.xpath("//td[@class='datePickerMonth']"));
			panelyear = getPanelYear(previousMMYYYY);
			panelmonth = getPanelMonth(previousMMYYYY);
		}
		if (Integer.parseInt(actyear) == Integer.parseInt(panelyear)
				&& Integer.parseInt(actmonth) == Integer.parseInt(panelmonth)) {
			List<WebElement> dayButton = getPickDaysWebElements();
			for (WebElement day : dayButton) {
				String daytext = day.getText();
				if (Integer.parseInt(daytext) == Integer.parseInt(actday)) {
					day.click();
					break;
				}
			}
		}
	}

	private List<WebElement> getPickDaysWebElements() {
		List<WebElement> element = driver
				.findElements(By
						.xpath("//table[@class='datePickerDays']//tr/td//div[@tabIndex='0']"));
		return element;
	}

	private String getPanelYear(WebElement MMYY) {
		String date = MMYY.getText();
		String panelyear;
		String[] paneldate = date.split("");
		panelyear = paneldate[5] + paneldate[6] + paneldate[7] + paneldate[8];
		return panelyear;
	}

	private String getPanelMonth(WebElement MMYY) {
		String date = MMYY.getText();
		String month;
		String[] paneldate = date.split("");
		String panelmonth = paneldate[1] + paneldate[2] + paneldate[3];
		month = BaseTestUtil.getPanelMonthValue(panelmonth);
		return month;
	}

	public void setCheckBoxValue(WebElement element, String value) {
		String isChecked;
		if (element.getAttribute("CHECKED") != null) {
			isChecked = "Y";
		} else {
			isChecked = "N";
		}

		if (!value.equals(isChecked)) {
			element.click();
		}
	}

	public List<String> getUIcolumnFields() {
		List<WebElement> referenceData = new ArrayList<WebElement>();
		List<String> uiFields = new ArrayList<String>();
		if (waitingElement(By
				.xpath("//div[@class='dataGridWidget']/div[1]//table"))) {
			referenceData = driver
					.findElements(By
							.xpath("//div[@class='dataGridWidget']/div[1]//table//th/span"));
			for (int i = 0; i < referenceData.size(); i++) {
				uiFields.add(referenceData.get(i).getAttribute("innerText"));
			}
		}
		return uiFields;
	}

}
