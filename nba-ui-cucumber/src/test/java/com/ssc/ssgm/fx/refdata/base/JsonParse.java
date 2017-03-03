package com.ssc.ssgm.fx.refdata.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JsonParse {
    
    public Map<String, Object> parseJson(String params){
        Map<String, Object> paramsMap= new HashMap<String, Object>();
        Map<String, Object> currencyPairMap= new HashMap<String, Object>();
        JsonParser jsonParser = new JsonParser();
        JsonElement json = jsonParser.parse(params);
        JsonObject jsonObj=json.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> set = jsonObj.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
        while(iterator.hasNext()){
            Map.Entry<String, JsonElement> entry = iterator.next();
            String key=entry.getKey().toUpperCase();
            String value=entry.getValue().toString();
            if (key.equalsIgnoreCase("currencyPairGroup")) {              
                currencyPairMap = parseJson(value);
                Set<String> setCurrencyPair = currencyPairMap.keySet(); 
                Iterator<String> iter = setCurrencyPair.iterator();
                while (iter.hasNext()) {
                    //paramsMap.put("GROUPID", currencyPairMap.get("GROUPID").toString().replace("\"",""));
                    paramsMap.put("PRICING_SERVICE", currencyPairMap.get("PRICINGSERVICEID").toString().replace("\"",""));
                    paramsMap.put("CCY1", currencyPairMap.get("CCY1").toString().replace("\"",""));
                    paramsMap.put("CCY2", currencyPairMap.get("CCY2").toString().replace("\"",""));
                    break;
                }
                              
            }else if (key.equalsIgnoreCase("ccy1tiers")||key.equalsIgnoreCase("ccy2tiers")) {
                continue;
                
            }else{
                if (key.equalsIgnoreCase("ccy1MinimalSize")) {
                    key = "CCY1_MINIMUM"; 
                }else if (key.equalsIgnoreCase("ccy2MinimalSize")) {
                    key = "CCY2_MINIMUM"; 
                }else if (key.equalsIgnoreCase("ccy1NormalSize")) {
                    key = "CCY1_NORMAL_SIZE"; 
                }else if (key.equalsIgnoreCase("ccy2NormalSize")) {
                    key = "CCY2_NORMAL_SIZE"; 
                }else if (key.equalsIgnoreCase("cutoff")) {
                    key = "DEADLINE"; 
                }
                paramsMap.put(key, value.replace("\"", ""));
            }
        }
        return paramsMap;
    }

}
