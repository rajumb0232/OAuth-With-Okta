package com.example.authwithokta.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class RefreshTokenResponse {
    private String refreshToken;
    private Date issuedAt;
    private Date expiry;
}
