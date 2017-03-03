package com.ssc.ssgm.fx.refdata.model;

public class PricingServiceProperty {
	private String propertyId;
	private String propertyName;
	private String propertyValue;
	private String updatedBy;
	private long updatedDatetime;
	public String getPropertyId() {
		return propertyId;
	}
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyValue() {
		return propertyValue;
	}
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
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
				+ ((propertyId == null) ? 0 : propertyId.hashCode());
		result = prime * result
				+ ((propertyName == null) ? 0 : propertyName.hashCode());
		result = prime * result
				+ ((propertyValue == null) ? 0 : propertyValue.hashCode());
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
		PricingServiceProperty other = (PricingServiceProperty) obj;
		if (propertyId == null) {
			if (other.propertyId != null)
				return false;
		} else if (!propertyId.equals(other.propertyId))
			return false;
		if (propertyName == null) {
			if (other.propertyName != null)
				return false;
		} else if (!propertyName.equals(other.propertyName))
			return false;
		if (propertyValue == null) {
			if (other.propertyValue != null)
				return false;
		} else if (!propertyValue.equals(other.propertyValue))
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
		return "PricingServiceProperty [propertyId=" + propertyId
				+ ", propertyName=" + propertyName + ", propertyValue="
				+ propertyValue + ", updatedBy=" + updatedBy
				+ ", updatedDatetime=" + updatedDatetime + "]";
	}
	
	

	
}
