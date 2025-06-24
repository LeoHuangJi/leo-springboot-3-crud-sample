package vn.leoo.common.dto;

import java.util.Date;


public class ExceptionDTO extends BaseDTO{
	private static final long serialVersionUID = 1577912609541826336L;
	
	String code;	
	String description;
	Date timestamp;
	String resourceKey;
	
	public ExceptionDTO(){

	}

	public ExceptionDTO(String code, String description, String resourceKey){
		this.code = code;
		this.description = description;
		this.timestamp = new Date();
		this.resourceKey = resourceKey;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}
}
