package com.evaluasi.EvaluasiHUMBackEnd.jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {


    private final JwtFilter jwtFilter;

    private final AuthenticationProvider authenticationProvider;


        //use default cors
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http    .cors(withDefaults())
//                .csrf(csrf -> csrf.disable())
//                .authorizeRequests()
//                .requestMatchers("/karyawan/updatecode/{kodekaryawan}","/karyawan/deletecode/{kodekaryawan}","/barang/deletecode/{kodebarang}","/barang/updatecode/{kodebarang}","/api/auth/user","/user/login","/user/profile","/user/changePassword","/user/me","/user/register","/user/forgotPassword","/user/register/accountVerification/**","/karyawan/all","/karyawan/add","/karyawan/cari/{id}","/karyawan/delete/{id}","/karyawan/delete/","/karyawan/update/","/barang/update/","/barang/delete/","/karyawan/update/{id}","/barang/all","/barang/add","/barang/cari/{id}","/barang/delete/{id}","/barang/update/{id}")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }

        // use corsConfigurationSource()
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http    .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers("/penilaian/**","/bobotkriteria/**","/rank/**","/kriteria/**","/picos/**","/cpt/**","/salesdetail/**","/api/**","/sales/**","/jawaban/**","/karyawan/**","/rule/**","/evaluasi/**","/pertanyaan/**","/user/**","/hasilevaluasi/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
        }


        @Bean
        CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173","http://localhost:5173/"));
        configuration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin",
                "Content-Type", "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        configuration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept",
                "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



}
