package com.example.activity_tracker_monitor.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private SecretKey Key(){
        return Keys.hmacShaKeyFor(JwtCredentials.SECRET.getBytes());
    }

    public String generateToken(long deviceId, long employeeId){
        return Jwts.builder()
                .subject(String.valueOf(deviceId))
                .claim("employeeId" , employeeId)
                .claim("role" , "DEVICE_AGENT")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + JwtCredentials.EXPIRATION))
                .signWith(Key())
                .compact();

    }
}
