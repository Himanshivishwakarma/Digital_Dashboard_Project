package digital_board.digital_board.Config;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import digital_board.digital_board.Security.JwtAuthenticationEntryPoint;
import digital_board.digital_board.Security.JwtAuthenticationFilter;





@Configuration
public class SecurityConfig {


    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
           

        
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests().
                // requestMatchers("/auth/login").permitAll().
                requestMatchers(HttpMethod.POST).permitAll().
                requestMatchers(HttpMethod.GET).permitAll().
                requestMatchers(HttpMethod.PUT).permitAll()
                .anyRequest()
                .authenticated()
                .and().exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    

     

        
    }


}