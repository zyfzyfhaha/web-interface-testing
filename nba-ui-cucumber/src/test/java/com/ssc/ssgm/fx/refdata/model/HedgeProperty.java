package com.ssc.ssgm.fx.refdata.model;

public class HedgeProperty {
	private String fundHedgeId;
	private String benchmarkDate;
    private String monthEndDate;
    private String updatedDatetime;
    private String updatedBy;
    private String actionCd;
    private String auditBy;
	public String getFundHedgeId() {
		return fundHedgeId;
	}
	public void setFundHedgeId(String fundHedgeId) {
		this.fundHedgeId = fundHedgeId;
	}
	public String getBenchmarkDate() {
		return benchmarkDate;
	}
	public void setBenchmarkDate(String benchmarkDate) {
		this.benchmarkDate = benchmarkDate;
	}
	public String getMonthEndDate() {
		return monthEndDate;
	}
	public void setMonthEndDate(String monthEndDate) {
		this.monthEndDate = monthEndDate;
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
	public String getActionCd() {
		return actionCd;
	}
	public void setActionCd(String actionCd) {
		this.actionCd = actionCd;
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
				+ ((benchmarkDate == null) ? 0 : benchmarkDate.hashCode());
		result = prime * result
				+ ((fundHedgeId == null) ? 0 : fundHedgeId.hashCode());
		result = prime * result
				+ ((monthEndDate == null) ? 0 : monthEndDate.hashCode());
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
		HedgeProperty other = (HedgeProperty) obj;
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
		if (benchmarkDate == null) {
			if (other.benchmarkDate != null)
				return false;
		} else if (!benchmarkDate.equals(other.benchmarkDate))
			return false;
		if (fundHedgeId == null) {
			if (other.fundHedgeId != null)
				return false;
		} else if (!fundHedgeId.equals(other.fundHedgeId))
			return false;
		if (monthEndDate == null) {
			if (other.monthEndDate != null)
				return false;
		} else if (!monthEndDate.equals(other.monthEndDate))
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
		return "HedgeProperty [fundHedgeId=" + fundHedgeId + ", benchmarkDate="
				+ benchmarkDate + ", monthEndDate=" + monthEndDate
				+ ", updatedDatetime=" + updatedDatetime + ", updatedBy="
				+ updatedBy + ", actionCd=" + actionCd + ", auditBy=" + auditBy
				+ "]";
	}
    
    
}
