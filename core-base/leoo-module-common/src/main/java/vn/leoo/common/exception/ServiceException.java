package vn.leoo.common.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import vn.leoo.common.constants.AppErrorCode;

public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long code;
	private boolean status = false;
	private String message;
	private Object data;
	private Instant timestamp;

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	@JsonIgnore
	private HttpStatus httpStatus;

	public ServiceException(AppErrorCode appErrorCode, String message) {
		super();
		this.code = appErrorCode.getCode();
		this.status = false;
		this.message = message;
		this.httpStatus = appErrorCode.getHttpStatus();
		this.timestamp = Instant.now();
	}

	public ServiceException(AppErrorCode appErrorCode, String message, Object data) {
		super();
		this.code = appErrorCode.getCode();
		this.status = false;
		this.message = message;
		this.data = data;
		this.httpStatus = appErrorCode.getHttpStatus();
		this.timestamp = Instant.now();
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}