package com.smartparking.userservice.controller;

import com.smartparking.userservice.dto.UserDTO;
import com.smartparking.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String getUser() {
        return "User service is running";
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody UserDTO userDTO) {
        boolean status = userService.saveUser(userDTO);
        if (status) {
            return "User created successfully";
        } else {
            return "User creation failed";
        }
    }
}
