package com.gym.congestion.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys; // 추가됨!
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey; // 추가됨!
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenUtil {

    // 1. 키는 32자 이상이면 되고, 이제 특수문자가 들어가도 상관없어!
    private String secretKey = "your-very-long-and-secure-secret-key-for-gym-service-2026";
    private long expireTimeMs = 1000 * 60 * 60; // 1시간

    public String createToken(String email) {
        // 2. 문자열을 보안성이 검증된 'SecretKey' 객체로 변환
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(key, SignatureAlgorithm.HS256) // 수정된 부분!
                .compact();
    }
    public String getEmail(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().get("email", String.class);
    }

    public boolean isExpired(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }
}