package com.rogerli.springmall.service;

import com.rogerli.springmall.dto.UserLoginRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Calendar;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JWTService {
    @Autowired
    private AuthenticationManager authenticationManager;

    private final String KEY = "JWTGOGOYAAAAABCABCABCQPALWOSKWEIEURFJF";
    public String generateToken(UserLoginRequest request) {
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authentication = authenticationManager.authenticate(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 2);
        Claims claims = Jwts.claims();
        claims.put("username", userDetails.getUsername());
        claims.setExpiration(calendar.getTime());
        claims.setIssuer("JWT Test");
        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());
        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .compact();
    }

    public Map<String, Object> parseToken(String token){
        Key secretKey = Keys.hmacShaKeyFor(KEY.getBytes());
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
        Claims claims = parser
                .parseClaimsJws(token)
                .getBody();
        return claims.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
