package com.example.authwithokta.security;

import com.example.authwithokta.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CustomerUserDetails(
                userRepo.findByUserEmail(username).orElseThrow(
                        () -> new UsernameNotFoundException("Failed to find User with the give user email.")
                )
        );
    }
}
