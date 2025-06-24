package vn.leoo.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.leoo.auth.service.AuthService;
import vn.leoo.auth.service.AuthTokenService;
import vn.leoo.common.dto.ResponseData;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {
	@Autowired
    private AuthTokenService authTokenService;
	
	@PostMapping("/devices")
    public ResponseEntity<?> allDevices( ) {
    	ResponseData res = authTokenService.getDeviceSessions();
		return new ResponseEntity<>(res, res.getHttpStatus());
    	
    }
	
	
	/*
	 * @DeleteMapping("/{id}") public ResponseEntity<?> refreshToken( ) {
	 * ResponseData res = authService.getDeviceSessions(); return new
	 * ResponseEntity<>(res, res.getHttpStatus());
	 * 
	 * }
	 */
	 
  
}