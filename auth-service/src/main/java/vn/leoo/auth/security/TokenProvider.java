package vn.leoo.auth.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import vn.leoo.common.constants.AppErrorCode;
import vn.leoo.common.constants.Message;
import vn.leoo.common.dto.ResponseData;
import vn.leoo.common.exception.ServiceException;
import vn.leoo.common.properties.AppProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

@Service
public class TokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	private AppProperties appProperties;

	public TokenProvider(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	public String createToken(String userId) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

		Map<String, Object> claims = new HashMap<>();

		claims.put("user", Map.of("id", userId, "roles", List.of("ROLE_USER")));

		SecretKey key = Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes(StandardCharsets.UTF_8));

		return Jwts.builder().setClaims(claims).setSubject(userId.toString()).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(key, SignatureAlgorithm.HS512).compact();
	}

	public String generateRefreshToken(String userId) {
		SecretKey key = Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes(StandardCharsets.UTF_8));
		Instant now = Instant.now();
		Map<String, Object> claims = Map.of("type", "refresh");
		return Jwts.builder().setClaims(claims).setSubject(userId.toString()).setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(7, ChronoUnit.DAYS))).signWith(key, SignatureAlgorithm.HS512)
				.compact();
	}

	public Claims parseToken(String token) {
		SecretKey key = Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes(StandardCharsets.UTF_8));
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public ResponseData<String> validateToken(String authToken) {
		try {

			SecretKey key = Keys
					.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes(StandardCharsets.UTF_8));
			Jws<Claims> parsedToken = Jwts.parserBuilder().setSigningKey(key).build()

					.parseClaimsJws(authToken)

			;
			Claims claims = parsedToken.getBody();
			String subject = claims.getSubject(); // userId
			Date expiration = claims.getExpiration();
			return ResponseData.ok(subject);
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
			return ResponseData.unauthorized("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
			return ResponseData.unauthorized("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
			return ResponseData.unauthorized("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
			return ResponseData.unauthorized("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
			return ResponseData.unauthorized("JWT claims string is empty.");
		}
	}

}
