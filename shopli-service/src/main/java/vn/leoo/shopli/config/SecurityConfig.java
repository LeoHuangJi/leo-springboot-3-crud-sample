/*
 * package vn.leoo.shopli.config;
 * 
 * import org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.security.config.annotation.web.builders.HttpSecurity;
 * import org.springframework.security.config.annotation.web.configuration.
 * EnableWebSecurity; import
 * org.springframework.security.config.annotation.web.configurers.
 * AbstractHttpConfigurer; import
 * org.springframework.security.config.http.SessionCreationPolicy; import
 * org.springframework.security.web.SecurityFilterChain; import
 * org.springframework.security.web.authentication.
 * UsernamePasswordAuthenticationFilter;
 * 
 * @Configuration
 * 
 * @EnableWebSecurity public class SecurityConfig { // chạy khi khởi động ứng
 * dụng
 * 
 * 
 * @Bean public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter
 * jwtFilter) throws Exception { return
 * http.csrf(AbstractHttpConfigurer::disable) .sessionManagement(s ->
 * s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
 * .authorizeHttpRequests( auth ->
 * auth.requestMatchers(AuthWhitelist.PATHS).permitAll().anyRequest().
 * authenticated()) .addFilterBefore(jwtFilter,
 * UsernamePasswordAuthenticationFilter.class).build(); } }
 */