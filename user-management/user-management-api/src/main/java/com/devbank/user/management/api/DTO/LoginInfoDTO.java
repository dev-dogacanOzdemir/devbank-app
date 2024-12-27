package com.devbank.user.management.api.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoDTO {

    private String id;
    private String userId;
    private String ipAddress;
    private LocalDateTime loginTime;

}
