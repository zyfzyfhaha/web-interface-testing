package com.ssc.ssgm.fx.refdata.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ssc.ssgm.fx.refdata.model.PricingServiceConfiguration;
import com.ssc.ssgm.fx.refdata.model.PricingServiceProperty;

@Repository
public class PricingServiceConfigurationDao extends BaseDBUtil {

	public List<PricingServiceConfiguration> getPricingServiceConfigurationFromDB() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer
				.append("SELECT PS.PRICING_SERVICE_ID,PS.FULL_NAME,PSP.PROPERTY_ID,PSP.PROPERTY_NAME,PSP.PROPERTY_VALUE,PSP.UPDATED_BY,TO_CHAR(PSP.UPDATED_DATETIME, 'MM/DD/YYYY HH24:MI:SS') UPDATED_DATETIME FROM NBADBA.PRICING_SERVICE PS, NBADBA.PRICING_SERVICE_PROPERTY PSP WHERE PS.PRICING_SERVICE_ID = PSP.PRICING_SERVICE_ID ORDER BY PS.PRICING_SERVICE_ID, PSP.PROPERTY_NAME");

		List<PricingServiceConfiguration> pscDB = (List<PricingServiceConfiguration>) jdbcNBATemplate
				.query(stringBuffer.toString(),
						new ResultSetExtractor<List<PricingServiceConfiguration>>() {
							@Override
							public List<PricingServiceConfiguration> extractData(
									final ResultSet rs) throws SQLException {
								List<PricingServiceConfiguration> pscList = new ArrayList<PricingServiceConfiguration>();
								while (rs.next()) {
									PricingServiceConfiguration psc = new PricingServiceConfiguration();
									List<PricingServiceProperty> pspList = new ArrayList<PricingServiceProperty>();
									PricingServiceProperty psp = new PricingServiceProperty();
									psc.setPricingServiceId(rs
											.getString("PRICING_SERVICE_ID"));
									psc.setFullName(rs.getString("FULL_NAME"));
									psp.setPropertyId(rs
											.getString("PROPERTY_ID"));
									psp.setPropertyName(rs
											.getString("PROPERTY_NAME"));
									psp.setPropertyValue(rs
											.getString("PROPERTY_VALUE"));
									psp.setUpdatedBy(rs.getString("UPDATED_BY"));

									String dateTime = rs
											.getString("UPDATED_DATETIME");
									LOGGER.info("dateTime from DB: " + dateTime);
									DateFormat df = new SimpleDateFormat(
											"MM/dd/yyyy HH:mm:ss",
											Locale.ENGLISH);
									df.setTimeZone(TimeZone.getTimeZone("America/New_York"));
									Date updateDttm = new Date();
									try {
										updateDttm = df.parse(dateTime);
										LOGGER.info("updateDttm after format: "
												+ updateDttm + " long value " + updateDttm.getTime());
									} catch (ParseException e) {
										e.printStackTrace();
									}
									psp.setUpdatedDatetime(updateDttm.getTime());
									pspList.add(psp);
									psc.setProperties(pspList);
									pscList.add(psc);
								}
								return pscList;
							}
						});
		return pscDB;

	}

	public List<PricingServiceConfiguration> getPricingServiceFromDB() {

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer
				.append("SELECT DISTINCT PS.PRICING_SERVICE_ID, PS.FULL_NAME FROM NBADBA.PRICING_SERVICE PS, NBADBA.PRICING_SERVICE_PROPERTY PSP WHERE PS.PRICING_SERVICE_ID = PSP.PRICING_SERVICE_ID ORDER BY PS.PRICING_SERVICE_ID ");

		List<PricingServiceConfiguration> psDB = (List<PricingServiceConfiguration>) jdbcNBATemplate
				.query(stringBuffer.toString(),
						new ResultSetExtractor<List<PricingServiceConfiguration>>() {
							@Override
							public List<PricingServiceConfiguration> extractData(
									final ResultSet rs) throws SQLException {
								List<PricingServiceConfiguration> psList = new ArrayList<PricingServiceConfiguration>();
								while (rs.next()) {
									PricingServiceConfiguration ps = new PricingServiceConfiguration();
									ps.setPricingServiceId(rs
											.getString("PRICING_SERVICE_ID"));
									ps.setFullName(rs.getString("FULL_NAME"));

									psList.add(ps);
								}
								return psList;
							}
						});
		return psDB;

	}

}
