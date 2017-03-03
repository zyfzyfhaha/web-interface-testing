package com.ssc.ssgm.fx.refdata.client;

import java.util.List;
import java.util.Map;

/**
 * The interface client to call idf service.
 * @author e576929
 **/
public interface IClientService {
    /**
     * Call idf service.
     * @param reuquestURL
     *            the idf service url.
     * @return List<<Map<String, Object>>
     */
    List<Map<String, Object>> callService(String reuquestURL);
    /**
     * Call idf service.
     * @param reuquestURL
     *            the idf service url.
     * @param headerList
     *            the present field order of object[]
     * @return List<Object[]>
     */
    List<Object[]> callService(String reuquestURL, List<String> headerList);
}
