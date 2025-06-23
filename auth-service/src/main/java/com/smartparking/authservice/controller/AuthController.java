package com.smartparking.authservice.controller;

import com.smartparking.authservice.dto.AuthDTO;
import com.smartparking.authservice.dto.LoginDTO;
import com.smartparking.authservice.dto.ResponseDTO;
import com.smartparking.authservice.dto.UserDTO;
import com.smartparking.authservice.service.CustomUserDetailsService;
import com.smartparking.authservice.util.JwtUtil;
import com.smartparking.authservice.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping(path = "/authenticate")
    public ResponseEntity<ResponseDTO> authenticateLogin(@RequestBody LoginDTO loginDTO) {
        UserDTO loadedUser = customUserDetailsService.getUser(loginDTO.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(VarList.Unauthorized, "Invalid Credentials", e.getMessage()));
        }


        if (loadedUser == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        String token = jwtUtil.generateToken(loadedUser);
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO(VarList.Conflict, "Authorization Failure! Please Try Again", null));
        }

        AuthDTO authDTO = new AuthDTO();
        authDTO.setToken(token);
        authDTO.setUser(loadedUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO(VarList.Created, "Authorization Success", authDTO));
    }

    // Feign Client Methods
    @PostMapping(path = "/validate")
    public ResponseEntity<String> generateToken(@RequestBody UserDTO userDTO) {
        String token = jwtUtil.generateToken(userDTO);
        if (token == null || token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Authorization Failure! Please Try Again");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(token);
    }
}
