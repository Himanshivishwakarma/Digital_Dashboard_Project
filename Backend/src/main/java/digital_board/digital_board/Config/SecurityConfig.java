package digital_board.digital_board.Config;

import org.springframework.web.client.RestTemplate;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Deprecated
public class SecurityConfig {
    private static final String[] public_urls ={
        "/login",
        "public",
        "api/v1/auth/**",
        "/v3/api-docs",
        "/v2/api-docs",
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/webjars/**"};
      @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://dev-2v6nqrql62h5dwnv.us.auth0.com/.well-known/jwks.json").build();
    }
  
     @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers(public_urls).permitAll()
                        .requestMatchers(HttpMethod.POST).permitAll()
                        .requestMatchers(HttpMethod.GET).permitAll()
                        .requestMatchers(HttpMethod.PUT).permitAll()
                        .requestMatchers("/notice/add").permitAll()
                        .anyRequest().authenticated())
              .oauth2ResourceServer(oauth2ResourceServer ->
        oauth2ResourceServer.jwt(jwt -> jwt.decoder(jwtDecoder()))).build();
    }
        @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    //  @Bean
    // public CorsFilter corsFilter() {
    //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    //     CorsConfiguration config = new CorsConfiguration();
    //     config.setAllowCredentials(true);
    //     config.addAllowedOrigin("*"); // Allow all origins
    //     config.addAllowedHeader("*"); // Allow all headers
    //     config.addAllowedMethod("OPTIONS");
    //     config.addAllowedMethod("GET");
    //     config.addAllowedMethod("POST");
    //     config.addAllowedMethod("PUT");
    //     config.addAllowedMethod("DELETE");
    //     source.registerCorsConfiguration("/**", config);
    //     return new CorsFilter(source);
    // }
}