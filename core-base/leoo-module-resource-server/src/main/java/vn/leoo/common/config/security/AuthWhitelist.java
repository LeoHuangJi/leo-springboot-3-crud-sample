package vn.leoo.common.config.security;

public class AuthWhitelist {
	public static final String[] PATHS = {
	        "/v2/api-docs",
	        "/v3/api-docs/**",
	        "/swagger-resources/**",
	        "/configuration/ui",
	        "/configuration/security",
	        "/swagger-ui.html",
	        "/webjars/**",
	        "/swagger-ui/**",
	        "/swagger-ui/index.html",
	        "/tutorials/**",
	        "/auth/login",           
	        "/auth/refresh-token"    
	    };
}
