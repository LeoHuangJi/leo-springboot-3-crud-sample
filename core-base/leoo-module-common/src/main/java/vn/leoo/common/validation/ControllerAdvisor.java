package vn.leoo.common.validation;

import java.nio.file.AccessDeniedException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import vn.leoo.common.constants.AppErrorCode;
import vn.leoo.common.dto.ResponseData;
import vn.leoo.common.dto.SubError;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path.Node;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	@Autowired
	private MessageSource mgsSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<FieldError> fields = ex.getBindingResult().getFieldErrors();
		List<SubError> errors = new ArrayList<>();

		for (FieldError e : fields) {
			String msg = mgsSource.getMessage(e.getDefaultMessage(), null, e.getDefaultMessage(), new Locale("vi"));

			SubError error = new SubError();
			error.setMessage(msg);
			error.setFieldName(e.getField());
			error.setValue(e.getRejectedValue() != null ? e.getRejectedValue().toString() : "");
			errors.add(error);
		}

		ResponseData<Object> response = ResponseData.<Object>builder().code(AppErrorCode.BAD_REQUEST.getCode())
				.status(false).message("Dữ liệu không hợp lệ").data(errors).timestamp(Instant.now()).build();

		return new ResponseEntity<>(response, AppErrorCode.BAD_REQUEST.getHttpStatus());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		List<SubError> errors = new ArrayList<>();

		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			String fieldName = "";
			for (Node node : violation.getPropertyPath()) {
				fieldName = node.getName();
			}

			SubError error = new SubError();
			String msg = mgsSource.getMessage(violation.getMessage(), null, violation.getMessage(), Locale.ENGLISH);
			error.setMessage(msg);
			error.setFieldName(fieldName);
			error.setValue(violation.getInvalidValue().toString());
			errors.add(error);
		}

		ResponseData<Object> response = ResponseData.<Object>builder().code(AppErrorCode.BAD_REQUEST.getCode())

				.status(false).message(ex.getMessage()).data(errors).timestamp(Instant.now()).build();

		return new ResponseEntity<>(response, AppErrorCode.BAD_REQUEST.getHttpStatus());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(Map.of("status", 403, "error", "Forbidden", "message", ex.getMessage()));
	}
}