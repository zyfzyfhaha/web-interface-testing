package com.ssc.ssgm.fx.refdata.model;

public class InstrumentProfile {
	private long instrumentProfileId;
	private String instrumentName;
	private String updatedDatetime;
	private String updatedBy;
	private int locationTime;
	private String phyLocId;
	private String rateSource;
	private String pricingServiceId;
	public long getInstrumentProfileId() {
		return instrumentProfileId;
	}
	public void setInstrumentProfileId(long instrumentProfileId) {
		this.instrumentProfileId = instrumentProfileId;
	}
	public String getInstrumentName() {
		return instrumentName;
	}
	public void setInstrumentName(String instrumentName) {
		this.instrumentName = instrumentName;
	}
	public String getUpdatedDatetime() {
		return updatedDatetime;
	}
	public void setUpdatedDatetime(String updatedDatetime) {
		this.updatedDatetime = updatedDatetime;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public int getLocationTime() {
		return locationTime;
	}
	public void setLocationTime(int locationTime) {
		this.locationTime = locationTime;
	}
	public String getPhyLocId() {
		return phyLocId;
	}
	public void setPhyLocId(String phyLocId) {
		this.phyLocId = phyLocId;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((instrumentName == null) ? 0 : instrumentName.hashCode());
		result = prime * result
				+ (int) (instrumentProfileId ^ (instrumentProfileId >>> 32));
		result = prime * result + locationTime;
		result = prime * result
				+ ((phyLocId == null) ? 0 : phyLocId.hashCode());
		result = prime
				* result
				+ ((pricingServiceId == null) ? 0 : pricingServiceId.hashCode());
		result = prime * result
				+ ((rateSource == null) ? 0 : rateSource.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result
				+ ((updatedDatetime == null) ? 0 : updatedDatetime.hashCode());
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
		InstrumentProfile other = (InstrumentProfile) obj;
		if (instrumentName == null) {
			if (other.instrumentName != null)
				return false;
		} else if (!instrumentName.equals(other.instrumentName))
			return false;
		if (instrumentProfileId != other.instrumentProfileId)
			return false;
		if (locationTime != other.locationTime)
			return false;
		if (phyLocId == null) {
			if (other.phyLocId != null)
				return false;
		} else if (!phyLocId.equals(other.phyLocId))
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
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (updatedDatetime == null) {
			if (other.updatedDatetime != null)
				return false;
		} else if (!updatedDatetime.equals(other.updatedDatetime))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JsonInstrumentProfile [instrumentProfileId="
				+ instrumentProfileId + ", instrumentName=" + instrumentName
				+ ", updatedDatetime=" + updatedDatetime + ", updatedBy="
				+ updatedBy + ", locationTime=" + locationTime + ", phyLocId="
				+ phyLocId + ", rateSource=" + rateSource
				+ ", pricingServiceId=" + pricingServiceId + "]";
	}
	


}
