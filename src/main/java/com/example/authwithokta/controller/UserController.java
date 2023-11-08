package com.example.authwithokta.controller;

import com.example.authwithokta.dto.JwtResponse;
import com.example.authwithokta.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/sso/{userRole}/register")
    public ResponseEntity<JwtResponse> registerOidcUser(@PathVariable String userRole){
        log.info("registering the OIDC User");
        return userService.registerOidcUser(userRole);
    }

    @GetMapping("/pl/one")
    public String publicMethodOne(){
        return "Accessing public resources";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/sso/test")
    public String test(){
        return "Accessing private resources";
    }
}
