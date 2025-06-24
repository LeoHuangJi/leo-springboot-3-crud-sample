package vn.leoo.auth.service.impl;

import java.net.URI;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import vn.leoo.auth.entity.AuthTokenEntity;
import vn.leoo.auth.entity.UserEntity;
import vn.leoo.auth.enums.AuthProvider;
import vn.leoo.auth.exception.BadRequestException;
import vn.leoo.auth.mapper.AuthTokenMapper;
import vn.leoo.auth.payload.AuthResponse;
import vn.leoo.auth.payload.DeviceSessionDTO;
import vn.leoo.auth.payload.LoginRequest;
import vn.leoo.auth.payload.SignUpRequest;
import vn.leoo.auth.payload.TokenRefreshResponse;
import vn.leoo.auth.repository.AuthTokenRepository;
import vn.leoo.auth.repository.UserRepository;
import vn.leoo.auth.security.TokenProvider;
import vn.leoo.auth.security.UserPrincipal;
import vn.leoo.auth.service.AuthService;
import vn.leoo.auth.service.AuthTokenService;
import vn.leoo.auth.util.SecurityUtil;
import vn.leoo.common.constants.AppErrorCode;
import vn.leoo.common.constants.Message;
import vn.leoo.common.dto.ResponseData;
import vn.leoo.common.exception.ServiceException;

@Service
public class AuthImpl implements AuthService {

	private AuthenticationManager authenticationManager;

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final TokenProvider tokenProvider;
	private final AuthTokenMapper authTokenMapper;

	private final AuthTokenService authTokenService;

	public AuthImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			PasswordEncoder passwordEncoder, TokenProvider tokenProvider, AuthTokenMapper authTokenMapper,
			AuthTokenService authTokenService) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
		this.authTokenMapper = authTokenMapper;
		this.authTokenService = authTokenService;
	}

	@Transactional(readOnly = true)
	public ResponseData<AuthResponse> login(LoginRequest loginRequest, HttpServletRequest httpRequest) {
		if (!StringUtils.hasText(loginRequest.getEmail()) || !StringUtils.hasText(loginRequest.getPassword())) {
			throw new ServiceException(AppErrorCode.BAD_REQUEST, "Email và mật khẩu không được để trống");
		}

		// Tìm user theo email
		UserEntity user = userRepository.findByEmail(loginRequest.getEmail())
				.orElseThrow(() -> new ServiceException(AppErrorCode.UNAUTHORIZED, "Email hoặc mật khẩu không đúng"));

		// So sánh mật khẩu
		if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
			throw new ServiceException(AppErrorCode.UNAUTHORIZED, "Email hoặc mật khẩu không đúng");
		}

		// Tạo UserPrincipal nếu bạn cần
		UserPrincipal userPrincipal = UserPrincipal.create(user);

		// Tạo token & refreshToken
		String token = tokenProvider.createToken(userPrincipal.getId());
		String refreshToken = tokenProvider.generateRefreshToken(userPrincipal.getId());

		AuthResponse authResponse = new AuthResponse(token, userPrincipal.getId(), userPrincipal.getUsername(),
				userPrincipal.getName(), userPrincipal.getImageUrl(), refreshToken);

		// Lưu refresh token vào DB hoặc cache nếu cần
		authTokenService.saveAuthenToken(userPrincipal, httpRequest, refreshToken);

		return ResponseData.ok(authResponse);
	}

	/*
	 * @Transactional(readOnly = true) public ResponseData login(LoginRequest
	 * loginRequest, HttpServletRequest httpRequest) { try { if
	 * (!StringUtils.hasText(loginRequest.getEmail()) ||
	 * !StringUtils.hasText(loginRequest.getPassword())) { throw new
	 * ServiceException(HttpStatus.BAD_REQUEST,
	 * "Email và mật khẩu không được để trống"); } Authentication authentication =
	 * authenticationManager.authenticate( new
	 * UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
	 * loginRequest.getPassword()));
	 * 
	 * SecurityContextHolder.getContext().setAuthentication(authentication);
	 * 
	 * UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
	 * 
	 * String token = tokenProvider.createToken(userPrincipal.getId()); String
	 * refreshToken = tokenProvider.generateRefreshToken(userPrincipal.getId());
	 * AuthResponse authResponse = new AuthResponse(token, userPrincipal.getId(),
	 * userPrincipal.getUsername(), userPrincipal.getName(),
	 * userPrincipal.getImageUrl(), refreshToken);
	 * 
	 * saveAuthenToken(userPrincipal, httpRequest, refreshToken);
	 * 
	 * return new ResponseData(HttpStatus.OK, true, Message.OK, authResponse);
	 * 
	 * } catch (UsernameNotFoundException e) { throw new
	 * ServiceException(HttpStatus.UNAUTHORIZED,
	 * "Email không tồn tại trong hệ thống"); } catch (BadCredentialsException e) {
	 * throw new ServiceException(HttpStatus.UNAUTHORIZED, "Mật khẩu không đúng"); }
	 * catch (DisabledException e) { throw new
	 * ServiceException(HttpStatus.FORBIDDEN, "Tài khoản đã bị vô hiệu hóa"); }
	 * catch (Exception e) { throw new
	 * ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi hệ thống: " +
	 * e.getMessage()); } }
	 */

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public ResponseData<UserEntity> registerUser(SignUpRequest signUpRequest) {
		try {

			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				throw new BadRequestException("Email address already in use.");
			}

			// Creating user's account
			UserEntity user = new UserEntity();
			user.setName(signUpRequest.getName());
			user.setEmail(signUpRequest.getEmail());
			user.setProvider(AuthProvider.local);
			user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

			UserEntity result = userRepository.save(user);

			return ResponseData.ok(result);

		} catch (Exception e) {
			throw new ServiceException(AppErrorCode.INTERNAL_ERROR, e.getMessage());
		}
	}

	/*
	 * public ResponseData deleteSession(String id) { // truyền session của thiết bị
	 * AuthTokenEntity token = tokenRepo.findById(id) .orElseThrow(() -> new
	 * NotFoundException("Session not found")); Long userId = ((UserPrincipal)
	 * auth.getPrincipal()).getId(); if (!token.getUserId().equals(userId)) { throw
	 * new AccessDeniedException("Not your session"); }
	 * 
	 * token.setRevoked(true); tokenRepo.save(token);
	 * 
	 * }
	 * 
	 * public ResponseData revokeAllSessions(String id) { // Xóa theo user đăng nhập
	 * AuthTokenEntity token = tokenRepo.findById(id) .orElseThrow(() -> new
	 * NotFoundException("Session not found")); Long userId = ((UserPrincipal)
	 * auth.getPrincipal()).getId(); List<AuthTokenEntity> sessions =
	 * tokenRepo.findAllByUserIdAndRevokedFalse(userId); for (AuthTokenEntity
	 * tokenEntity : sessions) { token.setRevoked(true); }
	 * tokenRepo.saveAll(sessions);
	 * 
	 * }
	 */

}
