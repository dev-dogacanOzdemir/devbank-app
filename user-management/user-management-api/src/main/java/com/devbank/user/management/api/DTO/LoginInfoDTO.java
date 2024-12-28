package com.devbank.user.management.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoDTO {

    private String userId;
    private String ipAddress;
    private LocalDateTime loginTime;

}
