package com.devbank.user.management.api.DTO;

import com.devbank.user.management.api.enums.Role;
import lombok.Data;

@Data
public class AuthenticationRequest {
    private String tcNumber;
    private String phoneNumber;
    private String password;
    private Role role;
}
