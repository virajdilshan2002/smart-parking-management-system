package com.smartparking.userservice.service;

import com.smartparking.userservice.dto.UserDTO;

public interface UserService {
    boolean saveUser(UserDTO userDTO);
}
