package com.devbank.user.management.impl.mongo.document;


import com.devbank.user.management.api.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserDocument {

    @Id
    private String id;
    private String name;
    private String surname;
    private String tcNumber;
    private String phoneNumber;
    private Role role;
    private String passwordHash;
    private Date createdAt;

}
