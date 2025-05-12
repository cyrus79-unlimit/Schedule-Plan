package com.jetbrains.cyrus79_unlimit.schedule_plan.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUntil {

    private SecretKey getSigningKey() {
        String jwtSecret = "schedule_plan_project_secret_key";
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    //Token generation
    public String generateToken(String username) {
        long jwtExpirationMs = 86400000;
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Claims validateToken(String token) {
        try {
            Jwt<?, ?> parsedJwt = Jwts.parser()  // new simpler static parser
                    .verifyWith(getSigningKey()) // directly verifies the signature
                    .build()
                    .parse(token);

            return (Claims) parsedJwt.getPayload();
        } catch (JwtException e) {
            // Token is invalid (expired, malformed, etc.)
            throw new RuntimeException("Invalid or expired JWT", e);
        }
    }
}
