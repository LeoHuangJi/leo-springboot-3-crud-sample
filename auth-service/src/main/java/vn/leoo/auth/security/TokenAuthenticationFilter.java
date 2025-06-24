package vn.leoo.auth.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.ibm.disthub2.spi.EntryPoint;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.leoo.common.constants.AppErrorCode;
import vn.leoo.common.dto.ResponseData;
import vn.leoo.common.exception.ServiceException;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * Xử lý logic tùy chỉnh trên mỗi request, như xác thực token
 * 
 * */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			String jwt = getJwtFromRequest(request);
			if (StringUtils.hasText(jwt)) {
				ResponseData<String> res = tokenProvider.validateToken(jwt);
				if (res.getStatus()) {
					String userId = res.getData().toString();
					UserDetails userDetails = customUserDetailsService.loadUserById(userId);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {

					throw new ServiceException(AppErrorCode.UNAUTHORIZED, res.getMessage());
				}

			}
		} catch (Exception ex) {

			logger.error(ex.getMessage());
			// Không throw ex sẽ khiến Spring không gọi EntryPoint

			Map<String, Object> map = new HashMap<>();
			map.put("code", AppErrorCode.UNAUTHORIZED.getCode());
			map.put("status", false);
			map.put("message", ex.getMessage());
			map.put("path", request.getServletPath());
			map.put("timestamp", String.valueOf(new Date().getTime()));
			response.setStatus(401);
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(new Gson().toJson(map));
			return;

		}

		filterChain.doFilter(request, response);

	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
