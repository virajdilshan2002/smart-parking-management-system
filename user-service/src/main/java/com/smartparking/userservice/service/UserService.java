package com.smartparking.userservice.service;

import com.smartparking.userservice.dto.UserDTO;

public interface UserService {
    int saveUser(UserDTO userDTO);

    UserDTO getUserByEmail(String email);

    boolean existsByEmail(String email);
}
