package com.bzvs.easydict.service.api;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String jwt);

    boolean isTokenValid(String jwt, UserDetails userDetails);

    String generateToken(UserDetails user);

    long getExpirationTime();
}
