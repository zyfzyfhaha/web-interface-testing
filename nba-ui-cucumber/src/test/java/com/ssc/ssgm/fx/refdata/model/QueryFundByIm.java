package com.ssc.ssgm.fx.refdata.model;

public class QueryFundByIm {

	private String fund;
	private String custId;
	public String getFund() {
		return fund;
	}
	public void setFund(String fund) {
		this.fund = fund;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custId == null) ? 0 : custId.hashCode());
		result = prime * result + ((fund == null) ? 0 : fund.hashCode());
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
		QueryFundByIm other = (QueryFundByIm) obj;
		if (custId == null) {
			if (other.custId != null)
				return false;
		} else if (!custId.equals(other.custId))
			return false;
		if (fund == null) {
			if (other.fund != null)
				return false;
		} else if (!fund.equals(other.fund))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "QueryFundByIm [fund=" + fund + ", custId=" + custId + "]";
	}
	
}
