package com.devbank.user.management.api.DTO;

import com.devbank.user.management.api.enums.Role;
import lombok.*;
import org.springframework.context.annotation.Bean;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private String userId;
    private Role role;

}
