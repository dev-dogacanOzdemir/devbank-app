package com.devbank.user.management.impl.mongo.document;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class UserDocument {

    @Id
    private  String id;
    private String username;
    private String password;
    private String role;

}
