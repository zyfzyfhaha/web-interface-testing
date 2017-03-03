package com.ssc.ssgm.fx.refdata.model;

import java.math.BigDecimal;

public class FundInstHedge {
	private String isEligible;
	private BigDecimal percentage;
	private String isTargetDate;
	private String tenorValue;
	public String getIsEligible() {
		return isEligible;
	}
	public void setIsEligible(String isEligible) {
		this.isEligible = isEligible;
	}
	public BigDecimal getPercentage() {
		return percentage;
	}
	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}
	public String getIsTargetDate() {
		return isTargetDate;
	}
	public void setIsTargetDate(String isTargetDate) {
		this.isTargetDate = isTargetDate;
	}
	public String getTenorValue() {
		return tenorValue;
	}
	public void setTenorValue(String tenorValue) {
		this.tenorValue = tenorValue;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((isEligible == null) ? 0 : isEligible.hashCode());
		result = prime * result
				+ ((isTargetDate == null) ? 0 : isTargetDate.hashCode());
		result = prime * result
				+ ((percentage == null) ? 0 : percentage.hashCode());
		result = prime * result
				+ ((tenorValue == null) ? 0 : tenorValue.hashCode());
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
		FundInstHedge other = (FundInstHedge) obj;
		if (isEligible == null) {
			if (other.isEligible != null)
				return false;
		} else if (!isEligible.equals(other.isEligible))
			return false;
		if (isTargetDate == null) {
			if (other.isTargetDate != null)
				return false;
		} else if (!isTargetDate.equals(other.isTargetDate))
			return false;
		if (percentage == null) {
			if (other.percentage != null)
				return false;
		} else if (!percentage.equals(other.percentage))
			return false;
		if (tenorValue == null) {
			if (other.tenorValue != null)
				return false;
		} else if (!tenorValue.equals(other.tenorValue))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FundInstHedge [isEligible=" + isEligible + ", percentage="
				+ percentage + ", isTargetDate=" + isTargetDate
				+ ", tenorValue=" + tenorValue + "]";
	}
	
	
}
