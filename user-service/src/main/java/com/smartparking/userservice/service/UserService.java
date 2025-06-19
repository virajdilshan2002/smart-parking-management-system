package com.smartparking.userservice.service;

import com.smartparking.userservice.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    int saveUser(UserDTO userDTO);

    UserDTO getUserByEmail(String email);

    UserDetails loadUserByUsername(String email);

    boolean existsByEmail(String email);

}
