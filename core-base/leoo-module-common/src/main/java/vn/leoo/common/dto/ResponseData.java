package vn.leoo.common.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.leoo.common.constants.AppErrorCode;
import vn.leoo.common.constants.Message;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseData<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private int code;
	private boolean status;
	private String message;
	private T data;
	@JsonIgnore
	private HttpStatus httpStatus;

	private Instant timestamp;

	public ResponseData(AppErrorCode appErrorCode, boolean status, String message, T data) {
		super();
		this.code = appErrorCode.getCode();
		this.status = status;
		this.message = message;
		this.data = data;
		this.httpStatus = appErrorCode.getHttpStatus();
		this.timestamp = Instant.now();
	}

	public ResponseData(AppErrorCode appErrorCode, boolean status, String message) {
		super();
		this.code = appErrorCode.getCode();
		this.status = status;
		this.message = message;
		this.httpStatus = appErrorCode.getHttpStatus();
		this.timestamp = Instant.now();
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@JsonIgnore
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public static <T> ResponseData<T> ok(T data) {
		return new ResponseData<>(AppErrorCode.SUCCESS, true, Message.OK, data);
	}

	public static <T> ResponseData<T> created(T data) {
		return new ResponseData<>(AppErrorCode.CREATED, true, Message.CREATED, data);
	}

	public static <T> ResponseData<T> notFound(String message) {
		message = StringUtils.isNotBlank(message)?message:AppErrorCode.NOT_FOUND.getDefaultMessage();
		return new ResponseData<>(AppErrorCode.NOT_FOUND, false, message, null);
	}
	public static <T> ResponseData<T> badRequest(String message) {
		message = StringUtils.isNotBlank(message)?message:AppErrorCode.BAD_REQUEST.getDefaultMessage();
		return new ResponseData<>(AppErrorCode.BAD_REQUEST, false, message, null);
	}
	public static <T> ResponseData<T> badRequest(String message,T data) {
		message = StringUtils.isNotBlank(message)?message:AppErrorCode.BAD_REQUEST.getDefaultMessage();
		return new ResponseData<>(AppErrorCode.BAD_REQUEST, false, message, data);
	}
	public static <T> ResponseData<T> forbidden(String message) {
		message = StringUtils.isNotBlank(message)?message:AppErrorCode.FORBIDDEN.getDefaultMessage();
		return new ResponseData<>(AppErrorCode.FORBIDDEN, false, message, null);
	}
	public static <T> ResponseData<T> forbidden(String message,T data) {
		
		return new ResponseData<>(AppErrorCode.FORBIDDEN, false, message, data);
	}
	
	public static <T> ResponseData<T> unauthorized(String message) {
		return new ResponseData<>(AppErrorCode.UNAUTHORIZED, false, message, null);
	}
	public static <T> ResponseData<T> unauthorized(String message,T data) {
		return new ResponseData<>(AppErrorCode.UNAUTHORIZED, false, message, data);
	}

	/*
	 * public static <T> ResponseData<T> NOT_ACCEPTABLE(String message) { return new
	 * ResponseData<>(AppErrorCode.B, false, message, null); }
	 */

	public static <T> ResponseData<T> from(AppErrorCode errorCode, T data, String customMsg) {
		return ResponseData.<T>builder().code(errorCode.getCode()).status(errorCode.getHttpStatus().is2xxSuccessful())
				.message(customMsg != null ? customMsg : errorCode.getDefaultMessage()).data(data)
				.timestamp(Instant.now()).build();
	}

	public static <T> ResponseEntity<ResponseData<T>> toResponse(AppErrorCode errorCode, T data, String msg) {
		return new ResponseEntity<>(from(errorCode, data, msg), errorCode.getHttpStatus());
	}

}
