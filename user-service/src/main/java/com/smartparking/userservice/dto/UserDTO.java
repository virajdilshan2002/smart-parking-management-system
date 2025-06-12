package com.smartparking.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDTO {
    private String email;
    private String username;
    private String password;
    private String contact;
    private String address;
    private String role;
}
