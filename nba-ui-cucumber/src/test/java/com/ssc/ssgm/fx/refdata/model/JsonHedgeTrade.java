package com.ssc.ssgm.fx.refdata.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



public class JsonHedgeTrade implements Cloneable {
	
	private Long custId;
	private String instrumentName;
	private String fundBookingCode;
	private Long instrumentprofileId;
	private String location;
	private String locationTime;
	private String rateSource;
	private String pricingServiceId;
	private List<HedgeTradeDetail> detail;
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	public String getFundBookingCode() {
		return fundBookingCode;
	}
	public void setFundBookingCode(String fundBookingCode) {
		this.fundBookingCode = fundBookingCode;
	}
	public Long getInstrumentprofileId() {
		return instrumentprofileId;
	}
	public void setInstrumentprofileId(Long instrumentprofileId) {
		this.instrumentprofileId = instrumentprofileId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocationTime() {
		return locationTime;
	}
	public void setLocationTime(String locationTime) {
		this.locationTime = locationTime;
	}
	public String getRateSource() {
		return rateSource;
	}
	public void setRateSource(String rateSource) {
		this.rateSource = rateSource;
	}
	public String getPricingServiceId() {
		return pricingServiceId;
	}
	public void setPricingServiceId(String pricingServiceId) {
		this.pricingServiceId = pricingServiceId;
	}
	public List<HedgeTradeDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<HedgeTradeDetail> detail) {
		this.detail = detail;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custId == null) ? 0 : custId.hashCode());
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result
				+ ((fundBookingCode == null) ? 0 : fundBookingCode.hashCode());
		result = prime * result
				+ ((instrumentName == null) ? 0 : instrumentName.hashCode());
		result = prime
				* result
				+ ((instrumentprofileId == null) ? 0 : instrumentprofileId
						.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result
				+ ((locationTime == null) ? 0 : locationTime.hashCode());
		result = prime
				* result
				+ ((pricingServiceId == null) ? 0 : pricingServiceId.hashCode());
		result = prime * result
				+ ((rateSource == null) ? 0 : rateSource.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JsonHedgeTrade other = (JsonHedgeTrade) obj;
		if (custId == null) {
			if (other.custId != null)
				return false;
		} else if (!custId.equals(other.custId))
			return false;
		
		
//		if (detail == null) {
//			if (other.detail != null)
//				return false;
//		} else if (!detail.equals(other.detail))
//			return false;
		
		if ((detail.size() != other.detail.size()))
		{
			return false;
		}
		else 
		{
			for (HedgeTradeDetail a : other.detail)
			{
				if(!detail.contains(a))
					return false;
			}
		}
		
		if (fundBookingCode == null) {
			if (other.fundBookingCode != null)
				return false;
		} else if (!fundBookingCode.equals(other.fundBookingCode))
			return false;
		if (instrumentName == null) {
			if (other.instrumentName != null)
				return false;
		} else if (!instrumentName.equals(other.instrumentName))
			return false;
		if (instrumentprofileId == null) {
			if (other.instrumentprofileId != null)
				return false;
		} else if (!instrumentprofileId.equals(other.instrumentprofileId))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (locationTime == null) {
			if (other.locationTime != null)
				return false;
		} else if (!locationTime.equals(other.locationTime))
			return false;
		if (pricingServiceId == null) {
			if (other.pricingServiceId != null)
				return false;
		} else if (!pricingServiceId.equals(other.pricingServiceId))
			return false;
		if (rateSource == null) {
			if (other.rateSource != null)
				return false;
		} else if (!rateSource.equals(other.rateSource))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JsonHedgeTrade [custId=" + custId + ", instrumentName="
				+ instrumentName + ", fundBookingCode=" + fundBookingCode
				+ ", instrumentprofileId=" + instrumentprofileId
				+ ", location=" + location + ", locationTime=" + locationTime
				+ ", rateSource=" + rateSource + ", pricingServiceId="
				+ pricingServiceId + ", detail=" + detail + "]";
	}
	
	
	public JsonHedgeTrade clone() throws CloneNotSupportedException {
		JsonHedgeTrade a = (JsonHedgeTrade) super.clone();
		if (this.getDetail() != null) {
			a.setDetail(new ArrayList<HedgeTradeDetail>());
			for (HedgeTradeDetail detail : this.getDetail()) {
				a.getDetail().add(detail.clone());
			}
		}
		return a;
	}
	
}
