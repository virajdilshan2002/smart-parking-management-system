package com.smartparking.authservice.feign;

import com.smartparking.authservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface UserInterface {

    @GetMapping("/api/v1/user/get/{email}")
    ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email);
}
