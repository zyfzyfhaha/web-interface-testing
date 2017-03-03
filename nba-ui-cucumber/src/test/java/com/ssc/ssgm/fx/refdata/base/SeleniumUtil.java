package com.ssc.ssgm.fx.refdata.base;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;

import com.ssc.ssgm.fx.refdata.client.IClientService;

import com.ssc.faw.util.GenException;
import com.ssc.faw.util.PwMatrixUtil;

public abstract class SeleniumUtil {

    protected static final Logger logger = Logger.getLogger(SeleniumUtil.class);
    protected static WebDriver driver;
    private static boolean isLogined = false;
    public static boolean isUARegion = false;
    private Config config = new Config();
    private String baseURL;
    protected IClientService client;
    

    public static WebDriver getWebDriver() {
        if (driver == null) {
            driver = new InternetExplorerDriver();
        }
        return driver;
    }
    
    public static boolean isSeleniumLogined(){
    	return isLogined;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void navigateToUrl(String url) {
        driver.navigate().to(url);
    }

    public void init() {
        int count = 0;
        while (count<=3) {
            try {
                initDriver();
                break;
            }
            catch (UnreachableBrowserException e) {
                logger.error(e,e);
                quit();
                count++;
            }            
        }
    }
    
    public void initDriver() {
        getWebDriver();
        driver.manage().window().maximize();
        Navigation navigation = driver.navigate();
        baseURL = config.getProperty("baseUrl");
        navigation.to(baseURL);
    }

    public boolean login() {
        if (!isLogined) {
            String username = config.getProperty("userId");
            boolean useMatrix = Boolean.valueOf(config.getProperty("useMatrix"));
            String password = "";
            if (useMatrix) {
                PwMatrixUtil util = new PwMatrixUtil("FFO", username);
                try {
                    password = util.getPassword();
                }
                catch (GenException e) {
                    logger.error(e, e);
                }
            }
            else {
                password = config.getProperty("password");
            }
            isUARegion = false;
            if (baseURL.toLowerCase().contains("cloud")) {
                loginCloud(username, password);
                if (baseURL.toLowerCase().contains("ua1")) {
                    isUARegion = true;
                }
            }
            else {
                loginLocal(username);
            }
        }
        isLogined = false;
        isLogined = checkLogined();
        return isLogined;
    }

    public boolean roleLogin(String roleType) {
    	if (isLogined) {
    		quit();
		}
    	init();
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
            }
            catch (GenException e) {
                logger.error(e, e);
            }
        }
        isUARegion = false;
        if (baseURL.toLowerCase().contains("cloud")) {
            loginCloud(username, password);
            if (baseURL.toLowerCase().contains("ua1")) {
                isUARegion = true;
            }
        }
        else {
            loginLocal(username);
        }
        isLogined = false;
        isLogined = checkLogined();
        return isLogined;
    }

    public void resetDriver() {
        quit();        
        isLogined = false;
        init();
    }

    public static void quit() {
    	try {
	    	if (driver != null) {
	            driver.quit();
	        }
        }
        catch (Exception e) {
            logger.error(e,e);
        }
    	finally{
    		driver = null;
    		try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}
    	}
    }
    
    protected abstract boolean checkLogined();

    protected abstract void loginLocal(String userName);

    private void loginCloud(String userName, String pass) {
        WebElement inputName = driver.findElement(By.xpath("//input[@name='username']"));
        inputName.clear();
        inputName.sendKeys(userName);
        WebElement password = driver.findElement(By.xpath("//input[@name='PASSWORD']"));
        password.clear();
        password.sendKeys(pass);
        WebElement loginBTN = driver.findElement(By.xpath("//input[@value='Submit']"));
        loginBTN.click();
    }

    public boolean isElementExist(By Locator) {
        try {
            driver.findElement(Locator);
            return true;
        }
        catch (NoSuchElementException ex) {
            return false;
        }
    }
    
    public boolean isElementExist(WebElement element) {
        try {
        	if (element.isDisplayed()) {
        		return true;
			}
        }
        catch (NoSuchElementException ex) {
            return false;
        }
        return false;
    }

    public boolean waitingElement(By Locator) {
    	return waitingElement(Locator, 60000);
    }
    
    public boolean waitingElement(WebElement element) {
    	return waitingElement(element, 60000);
    }
    
    public boolean waitingElement(By Locator, int timeOut) {
        Date startDate = new Date();
        long cost = 0;
        while (cost < timeOut) {
            if (isElementExist(Locator)) {
            	try {
                    Thread.sleep(200);
                }
                catch (InterruptedException e) {
                    logger.error(e,e);
                }
                return true;
            }
            Date endDate = new Date();
            cost = endDate.getTime() - startDate.getTime();
        }
        return false;
    }
    
    public boolean waitingElement(WebElement element, int timeOut) {
        Date startDate = new Date();
        long cost = 0;
        while (cost < timeOut) {
            if (isElementExist(element)) {
            	try {
                    Thread.sleep(200);
                }
                catch (InterruptedException e) {
                    logger.error(e,e);
                }
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
    
    public boolean waitingElementDisappear(WebElement element) {
        Date startDate = new Date();
        long timeOut = 60000;
        long cost = 0;
        while (cost < timeOut) {
            if (!isElementExist(element)) {
                return true;
            }
            Date endDate = new Date();
            cost = endDate.getTime() - startDate.getTime();
        }
        return false;
    }
    
    public boolean waitElementEnable(WebElement element) {
    	if (!waitingElement(element, 30000)) {
			return false;
		}
        Date startDate = new Date();
        long timeOut = 90000;
        long cost = 0;
        while (cost < timeOut) {
            if (element.isEnabled()) {
                return true;
            }
            Date endDate = new Date();
            cost = endDate.getTime() - startDate.getTime();
        }
        logger.error("Wait Element enable timeout for 90s!");
        return false;
    }

    public void selectListBoxValue(WebElement listBox, String selectValue) {
        waitElementEnable(listBox);
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            logger.error(e, e);
        }
        List<WebElement> listElements = listBox.findElements(By.tagName("option"));
        logger.info("Selenium: Read listBox find total elements count: " + listElements.size());
        for (WebElement element : listElements) {
            if (element.getText().contains(selectValue)) {
                element.click();
                return;
            }
        }
        throw new NoSuchElementException("Can't find target value in listBox: " + selectValue);
    }
    
    
    public String getListBoxValue(WebElement listBox) {
        waitElementEnable(listBox);
        WebElement ddlElement = listBox.findElement(By.tagName("option"));
        return ddlElement.getText();
    }
        
    public void setInputValue(WebElement inputBox, String value) {
        if (value.equals("@Null") || value.equals("@Empty")) {
            value = "";
        }
        WebElement parentElement = inputBox.findElement(By.xpath(".."));
        inputBox.clear();
        try {
            inputBox.sendKeys(value);
        }
        catch (StaleElementReferenceException e) {
            inputBox = parentElement.findElement(By.tagName("input"));
            inputBox.sendKeys(value);
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].focus(); arguments[0].blur(); return true", inputBox);
    }

}
