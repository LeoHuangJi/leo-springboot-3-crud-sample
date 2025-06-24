/*
 * package vn.leoo.common.util;
 * 
 * import java.util.Map;
 * 
 * import org.springframework.security.core.context.SecurityContextHolder;
 * import org.springframework.security.oauth2.jwt.Jwt; import
 * org.springframework.security.oauth2.jwt.JwtClaimNames; import
 * org.springframework.security.oauth2.provider.OAuth2Authentication; import
 * org.springframework.security.oauth2.provider.authentication.
 * OAuth2AuthenticationDetails;
 * 
 * import com.google.gson.Gson;
 * 
 * import vn.leoo.common.dto.CurrentUserDTO;
 * 
 * public class UserLogonUtils { public static String getUsernameOrSystem() {
 * 
 * try { Object principal =
 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); if
 * (principal instanceof Jwt) { return (String) ((Jwt)
 * principal).getClaims().get(JwtClaimNames.SUB);
 * 
 * } else { return "SYSTEM"; } } catch (NullPointerException e) {
 * 
 * return "SYSTEM"; } }
 * 
 * public static String getUsername() {
 * 
 * try { Object principal =
 * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 
 * if (principal instanceof Jwt) { return (String) ((Jwt)
 * principal).getClaims().get(JwtClaimNames.SUB); } if (principal instanceof
 * String) { CurrentUserDTO account = new CurrentUserDTO();
 * account.setAccount((String) principal); return account.getAccount(); } else {
 * return "anonymous"; } } catch (NullPointerException e) { throw new
 * SecurityException("Bạn phải đăng nhập để thực hiện chức năng này.!"); } }
 * 
 * public static CurrentUserDTO currentUser() {
 * 
 * try { Object principal =
 * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 * 
 * if (principal instanceof Jwt) { String accountJson = ((Jwt)
 * principal).getClaims().get(Constants.JwtClaimName.USER_INFO).toString();
 * return new Gson().fromJson(accountJson, Account.class); } else { throw new
 * SecurityException("Bạn phải đăng nhập để thực hiện chức năng này!"); } }
 * catch (NullPointerException e) { throw new
 * SecurityException("Bạn phải đăng nhập để thực hiện chức năng này!"); }
 * 
 * try { Object principal =
 * SecurityContextHolder.getContext().getAuthentication();
 * 
 * if (principal instanceof OAuth2Authentication) {
 * 
 * OAuth2AuthenticationDetails oAuth2AuthenticationDetails =
 * (OAuth2AuthenticationDetails) ((OAuth2Authentication) principal)
 * .getDetails(); Map<String, Object> decodedDetails = (Map<String, Object>)
 * oAuth2AuthenticationDetails .getDecodedDetails();
 * 
 * String accountJson = (String) decodedDetails.get("user_info"); return new
 * Gson().fromJson(accountJson, CurrentUserDTO.class); } else { TokenUtils
 * tokenUtils = TokenUtils.getInstance(); Map payload = tokenUtils.getPayload();
 * String userInfo = (String) payload.get("user_info"); CurrentUserDTO dto =
 * (CurrentUserDTO) JsonUtils.convertJsonStringtoObject(userInfo,
 * CurrentUserDTO.class); return dto; } } catch (NullPointerException e) { throw
 * new SecurityException("Bạn phải đăng nhập để thực hiện chức năng này..!"); }
 * } }
 */