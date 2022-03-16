package com.spring.template.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.spring.template.domain.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@Service
@Slf4j
@Configuration
public class JwtTokenUtil {

    private final String jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP";
    private final String jwtIssuer = "example.io";


    @Value("${jwt.expireAfter}")
    @DurationUnit(ChronoUnit.MILLIS)
    private Duration expireAfter;

    public String generateAccessToken(User user) {
        return JWT.create()
                .withSubject(String.format("%s,%s", user.getId(), user.getUsername()))
                .withIssuer(jwtIssuer)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expireAfter.toMillis()))
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC256(jwtSecret.getBytes()));
    }

    public String getUserId(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret.getBytes())).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getSubject().split(",")[0];
    }

    public Date getExpirationDate(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret.getBytes())).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getExpiresAt();
    }

    public String getUsername(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret.getBytes())).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        return decodedJWT.getSubject().split(",")[1];
    }

    public boolean verify(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(jwtSecret.getBytes())).build();
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException ex) {
            log.error("Invalid JWT - {}", ex.getMessage());
        }
        return false;
    }
}
