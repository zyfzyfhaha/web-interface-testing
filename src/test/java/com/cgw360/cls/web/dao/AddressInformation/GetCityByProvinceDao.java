package com.cgw360.cls.web.dao.AddressInformation;

import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.impl.PublicImpl;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;







import com.cgw360.cls.web.dao.BaseDButil;
import com.cgw360.cls.web.model.AddressInformation.JsonAreaData;
import com.cgw360.cls.web.model.AddressInformation.JsonProvince;
import com.google.common.reflect.TypeToken;

@Repository
public class GetCityByProvinceDao extends BaseDButil{
	
	public JsonProvince getJson(String jsonString)throws Throwable{
		Gson gson = new Gson();
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Type collectionType = new TypeToken<JsonProvince>() {}.getType();
		JsonProvince cityDetail = gson.fromJson(jsonString, collectionType);
		return cityDetail;
		
	}
	
	
	public boolean isCityDetailInDb(String areaCode)throws Throwable{
		
		StringBuffer  stringBuffer = new StringBuffer();
		stringBuffer.append("select count(*) from area_info where province in (select province from area_info where areacode = '" + areaCode + "')");
		
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
		
		if(count != 0){
			return true;
		}
		return false;
	}

	public JsonProvince getCityDetailFromDb(String areaCode)throws Throwable{
		StringBuffer  stringBuffer = new StringBuffer();
		stringBuffer.append("select * from area_info where province in (select province from area_info where areacode = '" + areaCode + "') group by city");
		
		JsonProvince cityDetails = (JsonProvince) jdbcTemplateAuto.query(stringBuffer.toString(), new ResultSetExtractor<JsonProvince>(){
			@Override
			public JsonProvince extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				JsonProvince cityDetails = new JsonProvince();
				List<JsonAreaData> areaDataList = new ArrayList<JsonAreaData>();
				while(rs.next()){
					JsonAreaData areaData = new JsonAreaData();
					areaData.setAreacode(rs.getString("areacode"));
					areaData.setPrivince(rs.getString("province"));
					if(rs.getString("city") != null){
						areaData.setCity(rs.getString("city"));
					}
					areaData.setFirst_py(rs.getString("first_py"));
					areaData.setIsvalid(rs.getString("isvalid"));
					areaData.setType(rs.getInt("type"));
					areaDataList.add(areaData);
				}
				cityDetails.setApi("api");
				cityDetails.setStatus("y");
				cityDetails.setStatuscode("10000");
				cityDetails.setData(areaDataList);
				
				// TODO Auto-generated method stub
				return cityDetails;
			}
		});
		return cityDetails;
	}
	
	public boolean compare(JsonProvince expectedResults, JsonProvince actualResults)throws Throwable{
		
		
		return false;
		
		
	}
	
}
