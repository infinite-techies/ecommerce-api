package com.infinitetechies.ecommerce_api.service.inf;

import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

public interface IJwtService {
    public String generateToken(String email);
    public Key getKey() ;
    public boolean validateToken(String token, UserDetails userDetails);
    public String extractUserEmail(String token);
}
