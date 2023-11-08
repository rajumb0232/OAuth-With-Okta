package com.example.authwithokta.security;

import com.example.authwithokta.dto.JwtResponse;
import com.example.authwithokta.entity.RefreshToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;
@Slf4j
@Service
public class JwtService {
    @Value("${jwt.access-key}")
    private String accessKey;
    @Autowired
    private RefreshToken refreshToken;
    @Autowired
    private JwtResponse jwtResponse;

    public String generateJwtToken(String email){
        log.info("Generating Access Token");
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*15))
                .signWith(singnatureKey(), SignatureAlgorithm.HS512).compact();
    }

    private Key singnatureKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
    }

    public RefreshToken generateRefreshToken(){
        log.info("Generating Refresh Token");
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setIssuedAt(new Date(System.currentTimeMillis()));
        System.out.println(refreshToken.getIssuedAt());
        refreshToken.setExpiry(new Date(System.currentTimeMillis() + (3 * 30 * 24 * 60 * 60 * 1000L)));
        System.out.println(refreshToken.getExpiry());
        return refreshToken;
    }



    // *** Claim extracting methods ***

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(singnatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return  claimResolver.apply(claims);
    }

    public String extractUserName(String jwtToken){
        return extractClaim(jwtToken, Claims::getSubject);
    }
    public Date extractTokenIssuedAt(String jwtToken){return extractClaim(jwtToken, Claims::getIssuedAt);}
    public Date extractTokenExpiration(String jwtToken){return extractClaim(jwtToken, Claims::getExpiration);}
}
