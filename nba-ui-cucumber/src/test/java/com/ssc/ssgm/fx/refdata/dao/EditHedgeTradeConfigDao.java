package com.ssc.ssgm.fx.refdata.dao;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ssc.ssgm.fx.refdata.model.HedgeProperty;
import com.ssc.ssgm.fx.refdata.model.HedgeTradeDetail;
import com.ssc.ssgm.fx.refdata.model.JsonFundBookingCode;
import com.ssc.ssgm.fx.refdata.model.JsonHedgeTrade;

@Repository
public class EditHedgeTradeConfigDao extends BaseDBUtil 

{

	public JsonHedgeTrade getResultsFromJson(String Json) 
	{
		Gson gson = new Gson();
		Type collectionType = new TypeToken<JsonHedgeTrade>() {}.getType();
		JsonHedgeTrade hedgeTrade = gson.fromJson(Json, collectionType);
		return hedgeTrade;
	}
	
	
	public List<HedgeTradeDetail> getHedgeTradeDetailsFromDb(List<String> xrefFundInstrumentDetailIdList)throws Throwable
	{
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT * FROM NBADBA.XREF_FUND_INST_HEDGE WHERE XREF_FUND_INSTRUMENT_DETAIL_ID IN (");
		
		for(String xrefFundInstrumentDetailId : xrefFundInstrumentDetailIdList)
			{
				stringBuffer.append("'").append(xrefFundInstrumentDetailId).append("',");
			}
			stringBuffer.replace(stringBuffer.length()-1, stringBuffer.length(),")");
		
		
		List<HedgeTradeDetail> hedgeTradeDetailsList  = (List<HedgeTradeDetail>) jdbcTemplate.query
				(stringBuffer.toString(), new ResultSetExtractor<List<HedgeTradeDetail>>()
						{

							@Override
							public List<HedgeTradeDetail> extractData(
									ResultSet rs) throws SQLException,
									DataAccessException 
									{
								List<HedgeTradeDetail> hedgeTradeDetailsList = new ArrayList<HedgeTradeDetail>();
								
								
										while(rs.next())
										{
											HedgeTradeDetail hedgeTradeDetail = new HedgeTradeDetail();
											hedgeTradeDetail.setPercentage(rs.getBigDecimal("PERCENTAGE"));
											hedgeTradeDetail.setIsTargetDate(rs.getString("IS_TENOR_TARGET_DATE"));
											hedgeTradeDetail.setTenorValue(rs.getString("TENOR_VALUE"));
											hedgeTradeDetail.setIsEligible(rs.getString("ELIGIBLE"));
											hedgeTradeDetail.setXrefFundInstrumentDetailId(rs.getLong("XREF_FUND_INSTRUMENT_DETAIL_ID"));
											hedgeTradeDetail.setLastUpdatedBy(rs.getString("UPDATED_BY"));
											hedgeTradeDetail.setLastUpdatedDttm(rs.getDate("UPDATED_DATETIME"));
											hedgeTradeDetailsList.add(hedgeTradeDetail);
										}
										
										return hedgeTradeDetailsList;
									}
						}
				);
		return hedgeTradeDetailsList;
	}
	
	
		
	
	
	public List<HedgeTradeDetail> getHedgeTradeDetailsFromAdtDb(List<String> xrefFundInstrumentDetailIdList)throws Throwable
	{
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT * FROM NBADBA.ADT_XREF_FUND_INST_HEDGE WHERE XREF_FUND_INSTRUMENT_DETAIL_ID IN (");
		
		for(String xrefFundInstrumentDetailId : xrefFundInstrumentDetailIdList)
			{
				stringBuffer.append("'").append(xrefFundInstrumentDetailId).append("',");
			}
			stringBuffer.replace(stringBuffer.length()-1, stringBuffer.length(),")");
		
		
		List<HedgeTradeDetail> hedgeTradeDetailsList  = (List<HedgeTradeDetail>) jdbcTemplate.query
				(stringBuffer.toString(), new ResultSetExtractor<List<HedgeTradeDetail>>()
						{

							@Override
							public List<HedgeTradeDetail> extractData(
									ResultSet rs) throws SQLException,
									DataAccessException 
									{
								List<HedgeTradeDetail> hedgeTradeDetailsList = new ArrayList<HedgeTradeDetail>();
								
								
										while(rs.next())
										{
											HedgeTradeDetail hedgeTradeDetail = new HedgeTradeDetail();
											hedgeTradeDetail.setPercentage(rs.getBigDecimal("PERCENTAGE"));
											hedgeTradeDetail.setIsTargetDate(rs.getString("IS_TENOR_TARGET_DATE"));
											hedgeTradeDetail.setTenorValue(rs.getString("TENOR_VALUE"));
											hedgeTradeDetail.setIsEligible(rs.getString("ELIGIBLE"));
											hedgeTradeDetail.setXrefFundInstrumentDetailId(rs.getLong("XREF_FUND_INSTRUMENT_DETAIL_ID"));
											hedgeTradeDetail.setLastUpdatedBy(rs.getString("UPDATED_BY"));
											hedgeTradeDetail.setLastUpdatedDttm(rs.getDate("UPDATED_DATETIME"));
											hedgeTradeDetail.setActionCd(rs.getString("ACTION_CD"));
											hedgeTradeDetailsList.add(hedgeTradeDetail);
										}
										
										return hedgeTradeDetailsList;
									}
						}
				);
		return hedgeTradeDetailsList;
	}
	
	
	public JsonFundBookingCode getFundBookingCodeFromDb(String custId)throws Throwable
	{
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT * FROM NBADBA.FUND_HEDGE_BOOKING_CODE WHERE CUST_ID = '"+ custId +"'");
		

		JsonFundBookingCode fundBookingCode  = (JsonFundBookingCode) jdbcTemplate.query
				(stringBuffer.toString(), new ResultSetExtractor<JsonFundBookingCode>()
						{

							@Override
							public JsonFundBookingCode extractData(
									ResultSet rs) throws SQLException,
									DataAccessException 
									{
											JsonFundBookingCode fundBookingCode = new JsonFundBookingCode();
								
										while(rs.next())
										{
											
											fundBookingCode.setCustId(rs.getLong("CUST_ID"));
											fundBookingCode.setFundBookingCode(rs.getString("FUND_BOOKING_CODE"));
											fundBookingCode.setLastUpdatedBy(rs.getString("UPDATED_BY"));
											fundBookingCode.setLastUpdatedDttm(rs.getDate("UPDATED_DATETIME"));
										}
										
										return fundBookingCode;
									}
						}
				);
		return fundBookingCode;
	}
	
	
	
	public JsonFundBookingCode getFundBookingCodeFromAdtDb(String custId)throws Throwable
	{
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT * FROM NBADBA.ADT_FUND_HEDGE_BOOKING_CODE WHERE CUST_ID = '"+ custId +"'");
		

		JsonFundBookingCode fundBookingCode  = (JsonFundBookingCode) jdbcTemplate.query
				(stringBuffer.toString(), new ResultSetExtractor<JsonFundBookingCode>()
						{

							@Override
							public JsonFundBookingCode extractData(
									ResultSet rs) throws SQLException,
									DataAccessException 
									{
								JsonFundBookingCode fundBookingCode = new JsonFundBookingCode();
								
										while(rs.next())
										{
											fundBookingCode.setCustId(rs.getLong("CUST_ID"));
											fundBookingCode.setFundBookingCode(rs.getString("FUND_BOOKING_CODE"));
											fundBookingCode.setLastUpdatedBy(rs.getString("UPDATED_BY"));
											fundBookingCode.setLastUpdatedDttm(rs.getDate("UPDATED_DATETIME"));
											fundBookingCode.setActionCd(rs.getString("ACTION_CD"));
										}
										
										return fundBookingCode;
									}
						}
				);
		return fundBookingCode;
	}
	
	
	
	public boolean isAdtHedgeTradeExist(List<String> xrefFundInstrumentDetailIdList)throws Throwable
	{
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT COUNT(*) FROM NBADBA.ADT_XREF_FUND_INST_HEDGE WHERE XREF_FUND_INSTRUMENT_DETAIL_ID IN (");
		
		for(String xrefFundInstrumentDetailId : xrefFundInstrumentDetailIdList)
			{
				stringBuffer.append("'").append(xrefFundInstrumentDetailId).append("',");
			}
			stringBuffer.replace(stringBuffer.length()-1, stringBuffer.length(),")");
		
		
		Integer count  = (Integer) jdbcTemplate.query
				(stringBuffer.toString(), new ResultSetExtractor<Integer>()
						{

							@Override
							public Integer extractData(
									ResultSet rs) throws SQLException,
									DataAccessException 
									{
								int count = 0;
								
										while(rs.next())
										{
											count = rs.getInt("COUNT(*)");
										}
										
										return count;
									}
						}
				);
		
		if(count == 0)
		{
			return true;
		}
		
		else 
		{
			return false;
		}
		
	}
	
	
	public boolean isAdtFundBookingCodeExist(String custId)throws Throwable
	{
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT COUNT(*) FROM NBADBA.ADT_FUND_HEDGE_BOOKING_CODE WHERE CUST_ID = '"+ custId +"'");
		
		Integer count  = (Integer) jdbcTemplate.query
				(stringBuffer.toString(), new ResultSetExtractor<Integer>()
						{

							@Override
							public Integer extractData(
									ResultSet rs) throws SQLException,
									DataAccessException 
									{
								int count = 0;
								
										while(rs.next())
										{
											count = rs.getInt("COUNT(*)");
										}
										
										return count;
									}
						}
				);
		
		if(count == 0)
		{
			return true;
		}
		
		else 
		{
			return false;
		}
		
	}
	
	
	
	
	public boolean compare(List<HedgeTradeDetail> expectedResults, List<HedgeTradeDetail> actualResults)
	{
		if(expectedResults.size() != actualResults.size())
		{
			return false;
		}
			
		else
		{
			for (HedgeTradeDetail a : actualResults)
			{
				if(!expectedResults.contains(a))
					return false;
			}
		}
		return true;
	}
	
	
	public boolean isHedgePropertyExist(List<String> xrefFundInstrumentDetailIdList)throws Throwable
	{
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT COUNT(*) FROM NBADBA.HEDGE_PROPERTY WHERE FUND_INST_HEDGE_ID IN (");
		
		for(String xrefFundInstrumentDetailId : xrefFundInstrumentDetailIdList)
			{
				stringBuffer.append("'").append(xrefFundInstrumentDetailId).append("',");
			}
			stringBuffer.replace(stringBuffer.length()-1, stringBuffer.length(),")");
		
		
		Integer count  = (Integer) jdbcTemplate.query
				(stringBuffer.toString(), new ResultSetExtractor<Integer>()
						{

							@Override
							public Integer extractData(
									ResultSet rs) throws SQLException,
									DataAccessException 
									{
								int count = 0;
								
										while(rs.next())
										{
											count = rs.getInt("COUNT(*)");
										}
										
										return count;
									}
						}
				);
		
		if(count == 0)
		{
			return true;
		}
		
		else 
		{
			return false;
		}
		
	}
	
	
	public boolean isAdtHedgePropertyExist(List<String> xrefFundInstrumentDetailIdList)throws Throwable
	{
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT COUNT(*) FROM NBADBA.ADT_HEDGE_PROPERTY WHERE FUND_INST_HEDGE_ID IN (");
		
		for(String xrefFundInstrumentDetailId : xrefFundInstrumentDetailIdList)
			{
				stringBuffer.append("'").append(xrefFundInstrumentDetailId).append("',");
			}
			stringBuffer.replace(stringBuffer.length()-1, stringBuffer.length(),")");
		
		
		Integer count  = (Integer) jdbcTemplate.query
				(stringBuffer.toString(), new ResultSetExtractor<Integer>()
						{

							@Override
							public Integer extractData(
									ResultSet rs) throws SQLException,
									DataAccessException 
									{
								int count = 0;
								
										while(rs.next())
										{
											count = rs.getInt("COUNT(*)");
										}
										
										return count;
									}
						}
				);
		
		if(count == 0)
		{
			return true;
		}
		
		else 
		{
			return false;
		}
		
	}
	
	
	public List<HedgeProperty> getHedgePropertyFromDb(List<String> xrefFundInstrumentDetailIdList)throws Throwable
	{
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT * FROM NBADBA.HEDGE_PROPERTY WHERE FUND_INST_HEDGE_ID IN (");
		
		for(String xrefFundInstrumentDetailId : xrefFundInstrumentDetailIdList)
			{
				stringBuffer.append("'").append(xrefFundInstrumentDetailId).append("',");
			}
			stringBuffer.replace(stringBuffer.length()-1, stringBuffer.length(),")");
		

			List<HedgeProperty> hedgePropertyList  = (List<HedgeProperty>) jdbcTemplate.query
				(stringBuffer.toString(), new ResultSetExtractor<List<HedgeProperty>>()
						{

							@Override
							public List<HedgeProperty> extractData(
									ResultSet rs) throws SQLException,
									DataAccessException 
									{
								List<HedgeProperty> hedgePropertyList = new ArrayList<HedgeProperty>();
										while(rs.next())
										{
											HedgeProperty hedgeProperty = new HedgeProperty();
											hedgeProperty.setFundHedgeId(rs.getString("FUND_INST_HEDGE_ID"));
											hedgeProperty.setBenchmarkDate(rs.getString("BENCHMARK_DATE"));
											hedgeProperty.setMonthEndDate(rs.getString("MONTH_END_DATE"));
											hedgeProperty.setUpdatedBy(rs.getString("UPDATED_BY"));
											hedgePropertyList.add(hedgeProperty);
										}
										
										return hedgePropertyList;
									}
						}
				);
		return hedgePropertyList;
	}
	
	
	
	public List<HedgeProperty> getHedgePropertyFromAdtDb(List<String> xrefFundInstrumentDetailIdList)throws Throwable
	{
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT * FROM NBADBA.ADT_HEDGE_PROPERTY WHERE FUND_INST_HEDGE_ID IN (");
		
		for(String xrefFundInstrumentDetailId : xrefFundInstrumentDetailIdList)
			{
				stringBuffer.append("'").append(xrefFundInstrumentDetailId).append("',");
			}
			stringBuffer.replace(stringBuffer.length()-1, stringBuffer.length(),")");
		

			List<HedgeProperty> hedgePropertyList  = (List<HedgeProperty>) jdbcTemplate.query
				(stringBuffer.toString(), new ResultSetExtractor<List<HedgeProperty>>()
						{

							@Override
							public List<HedgeProperty> extractData(
									ResultSet rs) throws SQLException,
									DataAccessException 
									{
								List<HedgeProperty> hedgePropertyList = new ArrayList<HedgeProperty>();
										while(rs.next())
										{
											HedgeProperty hedgeProperty = new HedgeProperty();
											hedgeProperty.setFundHedgeId(rs.getString("FUND_INST_HEDGE_ID"));
											hedgeProperty.setBenchmarkDate(rs.getString("BENCHMARK_DATE"));
											hedgeProperty.setMonthEndDate(rs.getString("MONTH_END_DATE"));
											hedgeProperty.setUpdatedBy(rs.getString("UPDATED_BY"));
											hedgeProperty.setActionCd(rs.getString("ACTION_CD"));
											hedgeProperty.setAuditBy(rs.getString("AUDIT_BY"));
											hedgePropertyList.add(hedgeProperty);
										}
										
										return hedgePropertyList;
									}
						}
				);
		return hedgePropertyList;
	}
	
	
	
	public boolean compare2(List<HedgeProperty> expectedResults, List<HedgeProperty> actualResults)
	{
		if(expectedResults.size() != actualResults.size())
		{
			return false;
		}
			
		else
		{
			for (HedgeProperty a : actualResults)
			{
				if(!expectedResults.contains(a))
					return false;
			}
		}
		return true;
	}
	
}
