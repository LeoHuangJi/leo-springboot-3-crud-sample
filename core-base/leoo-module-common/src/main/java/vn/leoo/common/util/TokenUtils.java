/*
 * package vn.leoo.common.util;
 * 
 * import java.nio.charset.StandardCharsets; import
 * java.security.InvalidKeyException; import
 * java.security.NoSuchAlgorithmException; import java.util.Base64; import
 * java.util.Map; import javax.crypto.Mac; import
 * javax.crypto.spec.SecretKeySpec; import
 * jakarta.servlet.http.HttpServletRequest; import
 * org.apache.commons.lang3.StringUtils; import
 * org.springframework.core.env.Environment; import
 * org.springframework.web.context.request.RequestContextHolder; import
 * org.springframework.web.context.request.ServletRequestAttributes;
 * 
 * import vn.leoo.common.component.SpringContext;
 * 
 * public class TokenUtils { HttpServletRequest request; Environment env;
 * 
 * private TokenUtils() { try { this.request =
 * ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).
 * getRequest(); this.env =
 * (Environment)SpringContext.getBean(Environment.class); } catch
 * (IllegalStateException var2) { System.out.println(var2.getMessage()); }
 * 
 * }
 * 
 * public static TokenUtils getInstance() { TokenUtils util = new TokenUtils();
 * return util; }
 * 
 * public String getTokenFromRequestContextHolder() { String token = null;
 * 
 * try { String requestHeader = this.request.getHeader("Authorization"); token =
 * StringUtils.substring(requestHeader, "Bearer ".length()); } catch (Exception
 * var3) { System.err.println(var3.getMessage()); }
 * 
 * return token; }
 * 
 * public Map<String, Object> getPayload() { String token =
 * this.getTokenFromRequestContextHolder(); Map<String, Object> payload =
 * this.getPayload(token); return payload; }
 * 
 * public Map<String, Object> getPayload(String token) { Map<String, Object>
 * payload = null; if (StringUtils.isNotEmpty(token)) { String[] parts =
 * token.split("\\.");
 * 
 * try { payload = JsonUtils.convertJsonStringToMap(this.decode(parts[1])); }
 * catch (Exception var5) { System.err.println(var5.getMessage()); } }
 * 
 * return payload; }
 * 
 * public boolean isExpired() { Map<String, Object> payload = this.getPayload();
 * long exp = ((Integer)payload.get("exp")).longValue(); return exp >
 * System.currentTimeMillis() / 1000L; }
 * 
 * public boolean isExpired(String token) { Map<String, Object> payload =
 * this.getPayload(token); long exp = ((Integer)payload.get("exp")).longValue();
 * Long current = System.currentTimeMillis() / 1000L; return exp < current; }
 * 
 * public boolean verifySignature() { String token =
 * this.getTokenFromRequestContextHolder(); boolean verifyOk =
 * this.verifySignature(token); return verifyOk; }
 * 
 * public boolean verifySignature(String token) { String secret =
 * this.env.getProperty("security.oauth2.client.base64-secret"); boolean
 * verifyOk = false; if (StringUtils.isNotEmpty(token)) { String[] parts =
 * token.split("\\.");
 * 
 * try { String signature = parts[2]; String headerAndPayloadHashed =
 * this.hmacSha256(parts[0] + "." + parts[1], secret); if
 * (signature.equals(headerAndPayloadHashed)) { verifyOk = true; } } catch
 * (Exception var7) { verifyOk = false; System.err.println(var7.getMessage()); }
 * }
 * 
 * return verifyOk; }
 * 
 * public String hmacSha256(String data, String secret) { try { byte[] hash =
 * secret.getBytes(StandardCharsets.UTF_8); Mac sha256Hmac =
 * Mac.getInstance("HmacSHA256"); SecretKeySpec secretKey = new
 * SecretKeySpec(hash, "HmacSHA256"); sha256Hmac.init(secretKey); byte[]
 * signedBytes = sha256Hmac.doFinal(data.getBytes(StandardCharsets.UTF_8));
 * return this.encode(signedBytes); } catch (InvalidKeyException |
 * NoSuchAlgorithmException var7) { return null; } }
 * 
 * private String decode(String encodedString) { return new
 * String(Base64.getUrlDecoder().decode(encodedString)); }
 * 
 * private String encode(byte[] signedBytes) { return
 * Base64.getUrlEncoder().withoutPadding().encodeToString(signedBytes); }
 * 
 * static class TokenUtilsHelper { static TokenUtils INSTANCE = new
 * TokenUtils();
 * 
 * TokenUtilsHelper() { } } }
 */