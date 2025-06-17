package com.smartparking.userservice.controller;

import com.smartparking.userservice.dto.ResponseDTO;
import com.smartparking.userservice.dto.UserDTO;
import com.smartparking.userservice.service.UserService;
import com.smartparking.userservice.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/isExists/{email}")
    ResponseEntity<Boolean> existsByEmail(@PathVariable String email){
        try {
            boolean exists = userService.existsByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(exists);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody UserDTO userDTO) {
        try {
            int status = userService.saveUser(userDTO);
            switch (status) {
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(VarList.Created, "Registered Success", userDTO.getUsername()));
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
}
