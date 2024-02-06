package com.evaluasi.EvaluasiHUMBackEnd.jwt;

// import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;


    // private Claims claims = null;
    private String username = null;
    private String token = null;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(httpServletRequest.getServletPath().matches("")){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        } else if (httpServletRequest.getServletPath().startsWith("")) {
            // Skip token validation for verification URL
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }else {
            String authorizationHeader = httpServletRequest.getHeader("Authorization");


            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
                token = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(token);
                // claims = jwtUtil.extractAllClaims(token);
            }

            if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(jwtUtil.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken );
                }
            }
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }
    public boolean isAdmin(){
        return "admin".equalsIgnoreCase(jwtUtil.extractRole(token));
    }
    public boolean isOwner(){
        return "owner".equalsIgnoreCase(jwtUtil.extractRole(token));
    }

    public boolean isUser() {
        return "user".equalsIgnoreCase(jwtUtil.extractRole(token));
    }
    public String getCurrentUser(){
        return username;
    }

}
