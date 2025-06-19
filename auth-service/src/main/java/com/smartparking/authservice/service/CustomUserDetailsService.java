package com.smartparking.authservice.service;


import com.smartparking.authservice.dto.UserDTO;
import com.smartparking.authservice.feign.UserInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserInterface userInterface;

    public CustomUserDetailsService(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            ResponseEntity<UserDTO> response = userInterface.getUserByEmail(email);
            UserDTO userDTO = response.getBody();

            if (userDTO == null) {
                throw new RuntimeException("User not found: " + email);
            }

            return new org.springframework.security.core.userdetails.User(
                    userDTO.getEmail(),
                    userDTO.getPassword(),
                    List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority(userDTO.getRole()))
            );

        } catch (Exception e) {
            throw new RuntimeException("Failed to load user: " + email, e);
        }
    }

    public UserDTO getUser(String username) throws UsernameNotFoundException {
        try {
            ResponseEntity<UserDTO> response = userInterface.getUserByEmail(username);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load user details: " + username, e);
        }
    }
}
