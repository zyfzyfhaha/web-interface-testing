package com.ssc.ssgm.fx.refdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.support.PageFactory;

import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;
import com.ssc.ssgm.fx.refdata.base.SpringContextHolder;
import com.ssc.ssgm.fx.refdata.model.ui.selenium.TraderMappingPage;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import com.ssc.ssgm.fx.refdata.dao.RefDateDAO;

public class TraderMappingStepDefs {
	
	private TraderMappingPage traderPage = PageFactory.initElements(TraderMappingPage.getWebDriver(), TraderMappingPage.class);
	private RefDateDAO dao = SpringContextHolder.getBean(RefDateDAO.class);
	
	private String[] currentMapping;
	
	@Given("^Navigate to Trader Mapping Page$")
	public void navigate_to_Trader_Mapping_Page() throws Throwable {
		traderPage.goTraderPage();
	}

	@Given("^User Prepare Add Trader Id mapping data$")
	public void user_Prepare_Add_Trader_Id_mapping_data(DataTable inputs) throws Throwable {
		Map<String, String> values = inputs.asMap(String.class, String.class);
		String[] orderedKeys = {"GMBH_WSS_ID","GMBH_REUTERS_ID","SSBT_WSS_ID","SSBT_REUTERS_ID","CITY"};
		currentMapping = BaseStepdefs.trasferMapValue(values, orderedKeys);
		traderPage.setPageValues(values);
	}

	@When("^there is no same trader mapping in database$")
	public void there_is_no_same_trader_mapping_in_database() throws Throwable {
		if (BaseWebDriver.isUARegion) {
			return;
		}
		List<Object[]> btbTraderMappingList = new ArrayList<Object[]>();
        btbTraderMappingList.add(currentMapping);
		dao.clearBTBTraderMapping(btbTraderMappingList);
	}
}
