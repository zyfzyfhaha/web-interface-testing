package com.ssc.ssgm.fx.refdata.model;

import java.util.List;

public class PricingServiceConfiguration {
	private String pricingServiceId;
	private String shortName;
	private String fullName;
	private List<PricingServiceProperty> properties;
	private String updatedBy;
	private long updatedDatetime;
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
	public List<PricingServiceProperty> getProperties() {
		return properties;
	}
	public void setProperties(List<PricingServiceProperty> properties) {
		this.properties = properties;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public long getUpdatedDatetime() {
		return updatedDatetime;
	}
	public void setUpdatedDatetime(long updatedDatetime) {
		this.updatedDatetime = updatedDatetime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fullName == null) ? 0 : fullName.hashCode());
		result = prime
				* result
				+ ((pricingServiceId == null) ? 0 : pricingServiceId.hashCode());
		result = prime * result
				+ ((properties == null) ? 0 : properties.hashCode());
		result = prime * result
				+ ((shortName == null) ? 0 : shortName.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result
				+ (int) (updatedDatetime ^ (updatedDatetime >>> 32));
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
		PricingServiceConfiguration other = (PricingServiceConfiguration) obj;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (pricingServiceId == null) {
			if (other.pricingServiceId != null)
				return false;
		} else if (!pricingServiceId.equals(other.pricingServiceId))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
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
		if (updatedDatetime != other.updatedDatetime)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "PricingServiceConfiguration [pricingServiceId="
				+ pricingServiceId + ", shortName=" + shortName + ", fullName="
				+ fullName + ", properties=" + properties + ", updatedBy="
				+ updatedBy + ", updatedDatetime=" + updatedDatetime + "]";
	}
	
	

}
