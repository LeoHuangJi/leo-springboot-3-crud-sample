/*
 * package vn.leoo.common.config.security;
 * 
 * import com.fasterxml.jackson.databind.ObjectMapper;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.access.AccessDeniedException; import
 * org.springframework.security.web.access.AccessDeniedHandler; import
 * org.springframework.stereotype.Component;
 * 
 * import jakarta.servlet.ServletException; import
 * jakarta.servlet.http.HttpServletRequest; import
 * jakarta.servlet.http.HttpServletResponse;
 * 
 * import java.io.IOException; import java.util.Date; import java.util.HashMap;
 * import java.util.Map;
 * 
 * @Component("customAccessDeniedHandler") public class CustomAccessDeniedHander
 * implements AccessDeniedHandler {
 * 
 * @Autowired private ObjectMapper objectMapper;
 * 
 * @SuppressWarnings({ "rawtypes", "unchecked" })
 * 
 * @Override public void handle(HttpServletRequest request, HttpServletResponse
 * response, AccessDeniedException accessDeniedException) throws IOException,
 * ServletException { Map map = new HashMap(); map.put("error", "400");
 * map.put("message", accessDeniedException.getMessage()); map.put("path",
 * request.getServletPath()); map.put("timestamp", String.valueOf(new
 * Date().getTime()));
 * 
 * response.setContentType("application/json;charset=UTF-8");
 * response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
 * response.getWriter().write(objectMapper.writeValueAsString(map)); } }
 */