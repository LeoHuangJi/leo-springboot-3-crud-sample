package vn.leoo.auth.payload;

import java.time.Instant;

public record DeviceSessionDTO(
	    Long id,
	    String userAgent,
	    String ipAddress,
	    Instant issuedAt,
	    Instant expiresAt
	) {}