package vn.leoo.common.util.web;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import vn.leoo.common.dto.ErrorDTO;
import vn.leoo.common.dto.ExceptionDTO;
import vn.leoo.common.dto.InformationDTO;
import vn.leoo.common.dto.ResultDTO;
import vn.leoo.common.util.ResourceUtils;
import vn.leoo.common.util.StringUtils;


public class ResponseUtil {
	static ResourceBundle rbMsg = null;
	static ResourceBundle rbErr = null;
	static {
		// Khoi chay ResourceBundle
		try {
			if(rbMsg == null)
				rbMsg = ResourceUtils.getResourceBundleFromClassPath("i18n.messages", new Locale("vi", "VN"));
			if(rbErr == null)
				rbErr = ResourceUtils.getResourceBundleFromClassPath("i18n.errors", new Locale("vi", "VN"));
//			ResourceUtils.loadFromClassPath("i18n.messages", new Locale("vi", "VN"));
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, null);
    }
	
	public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return maybeResponse.map(response -> ResponseEntity.ok().headers(header).body(response))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }	
	
	public static ResponseEntity<ResultDTO> ok(Object data){
		HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    	
		Response res = new Response(data);		
		return ResponseEntity.ok()
	            .headers(httpHeaders)
	            .body(res.getResult());
    }
	
	public static ResponseEntity<ResultDTO> ok(String key, String message, Map<String, Object> args){
		HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    	
		Response res = new Response(true, key, message, args);		
		return ResponseEntity.ok()
	            .headers(httpHeaders)
	            .body(res.getResult());
    }

	public static ResponseEntity<ErrorDTO> bad(String errorCode, String description, Map<String, Object> args){
    	HttpHeaders httpHeaders = new HttpHeaders();
    	httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    	
    	Response res = new Response(false, errorCode, description, args);
    	return ResponseEntity.badRequest()
    			.headers(httpHeaders)
    			.body(res.getError());
    }
	
	static class Response {
        private ErrorDTO error;
        private ResultDTO result;

	    public Response(Object data) {
	    	InformationDTO msg = new InformationDTO(true, data);
	    	result = new ResultDTO(msg);
	    }

	    public Response(boolean isOk, String key, String message, Map<String, Object> args) {
	        String info = message;
	        if(isOk) {
		        if(StringUtils.isNotEmpty(key) && StringUtils.isEmpty(info)) {
		        	info = ResourceUtils.getString(rbMsg, key);	        	
		        }
		        info = StringUtils.format(info, args);
		        InformationDTO msg = new InformationDTO(true, info, key);
		        result = new ResultDTO(msg);
	        }else {
	        	if(StringUtils.isNotEmpty(key) && StringUtils.isEmpty(info)) {
		        	info = ResourceUtils.getString(rbErr, key);	        	
		        }
		        info = StringUtils.format(info, args);
		        ExceptionDTO ex = new ExceptionDTO(key, info, key);
	        	error = new ErrorDTO(ex);
	        }
	    }

		public ErrorDTO getError() {
			return error;
		}

		public void setError(ErrorDTO error) {
			this.error = error;
		}

		public ResultDTO getResult() {
			return result;
		}

		public void setResult(ResultDTO result) {
			this.result = result;
		}
	}
}
