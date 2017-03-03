package com.cgw360.cls.web.client;

import java.util.List;
import java.util.Map;

public interface IClientService {
	
//	 /**
//     * Call service.
//     * @param reuquestURL
//     *            the service url.
//     * @return List<<Map<String, Object>>
//     */
//    List<Map<String, Object>> callService(String reuquestURL);
//    /**
//     * Call service.
//     * @param reuquestURL
//     *            the service url.
//     * @param headerList
//     *            the present field order of object[]
//     * @return List<Object[]>
//     */
    String callService(String reuquestURL) throws Throwable;

}
