package com.ssc.ssgm.fx.refdata.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ssc.ssgm.fx.refdata.dao.BaseDBUtil;
import com.ssc.ssgm.fx.refdata.model.PricingService;

@Repository
public class PricingServiceDao extends BaseDBUtil {

	public List<PricingService> getPricingServiceFromDB() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer
				.append("SELECT * FROM NBADBA.PRICING_SERVICE ORDER BY PRICING_SERVICE_ID");

		List<PricingService> psList = (List<PricingService>) jdbcNBATemplate
				.query(stringBuffer.toString(),
						new ResultSetExtractor<List<PricingService>>() {
							@Override
							public List<PricingService> extractData(
									final ResultSet rs) throws SQLException {
								List<PricingService> psDBList = new ArrayList<PricingService>();
								while (rs.next()) {
									PricingService ps = new PricingService();
									ps.setPricingServiceId(rs.getString("PRICING_SERVICE_ID"));
									ps.setShortName(rs.getString("SHORT_NAME"));
									ps.setFullName(rs.getString("FULL_NAME"));
									ps.setOmsProdCateId(rs.getString("OMS_PROD_CATE_ID"));
									ps.setUpdatedBy(rs.getString("UPDATED_BY"));
									ps.setUpdatedDatetime(rs.getString("UPDATED_DATETIME"));
									psDBList.add(ps);
								}
								return psDBList;
							}
						});
		return psList;
	}

}
