package vn.leoo.common.exception;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import vn.leoo.common.constants.AppErrorCode;
import vn.leoo.common.dto.ResponseData;

@ControllerAdvice
public class ApplicationExceptionHandler {

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<Object> handleException(HttpMediaTypeNotSupportedException e) {
		String provided = e.getContentType().toString();
		List<String> supported = e.getSupportedMediaTypes().stream().map(MimeType::toString)
				.collect(Collectors.toList());
		String error = provided + " is not one of the supported media types (" + String.join(", ", supported) + ")";

		return new ResponseEntity<>(
				new ResponseData<Object>(AppErrorCode.UNSUPPORTED_MEDIA_TYPE, false, String.format("%s: %s. %s",
						HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString(), e.getLocalizedMessage(), error)),
				HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

//	@ExceptionHandler(HttpMessageNotReadableException.class)
//	public ResponseEntity<Map<String, String>> handleException(HttpMessageNotReadableException e) throws IOException {
//
//		Map<String, String> errorResponse = new HashMap<>();
//		errorResponse.put("message", e.getLocalizedMessage());
//		errorResponse.put("status", HttpStatus.BAD_REQUEST.toString());
//
//		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<Object> handleException(HttpRequestMethodNotSupportedException e) throws IOException {
		String provided = e.getMethod();
		List<String> supported = Arrays.asList(e.getSupportedMethods());
		String error = provided + " is not one of the supported Http Methods (" + String.join(", ", supported) + ")";
		return new ResponseEntity<>(
				new ResponseData<Object>(AppErrorCode.METHOD_NOT_ALLOWED, false, String.format("%s: %s. %s",
						HttpStatus.METHOD_NOT_ALLOWED.toString(), e.getLocalizedMessage(), error)),
				HttpStatus.METHOD_NOT_ALLOWED);
	}

	/*
	 * @ExceptionHandler(ServiceException.class) public ResponseEntity<Object>
	 * handleServiceException(ServiceException ex, WebRequest request) {
	 * 
	 * return new ResponseEntity<>(new ResponseData(ex.getHttpStatus(), false,
	 * ex.getMessage()), ex.getHttpStatus()); }
	 */
	@ExceptionHandler(ServiceException.class)
	public ResponseEntity<Object> handleServiceException2(ServiceException ex, WebRequest request) {

		ResponseData<Object> response = ResponseData.<Object>builder().code((int) ex.getCode()) 
																								
				.status(false).message(ex.getMessage()).data(ex.getData()).timestamp(ex.getTimestamp()).build();

		return new ResponseEntity<>(response, ex.getHttpStatus());

	}

	// Có thể thêm các Exception khác nếu cần
	/*
	 * @ExceptionHandler(Exception.class) public ResponseEntity<Object>
	 * handleAll(Exception ex, WebRequest request) { return new ResponseEntity<>(
	 * new ResponseData<Object>(AppErrorCode.INTERNAL_ERROR, false, "Lỗi hệ thống: "
	 * + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR); }
	 */

}