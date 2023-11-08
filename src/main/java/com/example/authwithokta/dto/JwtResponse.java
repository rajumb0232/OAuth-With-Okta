package com.example.authwithokta.dto;

import com.example.authwithokta.entity.RefreshToken;
import io.jsonwebtoken.Jwt;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtResponse {
    private String accessToken;
    private Date issuedAt;
    private Date expiry;
    private RefreshTokenResponse refreshTokenResponse;

    public String getAccessToken() {
        return accessToken;
    }

    public JwtResponse setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public JwtResponse setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
        return this;
    }

    public Date getExpiry() {
        return expiry;
    }

    public JwtResponse setExpiry(Date expiry) {
        this.expiry = expiry;
        return this;
    }

    public RefreshTokenResponse getRefreshTokenResponse() {
        return refreshTokenResponse;
    }

    public JwtResponse setRefreshTokenResponse(RefreshTokenResponse refreshTokenResponse) {
        this.refreshTokenResponse = refreshTokenResponse;
        return this;
    }
}
