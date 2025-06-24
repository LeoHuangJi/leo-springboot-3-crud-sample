package vn.leoo.auth.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // thay cho EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;
import vn.leoo.auth.security.*;
import vn.leoo.auth.security.oauth2.CustomOAuth2UserService;
import vn.leoo.auth.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import vn.leoo.auth.security.oauth2.OAuth2AuthenticationFailureHandler;
import vn.leoo.auth.security.oauth2.OAuth2AuthenticationSuccessHandler;
import vn.leoo.common.constants.AppErrorCode;

/*
 * cấu hình các filter của Spring Security
 * 
 * */

@Configuration
@EnableMethodSecurity( // thay thế cho @EnableGlobalMethodSecurity
		securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;
	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
	private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	public SecurityConfig(CustomUserDetailsService customUserDetailsService,
			CustomOAuth2UserService customOAuth2UserService,
			OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
			OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler,
			HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
		this.customOAuth2UserService = customOAuth2UserService;
		this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
		this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
		this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
	}

	// Đây là request đi tới /api/**
	private final RequestMatcher apiRequestMatcher =new AntPathRequestMatcher("/api/**");

	private static final String[] AUTH_WHITELIST = { // -- Swagger UI v2
			"/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swagger-ui.html", "/webjars/**", // -- Swagger UI v3 (OpenAPI)
																			// "/v3/api-docs/**",
			"/swagger-ui/**",
			// Auth endpoints
			"/api/v1/auth/login/**", "/api/v1/auth/signup/**", "/api/v1/auth/refresh-token/**"
			// other public endpoints can be added here
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.cors(Customizer.withDefaults()).csrf(csrf -> csrf.disable()).formLogin(form -> form.disable())
				.httpBasic(httpBasic -> httpBasic.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(ex -> ex.defaultAuthenticationEntryPointFor(new RestAuthenticationEntryPoint(), // lỗi
																													// xác
																													// thực
						apiRequestMatcher).defaultAccessDeniedHandlerFor((request, response, accessDeniedException) -> {
							response.setContentType("application/json");
							response.setStatus(AppErrorCode.UNAUTHORIZED.getHttpStatus().value());
							Map<String, Object> map = new HashMap<>();
							map.put("code", AppErrorCode.UNAUTHORIZED.getCode());
							map.put("status", false);
							map.put("message", "Context path not allow");
							map.put("path", request.getServletPath());
							map.put("timestamp", String.valueOf(new Date().getTime()));

							response.setContentType("application/json;charset=UTF-8");
							response.getWriter().write(new Gson().toJson(map));
							return;

						}, apiRequestMatcher))
				.authorizeHttpRequests(auth -> auth
						// cho phép truy cập ko cần login
						//.requestMatchers(AUTH_WHITELIST).permitAll()
						.anyRequest().permitAll()

				)
				.oauth2Login(oauth2 -> oauth2
						.authorizationEndpoint(endpoint -> endpoint.baseUri("/oauth2/authorize")
								.authorizationRequestRepository(cookieAuthorizationRequestRepository()))
						.redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
						.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
						.successHandler(oAuth2AuthenticationSuccessHandler)
						.failureHandler(oAuth2AuthenticationFailureHandler));
//call TokenAuthenticationFilter

		http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter();
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> cookieAuthorizationRequestRepository() {
		return httpCookieOAuth2AuthorizationRequestRepository;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Lấy AuthenticationManager từ AuthenticationConfiguration
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
