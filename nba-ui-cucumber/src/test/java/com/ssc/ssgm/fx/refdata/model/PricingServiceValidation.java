package com.ssc.ssgm.fx.refdata.model;

public class PricingServiceValidation {
	private String validationId;
	private String validationDesc;
	private String pricingServiceId;
	private String property;
	private String value;
	private String lastUpdatedById;
	private String lastUpdatedDttm;
	public String getValidationId() {
		return validationId;
	}
	public void setValidationId(String validationId) {
		this.validationId = validationId;
	}
	public String getValidationDesc() {
		return validationDesc;
	}
	public void setValidationDesc(String validationDesc) {
		this.validationDesc = validationDesc;
	}
	public String getPricingServiceId() {
		return pricingServiceId;
	}
	public void setPricingServiceId(String pricingServiceId) {
		this.pricingServiceId = pricingServiceId;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLastUpdatedById() {
		return lastUpdatedById;
	}
	public void setLastUpdatedById(String lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}
	public String getLastUpdatedDttm() {
		return lastUpdatedDttm;
	}
	public void setLastUpdatedDttm(String columnValue) {
		this.lastUpdatedDttm = columnValue;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((lastUpdatedById == null) ? 0 : lastUpdatedById.hashCode());
		result = prime * result
				+ ((lastUpdatedDttm == null) ? 0 : lastUpdatedDttm.hashCode());
		result = prime
				* result
				+ ((pricingServiceId == null) ? 0 : pricingServiceId.hashCode());
		result = prime * result
				+ ((property == null) ? 0 : property.hashCode());
		result = prime * result
				+ ((validationDesc == null) ? 0 : validationDesc.hashCode());
		result = prime * result
				+ ((validationId == null) ? 0 : validationId.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		PricingServiceValidation other = (PricingServiceValidation) obj;
		if (lastUpdatedById == null) {
			if (other.lastUpdatedById != null)
				return false;
		} else if (!lastUpdatedById.equals(other.lastUpdatedById))
			return false;
		if (lastUpdatedDttm == null) {
			if (other.lastUpdatedDttm != null)
				return false;
		} else if (!lastUpdatedDttm.equals(other.lastUpdatedDttm))
			return false;
		if (pricingServiceId == null) {
			if (other.pricingServiceId != null)
				return false;
		} else if (!pricingServiceId.equals(other.pricingServiceId))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		if (validationDesc == null) {
			if (other.validationDesc != null)
				return false;
		} else if (!validationDesc.equals(other.validationDesc))
			return false;
		if (validationId == null) {
			if (other.validationId != null)
				return false;
		} else if (!validationId.equals(other.validationId))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "PricingServiceValidation [validationId=" + validationId
				+ ", validationDesc=" + validationDesc + ", pricingServiceId="
				+ pricingServiceId + ", property=" + property + ", value="
				+ value + ", lastUpdatedById=" + lastUpdatedById
				+ ", lastUpdatedDttm=" + lastUpdatedDttm + "]";
	}

	
}
