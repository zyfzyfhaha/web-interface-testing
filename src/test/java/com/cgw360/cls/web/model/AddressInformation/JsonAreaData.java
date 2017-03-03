package com.cgw360.cls.web.model.AddressInformation;

public class JsonAreaData {
	
	private String areacode;
	private String city;
	private String county;
	private String ctime;
	private String first_py;
	private String isvalid;
	private String mtime;
	private String privince;
	private int type;
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getcounty() {
		return county;
	}
	public void setcounty(String county) {
		this.county = county;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getFirst_py() {
		return first_py;
	}
	public void setFirst_py(String first_py) {
		this.first_py = first_py;
	}
	public String getIsvalid() {
		return isvalid;
	}
	public void setIsvalid(String isvalid) {
		this.isvalid = isvalid;
	}
	public String getMtime() {
		return mtime;
	}
	public void setMtime(String mtime) {
		this.mtime = mtime;
	}
	public String getPrivince() {
		return privince;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JsonAreaData other = (JsonAreaData) obj;
		if (areacode == null) {
			if (other.areacode != null)
				return false;
		} else if (!areacode.equals(other.areacode))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (county == null) {
			if (other.county != null)
				return false;
		} else if (!county.equals(other.county))
			return false;
		if (ctime == null) {
			if (other.ctime != null)
				return false;
		} else if (!ctime.equals(other.ctime))
			return false;
		if (first_py == null) {
			if (other.first_py != null)
				return false;
		} else if (!first_py.equals(other.first_py))
			return false;
		if (isvalid == null) {
			if (other.isvalid != null)
				return false;
		} else if (!isvalid.equals(other.isvalid))
			return false;
		if (mtime == null) {
			if (other.mtime != null)
				return false;
		} else if (!mtime.equals(other.mtime))
			return false;
		if (privince == null) {
			if (other.privince != null)
				return false;
		} else if (!privince.equals(other.privince))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	public void setPrivince(String privince) {
		this.privince = privince;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((areacode == null) ? 0 : areacode.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((county == null) ? 0 : county.hashCode());
		result = prime * result + ((ctime == null) ? 0 : ctime.hashCode());
		result = prime * result
				+ ((first_py == null) ? 0 : first_py.hashCode());
		result = prime * result + ((isvalid == null) ? 0 : isvalid.hashCode());
		result = prime * result + ((mtime == null) ? 0 : mtime.hashCode());
		result = prime * result
				+ ((privince == null) ? 0 : privince.hashCode());
		result = prime * result + type;
		return result;
	}
	@Override
	public String toString() {
		return "JsonAreaData [areacode=" + areacode + ", city=" + city
				+ ", county=" + county + ", ctime=" + ctime + ", first_py="
				+ first_py + ", isvalid=" + isvalid + ", mtime=" + mtime
				+ ", privince=" + privince + ", type=" + type + "]";
	}
	
	
	
}
