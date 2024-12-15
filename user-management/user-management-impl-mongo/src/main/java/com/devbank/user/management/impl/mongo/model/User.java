package com.devbank.user.management.impl.mongo.model;


import com.devbank.user.management.api.enums.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "users")
public class User {

    @Id
    private Long id;
    private String name;
    private String surname;
    private String tcNumber;
    private String phoneNumber;
    private Role role;
    private String passwordHash;
    private Date createdAt;

}
