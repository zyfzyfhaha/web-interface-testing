package com.ssc.ssgm.fx.refdata.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import com.ssc.ssgm.fx.refdata.client.IClientService;
import com.ssc.faw.util.GenException;
import com.ssc.faw.util.PwMatrixUtil;

public class BaseWebDriver {

	protected static final Logger logger = Logger
			.getLogger(BaseWebDriver.class);
	protected static WebDriver driver;
	private static boolean isLogined = false;
	public static boolean isUARegion = false;
	private Config config = new Config();
	private String baseURL;
	protected IClientService client;

	@FindBy(className = "primaryHeader")
	protected WebElement tag_PageHeader;
	@FindBy(xpath = "//button[@class='cdtBlueBarClose']")
	protected WebElement btn_AddPanelClose;
	@FindBy(xpath = "//div[@class='gwt-HTML countText']")
	protected WebElement label_queryPageNumber;
	@FindBy(id = "gwt-debug-saveButton")
	protected WebElement btn_edit_save;

	@FindBy(id = "button2")
	protected WebElement btn_nextQuery;

	public static WebDriver getWebDriver() {
		if (driver == null) {
			driver = new InternetExplorerDriver();
		}
		return driver;
	}

	public String getCurrentUrl() {
		return driver.getCurrentUrl();
	}

	public void navigateToSpecificUrl(String url) {
		Navigation navigation = driver.navigate();
		navigation.to(url);
	}

	public void init() {
		getWebDriver();
		driver.manage().window().maximize();
		Navigation navigation = driver.navigate();
		// baseURL = config.getProperty("baseUrl");
		baseURL = config.getProperty("UifUrl");

		navigation.to(baseURL);
	}

	public boolean login() {
		if (!isLogined) {
			String username = config.getProperty("UifuserId");
			boolean useMatrix = Boolean.valueOf(config
					.getProperty("UifuseMatrix"));
			String password = "";
			if (useMatrix) {
				PwMatrixUtil util = new PwMatrixUtil("FFO", username);
				try {
					password = util.getPassword();
				} catch (GenException e) {
					logger.error(e, e);
				}
			} else {
//				password = config.getProperty("Uifpassword");
				//descryt the password from config file
				FXPassEncryptor fx = new FXPassEncryptor();
				password = fx.decrypt(config.getProperty("Uifpassword"), Constants.SECURITYKEY);

			}
			isUARegion = false;
			if (baseURL.toLowerCase().contains("cloud")) {
				loginCloud(username, password);
				if (baseURL.toLowerCase().contains("ua1")) {
					isUARegion = true;
				}
			} else {
				loginLocal(username);
			}
			isLogined = true;
		}
		boolean succ = false;
		succ = waitingElement(By.xpath("//div[@class='featureBox-content']"));
		return succ;
	}

	public boolean roleLogin(String roleType) {
		Config role = new Config("role.properties");
		String username = roleType + ".user";
		username = role.getProperty(username);
		String password = roleType + ".password";
		password = role.getProperty(password);
		boolean useMatrix = Boolean.valueOf(config.getProperty("useMatrix"));
		if (useMatrix) {
			PwMatrixUtil util = new PwMatrixUtil("FFO", username);
			try {
				password = util.getPassword();
			} catch (GenException e) {
				logger.error(e, e);
			}
		}
		isUARegion = false;
		if (baseURL.toLowerCase().contains("cloud")) {
			loginCloud(username, password);
			if (baseURL.toLowerCase().contains("ua1")) {
				isUARegion = true;
			}
		} else {
			loginLocal(username);
		}
		isLogined = true;
		boolean succ = false;
		succ = waitingElement(By.xpath("//div[@class='featureBox-content']"));
		return succ;
	}

	public void initRole() {
		if (isLogined) {
			quit();
			isLogined = false;
		}
		init();
	}

	public static void quit() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	private void loginLocal(String userName) {
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

	private void loginCloud(String userName, String pass) {
		WebElement inputName = driver.findElement(By
				.xpath("//input[@name='username']"));
		inputName.clear();
		inputName.sendKeys(userName);
		WebElement password = driver.findElement(By
				.xpath("//input[@name='PASSWORD']"));
		password.clear();
		password.sendKeys(pass);
		WebElement loginBTN = driver.findElement(By
				.xpath("//input[@value='Submit']"));
		loginBTN.click();
	}

	public boolean isElementExist(By Locator) {
		try {
			driver.findElement(Locator);
			return true;
		} catch (NoSuchElementException ex) {
			return false;
		}
	}

	public boolean waitingElement(By Locator) {
		Date startDate = new Date();
		long timeOut = 60000;
		long cost = 0;
		while (cost < timeOut) {
			if (isElementExist(Locator)) {
				return true;
			}
			Date endDate = new Date();
			cost = endDate.getTime() - startDate.getTime();
		}
		return false;
	}

	public boolean waitingElementDisappear(By Locator) {
		Date startDate = new Date();
		long timeOut = 60000;
		long cost = 0;
		while (cost < timeOut) {
			if (!isElementExist(Locator)) {
				return true;
			}
			Date endDate = new Date();
			cost = endDate.getTime() - startDate.getTime();
		}
		return false;
	}

	public String getAddResult() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		By locator = By
				.xpath("//div[@id='CommonMessageBox']//div[@class='gwt-HTML']");
		if (waitingElement(locator)) {
			String result = driver.findElement(locator).getText();
			result = result.replace("<br>", "");
			result = result.replace("</br>", "");
			result = result.replace("\n", "");
			result = result.replace("\r", "");
			return result;
		}
		return "";
	}

	public String saveNewTolerance() {
		Actions actions = new Actions(driver);
		WebElement saveButton = driver.findElement(By
				.xpath("//button[@class='cdtBlueBarSubmitButton']"));
		actions.moveToElement(saveButton);
		saveButton.click();
		String result = getAddResult();
		if (result.isEmpty()) {
			saveButton.click();
			result = getAddResult();
		}
		driver.findElement(
				By.xpath("//div[@id='CommonMessageBox']//button[starts-with(@class,'cdtPrimaryButton ')]"))
				.click();
		return result;
	}

	public String queryResult() {
		String result = getAddResult();
		driver.findElement(
				By.xpath("//div[@id='CommonMessageBox']//button[starts-with(@class,'cdtPrimaryButton ')]"))
				.click();
		return result;
	}

	protected void setPageInputs(Map<String, String> values,
			Map<String, WebElement> inputMap) {
		for (Entry<String, String> entry : values.entrySet()) {
			String key = entry.getKey();
			WebElement element = inputMap.get(key);
			if (element != null && !"@Null".equals(entry.getValue())) {
				if (element.getTagName().equalsIgnoreCase("button")) {
					selectDropDownValue(element, entry.getValue());
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

	public void selectDropDownValue(WebElement dropdownlist, String selectValue) {
		waitElementEnable(dropdownlist);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"arguments[0].focus(); arguments[0].blur(); return true",
				dropdownlist);
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
			dropdownlist.click();
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

	public void setInputValue(WebElement inputBox, String value) {
		if (value.equals("@Null") || value.equals("@Empty")) {
			value = "";
		}
		WebElement parentElement = inputBox.findElement(By.xpath(".."));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"arguments[0].focus(); arguments[0].blur(); return true",
				inputBox);
		inputBox.clear();

		try {
			inputBox.sendKeys(value);
		} catch (StaleElementReferenceException e) {
			inputBox = parentElement.findElement(By.tagName("input"));
			inputBox.sendKeys(value);
		}
//		js.executeScript(
//				"arguments[0].focus(); arguments[0].blur(); return true",
//				inputBox);
		// js.executeScript("alert(jQuery.active)");
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

	public boolean waitPageLoadComplete() {
		Date startDate = new Date();
		long timeOut = 60000;
		long cost = 0;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		while (cost < timeOut) {
			long count = (long) js.executeScript("return jQuery.active;");
			if (count == 0) {
				return true;
			}
			Date endDate = new Date();
			cost = endDate.getTime() - startDate.getTime();
		}
		return false;
	}

	public boolean waitElementEnable(WebElement element) {
		Date startDate = new Date();
		long timeOut = 30000;
		long cost = 0;
		while (cost < timeOut) {
			if (element.isEnabled()) {
				return true;
			}
			Date endDate = new Date();
			cost = endDate.getTime() - startDate.getTime();
		}
		return false;
	}

	public int waitAfterSearch() {
		if (isElementExist(By.xpath("//div[@id='CommonMessageBox']"))) {
			return -1;
		}
		Date startDate = new Date();
		long timeOut = 60000;
		long cost = 0;
		while (cost < timeOut) {
			if (isElementExist(By
					.xpath("//div[@class='dataGridWidget']/div[3]//tr[starts-with(@class,'dataGridEvenRow')]"))) {
				return 0;
			}
			if (isElementExist(By.xpath("//div[@class='noDataFound']"))) {
				return 1;
			}
			Date endDate = new Date();
			cost = endDate.getTime() - startDate.getTime();
		}
		return -1;
	}

	public void waitLoadingEnd() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			logger.error(e, e);
		}
		waitingElementDisappear(By.xpath("//div[@class='loadingIndicator']"));
	}

	public String getErrorMsg() {
		if (!waitingElement(By.xpath("//div[@class='noDataFound']"))) {
			throw new NoSuchElementException("Element not found");
		}
		return driver.findElement(By.xpath("//div[@class='noDataFound']"))
				.getText();
	}

	public String getPageHeaderName() {
		return tag_PageHeader.getText();
	}

	public void closeAddPanel() {
		btn_AddPanelClose.click();
	}

	public int getTotalQueryNumbers() {
		String[] total = label_queryPageNumber.getText().split(" ");
		return Integer.parseInt(total[2]);
	}

	public int getOnePageNumbers() {
		if (label_queryPageNumber.getText().contains("#")) {
			String[] pageNumbers = label_queryPageNumber.getText().split(" ")[0]
					.split("#");
			return Integer.parseInt(pageNumbers[1]);
		} else {
			String[] pageNumbers = label_queryPageNumber.getText().split(" ")[0]
					.split("-");
			return Integer.parseInt(pageNumbers[1]);
		}

	}

	public void gotoNextQueryPage() {
		btn_nextQuery.click();
	}

	public void getSearchButton() {
		if (!waitingElement(By.xpath("//button[@id='gwt-debug-searchButton']"))) {
			throw new NoSuchElementException("Query button not found");
		}
		driver.findElement(By.xpath("//button[@id='gwt-debug-searchButton']"))
				.click();
	}

	public void saveUpdateResult() {
		btn_edit_save.click();
		waitLoadingEnd();
	}

	public String saveErrorResult() {
		btn_edit_save.click();
		waitLoadingEnd();
		return queryResult();
	}

	public void switchToFrame(String frameName) {
		List<WebElement> frames = driver.findElements(By.tagName("IFRAME"));
		for (WebElement ele : frames) {
			if (ele.getAttribute("src").contains(frameName)) {
				driver.switchTo().frame(ele);
				return;
			}
		}
		return;
	}

	public WebElement findElementByWrapperAndTagName(String wrapperId,
			String tagName) {

		WebElement wrapper = driver.findElement(By.id(wrapperId));
		if (wrapper != null) {
			return wrapper.findElement(By.tagName(tagName));
		}

		return null;
	}

	public WebElement findElementById(String id) {
		return driver.findElement(By.id(id));
	}

	public WebElement findElementByXpath(String xpath) {
		return driver.findElement(By.xpath(xpath));
	}

	public List<WebElement> findElementListByXpath(String xpath) {
		List<WebElement> elementList = driver.findElements(By.xpath(xpath));
		return elementList;
	}

	public Map<String, Integer> get_col_map() {
		Map<String, Integer> colMap = new LinkedHashMap<String, Integer>();

		// Get the column no
		List<WebElement> headerCol = driver.findElements(By
				.cssSelector(".dataGridHeader span"));

		for (Integer index = 0; index < headerCol.size(); index++) {

			String colValue = headerCol.get(index).getText().trim()
					.toUpperCase();
			colMap.put(colValue, index);
		}

		return colMap;
	}

	public String getSaveButtonProperty() {
		if(isElementExist(By.cssSelector(".cdtBlueBarSubmitButton-disabled"))){
			return "DISABLED";
		}
		return null;
	}
}
