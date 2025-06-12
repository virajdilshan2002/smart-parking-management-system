package com.smartparking.vehicleservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserInterface {

    @GetMapping("/api/v1/user/isExists/{email}")
    ResponseEntity<Boolean> existsByEmail(@PathVariable String email);
}
