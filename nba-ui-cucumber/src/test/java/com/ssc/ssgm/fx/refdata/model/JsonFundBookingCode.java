package com.ssc.ssgm.fx.refdata.model;

import java.sql.Date;

public class JsonFundBookingCode {

	private Long custId;
	private String fundBookingCode;
	private String lastUpdatedBy;
	private Date lastUpdatedDttm;
	private String actionCd;
	private Date auditTimeDate;
	private String auditBy;
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
	public String getFundBookingCode() {
		return fundBookingCode;
	}
	public void setFundBookingCode(String fundBookingCode) {
		this.fundBookingCode = fundBookingCode;
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
	public String getActionCd() {
		return actionCd;
	}
	public void setActionCd(String actionCd) {
		this.actionCd = actionCd;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actionCd == null) ? 0 : actionCd.hashCode());
		result = prime * result + ((auditBy == null) ? 0 : auditBy.hashCode());
		result = prime * result
				+ ((auditTimeDate == null) ? 0 : auditTimeDate.hashCode());
		result = prime * result + ((custId == null) ? 0 : custId.hashCode());
		result = prime * result
				+ ((fundBookingCode == null) ? 0 : fundBookingCode.hashCode());
		result = prime * result
				+ ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result
				+ ((lastUpdatedDttm == null) ? 0 : lastUpdatedDttm.hashCode());
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
		JsonFundBookingCode other = (JsonFundBookingCode) obj;
		if (actionCd == null) {
			if (other.actionCd != null)
				return false;
		} else if (!actionCd.equals(other.actionCd))
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
		if (custId == null) {
			if (other.custId != null)
				return false;
		} else if (!custId.equals(other.custId))
			return false;
		if (fundBookingCode == null) {
			if (other.fundBookingCode != null)
				return false;
		} else if (!fundBookingCode.equals(other.fundBookingCode))
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
		return true;
	}
	@Override
	public String toString() {
		return "JsonFundBookingCode [custId=" + custId + ", fundBookingCode="
				+ fundBookingCode + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", lastUpdatedDttm=" + lastUpdatedDttm + ", actionCd="
				+ actionCd + ", auditTimeDate=" + auditTimeDate + ", auditBy="
				+ auditBy + "]";
	} 
	
	
}
