package com.devbank.user.management.api.DTO;

import lombok.Data;

@Data
public class UserDTO {
    private String id;
    private String username;
    private String password;
    private String role; //ADMIN, USER gibi roller
}
