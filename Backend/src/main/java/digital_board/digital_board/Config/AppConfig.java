// package digital_board.digital_board.Config;

// import org.springframework.web.filter.CorsFilter;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.web.servlet.FilterRegistrationBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
// import org.springframework.web.cors.CorsConfiguration;
// import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


// @Configuration
// public class AppConfig {

//     @Autowired
//     private UserDetailsService userDetailsService;

//     // @Override
//     // public UserDetails loadUserByUsername(String username) throws
//     // UsernameNotFoundException {
//     // // loading user from databases by username
//     // User user = this.userRepo.findByEmail(username).orElseThrow(()-> new
//     // ResourceNotFoundException("User", "email : "+username,0));

//     // return user;
//     // }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
//         return builder.getAuthenticationManager();
//     }

//     @Bean
//     public DaoAuthenticationProvider daoAuthenticationProvider() {
//         DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//         provider.setUserDetailsService(userDetailsService);
//         provider.setPasswordEncoder(passwordEncoder());
//         return provider;
//     }


//     // @Bean
//     // public FilterRegistrationBean corsFilter() 
//     // {
//     //     CorsConfiguration corsConfiguration = new CorsConfiguration();
//     //     corsConfiguration.setAllowCredentials(true);
//     //     corsConfiguration.addAllowedOriginPattern("*");
//     //     corsConfiguration.addAllowedHeader("Authorization");
//     //     corsConfiguration.addAllowedHeader("Content-Type");
//     //     corsConfiguration.addAllowedHeader("Accept");
//     //     corsConfiguration.addAllowedMethod("POST");
//     //     corsConfiguration.addAllowedMethod("GET");
//     //     corsConfiguration.addAllowedMethod("DELETE");
//     //     corsConfiguration.addAllowedMethod("PUT");
//     //     corsConfiguration.addAllowedMethod("OPTIONS");
//     //     corsConfiguration.setMaxAge(3600L);
    
//     //     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

//     //     source.registerCorsConfiguration("/**", corsConfiguration);
    
//     //     FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));

    
//     //     return bean;
//     // }
    

// }
