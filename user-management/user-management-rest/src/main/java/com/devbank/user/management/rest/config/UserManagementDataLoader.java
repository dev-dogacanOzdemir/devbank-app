package com.devbank.user.management.rest.config;

import com.devbank.user.management.impl.mongo.document.LoginInfoDocument;
import com.devbank.user.management.impl.mongo.document.UserDocument;
import com.devbank.user.management.impl.mongo.repository.LoginInfoRepository;
import com.devbank.user.management.impl.mongo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import com.devbank.user.management.api.enums.Role;

@Component
public class UserManagementDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final LoginInfoRepository loginInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public UserManagementDataLoader(UserRepository userRepository, LoginInfoRepository loginInfoRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.loginInfoRepository = loginInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            UserDocument admin = new UserDocument(
                    "999999",
                    "Doğacan",
                    "Özdemir",
                    "devbankADMIN",
                    "ADMIN",
                    Role.ROLE_ADMIN,
                    passwordEncoder.encode("dev.bank$$2024"), // Parolayı hashle
                    new Date()
            );
            userRepository.save(admin);

            // Normal kullanıcı
            UserDocument user = new UserDocument(
                    null,
                    "John",
                    "Doe",
                    "11122233444",
                    "5552223344",
                    Role.ROLE_USER,
                    passwordEncoder.encode("hashed_password"), // Parolayı hashle
                    new Date()
            );
            userRepository.save(user);

            // Müşteri
            UserDocument customer = new UserDocument(
                    "1001",
                    "Emel",
                    "Özdemir",
                    "22233344555",
                    "5553334455",
                    Role.ROLE_CUSTOMER,
                    passwordEncoder.encode("dev.bank$$2024"), // Parolayı hashle
                    new Date()
            );
            userRepository.save(customer);
            System.out.println("User data loaded.");
        }

        if (loginInfoRepository.count() == 0) {
            LoginInfoDocument loginInfo = new LoginInfoDocument(1L, 1L, "192.168.0.1", new Date());
            loginInfoRepository.save(loginInfo);
            System.out.println("Login info loaded.");
        }
    }
}

