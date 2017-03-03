package com.ssc.ssgm.fx.refdata.model;

public class CurrencyPairGroup {
	String groupId;
	String pricingServiceId;
	String ccy1;
	String ccy2;
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getPricingServiceId() {
		return pricingServiceId;
	}
	public void setPricingServiceId(String pricingServiceId) {
		this.pricingServiceId = pricingServiceId;
	}
	public String getCcy1() {
		return ccy1;
	}
	public void setCcy1(String ccy1) {
		this.ccy1 = ccy1;
	}
	public String getCcy2() {
		return ccy2;
	}
	public void setCcy2(String ccy2) {
		this.ccy2 = ccy2;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ccy1 == null) ? 0 : ccy1.hashCode());
		result = prime * result + ((ccy2 == null) ? 0 : ccy2.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime
				* result
				+ ((pricingServiceId == null) ? 0 : pricingServiceId.hashCode());
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
		CurrencyPairGroup other = (CurrencyPairGroup) obj;
		if (ccy1 == null) {
			if (other.ccy1 != null)
				return false;
		} else if (!ccy1.equals(other.ccy1))
			return false;
		if (ccy2 == null) {
			if (other.ccy2 != null)
				return false;
		} else if (!ccy2.equals(other.ccy2))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (pricingServiceId == null) {
			if (other.pricingServiceId != null)
				return false;
		} else if (!pricingServiceId.equals(other.pricingServiceId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CurrencyPairGroup [groupId=" + groupId + ", pricingServiceId="
				+ pricingServiceId + ", ccy1=" + ccy1 + ", ccy2=" + ccy2 + "]";
	}
	
	

}
