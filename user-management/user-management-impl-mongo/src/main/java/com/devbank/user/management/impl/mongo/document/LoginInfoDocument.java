package com.devbank.user.management.impl.mongo.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "login-info")
@AllArgsConstructor
@NoArgsConstructor
public class LoginInfoDocument {

    @Id
    private String id;
    private String userId;
    private String ipAddress;
    private LocalDateTime loginTime;

}
