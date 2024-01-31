package com.evaluasi.EvaluasiHUMBackEnd.jwt;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableMethodSecurity
public class ApplicationConfig {
    @Bean
    public UserDetailsService userDetailsService(){
//        UserDetails admin = User.withUsername("yogi@gmail.com")
//                .password(passwordEncoder().encode("yogiset"))
//                .roles("USER")
////                .accountExpired(false)
////                .accountLocked(false)
////                .credentialsExpired(false)
////                .disabled(false)
//                .build();
//        UserDetails user = User.withUsername("john@gmail.com")
//                .password(passwordEncoder().encode("johnna"))
//                .roles("USER","ADMIN","HR")
////                .accountExpired(false)
////                .accountLocked(false)
////                .credentialsExpired(false)
////                .disabled(false)
//                .build();
//        return new InMemoryUserDetailsManager(admin, user); //get user,password from UserDetailsService
        return new CustomUserDetailsService(); // get user,password,email,status,etc from database
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
