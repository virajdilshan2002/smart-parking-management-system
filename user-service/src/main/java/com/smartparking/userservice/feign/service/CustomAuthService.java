package com.smartparking.userservice.feign.service;

import com.smartparking.userservice.dto.UserDTO;
import com.smartparking.userservice.feign.AuthInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthService {

    private final AuthInterface authInterface;

    public CustomAuthService(AuthInterface authInterface) {
        this.authInterface = authInterface;
    }


    public String generateToken(UserDTO userDTO) {
        ResponseEntity<String> response = authInterface.generateToken(userDTO);
        return response.getBody();
    }
}
