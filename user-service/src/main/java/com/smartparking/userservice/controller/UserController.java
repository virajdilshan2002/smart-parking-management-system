package com.smartparking.userservice.controller;

import com.smartparking.userservice.dto.AuthDTO;
import com.smartparking.userservice.dto.ResponseDTO;
import com.smartparking.userservice.dto.UserDTO;
import com.smartparking.userservice.feign.service.CustomAuthService;
import com.smartparking.userservice.service.UserService;
import com.smartparking.userservice.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final CustomAuthService customAuthService;

    public UserController(UserService userService, CustomAuthService customAuthService) {
        this.userService = userService;
        this.customAuthService = customAuthService;
    }


    @PutMapping("/update")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ResponseDTO> retrieveUser(@RequestHeader("Authorization") String authorization, @RequestBody UserDTO userDTO) {
        int status = userService.updateUser(userDTO);
        return switch (status) {
            case VarList.OK ->
                    ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(VarList.OK, "User Updated Success", userDTO));
            case VarList.Bad_Gateway ->
                    ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            default ->
                    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(VarList.Internal_Server_Error, "Error", null));
        };

    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserDTO userDTO) {
        try {
            int status = userService.saveUser(userDTO);
            switch (status) {
                case VarList.Created -> {
                    String token = customAuthService.generateToken(userDTO);
                    AuthDTO authDTO = new AuthDTO();
                    authDTO.setUser(userDTO);
                    authDTO.setToken(token);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Registered Success", authDTO));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseDTO(VarList.Not_Acceptable, "Email Already Used", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/loadUser/{email}")
    public ResponseEntity<UserDetails> loadUserByUsername(@PathVariable String email) {
        try {
            UserDetails userDetails = userService.loadUserByUsername(email);
            return ResponseEntity.status(HttpStatus.OK).body(userDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Feign End Points
    @GetMapping("/get/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        try {
            UserDTO userDTO = userService.getUserByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/isExists/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        try {
            boolean exists = userService.existsByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }
}
