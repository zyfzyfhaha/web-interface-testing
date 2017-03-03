package com.cgw360.cls.web.common;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

//import com.ssc.ssgm.fx.nba.client.IClientService;



import com.cgw360.cls.web.client.IClientService;

import cucumber.api.DataTable;
import cucumber.api.Scenario;

public abstract class AbstractStepdefs {

	/**
	 * Client Service.
	 */
	public static IClientService client;
	/**
	 * the cucumber printer instance.
	 */
	public CucumberDiffer differ;
	/**
	 * the cucumber scenario instance.
	 */
	public Scenario scenario;
	/**
	 * running tag name;
	 */
	public String tagName;
	/**
	 * Result for service.
	 */
	public List<Object[]> result;
	
	public String resultString;

	public List<String> headerList = Arrays.asList("JSON", "__errorMsg");

	/**
	 * to create the cucumber printer object.
	 * 
	 * @param scenario
	 *            is the object in cucumber which can write content to report.
	 */
	public void before(final Scenario scenario) {
		this.scenario = scenario;
		this.differ = new CucumberDiffer(scenario);
		Collection<String> coll = scenario.getSourceTagNames();
		tagName = coll.iterator().next();
	}

	/**
	 * prepare test Cloud class.
	 * 
	 * @throws Throwable
	 */
	public void init() throws Throwable {
		Properties properties = new Properties();
		properties.load(this.getClass().getClassLoader()
				.getResourceAsStream("config.properties"));

		Class<IClientService> clz = (Class<IClientService>) Class
				.forName(properties.getProperty("clientService"));
		Constructor<IClientService> constructor = clz
				.getConstructor(new Class[] { Properties.class });
		client = constructor.newInstance(new Object[] { properties });
	}

	/**
	 * GET check expectHeader
	 * 
	 * @param result
	 * @param expectHeader
	 * @return list
	 */
	public List<Object[]> getExpectResult(List<Object[]> result,
			List<String> idfOutPut, String[] expectHeader) {
		int[] indexs = new int[expectHeader.length];
		for (int i = 0; i < expectHeader.length; i++) {
			if (idfOutPut.contains(expectHeader[i])) {
				indexs[i] = idfOutPut.indexOf(expectHeader[i]);
			}
		}
		List<Object[]> expectData = new ArrayList<Object[]>();
		for (Object[] obj : result) {
			Object[] value = new Object[expectHeader.length];
			for (int j = 0; j < indexs.length; j++) {
				value[j] = obj[indexs[j]];
			}
			expectData.add(value);
		}
		return expectData;
	}

	/**
	 * compare expect data vs idf data
	 * 
	 * @param result
	 * @param expectable
	 * @param idfOutPut
	 * @param expectHeader
	 */
	public void diffData(List<Object[]> result, DataTable expectable,
			List<String> idfOutPut, String[] expectHeader) {
		List<Object[]> data = getExpectResult(result, idfOutPut, expectHeader);
		data.add(0, expectHeader);
		DataTable actual = DataTable.create(data);
		differ.diff(expectable, actual);
	}

	/**
	 * Call IDF Service
	 * 
	 * @param idfNumber
	 * @param inputJson
	 * @param user
	 * @return OutputJson and Error Message
	 */
//	public List<Object[]> callIdfServiceByIdfNumber(String idfNumber,
//			String inputJson, String user) {
//
//		StringBuffer requestURL = new StringBuffer();
//		requestURL = requestURL.append("__request=").append(idfNumber)
//				.append("&JSON=").append(inputJson).append("&USER=")
//				.append(user);
//
//		result = client.callService(requestURL.toString(), headerList);
//		return result;
//
//	}
	
//	public String callIdfServiceByIdfNumber(String idfNumber,
//	String inputJson, String user) {
//
//StringBuffer requestURL = new StringBuffer();
//requestURL = requestURL.append("__request=").append(idfNumber)
//		.append("&JSON=").append(inputJson).append("&USER=")
//		.append(user);
//
//	resultString = client.callService(requestURL.toString());
//	return resultString;

//}
	

	/**
	 * 
	 * @param result
	 * @return OutputJson
	 */
	public String getOutputJson(List<Object[]> result) {
		String outputJson = (result.get(0))[0].toString();
		return outputJson;
	}

	/**
	 * 
	 * @param result
	 * @return Error Message
	 */
	public String getErrMsg(List<Object[]> result) {
		String error = (result.get(0))[1].toString();
		return error;
	}

	public String urlEncodingForspecial(String s) {
		// Sepcial Character: %-%25; ^-%5E; &-%26; +-%2B; /-%2F;\-%5C; |-%7C;
		// "-%22
		s = s.replace("%", "%25");
		s = s.replace("#", "%23").replace("&", "%26").replace("_", "%5F")
				.replace("+", "%2B").replace("/", "//").replace("\\", "\\\\").replace("|", "%7C")
				.replace("\"", "\\\"");
		return s;

	}

}
