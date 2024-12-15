package com.devbank.user.management.api.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class LoginInfoDTO {

    private Long userId;
    private String ipAddress;
    private Date loginTime;

    public LoginInfoDTO(String ipAddress, Date loginTime) {
        this.ipAddress = ipAddress;
        this.loginTime = loginTime;
    }

    public LoginInfoDTO() {}

}
