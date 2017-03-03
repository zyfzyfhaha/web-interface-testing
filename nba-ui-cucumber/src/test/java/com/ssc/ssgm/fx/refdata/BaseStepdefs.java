package com.ssc.ssgm.fx.refdata;

import java.util.Map;

import org.junit.Assert;

import com.ssc.ssgm.fx.refdata.base.BaseTestUtil;
import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class BaseStepdefs {

	private BaseWebDriver baseDriver = new BaseWebDriver();

	@When("^user can get Add result after click Save$")
	public void checkSaveResult(Map<String, String> values) {
		String expectResult = values.get("Result");
		if (expectResult.toUpperCase().contains("DISABLED")) {
			String saveBtnProperty = baseDriver.getSaveButtonProperty();
			Assert.assertEquals("Save button should be disabled. ", "DISABLED",
					saveBtnProperty);
		} else {
			String actualResult = baseDriver.saveNewTolerance();

			Assert.assertEquals("Add Result: ", expectResult, actualResult);
		}

	}

	@When("^user open Add panel$")
	public void openAddPanel() {
		baseDriver.openAddPanel();
	}

	@Then("User click search to query")
	public void clickSearch() throws Exception {
		baseDriver.getSearchButton();
		baseDriver.waitAfterSearch();
	}

	@Then("^verify Query return error message$")
	public void verifyError(Map<String, String> values) throws Throwable {
		String expectValue = values.get("Result");
		String actualValue = baseDriver.getErrorMsg();
		BaseTestUtil.assertEqual("error message", expectValue, actualValue);
	}

	public static String[] trasferMapValue(Map<String, String> map,
			String[] orderedKeys) {
		String[] result = new String[orderedKeys.length];
		for (int i = 0; i < orderedKeys.length; i++) {
			String value = "";
			if (map.containsKey(orderedKeys[i])
					&& !map.get(orderedKeys[i]).equals("@Null")) {
				value = map.get(orderedKeys[i]);
			}
			result[i] = value;
		}
		return result;
	}
}
