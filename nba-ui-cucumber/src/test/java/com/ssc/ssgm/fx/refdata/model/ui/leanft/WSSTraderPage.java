package com.ssc.ssgm.fx.refdata.model.ui.leanft;

import java.util.List;

import com.hp.lft.sdk.Description;
import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.web.EventInfoFactory;
import com.hp.lft.sdk.web.FileField;
import com.hp.lft.sdk.web.XPathDescription;

public class WSSTraderPage extends RefUIBaseUtil {
		
	//Search part
    private Description input_traderId = new XPathDescription("//span[@id='gwt-debug-TraderIdInput']//input");
    private Description input_wssTraderCode = new XPathDescription("//span[@id='gwt-debug-WSSTraderCodeInput']//input");
    private Description input_wssTraderName = new XPathDescription("//span[@id='gwt-debug-WSSTraderNameInput']//input");
    
	private Description btn_navigate = new XPathDescription("//*[@id='gwt-debug-Covered-Persons']");
	private Description btn_AuditHistory = new XPathDescription("//button[@id='gwt-debug-converedPersonhistoryButton']");
	private Description btn_AuditHistory_close = new XPathDescription("//button[@id='gwt-debug-cancel_button']");
	private Description btn_editPanel_close = new XPathDescription("//button[@class='cdtBlueBarClose']");	
	private Description table_AuditHeads = //new TableDescription.Builder().tagName("TABLE").index(22).build();
			new XPathDescription("//DIV[@id='gwt-debug-audit_trail_data_grid']//DIV[@class='dataGridWidget']/DIV[1]//TABLE");
	private Description table_AuditValues = //new TableDescription.Builder().tagName("TABLE").index(23).build();
			new XPathDescription("//DIV[@id='gwt-debug-audit_trail_data_grid']//DIV[@class='dataGridWidget']/DIV[3]//TABLE");
	
	private Description btn_upload = new XPathDescription("//button[@id='gwt-debug-uif_fwk_upload_btn']");
	private Description input_uploadFile = new XPathDescription("//input[@class='gwt-FileUpload']");
	private Description btn_doUpload = new XPathDescription("//button[@id='gwt-debug-uploadDataButton']");
	
	//Add Page
	//private Description employeeInput = new XPathDescription("//span[@id='gwt-debug-WSSTraderAdd-TRADERNAME']/input");
	
	//Edit Page
    private Description input_employeeId = new XPathDescription("//span[@id='gwt-debug-EmployeeIdInput']//input");
    private Description input_employeeName = new XPathDescription("//span[@id='gwt-debug-TraderNameEditInput']//input");
    private Description ddl_supervisorRegion = new XPathDescription("//div[@id='gwt-debug-SupervisorRegionEditInput']/button");
    private Description ddl_group = new XPathDescription("//div[@id='gwt-debug-WSSGroupCodeEditInput']/button");
    private Description input_effectiveDate = new XPathDescription("//span[@id='gwt-debug-WSSEffectiveDateEditInput']//input");
    private Description input_expiryDate = new XPathDescription("//span[@id='gwt-debug-WSSExpiryDateEditInput']//input");
    private Description input_expiryReason = new XPathDescription("//span[@id='gwt-debug-WSSExpiryReasonEditInput']//input");

	public void navigateToWSSTraderPage(){
		clickButton(btn_navigate);
	}
		
	public void openAuditHistory(){
		clickButton(btn_AuditHistory);
	}
	
	public void closeAuditHistory(){
	    clickButton(btn_AuditHistory_close);
	}
	
    public void closeEditPanel() {
        clickButton(btn_editPanel_close);
    }
	
	public void setSearchValue(String traderId, String wssTraderCode, String wssTraderName){
		setInputValue(input_traderId, traderId);
		setInputValue(input_wssTraderCode, wssTraderCode);
		setInputValue(input_wssTraderName, wssTraderName);
	}
	
	public void searchWSSTrader(String traderId, String wssTraderCode, String wssTraderName){
		setSearchValue(traderId, wssTraderCode, wssTraderName);
		clickSearch();
	}
	
	public List<String> getAuditHeads() {
		return getTableHead(table_AuditHeads);
	}
	
	public List<List<String>> getAuditValues() {
		return getTableValue(table_AuditValues);
	}
	
	public void setEmployeeId(String value){
		setInputValue(input_employeeId, value);
	}
	
    public void setEmployeeName(String value){
        setInputValue(input_employeeName, value);
    }
    
    public void setEffectiveDate(String value){
        setInputValue(input_effectiveDate, value);
    }
    
    public void setExpiryDate(String value){
        setInputValue(input_expiryDate, value);
    }
    public void setExpiryReason(String value){
        setInputValue(input_expiryReason, value);
    }
    
    public void setSuperviorRegion(String value){
        selectDropDownValue(ddl_supervisorRegion, value);
    }
    
    public void setGroup(String value){
        selectDropDownValue(ddl_group, value);
    }
    
    public void openUploadWindow(){
    	//navigateToWSSTraderPage();
    	clickButton(btn_upload);
    	waitElement(input_uploadFile);
    }
    
    public void setUploadFilePath(String path){ 
    	try {
            browser.sync();
            FileField inputBox = browser.describe(FileField.class, input_uploadFile);      
            inputBox.setValue(path);
            inputBox.fireEvent(EventInfoFactory.createEventInfo("focus"));
            inputBox.fireEvent(EventInfoFactory.createEventInfo("blur"));
            browser.sync();
		} catch (GeneralLeanFtException e) {
			logger.error(e,e);
		}
    }

    public String uploadAndGetResult(){
    	if (!isErrorReturns()) {
    		clickButton(btn_doUpload);
		}
    	return getErrorMsg();
    }

}
