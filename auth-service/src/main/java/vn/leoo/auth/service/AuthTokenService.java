package vn.leoo.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import vn.leoo.auth.payload.TokenRefreshRequest;
import vn.leoo.auth.security.UserPrincipal;
import vn.leoo.common.dto.ResponseData;

public interface AuthTokenService {
	void saveAuthenToken(UserPrincipal userPrincipal, HttpServletRequest httpRequest, String refreshToken);
	 ResponseData refreshToken(  TokenRefreshRequest refreshRequest,HttpServletRequest httpRequest) ;
	 ResponseData getDeviceSessions() ;
	 ResponseData logout(String refreshToken) ;
	 
}
