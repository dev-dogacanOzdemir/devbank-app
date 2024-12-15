package com.devbank.user.management.impl.mongo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "login_info")
public class LoginInfo {

    @Id
    private Long id;
    private Long userId;
    private String ipAddress;
    private Date loginTime;
}
