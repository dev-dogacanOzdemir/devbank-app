package com.devbank.user.management.api.DTO;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String tcNumber;
    private String phoneNumber;
    private String password;
}
