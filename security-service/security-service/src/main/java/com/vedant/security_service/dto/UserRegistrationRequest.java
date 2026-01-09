package com.vedant.security_service.dto;

import com.vedant.security_service.entity.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {

    private String username;
    private String email;
    private String password;
    private Role role;
}
