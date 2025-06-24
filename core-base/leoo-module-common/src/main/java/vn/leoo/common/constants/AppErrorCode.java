package vn.leoo.common.constants;

import org.springframework.http.HttpStatus;

public enum AppErrorCode {
	// --- 2xx
	SUCCESS(2000, HttpStatus.OK, "Thành công"), CREATED(2001, HttpStatus.CREATED, "Tạo mới thành công"),

	// --- 4xx: Client error
	//Request bị sai cú pháp, định dạng, thiếu trường, kiểu dữ liệu không khớp JSON…
	/*
	 * POST /users Content-Type: application/json
	 * 
	 * { "email": "abc", <-- thiếu @ => sai định dạng "password": 1234 <-- kiểu số,
	 * cần string }
	 */
	BAD_REQUEST(4001, HttpStatus.BAD_REQUEST, "Yêu cầu không hợp lệ"),
	//Dữ liệu đúng cú pháp, nhưng vi phạm logic nghiệp vụ (ràng buộc business)
	UNPROCESSABLE_ENTITY(4001, HttpStatus.UNPROCESSABLE_ENTITY, "Dữ liệu không hợp lệ"),
	UNAUTHORIZED(4101, HttpStatus.UNAUTHORIZED, "Chưa đăng nhập"),
	FORBIDDEN(4301, HttpStatus.FORBIDDEN, "Không có quyền"),
	NOT_FOUND(4401, HttpStatus.NOT_FOUND, "Không tìm thấy dữ liệu"),
	METHOD_NOT_ALLOWED(4500, HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed"),
	CONFLICT(4900, HttpStatus.CONFLICT, "Conflict"),
	// --- 5xx: Server error
	INTERNAL_ERROR(5000, HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi hệ thống"),
	DATABASE_ERROR(5001, HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi kết nối cơ sở dữ liệu"),
	UNSUPPORTED_MEDIA_TYPE(4150, HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Unsupported Media Type"),
	
	
	;
	
	

	private final int code;
	private final HttpStatus httpStatus;
	private final String defaultMessage;

	AppErrorCode(int code, HttpStatus httpStatus, String message) {
		this.code = code;
		this.httpStatus = httpStatus;
		this.defaultMessage = message;
	}

	public int getCode() {
		return code;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}
}
