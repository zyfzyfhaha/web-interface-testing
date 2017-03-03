package com.ssc.ssgm.fx.refdata.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;

public class BaseTestUtil {

	protected static final Logger logger = Logger.getLogger(BaseTestUtil.class);
	
	public static void assertListEqual(List<String> expectList,
			List<String> actualList) {
		Assert.assertEquals("The size is not equal, size of expect list is "
				+ expectList.size() + ", but the size of actual list is "
				+ actualList.size(), expectList.size(), actualList.size());
		for (int i = 0; i < expectList.size(); i++) {
			Assert.assertEquals("The value in list index "+i+" is not equal, expect value is "
					+ expectList.get(i) + ", but actual value is "
					+ actualList.get(i), expectList.get(i), actualList.get(i));
		}
	}
	
	public static void assertEqual(String msg, String expectValue,
			String actualValue) {
		Assert.assertEquals("The value of " +msg+ " is not equal, expect is "
				+ expectValue + ", but actual is "
				+ actualValue, expectValue, actualValue);
	}
	
	public static List<Object[]> getCurrecncyPairFromMap(Map<String, String> input){
		List<Object[]> currencyPairList = new ArrayList<Object[]>();
		String pricingService = input.get("Pricing Service").substring(0, 3);
		String ccy1 = input.get("CCY1");
		String ccy2 = input.get("CCY2");
		currencyPairList.add(new String[]{pricingService+"_"+ccy1+"_"+ccy2});
		currencyPairList.add(new String[]{pricingService+"_"+ccy2+"_"+ccy1});
		return currencyPairList;
	}
	
	public static boolean isEqualValue(String value1, String value2, String name) {
        if (value1 == null || value1.isEmpty()) {
            if (value2 != null && !value2.isEmpty()) {
                logger.error("check equals: " + name + " is null");
                return false;
            }
        } else if (!value1.equals(value2)) {
            logger.error("check equals: " + name + " is " + value1 + " when another is " + value2);
            return false;
        }
        return true;
    }
	
	public static boolean isEqualValue(List<String[]> value1, List<String[]> value2, String name) {
        if (value1 == null || value1.isEmpty()) {
            if (value2 != null && !value2.isEmpty()) {
                logger.error("check equals: " + name + " is null");
                return false;
            }
        } else if (value1.size()!=value2.size()) {
            logger.error("check equals: " + name + "'s size is " + value1.size() + " when another is " + value2.size());
            return false;
        } else {
			for (int i = 0; i < value1.size(); i++) {
				if (value1.get(i).length!=value2.get(i).length) {
					logger.error("check equals: " + name + "'s size of row " + i + " is " + value1.get(i).length + " when another is " + value2.get(i).length);
		            return false;
				}
				for (int j = 0; j < value1.get(i).length; j++) {
					if (!value1.get(i)[j].equals(value2.get(i)[j])) {
						logger.error("check equals: " + name + " row " + i + " column " + j +" is " + value1.get(i)[j] + " when another is " + value2.get(i)[j]);
			            return false;
					}
				}
			}
		}
        return true;
    }
	
	public static String getPanelMonthValue(String panelmonth) {
        String month;
        switch (panelmonth) {

        case "JAN":
            month = "01";
            break;
        case "FEB":
            month = "02";
            break;
        case "MAR":
            month = "03";
            break;
        case "APR":
            month = "04";
            break;
        case "MAY":
            month = "05";
            break;
        case "JUN":
            month = "06";
            break;
        case "JUL":
            month = "07";
            break;
        case "AUG":
            month = "08";
            break;
        case "SEP":
            month = "09";
            break;
        case "OCT":
            month = "10";
            break;
        case "NOV":
            month = "11";
            break;
        case "DEC":
            month = "12";
            break;
        default:
            month = "error";
        }
        return month;
    }
}
