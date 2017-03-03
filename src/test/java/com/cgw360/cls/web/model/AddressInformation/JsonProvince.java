package com.cgw360.cls.web.model.AddressInformation;

import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.impl.PublicImpl;

public class JsonProvince {

	private String message;
	private String status;
	private List<JsonAreaData> data;
	private String api;
	private String statuscode;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<JsonAreaData> getData() {
		return data;
	}
	public void setData(List<JsonAreaData> data) {
		this.data = data;
	}
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public String getStatuscode() {
		return statuscode;
	}
	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((api == null) ? 0 : api.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((statuscode == null) ? 0 : statuscode.hashCode());
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
		JsonProvince other = (JsonProvince) obj;
		if (api == null) {
			if (other.api != null)
				return false;
		} else if (!api.equals(other.api))
			return false;
		
		
//		if (data == null) {
//			if (other.data != null)
//				return false;
//		} else if (data.equals(other.data))
//			return false;
		
		if(!compare(data, other.data)){
			return false;
		}
		
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statuscode == null) {
			if (other.statuscode != null)
				return false;
		} else if (!statuscode.equals(other.statuscode))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "JsonProvince [message=" + message + ", status=" + status
				+ ", data=" + data + ", api=" + api + ", statuscode="
				+ statuscode + "]";
	}
	
	public boolean compare(List<JsonAreaData> expectedResults, List<JsonAreaData> actualResults){
		if(expectedResults.size() != actualResults.size()){
			return false;
		}else{
			for(JsonAreaData a : actualResults){
				if(!expectedResults.contains(a)){
					return false;
				}
			}
		}
		return true;
	}
	
	
}
