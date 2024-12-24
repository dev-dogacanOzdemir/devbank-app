package com.devbank.user.management.rest.config;

import com.devbank.user.management.impl.mongo.document.LoginInfoDocument;
import com.devbank.user.management.impl.mongo.document.UserDocument;
import com.devbank.user.management.impl.mongo.repository.LoginInfoRepository;
import com.devbank.user.management.impl.mongo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
import com.devbank.user.management.api.enums.Role;

@Component
public class UserManagementDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final LoginInfoRepository loginInfoRepository;

    public UserManagementDataLoader(UserRepository userRepository, LoginInfoRepository loginInfoRepository) {
        this.userRepository = userRepository;
        this.loginInfoRepository = loginInfoRepository;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            UserDocument user = new UserDocument(1L, "John", "Doe", "12345678901", "5551234567", Role.USER, "hashed_password", new Date());
            userRepository.save(user);
            System.out.println("User data loaded.");
        }

        if (loginInfoRepository.count() == 0) {
            LoginInfoDocument loginInfo = new LoginInfoDocument(1L, 1L, "192.168.0.1", new Date());
            loginInfoRepository.save(loginInfo);
            System.out.println("Login info loaded.");
        }
    }
}

