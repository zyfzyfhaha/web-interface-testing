package com.cgw360.cls.web.dao.AddressInformation;

import gherkin.deps.com.google.gson.Gson;
import gherkin.deps.com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;







import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.cgw360.cls.web.dao.BaseDButil;
import com.cgw360.cls.web.model.AddressInformation.JsonAreaData;
import com.cgw360.cls.web.model.AddressInformation.JsonProvince;
import com.google.common.reflect.TypeToken;

@Repository
public class GetProvinceDao extends BaseDButil{
	
	public JsonProvince getJson(String jsonString)throws Throwable{
		Gson gson = new Gson();
		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		Type collectionType = new TypeToken<JsonProvince>() {}.getType();
		JsonProvince provinceDetail = gson.fromJson(jsonString, collectionType);
		return provinceDetail;
	}
	

	public JsonProvince getProvinceDetail() throws Throwable{
		
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("SELECT * FROM auto.area_info");
		JsonProvince provinceDetail = (JsonProvince) jdbcTemplateAuto.query(stringBuffer.toString(), new ResultSetExtractor<JsonProvince>(){

			@Override
			public JsonProvince extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				
				JsonProvince provinceDetail = new JsonProvince();
				List<JsonAreaData> areaDataList = new ArrayList<JsonAreaData>();
				while(rs.next()){
					JsonAreaData areaData = new JsonAreaData();
					areaData.setAreacode(rs.getString("areacode"));
					areaData.setPrivince(rs.getString("province"));
					areaData.setFirst_py(rs.getString("first_py"));
					areaData.setIsvalid(rs.getString("isvalid"));
					areaData.setType(rs.getInt("type"));
					areaDataList.add(areaData);
				}
				provinceDetail.setApi("api");
				provinceDetail.setStatus("y");
				provinceDetail.setStatuscode("10000");
				provinceDetail.setData(areaDataList);
				
				
				// TODO Auto-generated method stub
				return provinceDetail;
			}
		});
		
		return provinceDetail;
	}
	
}
