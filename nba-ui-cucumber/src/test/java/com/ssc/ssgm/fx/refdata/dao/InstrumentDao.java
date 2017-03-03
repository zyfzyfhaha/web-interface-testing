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

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ssc.ssgm.fx.refdata.model.CurrencyPairGroup;
import com.ssc.ssgm.fx.refdata.model.InstrumentProfile;

@Repository
public class InstrumentDao extends BaseDBUtil {

	public List<InstrumentProfile> getInstrumentFromDB(String instrumentName,
			String pricingServiceId, String rateSourceId, String location,
			String sortBy, String order) {

		if (instrumentName.contains("%")) {
			instrumentName = instrumentName.replace("%", "/%");
		}

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer
				.append("SELECT INSTRUMENT_NAME, PRICING_SERVICE_ID, RATE_SOURCE, LOCATION_TIME, PHY_LOC_ID, UPDATED_BY, TO_CHAR(UPDATED_DATETIME, 'MM/DD/YYYY HH24:MI:SS') UPDATED_DATETIME FROM NBADBA.INSTRUMENT_PROFILE WHERE 1=1 ");
		if (StringUtils.isNotBlank(instrumentName)) {
			stringBuffer.append("AND INSTRUMENT_NAME LIKE '%")
					.append(instrumentName).append("%' ESCAPE '/' ");
		}

		if (StringUtils.isNoneBlank(pricingServiceId)) {
			stringBuffer.append("AND PRICING_SERVICE_ID = '")
					.append(pricingServiceId).append("' ");
		}

		if (StringUtils.isNoneBlank(rateSourceId)) {
			stringBuffer.append("AND RATE_SOURCE = '").append(rateSourceId)
					.append("' ");
		}

		if (StringUtils.isNoneBlank(location)) {
			stringBuffer.append("AND PHY_LOC_ID = '").append(location)
					.append("' ");
		}
		// PRICING SERVICE | ASC |
		// | RATE SOURCE | DESC |
		// | LOCATION | ASC |
		// | INSTRUMENT NAME | DESC |
		switch (sortBy) {
		case "PRICING SERVICE":
			stringBuffer.append("ORDER BY PRICING_SERVICE_ID ");
			break;
		case "RATE SOURCE":
			stringBuffer.append("ORDER BY RATE_SOURCE ");
			break;
		case "LOCATION":
			stringBuffer.append("ORDER BY PHY_LOC_ID ");
			break;
		case "INSTRUMENT NAME":
			stringBuffer.append("ORDER BY INSTRUMENT_NAME ");
			break;
		}
		switch (order) {
		case "ASC":
			stringBuffer.append("ASC");
			break;
		case "DESC":
			stringBuffer.append("DESC");
			break;
		}

		stringBuffer.append(", PRICING_SERVICE_ID, INSTRUMENT_NAME ");

		List<InstrumentProfile> ipList = (List<InstrumentProfile>) jdbcNBATemplate
				.query(stringBuffer.toString(),
						new ResultSetExtractor<List<InstrumentProfile>>() {
							@Override
							public List<InstrumentProfile> extractData(
									final ResultSet rs) throws SQLException {
								List<InstrumentProfile> ipDBList = new ArrayList<InstrumentProfile>();
								while (rs.next()) {
									InstrumentProfile ip = new InstrumentProfile();
									ip.setInstrumentName(rs
											.getString("INSTRUMENT_NAME"));
									ip.setPricingServiceId(rs
											.getString("PRICING_SERVICE_ID"));
									ip.setRateSource(rs
											.getString("RATE_SOURCE"));
									ip.setLocationTime(rs
											.getInt("LOCATION_TIME"));
									ip.setPhyLocId(rs.getString("PHY_LOC_ID"));
									ip.setUpdatedBy(rs.getString("UPDATED_BY"));

									String dateTime = rs
											.getString("UPDATED_DATETIME");
									// LOGGER.info("dateTime from DB: " +
									// dateTime);
									DateFormat df = new SimpleDateFormat(
											"MM/dd/yyyy HH:mm:ss",
											Locale.ENGLISH);
									df.setTimeZone(TimeZone
											.getTimeZone("America/New_York"));
									Date updateDttm = new Date();
									try {
										updateDttm = df.parse(dateTime
												.substring(0, 19));
										// LOGGER.info("updateDttm after format: "
										// + updateDttm + " long value "
										// + updateDttm.getTime());
									} catch (ParseException e) {
										e.printStackTrace();
									}
									ip.setUpdatedDatetime(Long
											.toString(updateDttm.getTime()));
									ipDBList.add(ip);
								}
								return ipDBList;
							}
						});
		return ipList;

	}

	public List<InstrumentProfile> getInstrumentFromDB(String instrumentName,
			String pricingServiceId, String rateSourceId, String location) {

		if (instrumentName.contains("%")) {
			instrumentName = instrumentName.replace("%", "/%");
		}

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer
				.append("SELECT INSTRUMENT_NAME, PRICING_SERVICE_ID, RATE_SOURCE, LOCATION_TIME, PHY_LOC_ID, UPDATED_BY, TO_CHAR(UPDATED_DATETIME, 'MM/DD/YYYY HH24:MI:SS') UPDATED_DATETIME FROM NBADBA.INSTRUMENT_PROFILE WHERE 1=1 ");
		if (StringUtils.isNotBlank(instrumentName)) {
			stringBuffer.append("AND INSTRUMENT_NAME LIKE '%")
					.append(instrumentName).append("%' ESCAPE '/' ");
		}

		if (StringUtils.isNoneBlank(pricingServiceId)) {
			stringBuffer.append("AND PRICING_SERVICE_ID = '")
					.append(pricingServiceId).append("' ");
		}

		if (StringUtils.isNoneBlank(rateSourceId)) {
			stringBuffer.append("AND RATE_SOURCE = '").append(rateSourceId)
					.append("' ");
		}

		if (StringUtils.isNoneBlank(location)) {
			stringBuffer.append("AND PHY_LOC_ID = '").append(location)
					.append("' ");
		}

		stringBuffer.append("ORDER BY PRICING_SERVICE_ID, INSTRUMENT_NAME");

		List<InstrumentProfile> ipList = (List<InstrumentProfile>) jdbcNBATemplate
				.query(stringBuffer.toString(),
						new ResultSetExtractor<List<InstrumentProfile>>() {
							@Override
							public List<InstrumentProfile> extractData(
									final ResultSet rs) throws SQLException {
								List<InstrumentProfile> ipDBList = new ArrayList<InstrumentProfile>();
								while (rs.next()) {
									InstrumentProfile ip = new InstrumentProfile();
									ip.setInstrumentName(rs
											.getString("INSTRUMENT_NAME"));
									ip.setPricingServiceId(rs
											.getString("PRICING_SERVICE_ID"));
									ip.setRateSource(rs
											.getString("RATE_SOURCE"));
									ip.setLocationTime(rs
											.getInt("LOCATION_TIME"));
									ip.setPhyLocId(rs.getString("PHY_LOC_ID"));
									ip.setUpdatedBy(rs.getString("UPDATED_BY"));

									String dateTime = rs
											.getString("UPDATED_DATETIME");
									LOGGER.info("dateTime from DB: " + dateTime);
									DateFormat df = new SimpleDateFormat(
											"MM/dd/yyyy HH:mm:ss",
											Locale.ENGLISH);
									df.setTimeZone(TimeZone
											.getTimeZone("America/New_York"));
									Date updateDttm = new Date();
									try {
										updateDttm = df.parse(dateTime
												.substring(0, 19));
										LOGGER.info("updateDttm after format: "
												+ updateDttm + " long value "
												+ updateDttm.getTime());
									} catch (ParseException e) {
										e.printStackTrace();
									}
									ip.setUpdatedDatetime(Long
											.toString(updateDttm.getTime()));
									ipDBList.add(ip);
								}
								return ipDBList;
							}
						});
		return ipList;

	}

	public List<CurrencyPairGroup> getCcyPairList(String instrumentName) {

		if (instrumentName.contains("%")) {
			instrumentName = instrumentName.replace("%", "/%");
		}

		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer
				.append("SELECT CRNCY_ID, CNTR_CRNCY_ID,CRNCY_PAIR_ID FROM FXDBA.CURRENCY_PAIR WHERE CRNCY_PAIR_ID IN (")
				.append("SELECT ID.CRNCY_PAIR_ID FROM NBADBA.INSTRUMENT_PROFILE IP, NBADBA.INSTRUMENT_DETAIL ID ")
				.append("WHERE IP.INSTRUMENT_PROFILE_ID = ID.INSTRUMENT_PROFILE_ID AND IP.INSTRUMENT_NAME='")
				.append(instrumentName).append("') ORDER BY CRNCY_PAIR_ID");

		List<CurrencyPairGroup> cpList = (List<CurrencyPairGroup>) jdbcNBATemplate
				.query(stringBuffer.toString(),
						new ResultSetExtractor<List<CurrencyPairGroup>>() {
							@Override
							public List<CurrencyPairGroup> extractData(
									final ResultSet rs) throws SQLException {
								List<CurrencyPairGroup> cpDBList = new ArrayList<CurrencyPairGroup>();
								while (rs.next()) {
									CurrencyPairGroup cp = new CurrencyPairGroup();
									cp.setCcy1(rs.getString("CRNCY_ID"));
									cp.setCcy2(rs.getString("CNTR_CRNCY_ID"));

									cpDBList.add(cp);
								}
								return cpDBList;
							}
						});
		return cpList;

	}
}
