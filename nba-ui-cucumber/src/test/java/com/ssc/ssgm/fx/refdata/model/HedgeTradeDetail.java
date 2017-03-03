package com.ssc.ssgm.fx.refdata.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class HedgeTradeDetail implements Cloneable {
	
	private Long custId;
	private Long xrefFundInstrumentDetailId;
	private String isEligible;
	private BigDecimal percentage;
	private String isTargetDate;
	private String tenorValue;
	private List<String> targetTenorDate;
	private String ccy1;
	private String ccy2;
	private Long ccyPairId;
	private String actionType;
	private String lastUpdatedBy;
	private Date lastUpdatedDttm;
	private Date auditTimeDate;
	private String auditBy;
	private String actionCd;
	private Long instrumentDetailId;
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public Long getXrefFundInstrumentDetailId() {
		return xrefFundInstrumentDetailId;
	}
	public void setXrefFundInstrumentDetailId(Long xrefFundInstrumentDetailId) {
		this.xrefFundInstrumentDetailId = xrefFundInstrumentDetailId;
	}
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
	public List<String> getTargetTenorDate() {
		return targetTenorDate;
	}
	public void setTargetTenorDate(List<String> targetTenorDate) {
		this.targetTenorDate = targetTenorDate;
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
	public Long getCcyPairId() {
		return ccyPairId;
	}
	public void setCcyPairId(Long ccyPairId) {
		this.ccyPairId = ccyPairId;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdatedDttm() {
		return lastUpdatedDttm;
	}
	public void setLastUpdatedDttm(Date lastUpdatedDttm) {
		this.lastUpdatedDttm = lastUpdatedDttm;
	}
	public Date getAuditTimeDate() {
		return auditTimeDate;
	}
	public void setAuditTimeDate(Date auditTimeDate) {
		this.auditTimeDate = auditTimeDate;
	}
	public String getAuditBy() {
		return auditBy;
	}
	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}
	public String getActionCd() {
		return actionCd;
	}
	public void setActionCd(String actionCd) {
		this.actionCd = actionCd;
	}
	public Long getInstrumentDetailId() {
		return instrumentDetailId;
	}
	public void setInstrumentDetailId(Long instrumentDetailId) {
		this.instrumentDetailId = instrumentDetailId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actionCd == null) ? 0 : actionCd.hashCode());
		result = prime * result
				+ ((actionType == null) ? 0 : actionType.hashCode());
		result = prime * result + ((auditBy == null) ? 0 : auditBy.hashCode());
		result = prime * result
				+ ((auditTimeDate == null) ? 0 : auditTimeDate.hashCode());
		result = prime * result + ((ccy1 == null) ? 0 : ccy1.hashCode());
		result = prime * result + ((ccy2 == null) ? 0 : ccy2.hashCode());
		result = prime * result
				+ ((ccyPairId == null) ? 0 : ccyPairId.hashCode());
		result = prime * result + ((custId == null) ? 0 : custId.hashCode());
		result = prime
				* result
				+ ((instrumentDetailId == null) ? 0 : instrumentDetailId
						.hashCode());
		result = prime * result
				+ ((isEligible == null) ? 0 : isEligible.hashCode());
		result = prime * result
				+ ((isTargetDate == null) ? 0 : isTargetDate.hashCode());
		result = prime * result
				+ ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result
				+ ((lastUpdatedDttm == null) ? 0 : lastUpdatedDttm.hashCode());
		result = prime * result
				+ ((percentage == null) ? 0 : percentage.hashCode());
		result = prime * result
				+ ((targetTenorDate == null) ? 0 : targetTenorDate.hashCode());
		result = prime * result
				+ ((tenorValue == null) ? 0 : tenorValue.hashCode());
		result = prime
				* result
				+ ((xrefFundInstrumentDetailId == null) ? 0
						: xrefFundInstrumentDetailId.hashCode());
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
		HedgeTradeDetail other = (HedgeTradeDetail) obj;
		if (actionCd == null) {
			if (other.actionCd != null)
				return false;
		} else if (!actionCd.equals(other.actionCd))
			return false;
		if (actionType == null) {
			if (other.actionType != null)
				return false;
		} else if (!actionType.equals(other.actionType))
			return false;
		if (auditBy == null) {
			if (other.auditBy != null)
				return false;
		} else if (!auditBy.equals(other.auditBy))
			return false;
		if (auditTimeDate == null) {
			if (other.auditTimeDate != null)
				return false;
		} else if (!auditTimeDate.equals(other.auditTimeDate))
			return false;
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
		if (ccyPairId == null) {
			if (other.ccyPairId != null)
				return false;
		} else if (!ccyPairId.equals(other.ccyPairId))
			return false;
		if (custId == null) {
			if (other.custId != null)
				return false;
		} else if (!custId.equals(other.custId))
			return false;
		if (instrumentDetailId == null) {
			if (other.instrumentDetailId != null)
				return false;
		} else if (!instrumentDetailId.equals(other.instrumentDetailId))
			return false;
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
		if (lastUpdatedBy == null) {
			if (other.lastUpdatedBy != null)
				return false;
		} else if (!lastUpdatedBy.equals(other.lastUpdatedBy))
			return false;
		if (lastUpdatedDttm == null) {
			if (other.lastUpdatedDttm != null)
				return false;
		} else if (!lastUpdatedDttm.equals(other.lastUpdatedDttm))
			return false;
		if (percentage == null) {
			if (other.percentage != null)
				return false;
		} else if (!percentage.equals(other.percentage))
			return false;
		if (targetTenorDate == null) {
			if (other.targetTenorDate != null)
				return false;
		} else if (!targetTenorDate.equals(other.targetTenorDate))
			return false;
		if (tenorValue == null) {
			if (other.tenorValue != null)
				return false;
		} else if (!tenorValue.equals(other.tenorValue))
			return false;
		if (xrefFundInstrumentDetailId == null) {
			if (other.xrefFundInstrumentDetailId != null)
				return false;
		} else if (!xrefFundInstrumentDetailId
				.equals(other.xrefFundInstrumentDetailId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "HedgeTradeDetail [custId=" + custId
				+ ", xrefFundInstrumentDetailId=" + xrefFundInstrumentDetailId
				+ ", isEligible=" + isEligible + ", percentage=" + percentage
				+ ", isTargetDate=" + isTargetDate + ", tenorValue="
				+ tenorValue + ", targetTenorDate=" + targetTenorDate
				+ ", ccy1=" + ccy1 + ", ccy2=" + ccy2 + ", ccyPairId="
				+ ccyPairId + ", actionType=" + actionType + ", lastUpdatedBy="
				+ lastUpdatedBy + ", lastUpdatedDttm=" + lastUpdatedDttm
				+ ", auditTimeDate=" + auditTimeDate + ", auditBy=" + auditBy
				+ ", actionCd=" + actionCd + ", instrumentDetailId="
				+ instrumentDetailId + "]";
	}
	
	
	public HedgeTradeDetail clone() throws CloneNotSupportedException {
		HedgeTradeDetail a = (HedgeTradeDetail) super.clone();
		return a;
	}
	
	
}
