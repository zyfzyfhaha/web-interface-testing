package com.ssc.ssgm.fx.refdata.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssc.ssgm.fx.refdata.model.FundInstHedge;
import com.ssc.ssgm.fx.refdata.util.FileUtil;

@Repository
public class HedgeTradeDao extends BaseDBUtil {
	public void deleteHedgeSubscriptionByCustIdInstrument(String fileName,
			String custId, String instrumentName) {
		String content = FileUtil.readFile(fileName).trim();
		content = content.replaceAll("CUST_ID_DETAIL", custId).replaceAll(
				"INSTRUMENT_NAME_DETAIL", instrumentName);
		List<String> sqls = new ArrayList<String>(Arrays.asList(content
				.split(";#")));
		executeSQL(sqls);
	}

	public void insertHedgeSubscription(String fileName) {
		String content = FileUtil.readFile(fileName).trim();
		List<String> sqls = new ArrayList<String>(Arrays.asList(content
				.split(";#")));
		executeSQL(sqls);
	}
	public List<FundInstHedge> getHedgeFromDbByCustId_Inst_CcyPair(
			String custid, String instrumentNmae, String ccy1, String ccy2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
}
