package com.ssc.ssgm.fx.refdata.base.extension;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;

import com.hp.lft.sdk.Description;
import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.web.Button;
import com.hp.lft.sdk.web.EditField;
import com.hp.lft.sdk.web.WebElement;
import com.hp.lft.sdk.web.XPathDescription;
import com.ssc.ssgm.fx.refdata.base.LFTUtil;

public class LFTOWT2BaseUtil extends LFTUtil {
	
    @Override
    protected boolean checkLogined() throws Exception{
    	//not implemented
    	return false;
    }
    
    @Override
    protected void loginLocal(String userName) throws Exception {
		EditField inputName = browser.describe(EditField.class,  
                new XPathDescription("//input[@name='userId']"));
        inputName.setValue(userName);
        EditField displayName = browser.describe(EditField.class,  
                new XPathDescription("//input[@name='userName']"));
        displayName.setValue("Test");
        EditField email = browser.describe(EditField.class,  
                new XPathDescription("//input[@name='userEmail']"));
        email.setValue("test@1.com");
        WebElement loginBTN = getElement("//input[@value='Login']");
        loginBTN.click();
    }

    public void setSelectValues(Description span, String icon) {
        if (icon == null || icon.equals("@Null")) {
            return;
        }
        try {
            browser.sync();
            WebElement spanBox = browser.describe(WebElement.class, span);
            spanBox.click();
            browser.sync();
        }
        catch (Exception e) {
            logger.error("Do not find the icon: " + icon);
        }
    }

    public void selectDropDownValue(Description dropdownDesc, String selectValue) {
        try {
            browser.sync();
            Button dropdownlist = browser.describe(Button.class, dropdownDesc);
            waitElementEnable(dropdownlist, 30000);
            if (selectValue == null || selectValue.equals("@Null") || dropdownlist.getInnerText().contains(selectValue)) {
                return;
            }
            dropdownlist.click();
            Thread.sleep(500);
            WebElement[] ddlElements = browser.findChildren(WebElement.class,
                    new XPathDescription("//div[@class='cdtListView']//a"));
            int count = 0;
            while (ddlElements.length == 0 && count < 10) {
            	Thread.sleep(500);
            	ddlElements = browser.findChildren(WebElement.class,
                        new XPathDescription("//div[@class='cdtListView']//a"));
			}
            logger.info("LeanFT: Read dropdownlist find total elements count: " + ddlElements.length);
            List<String> listValues = new ArrayList<String>();
            for (WebElement element : ddlElements) {
            	listValues.add(element.getInnerText());
                if (element.getInnerText().contains(selectValue)) {
                    element.click();
                    return;
                }
            }
            if (listValues.size()<=3) {
            	for (String value : listValues) {
					logger.error("LeanFT: Read dropdownlist value: " + value);
				}				
			}	
        }
        catch (Exception e) {
            logger.error(e, e);
        }
        throw new NoSuchElementException("Can't find target value in dropdownlist: " + selectValue);
    }
        
    public void waitLoadingEnd() {
        try {
        	waitElement(new XPathDescription("//div[@class='loadingIndicator']"),3000);
        	waitElementDisappear(new XPathDescription("//div[@class='loadingIndicator']"),30000);
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            logger.error(e, e);
        }
    }
        
    public boolean waitSearchLoadingEnd() {
    	boolean status = false;
        try {
        	waitElement(getElement("//div[@class='dataGridWidget']//img[@class='cdtDataGridLoadingImage']"),3000);
        	status = waitElementDisappear(new XPathDescription("//img[@class='cdtDataGridLoadingImage']"),30000);
        	Thread.sleep(500);
        }
        catch (InterruptedException e) {
            logger.error(e, e);
        }
        return status;
    }
    
    protected boolean isEditBoxReadOnly(Description desc){
    	try {
    		browser.sync();
    		EditField editField = browser.describe(EditField.class, desc);
			return editField.getClassName().contains("disabled")||editField.getClassName().contains("readonly");
		} catch (GeneralLeanFtException e) {
			logger.error(e,e);
			return false;
		}
    }
}
