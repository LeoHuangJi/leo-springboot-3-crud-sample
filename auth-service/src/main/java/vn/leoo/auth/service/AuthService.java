package vn.leoo.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import vn.leoo.auth.payload.LoginRequest;
import vn.leoo.auth.payload.SignUpRequest;
import vn.leoo.common.dto.ResponseData;

public interface AuthService {
	ResponseData login(LoginRequest loginRequest, HttpServletRequest httpRequest);

	ResponseData registerUser(SignUpRequest signUpRequest);


	
}
