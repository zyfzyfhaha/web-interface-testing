package com.ssc.ssgm.fx.refdata.model;

public class PricingService {
	String pricingServiceId;
	String shortName;
	String fullName;
	String omsProdCateId;
	String updatedDatetime;
	String updatedBy;
	public String getPricingServiceId() {
		return pricingServiceId;
	}
	public void setPricingServiceId(String pricingServiceId) {
		this.pricingServiceId = pricingServiceId;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getOmsProdCateId() {
		return omsProdCateId;
	}
	public void setOmsProdCateId(String omsProdCateId) {
		this.omsProdCateId = omsProdCateId;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result
				+ ((omsProdCateId == null) ? 0 : omsProdCateId.hashCode());
		result = prime
				* result
				+ ((pricingServiceId == null) ? 0 : pricingServiceId.hashCode());
		result = prime * result
				+ ((shortName == null) ? 0 : shortName.hashCode());
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
		PricingService other = (PricingService) obj;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (omsProdCateId == null) {
			if (other.omsProdCateId != null)
				return false;
		} else if (!omsProdCateId.equals(other.omsProdCateId))
			return false;
		if (pricingServiceId == null) {
			if (other.pricingServiceId != null)
				return false;
		} else if (!pricingServiceId.equals(other.pricingServiceId))
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
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
		return "PricingService [pricingServiceId=" + pricingServiceId
				+ ", shortName=" + shortName + ", fullName=" + fullName
				+ ", omsProdCateId=" + omsProdCateId + ", updatedDatetime="
				+ updatedDatetime + ", updatedBy=" + updatedBy + "]";
	}

	
}
