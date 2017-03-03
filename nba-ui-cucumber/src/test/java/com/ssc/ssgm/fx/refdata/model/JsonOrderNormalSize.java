package com.ssc.ssgm.fx.refdata.model;

import java.math.BigDecimal;



public class JsonOrderNormalSize {
	
	private String im;
	private String fund;
	private String custId;
	private BigDecimal ccy1NormalSize;
	private BigDecimal ccy2NormalSize;
	private String autoCalculate;
	private String comment;
	private CurrencyPairGroup currencyPairGroup;
	private String lastUpdatedById;
	private long lastUpdatedDttm;
	public String getIm() {
		return im;
	}
	public void setIm(String im) {
		this.im = im;
	}
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
	public BigDecimal getCcy1NormalSize() {
		return ccy1NormalSize;
	}
	public void setCcy1NormalSize(BigDecimal ccy1NormalSize) {
		this.ccy1NormalSize = ccy1NormalSize;
	}
	public BigDecimal getCcy2NormalSize() {
		return ccy2NormalSize;
	}
	public void setCcy2NormalSize(BigDecimal ccy2NormalSize) {
		this.ccy2NormalSize = ccy2NormalSize;
	}
	public String getAutoCalculate() {
		return autoCalculate;
	}
	public void setAutoCalculate(String autoCalculate) {
		this.autoCalculate = autoCalculate;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public CurrencyPairGroup getCurrencyPairGroup() {
		return currencyPairGroup;
	}
	public void setCurrencyPairGroup(CurrencyPairGroup currencyPairGroup) {
		this.currencyPairGroup = currencyPairGroup;
	}
	public String getLastUpdatedById() {
		return lastUpdatedById;
	}
	public void setLastUpdatedById(String lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
	}
	public long getLastUpdatedDttm() {
		return lastUpdatedDttm;
	}
	public void setLastUpdatedDttm(long lastUpdatedDttm) {
		this.lastUpdatedDttm = lastUpdatedDttm;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((autoCalculate == null) ? 0 : autoCalculate.hashCode());
		result = prime * result
				+ ((ccy1NormalSize == null) ? 0 : ccy1NormalSize.hashCode());
		result = prime * result
				+ ((ccy2NormalSize == null) ? 0 : ccy2NormalSize.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime
				* result
				+ ((currencyPairGroup == null) ? 0 : currencyPairGroup
						.hashCode());
		result = prime * result + ((custId == null) ? 0 : custId.hashCode());
		result = prime * result + ((fund == null) ? 0 : fund.hashCode());
		result = prime * result + ((im == null) ? 0 : im.hashCode());
		result = prime * result
				+ ((lastUpdatedById == null) ? 0 : lastUpdatedById.hashCode());
		result = prime * result
				+ (int) (lastUpdatedDttm ^ (lastUpdatedDttm >>> 32));
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
		JsonOrderNormalSize other = (JsonOrderNormalSize) obj;
		if (autoCalculate == null) {
			if (other.autoCalculate != null)
				return false;
		} else if (!autoCalculate.equals(other.autoCalculate))
			return false;
		if (ccy1NormalSize == null) {
			if (other.ccy1NormalSize != null)
				return false;
		} else if (!ccy1NormalSize.equals(other.ccy1NormalSize))
			return false;
		if (ccy2NormalSize == null) {
			if (other.ccy2NormalSize != null)
				return false;
		} else if (!ccy2NormalSize.equals(other.ccy2NormalSize))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (currencyPairGroup == null) {
			if (other.currencyPairGroup != null)
				return false;
		} else if (!currencyPairGroup.equals(other.currencyPairGroup))
			return false;
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
		if (im == null) {
			if (other.im != null)
				return false;
		} else if (!im.equals(other.im))
			return false;
		if (lastUpdatedById == null) {
			if (other.lastUpdatedById != null)
				return false;
		} else if (!lastUpdatedById.equals(other.lastUpdatedById))
			return false;
		if (lastUpdatedDttm != other.lastUpdatedDttm)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JsonOrderNormalSize [im=" + im + ", fund=" + fund + ", custId="
				+ custId + ", ccy1NormalSize=" + ccy1NormalSize
				+ ", ccy2NormalSize=" + ccy2NormalSize + ", autoCalculate="
				+ autoCalculate + ", comment=" + comment
				+ ", currencyPairGroup=" + currencyPairGroup
				+ ", lastUpdatedById=" + lastUpdatedById + ", lastUpdatedDttm="
				+ lastUpdatedDttm + "]";
	}
	
}
