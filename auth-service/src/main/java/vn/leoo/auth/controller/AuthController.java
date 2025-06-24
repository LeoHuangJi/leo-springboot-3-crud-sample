package vn.leoo.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import vn.leoo.auth.payload.LoginRequest;
import vn.leoo.auth.payload.SignUpRequest;
import vn.leoo.auth.payload.TokenRefreshRequest;

import vn.leoo.auth.service.AuthService;
import vn.leoo.auth.service.AuthTokenService;
import vn.leoo.common.dto.ResponseData;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

   

    @Autowired
    private AuthService authService;
    @Autowired
    private  AuthTokenService authTokenService;
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,HttpServletRequest httpRequest) {
    	ResponseData res = authService.login(loginRequest,httpRequest);
		return new ResponseEntity<>(res, res.getHttpStatus());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    	ResponseData res = authService.registerUser(signUpRequest);
		return new ResponseEntity<>(res, res.getHttpStatus());
    	
    }
    
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest refreshRequest, HttpServletRequest httpRequest ) {
    	ResponseData res = authTokenService.refreshToken(refreshRequest,httpRequest);
		return new ResponseEntity<>(res, res.getHttpStatus());
    	
    }
    
   
}
