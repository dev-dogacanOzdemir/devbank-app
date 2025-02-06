package com.devbank.user.management.api.DTO;

import com.devbank.user.management.api.enums.Role;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private String userId;
    private Role role;

}
