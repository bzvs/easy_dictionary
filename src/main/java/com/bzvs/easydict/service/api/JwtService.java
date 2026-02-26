package com.bzvs.easydict.service.api;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String jwt);

    boolean isTokenValid(String jwt, UserDetails userDetails);

    String generateAccessToken(UserDetails user);

    String generateRefreshToken(UserDetails userDetails);

    boolean isRefreshTokenValid(String token);

    long getExpirationTime();

    long getRefreshExpirationTime();
}
