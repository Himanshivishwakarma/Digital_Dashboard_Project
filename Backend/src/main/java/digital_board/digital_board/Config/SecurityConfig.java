package digital_board.digital_board.Config;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

// import static org.springframework.security.config.Customizer.withDefaults;

// @Configuration
// @EnableWebSecurity
// public class SecurityConfig {

//     private final LogoutHandler logoutHandler;

//     public SecurityConfig(LogoutHandler logoutHandler) {
//         this.logoutHandler = logoutHandler;
//     }

//     @Bean
//     SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//         return http
//                 .authorizeHttpRequests(auth -> auth
//                         .requestMatchers("/").permitAll().requestMatchers("/public").permitAll()
//                         .requestMatchers("/notice/add").permitAll()
//                         .anyRequest().authenticated())
//                 .logout(logout -> logout
//                         .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                         .addLogoutHandler(logoutHandler))
//                 .oauth2Login(withDefaults())
//                 .formLogin(withDefaults())
//                 .build();
//     }

//     @Bean
//     public RestTemplate restTemplate() {
//         return new RestTemplate();
//     }
   
// }
// src/main/java/com/auth0/example/security/SecurityConfig.java



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    // @Bean
    // public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
       

    //     http.authorizeRequests(authorizeRequests ->
    //     authorizeRequests.requestMatchers(public_urls).permitAll().
    //     requestMatchers("/student").authenticated())
    //     // .authorizeRequests(authorizeRequests -> authorizeRequests.requestMatchers("/Swagger").permitAll())
    //     .oauth2ResourceServer(oauth2ResourceServer ->
    //     oauth2ResourceServer.jwt(jwt -> jwt.decoder(jwtDecoder())));


    // return http.build();

    // }
     @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/").permitAll().requestMatchers("/public").permitAll()
                        .requestMatchers("/notice/add").permitAll()
                        .anyRequest().authenticated())
              .oauth2ResourceServer(oauth2ResourceServer ->
        oauth2ResourceServer.jwt(jwt -> jwt.decoder(jwtDecoder()))).build();
              
    }
        @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
