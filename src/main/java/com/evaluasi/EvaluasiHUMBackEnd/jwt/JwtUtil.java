package com.evaluasi.EvaluasiHUMBackEnd.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final SecretKey secretKey;
    private final long jwtExpirationInMillis = 1000 * 60 * 60 * 10; // 10 hours

    public JwtUtil() {
        // Generate a secure key for HS256 algorithm in the constructor
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username, String role,Long id,String nik,Long idkar) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("id", id);
        claims.put("nik", nik);
        claims.put("idkar", idkar);
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMillis))
                .signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Claims decodeJwt(String jwtToken,String secretKey) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtToken).getBody();

        return claims;
    }
    public String extractRole(String token) {
        return extractClaims(token, claims -> (String) claims.get("role"));
    }

    public Claims validateAndExtractClaims(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Check token expiration
            if (claims.getExpiration().before(new Date())) {
                throw new ExpiredJwtException(null, claims, "Token has expired");
            }

            return claims;
        } catch (ExpiredJwtException ex) {
            // Handle token expiration
            throw ex;
        } catch (JwtException e) {
            // Handle invalid tokens
            throw new RuntimeException("Invalid token");
        }
    }
}
