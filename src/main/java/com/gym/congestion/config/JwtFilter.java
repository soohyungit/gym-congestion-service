package com.gym.congestion.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Authorization 헤더 꺼내기
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // 2. 토큰이 없거나 "Bearer "로 시작하지 않으면 통과 (권한 없음으로 진행)
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 토큰 꺼내기
        String token = authorization.split(" ")[1];

        // 4. 토큰 만료 여부 확인
        if (jwtTokenUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 5. 이메일 꺼내기
        String email = jwtTokenUtil.getEmail(token);

        // 6. 권한 부여 (SecurityContext에 유저 정보 저장)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, null, null); // 나중에 권한 추가 가능
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}