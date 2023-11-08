package com.example.authwithokta.service;

import com.example.authwithokta.dto.JwtResponse;
import com.example.authwithokta.dto.RefreshTokenResponse;
import com.example.authwithokta.entity.RefreshToken;
import com.example.authwithokta.entity.User;
import com.example.authwithokta.entity.UserRole;
import com.example.authwithokta.repository.RefreshTokenRepo;
import com.example.authwithokta.repository.UserRepo;
import com.example.authwithokta.security.JwtService;
import com.example.authwithokta.security.SecurityConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private SecurityConfig config;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtResponse jwtResponse;
    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    public ResponseEntity<JwtResponse> registerOidcUser(String userRole) {
        log.info("Extracting User Info from OidcUser");
        OidcUser user = config.getOidcUser();
        User oAuthUser = new User();
        oAuthUser.setUserName(user.getFullName());
        oAuthUser.setUserEmail(user.getEmail());
        oAuthUser.setUserRole(UserRole.valueOf(userRole.toUpperCase()));
        oAuthUser.setUserPassword(generateOAuthUserPassword());

        oAuthUser = userRepo.save(oAuthUser);
        JwtResponse response = generateJwt(oAuthUser.getUserEmail());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private JwtResponse generateJwt(String email) {
        String accessToken = jwtService.generateJwtToken(email);
        RefreshToken refreshToken = jwtService.generateRefreshToken();
        User user = userRepo.findByUserEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("No user found with the specified user name.")
        );
        refreshToken.setUser(user);
        refreshToken = refreshTokenRepo.save(refreshToken);
        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
        refreshTokenResponse.setRefreshToken(refreshToken.getRefreshToken());
        refreshTokenResponse.setExpiry(refreshToken.getExpiry());
        refreshTokenResponse.setIssuedAt(refreshToken.getIssuedAt());

        return jwtResponse
                .setAccessToken(accessToken)
                .setIssuedAt(jwtService.extractTokenIssuedAt(accessToken))
                .setExpiry(jwtService.extractTokenExpiration(accessToken))
                .setRefreshTokenResponse(refreshTokenResponse);
    }

    private String generateOAuthUserPassword() {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "0123456789#$%&@!*0123456789#$%&@!*0123456789#$%&@!*0123456789#$%&@!*0123456789#$%&@!" +
                "*0123456789#$%&@!*0123456789#$%&@!*";
        int pwdLength = 8;

        Random random = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < pwdLength; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }
}
