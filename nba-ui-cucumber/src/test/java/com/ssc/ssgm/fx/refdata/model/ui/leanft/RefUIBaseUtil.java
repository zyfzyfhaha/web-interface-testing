package com.ssc.ssgm.fx.refdata.model.ui.leanft;

import com.hp.lft.sdk.Description;
import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.web.WebElement;
import com.hp.lft.sdk.web.XPathDescription;
import com.ssc.ssgm.fx.refdata.base.extension.LFTOWT2BaseUtil;

public class RefUIBaseUtil extends LFTOWT2BaseUtil{
	
	@Override
    protected boolean checkLogined() throws Exception{
    	browser.sync();
		WebElement feature = getElement("//div[@id='MAIN_PANEL']");
		return feature.exists();
    }
	
    protected Description btn_detail_edit = new XPathDescription("//*[@id='gwt-debug-submitButton']");
    protected Description btn_save = new XPathDescription("//*[@id='gwt-debug-addpage_save_btn']");
    protected Description label_PopMsg = new XPathDescription("//div[@id='CommonMessageBox']//div[@class='gwt-HTML']");
	
    public int waitAfterSearch() {
        try {
			if (getElement("//div[@id='CommonMessageBox']").exists(1)) {
			    return -1;
			}
		} catch (GeneralLeanFtException e) {
			logger.error(e, e);
			return -2;
		}
        boolean status = waitSearchLoadingEnd();
        if (status) {
        	return 0;
		}
        return -2;
    }
    
    public void clickSearch(){
		clickButton(new XPathDescription("//button[@id='gwt-debug-searchButton']/i"));
	}
	
	public void clickRowDetail(int rowNum) {
		clickButton(new XPathDescription("//div[@class='dataGridWidget']/div[3]//table//tr["+rowNum+"]/td[2]/div"));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}
	
	public void ActiveEdit(){
	    clickButton(btn_detail_edit);
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}   
	
    public void ActiveSave() {
        clickButton(btn_detail_edit);
    }
    
    public void addSave() {
        clickButton(btn_save);
    }
    
	public void openAddPanel() {
        clickButton(new XPathDescription("//button[@class='cdtAddItem']"));
    }
	
	public boolean isErrorReturns(){
		if (waitElement(label_PopMsg, 3000)) {
			try {
				return getElement(new XPathDescription("//div[@id='CommonMessageBox']")).getInnerText().contains("Error");
			} catch (GeneralLeanFtException e) {
				logger.error(e, e);
			}
		}
		return false;
	}
	
	public String getErrorMsg() {
		String result = getPopupMsg();
		clickButton(new XPathDescription("//div[@id='CommonMessageBox']//button[starts-with(@class,'cdtPrimaryButton ')]"));
        return result;
	}
	
    public String getPopupMsg() {
        String result = "";
        if (waitElement(label_PopMsg)) {
            try {
                result = getElement(label_PopMsg).getInnerText();
            }
            catch (GeneralLeanFtException e) {
                logger.error(e, e);
            }
            result = result.replace("<br>", "");
            result = result.replace("</br>", "");
            result = result.replace("\n", "");
            result = result.replace("\r", "");
        }      
        return result;
    }
}
