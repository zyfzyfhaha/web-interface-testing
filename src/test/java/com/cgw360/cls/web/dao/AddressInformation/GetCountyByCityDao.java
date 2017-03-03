package com.cgw360.cls.web.dao.AddressInformation;

import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java_cup.runtime.lr_parser;

import org.apache.poi.ss.formula.functions.Count;
import org.nfunk.jep.function.If;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.cgw360.cls.web.dao.BaseDButil;
import com.cgw360.cls.web.model.AddressInformation.JsonAreaData;
import com.cgw360.cls.web.model.AddressInformation.JsonProvince;
import com.google.common.reflect.TypeToken;

@Repository
public class GetCountyByCityDao extends BaseDButil{
	
	public JsonProvince getJson(String outputString){
		Gson gson = new Gson();
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Type typeCollection = new TypeToken<JsonProvince>() {}.getType();
		JsonProvince countyDetail = gson.fromJson(outputString, typeCollection);
		return countyDetail;
		
	}
	
	
	public boolean iscountyDetailExistInDb(String areaCode)throws Throwable{
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select count(*) from auto.area_info where city in (select city from area_info where areacode = '" + areaCode + "')");
		
		Integer count = (Integer) jdbcTemplateAuto.query(stringBuffer.toString(), new ResultSetExtractor<Integer>(){

			@Override
			public Integer extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				int count = 0;
				while(rs.next()){
					count = rs.getInt("count(*)");
				}
				// TODO Auto-generated method stub
				return count;
			}
		});
		
		if(count == 0){
			return false;
		}
		return true;
	}
	
	public JsonProvince getCountyDetailFromDb(String areaCode)throws Throwable{
		
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("select * from auto.area_info where city in (select city from area_info where areacode = '" + areaCode + "')");
		
		JsonProvince countyDetails = (JsonProvince) jdbcTemplateAuto.query(stringBuffer.toString(), new ResultSetExtractor<JsonProvince>(){

			@Override
			public JsonProvince extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				
				JsonProvince countyDetails = new JsonProvince();
				List<JsonAreaData> areaDataList = new ArrayList<JsonAreaData>();
				
				while(rs.next()){
					JsonAreaData areaData = new JsonAreaData();
					areaData.setAreacode(rs.getString("areacode"));
					areaData.setPrivince(rs.getString("province"));
					areaData.setCity(rs.getString("city"));
					if(rs.getString("county") == null){
						areaData.setcounty(rs.getString("county"));
					}
					areaData.setFirst_py(rs.getString("first_py"));
					areaData.setIsvalid(rs.getString("isvalid"));
					areaData.setType(rs.getInt("type"));
					areaDataList.add(areaData);
				}
				countyDetails.setApi("api");
				countyDetails.setStatus("y");
				countyDetails.setStatuscode("10000");
				countyDetails.setData(areaDataList);
				
				// TODO Auto-generated method stub
				return countyDetails;
			}
		});
		return countyDetails;
	}
}
