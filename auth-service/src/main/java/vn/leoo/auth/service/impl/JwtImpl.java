package vn.leoo.auth.service.impl;

import java.awt.RenderingHints.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtImpl {
	@Value("${app.jwt.secret}")
    private String jwtSecret;

  
}
