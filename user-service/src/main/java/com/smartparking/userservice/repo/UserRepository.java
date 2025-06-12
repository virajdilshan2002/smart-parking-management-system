package com.smartparking.userservice.repo;

import com.smartparking.userservice.dto.UserDTO;
import com.smartparking.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
    UserDTO getByEmail(String email);
}
