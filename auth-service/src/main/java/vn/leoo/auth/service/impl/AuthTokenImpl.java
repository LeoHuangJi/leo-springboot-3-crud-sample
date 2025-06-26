package vn.leoo.auth.service.impl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import vn.leoo.auth.entity.AuthTokenEntity;
import vn.leoo.auth.mapper.AuthTokenMapper;
import vn.leoo.auth.payload.DeviceSessionDTO;
import vn.leoo.auth.payload.TokenRefreshRequest;
import vn.leoo.auth.payload.TokenRefreshResponse;
import vn.leoo.auth.repository.AuthTokenRepository;
import vn.leoo.auth.security.TokenProvider;
import vn.leoo.auth.security.UserPrincipal;
import vn.leoo.auth.service.AuthTokenService;
import vn.leoo.auth.util.SecurityUtil;
import vn.leoo.common.constants.Message;
import vn.leoo.common.dto.ResponseData;

@Service
public class AuthTokenImpl implements AuthTokenService {

	private final AuthTokenRepository tokenRepo;
	private final TokenProvider tokenProvider;
	private final AuthTokenMapper authTokenMapper;

	public AuthTokenImpl(AuthTokenRepository tokenRepo, TokenProvider tokenProvider, AuthTokenMapper authTokenMapper) {
		super();
		this.tokenRepo = tokenRepo;
		this.tokenProvider = tokenProvider;
		this.authTokenMapper = authTokenMapper;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void saveAuthenToken(UserPrincipal userPrincipal, HttpServletRequest httpRequest, String refreshToken) {
		String ip = httpRequest.getRemoteAddr();
		String userAgent = httpRequest.getHeader("User-Agent");
		AuthTokenEntity entity = new AuthTokenEntity();
		entity.setUserId(userPrincipal.getId());
		entity.setRefreshToken(refreshToken);
		entity.setIssuedAt(Instant.now());
		entity.setExpiresAt(Instant.now().plus(7, ChronoUnit.DAYS));
		entity.setUserAgent(userAgent);
		entity.setIpAddress(ip);
		entity.setRevoked(false);
		tokenRepo.save(entity);

	}

	@Transactional(readOnly = true)
	public ResponseData<TokenRefreshResponse> refreshToken(TokenRefreshRequest tokenRefreshRequest, HttpServletRequest httpRequest) {
		if (ObjectUtils.isEmpty(tokenRefreshRequest.getRefreshToken())) {
			return  ResponseData.badRequest("Refresh token is null");
		}

		Claims claims = tokenProvider.parseToken(tokenRefreshRequest.getRefreshToken());

		if (!"refresh".equals(claims.get("type"))) {
			throw new RuntimeException("Invalid token type");
		}

		AuthTokenEntity authToken = tokenRepo.findValidToken(tokenRefreshRequest.getRefreshToken(), Instant.now())
				.orElseThrow(() -> new RuntimeException("Refresh token invalid or expired"));

		String ip = httpRequest.getRemoteAddr();
		String userAgent = httpRequest.getHeader("User-Agent");
		// check nếu copy refresh token sang 1 thiết bị khác, sẽ bị chặn
		if (!Objects.equals(authToken.getUserAgent(), userAgent) || !Objects.equals(ip, authToken.getIpAddress())) {

			return  ResponseData.forbidden(Message.FORBIDDEN);
		}

		String newAccessToken = tokenProvider.createToken(claims.getSubject());
		TokenRefreshResponse refreshResponse = new TokenRefreshResponse(newAccessToken,
				tokenRefreshRequest.getRefreshToken(), null);
		return  ResponseData.ok(refreshResponse);

	}

	@Transactional(readOnly = true)
	public ResponseData<List<DeviceSessionDTO>> getDeviceSessions() {

		String id = SecurityUtil.getCurrentUserId();
		List<AuthTokenEntity> lstAuthTokenEntities = tokenRepo.findLatestSessionsPerDeviceRaw(id);

		List<DeviceSessionDTO> lstDtos = authTokenMapper.toDTOList(lstAuthTokenEntities);
		
		return  ResponseData.ok(lstDtos);
	}
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public ResponseData<String> logout(String refreshToken) {
		tokenRepo.findByRefreshToken(refreshToken).ifPresent(token -> {
			token.setRevoked(true);
			tokenRepo.save(token);
		});
		return  ResponseData.ok(Message.OK);
	}

}
