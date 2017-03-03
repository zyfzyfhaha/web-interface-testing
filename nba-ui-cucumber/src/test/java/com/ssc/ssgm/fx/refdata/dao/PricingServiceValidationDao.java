package com.ssc.ssgm.fx.refdata.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ssc.ssgm.fx.refdata.model.PricingServiceValidation;

@Repository
public class PricingServiceValidationDao extends BaseDBUtil {

	public List<PricingServiceValidation> get_validation_by_pricing_service_and_desc(
			String pricing_service_criteria, String validation_criteria) {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer
				.append("SELECT * FROM NBADBA.PRICING_SERVICE_VALIDATION WHERE 1=1 ");

		if (StringUtils.isNotBlank(validation_criteria)) {
			stringBuffer.append("AND VALIDATION_DESC = '")
					.append(validation_criteria).append("' ");
		}
		if (StringUtils.isNotBlank(pricing_service_criteria)) {
			stringBuffer.append("AND PRICING_SERVICE_ID = '")
					.append(pricing_service_criteria).append("' ");
		}
		stringBuffer.append("order by PRICING_SERVICE_ID, VALIDATION_DESC");
		List<PricingServiceValidation> psvDB = (List<PricingServiceValidation>) jdbcNBATemplate
				.query(stringBuffer.toString(),
						new ResultSetExtractor<List<PricingServiceValidation>>() {
							@Override
							public List<PricingServiceValidation> extractData(
									final ResultSet rs) throws SQLException {
								List<PricingServiceValidation> psvList = new ArrayList<PricingServiceValidation>();
								while (rs.next()) {
									PricingServiceValidation psv = new PricingServiceValidation();
									// psv.setValidationId(rs
									// .getString("VALIDATION_ID"));
									psv.setValidationDesc(rs
											.getString("VALIDATION_DESC"));
									psv.setPricingServiceId(rs
											.getString("PRICING_SERVICE_ID"));
									psv.setProperty(rs.getString("PROPERTY"));
									psv.setValue(rs.getString("VALUE"));
									psv.setLastUpdatedById(rs
											.getString("UPDATED_BY"));
									psvList.add(psv);
								}
								return psvList;
							}
						});
		return psvDB;
	}

}
