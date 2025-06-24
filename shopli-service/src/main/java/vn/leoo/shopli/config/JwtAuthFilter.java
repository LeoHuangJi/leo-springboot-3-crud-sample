/*
 * package vn.leoo.shopli.config;
 * 
 * import java.io.IOException; import java.nio.charset.StandardCharsets; import
 * java.util.Date; import java.util.HashMap; import java.util.List; import
 * java.util.Map; import java.util.stream.Collectors; import
 * java.util.stream.Stream;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.security.authentication.
 * UsernamePasswordAuthenticationToken; import
 * org.springframework.security.core.Authentication; import
 * org.springframework.security.core.GrantedAuthority; import
 * org.springframework.security.core.authority.SimpleGrantedAuthority; import
 * org.springframework.security.core.context.SecurityContextHolder; import
 * org.springframework.stereotype.Component; import
 * org.springframework.util.AntPathMatcher; import
 * org.springframework.web.filter.OncePerRequestFilter;
 * 
 * import com.google.gson.Gson;
 * 
 * import io.jsonwebtoken.Claims; import io.jsonwebtoken.JwtException; import
 * io.jsonwebtoken.Jwts; import io.jsonwebtoken.security.Keys; import
 * jakarta.servlet.FilterChain; import jakarta.servlet.ServletException; import
 * jakarta.servlet.http.HttpServletRequest; import
 * jakarta.servlet.http.HttpServletResponse;
 * 
 * @Component public class JwtAuthFilter extends OncePerRequestFilter { // mỗi
 * lần rquest
 * 
 * @Value("${jwt.auth.tokenSecret}") private String jwtSecret;
 * 
 * 
 * @Override protected void doFilterInternal(HttpServletRequest request,
 * HttpServletResponse response, FilterChain filterChain) throws
 * ServletException, IOException {
 * 
 * String authHeader = request.getHeader("Authorization"); String requestPath =
 * request.getRequestURI(); if (isWhitelisted(requestPath)) {
 * filterChain.doFilter(request, response); // Bỏ qua filter return; }
 * 
 * if (authHeader != null && authHeader.startsWith("Bearer ")) {
 * 
 * String token = authHeader.substring(7); try { Claims claims =
 * Jwts.parserBuilder()
 * .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8))
 * ).build() .parseClaimsJws(token).getBody();
 * 
 * 
 * claims.put("user", Map.of( "id", userId, "roles", List.of("ROLE_USER") ));
 * 
 * String username = claims.getSubject();
 * 
 * Map<String, Object> userMap = (Map<String, Object>) claims.get("user");
 * List<String> roles = (List<String>) userMap.get("roles");
 * 
 * List<GrantedAuthority> authorities =
 * roles.stream().map(SimpleGrantedAuthority::new)
 * .collect(Collectors.toList());
 * 
 * Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
 * authorities); SecurityContextHolder.getContext().setAuthentication(auth);
 * 
 * } catch (JwtException e) { sendUnauthorizedError(response, request,
 * "Token không hợp lệ hoặc đã hết hạn"); return; } }
 * 
 * else { sendUnauthorizedError(response, request,
 * "Thiếu Authorization header hoặc không đúng định dạng Bearer"); return; }
 * 
 * filterChain.doFilter(request, response); }
 * 
 * private boolean isWhitelisted(String path) { return
 * Stream.of(AuthWhitelist.PATHS).anyMatch(pattern -> new
 * AntPathMatcher().match(pattern, path)); }
 * 
 * private void sendUnauthorizedError(HttpServletResponse response,
 * HttpServletRequest request, String message) throws IOException {
 * response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
 * response.setContentType("application/json;charset=UTF-8");
 * 
 * Map<String, Object> map = new HashMap<>(); map.put("code",
 * HttpServletResponse.SC_UNAUTHORIZED); map.put("status", false);
 * map.put("message", message); map.put("path", request.getServletPath());
 * map.put("timestamp", String.valueOf(new Date().getTime()));
 * 
 * response.getWriter().write(new Gson().toJson(map)); } }
 */