package com.ssc.ssgm.fx.refdata.base;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.hp.lft.sdk.Description;
import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.ModifiableSDKConfiguration;
import com.hp.lft.sdk.NoValidLicenseException;
import com.hp.lft.sdk.ReplayObjectNotFoundException;
import com.hp.lft.sdk.SDK;
import com.hp.lft.sdk.WaitUntilTestObjectState;
import com.hp.lft.sdk.WaitUntilTestObjectState.WaitUntilEvaluator;
import com.hp.lft.sdk.web.Browser;
import com.hp.lft.sdk.web.BrowserDescription;
import com.hp.lft.sdk.web.BrowserFactory;
import com.hp.lft.sdk.web.BrowserType;
import com.hp.lft.sdk.web.Button;
import com.hp.lft.sdk.web.EditField;
import com.hp.lft.sdk.web.EventInfoFactory;
import com.hp.lft.sdk.web.Table;
import com.hp.lft.sdk.web.TableCell;
import com.hp.lft.sdk.web.TableRow;
import com.hp.lft.sdk.web.WebElement;
import com.hp.lft.sdk.web.XPathDescription;
import com.ssc.faw.util.GenException;
import com.ssc.faw.util.PwMatrixUtil;

public abstract class LFTUtil {
	
	protected static final Logger logger = Logger.getLogger(LFTUtil.class);
	protected static Browser browser;
	protected static boolean isLogined = false;
    public static boolean isUARegion = false;
    public static boolean isExsitingBrowser = false;
    
    protected Config config = new Config();
    protected String baseURL;
	
	public static Browser getDriver() throws Exception{
		if (browser == null) {
			initSDK();
			browser = BrowserFactory.launch(BrowserType.INTERNET_EXPLORER);
		}
		return browser;
	}
	
	public static Browser getDriver(String assignedBrowser) throws Exception{
		if (browser == null) {
			initSDK();
			try {
				browser =  BrowserFactory.attach(new BrowserDescription.Builder()
							.openTitle("WebDriver").build());
			} catch (ReplayObjectNotFoundException e) {
				browser =  BrowserFactory.attach(new BrowserDescription.Builder()
							.title(assignedBrowser).build());
//				String newTitle = assignedBrowser + " - Windows Internet Explore";
//				browser =  BrowserFactory.attach(new BrowserDescription.Builder()
//							.title(newTitle).build());
			}
			isExsitingBrowser = true;
		}
		return browser;
	}
	
	private static void initSDK() throws Exception {
		ModifiableSDKConfiguration sdkconfig = new ModifiableSDKConfiguration();
		sdkconfig.setServerAddress(new URI("ws://localhost:5095"));
		try {			
			SDK.init(sdkconfig);
		} catch (NoValidLicenseException e) {
			FXCMDUtil.runCMD("taskkill /F /IM LFTRuntime.exe");
			int count = 0;
			while (FXCMDUtil.isImageStarted("LFTRuntime")&&count<10) {
				Thread.sleep(500);			
				count++;
			}
			ensureRuntimeStart();
			SDK.init(sdkconfig);
		}
	}
	
	public void initDriver(){
		initDriver(true);
	}
	
	public void initDriver(boolean firstTry){
		try {
			getDriver();
			if ((!navigateToBaseURL())&&firstTry) {
				initDriver(false);
			}
		} catch (Exception e) {
			logger.error(e,e);
		}        
    }
	
	public void initDriverUsingExistBrowser(String assignedBrowser) throws Exception{
		initDriverUsingExistBrowser(assignedBrowser, true);
	}
	
	public void initDriverUsingExistBrowser(String assignedBrowser, boolean firstTry) throws Exception {
		getDriver(assignedBrowser);
		if ((!navigateToBaseURL())&&firstTry) {
			initDriverUsingExistBrowser(assignedBrowser, false);
		}
	}
	
	private boolean navigateToBaseURL() throws Exception{
		boolean result = true;
		baseURL = config.getProperty("baseUrl");
		try {
			browser.navigate(baseURL);
		} catch (NoValidLicenseException e) {
			quit();
			result = false;
		}
		return result;
	}
	
	protected String getBaseURL() {
		return config.getProperty("baseUrl");
	}
	
	public static boolean ensureRuntimeStart() {
		if (FXCMDUtil.isImageStarted("LFTRuntime")) {
			return true;
		}
		try {
			FXCMDUtil.runCMD("C:\\Program Files (x86)\\HP\\Unified Functional Testing\\bin\\LFTRuntime.exe");
			for (int i = 0; i < 20; i++) {
				if (FXCMDUtil.isImageStarted("LFTRuntime")) {
					Thread.sleep(10000);
					return true;
				}
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
		return false;
	}
	
	public void processRequestURL(String url) throws Exception{
		boolean needClose = false;
		if (browser == null) {
			initDriver();
			login();
			needClose = true;
		}
		browser.openNewTab();
		Browser tmpBrowser = BrowserFactory.attach(new BrowserDescription.Builder()
		.title("about:Tabs").build());
		tmpBrowser.navigate(url);
		tmpBrowser.sync();
		tmpBrowser.close();
		if (needClose) {
			quit();
		}
	}
	
	public boolean login(){
        try {
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
		} catch (Exception e) {
			logger.error(e,e);
			isLogined = false;
		}
        return isLogined;
    }
	
    protected abstract boolean checkLogined() throws Exception;

    protected abstract void loginLocal(String userName) throws Exception;
    	
	private void loginCloud(String userName, String pass) throws GeneralLeanFtException {
		EditField inputName = browser.describe(EditField.class,  
                new XPathDescription("//input[@name='username']"));
        inputName.setValue(userName);
        EditField password = browser.describe(EditField.class,  
                new XPathDescription("//input[@name='PASSWORD']"));
        password.setValue(pass);
        WebElement loginBTN = getElement("//input[@value='Submit']");
        loginBTN.click();
    }
	
	public static void quit() throws Exception{
		if (browser != null) {
			if (!isExsitingBrowser) {
				browser.close();
			}
			SDK.cleanup();
		} 
		isLogined = false;
		browser = null;
	}
	
	public WebElement getElement(String xpath){
		try {
			return browser.describe(WebElement.class, new XPathDescription(xpath));
		} catch (GeneralLeanFtException e) {
			logger.error(e,e);
			return null;
		}
	}
	
	public WebElement getElement(Description desc){
		try {
			return browser.describe(WebElement.class, desc);
		} catch (GeneralLeanFtException e) {
			logger.error(e,e);
			return null;
		}
	}
	
    protected void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}
	
	public boolean waitElement(Description elementDesc){
		return waitElement(elementDesc, 60000);
	}
	
	public boolean waitElement(WebElement element){
		return waitElement(element, 60000);
	}
	
	public boolean waitElement(WebElement element, long timeOut){
		WaitUntilEvaluator<WebElement> evaluator = new WaitUntilEvaluator<WebElement>() {
            @Override
            public boolean evaluate(WebElement testObject) {
                try {
                	return testObject.exists(1)&&testObject.isVisible();
				} catch (GeneralLeanFtException e) {
					logger.error("waitElement failed");
					logger.error(e,e);
					return false;
				}
            }
        };
        try {
			return WaitUntilTestObjectState.waitUntil(element, evaluator, timeOut);
		} catch (GeneralLeanFtException e) {
			logger.error(e,e);
			return false;
		}
	}
	
	public boolean waitElement(Description elementDesc, long timeOut){
		Date startDate = new Date();
        long cost = 0;
        while (cost < timeOut) {
            try {
            	WebElement element = getElement(elementDesc);
				if (element.exists(1)&&element.isVisible()) {
					Thread.sleep(200);
				    return true;
				}
			} catch (Exception e) {
				logger.error(e,e);
			}
            Date endDate = new Date();
            cost = endDate.getTime() - startDate.getTime();
        }
        return false;
	}
	
	//only support button element now
	public boolean waitElementEnable(WebElement element, long timeOut){
		WaitUntilEvaluator<WebElement> evaluator = new WaitUntilEvaluator<WebElement>() {
            @Override
            public boolean evaluate(WebElement testObject) {
                try {                	
					return testObject.exists(1)&&((Button)testObject).isEnabled();
				} catch (GeneralLeanFtException e) {
					return false;
				}
            }
        };
        try {
			return WaitUntilTestObjectState.waitUntil(element, evaluator, timeOut);
		} catch (GeneralLeanFtException e) {
			logger.error(e,e);
			return false;
		}
	}
	
	public boolean waitElementDisappear(Description elementDesc, long timeOut){
		Date startDate = new Date();
        long cost = 0;
        while (cost < timeOut) {
            try {
            	WebElement element = getElement(elementDesc);
				if (!element.exists(1)) {
					Thread.sleep(200);
				    return true;
				}
			} catch (Exception e) {
				logger.error(e,e);
			}
            Date endDate = new Date();
            cost = endDate.getTime() - startDate.getTime();
        }
        return false;
	}
	
    
	public void clickButton(Description btnDesc){
		try {
			browser.sync();
			browser.describe(WebElement.class, btnDesc).click();
			browser.sync();
		} catch (GeneralLeanFtException e) {
			logger.error(e,e);
		}
	}
		
	protected void setInputValue(Description input, String value) {
		if (value.equals("@Null") || value.equals("@Empty")) {
            value = "";
        }
		try {
			browser.sync();
			EditField inputBox = browser.describe(EditField.class, input);
			inputBox.click();
			inputBox.setValue(value);
			inputBox.fireEvent(EventInfoFactory.createEventInfo("focus"));
			inputBox.fireEvent(EventInfoFactory.createEventInfo("blur"));
			browser.sync();
			if (!inputBox.getValue().equals(value)) {
				sleep(1000);
				inputBox.click();
				inputBox.setValue(value);
				browser.sync();
			}
		} catch (GeneralLeanFtException e) {
			logger.error(e,e);
		}
	}
	    
    protected String getInputBoxText(Description desc) {
    	try {
			return browser.describe(EditField.class, desc).getValue();
		} catch (GeneralLeanFtException e) {
			logger.error(e,e);
			return "";
		}
	}
	    
	protected List<String> getTableHead(Description headTable) {
		List<String> heads = new ArrayList<String>();
		try {
			browser.sync();
			Table tableElement = browser.describe(Table.class, headTable);
			if (!tableElement.exists()) {
				logger.error("Table not found!");
				return heads;
			}
			Thread.sleep(1000);
			return tableElement.getColumnHeaders();
		} catch (Exception e) {
			logger.error(e,e);
		}
		return heads;
	}
	
	protected List<List<String>> getTableValue(Description table) {
	    List<List<String>> tableData = new ArrayList<List<String>>();
		try {
		    browser.sync();
		    Thread.sleep(1000);
			Table tableElement = browser.describe(Table.class, table);
			if (!tableElement.exists()) {
				logger.error("Table not found!");
				return tableData;
			}
			if (tableElement.getInnerText().trim().isEmpty()) {
				Thread.sleep(1000);
				tableElement = browser.describe(Table.class, table);
			}
			List<TableRow> rows = tableElement.getRows();
			for (TableRow row : rows) {
				List<String> rowData = new ArrayList<String>();
				List<TableCell> cells = row.getCells();
				for (TableCell cell : cells) {
					rowData.add(cell.getText());
				}
				tableData.add(rowData);
			}
		} catch (Exception e) {
			logger.error(e,e);
		}
		return tableData;
	}

}
