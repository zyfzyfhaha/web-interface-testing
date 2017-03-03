package com.ssc.ssgm.fx.refdata.model;

public class QueryIm {
	
	private String im;
	private String imId;
	public String getIm() {
		return im;
	}
	public void setIm(String im) {
		this.im = im;
	}
	public String getImId() {
		return imId;
	}
	public void setImId(String imId) {
		this.imId = imId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((im == null) ? 0 : im.hashCode());
		result = prime * result + ((imId == null) ? 0 : imId.hashCode());
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
		QueryIm other = (QueryIm) obj;
		if (im == null) {
			if (other.im != null)
				return false;
		} else if (!im.equals(other.im))
			return false;
		if (imId == null) {
			if (other.imId != null)
				return false;
		} else if (!imId.equals(other.imId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "QueryIm [im=" + im + ", imId=" + imId + "]";
	}
	
	
}
