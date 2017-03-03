package com.ssc.ssgm.fx.refdata.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ssc.ssgm.fx.refdata.base.BaseWebDriver;
import com.ssc.ssgm.fx.refdata.model.OrderTolerance;

@Repository
public class OrderDAO extends BaseDBUtil{

    public List<OrderTolerance> getQueryData(Map<String, String> criteria) {
        String sqlStr = "select distinct CPG.GROUP_ID, CPG.CCY1,CPG.CCY2,CPG.PRICING_SERVICE_ID,OMS1.MINIMAL_SIZE AS CCY1_MINIMUM,OMS2.MINIMAL_SIZE AS CCY2_MINIMUM " 
                +" from CURRENCY_PAIR_GROUP CPG LEFT JOIN ORDER_MINIMAL_SIZE OMS1 ON OMS1.GROUP_ID=CPG.GROUP_ID ";        
        if (criteria.containsKey("CCY1")&&!criteria.get("CCY1").equals("@Ignore")&&!criteria.get("CCY1").equals("@Null")) {           
            sqlStr = sqlStr +" AND OMS1.CURRENCY ='" + criteria.get("CCY1") + "' ";
        } 
        
        sqlStr = sqlStr + " LEFT JOIN ORDER_MINIMAL_SIZE OMS2 ON OMS2.GROUP_ID=CPG.GROUP_ID"; 
        if (criteria.containsKey("CCY2")&&!criteria.get("CCY2").equals("@Ignore")&&!criteria.get("CCY2").equals("@Null")) {
            sqlStr = sqlStr +" AND OMS2.CURRENCY ='" + criteria.get("CCY2") + "' ";
        }
                
        sqlStr = sqlStr + " WHERE 1=1";
        if (criteria.containsKey("PRICING_SERVICE_ID")&&!criteria.get("PRICING_SERVICE_ID").equals("@Ignore")&&!criteria.get("PRICING_SERVICE_ID").equals("@Null")) {
            sqlStr = sqlStr + " and CPG.PRICING_SERVICE_ID='" + criteria.get("PRICING_SERVICE_ID") + "'";
        }        
        if (criteria.containsKey("CCY1")&&!criteria.get("CCY1").equals("@Ignore")&&!criteria.get("CCY1").equals("@Null")&&criteria.containsKey("CCY2")&&!criteria.get("CCY2").equals("@Ignore")&&!criteria.get("CCY2").equals("@Null")) {
            sqlStr = sqlStr + " and ((CPG.CCY1='" + criteria.get("CCY1") + "' and CPG.CCY2='" + criteria.get("CCY2") + "') or (CPG.CCY2='" + criteria.get("CCY1") + "' and CPG.CCY1='" + criteria.get("CCY2") + "'))";
        }else if (criteria.containsKey("CCY1")&&!criteria.get("CCY1").equals("@Ignore")&&!criteria.get("CCY1").equals("@Null")) {           
            sqlStr = sqlStr +" and (CPG.CCY1='" + criteria.get("CCY1") + "' or CPG.CCY2='" + criteria.get("CCY1") + "')";
        }else if (criteria.containsKey("CCY2")&&!criteria.get("CCY2").equals("@Ignore")&&!criteria.get("CCY2").equals("@Null")) {
            sqlStr = sqlStr +" and (CPG.CCY1='" + criteria.get("CCY2") + "' or CPG.CCY2='" + criteria.get("CCY2") + "')";
        }

        List<OrderTolerance> orderList = jdbcTemplate.query(sqlStr,
                new ResultSetExtractor<List<OrderTolerance>>() {
                    @Override
                    public List<OrderTolerance> extractData(final ResultSet rs)
                            throws SQLException {
                        List<OrderTolerance> orderList = new ArrayList<OrderTolerance>();
                        while (rs.next()) {
                            OrderTolerance order = new OrderTolerance();
                            try {
                                order.setPricingServiceID(rs.getString("PRICING_SERVICE_ID"));
                                order.setCCY1(rs.getString("CCY1"));
                                order.setCCY2(rs.getString("CCY2"));                              
                                order.setCCY1_MINIMUM(rs.getString("CCY1_MINIMUM"));
                                order.setCCY2_MINIMUM(rs.getString("CCY2_MINIMUM"));
                            } catch (Exception e) {
                                LOGGER.error("error:" + e.getMessage());
                            }
                            orderList.add(order);
                        }
                        return orderList;

                    }
                });
        return orderList; 
    }
    
    public void deleteOrderTolerance(OrderTolerance orderTolerance) {
		String groupID = getGroupID(orderTolerance);
		if (groupID != null && !groupID.isEmpty()) {
			List<String> deleteSQLList = new ArrayList<String>();
			deleteSQLList.add("DELETE FROM NBADBA.ORDER_MINIMAL_SIZE WHERE GROUP_ID = '"+groupID+"'");
			deleteSQLList.add("DELETE FROM NBADBA.ORDER_SIZE_TIER WHERE GROUP_ID = '"+groupID+"'");
			if (BaseWebDriver.isUARegion) {
				executeSQLForQAUAT(deleteSQLList);
				return;
			}
			executeSQL(deleteSQLList);
		}
	}
    
    public void deleteOrderNormalSize(OrderTolerance orderTolerance) {
        String groupID = getGroupID(orderTolerance);
        String fundName = orderTolerance.getFund();
        if (groupID != null && !groupID.isEmpty()) {
            List<String> deleteSQLList = new ArrayList<String>();
            deleteSQLList.add("DELETE FROM NBADBA.ORDER_NORMAL_SIZE WHERE GROUP_ID = '"+groupID+"' AND CUST_ID = (SELECT CUST_ID FROM FXDBA.FUND WHERE CUST_SHRT_NM = '" + fundName +"')");
            //deleteSQLList.add("DELETE FROM CURRENCY_PAIR_GROUP WHERE GROUP_ID = '"+groupID+"'");
            if (BaseWebDriver.isUARegion) {
				executeSQLForQAUAT(deleteSQLList);
				return;
			}
            executeSQL(deleteSQLList);
        }
    }
    
    public String getGroupID(OrderTolerance orderTolerance){
    	String ccy1 = orderTolerance.getCCY1();
    	String ccy2 = orderTolerance.getCCY2();
    	String querySQL = "SELECT GROUP_ID FROM NBADBA.CURRENCY_PAIR_GROUP CPG where ((CPG.CCY1='"+ccy1+"' and CPG.CCY2='"+ccy2+"') or ((CPG.CCY1='"+ccy2+"' and CPG.CCY2='"+ccy1+"')))";
    	String pricingString = orderTolerance.getPricingService().substring(0, 3);
    	querySQL = querySQL + " and pricing_service_id = '" + pricingString + "'";
    	List<Map<String,Object>> resultList = querySQL(querySQL);
    	if (resultList.size()==0) {
			return "";
		}
    	String groupID = resultList.get(0).get("GROUP_ID").toString();
    	return groupID;
    }
    
    public void deleteCurrencyPairCutoff(List<Object[]> cutoffList) {
    	String deleteSQL = "Delete from NBADBA.currency_pair_cutoff where group_id = ?";
    	if (BaseWebDriver.isUARegion) {
    		List<String> deleteSQLList = new ArrayList<String>();
    		for (int i = 0; i < cutoffList.size(); i++) {
    			deleteSQLList.add(deleteSQL.replace("?", "'"+cutoffList.get(i)[0]+"'"));
			}
			executeSQLForQAUAT(deleteSQLList);
			return;
		}
    	jdbcTemplate.batchUpdate(deleteSQL, cutoffList);
    }

}
