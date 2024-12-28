package com.devbank.user.management.impl.mongo.mapper;

import com.devbank.user.management.api.DTO.LoginInfoDTO;
import com.devbank.user.management.impl.mongo.document.LoginInfoDocument;
import com.devbank.user.management.impl.mongo.document.UserDocument;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LoginInfoMapper {


    public LoginInfoDTO toDto(UserDocument userDocument, String ipAddress) {
        return new LoginInfoDTO(
                userDocument.getId(), // User ID
                ipAddress, // IP Address
                LocalDateTime.now() // Login Time
        );
    }

    public LoginInfoDTO toDto(LoginInfoDocument document) {
        return new LoginInfoDTO(
                document.getUserId(), // Kullanıcı ID'si
                document.getIpAddress(), // IP Adresi
                document.getLoginTime() // Giriş Zamanı
        );
    }
    public LoginInfoDocument toDocument(LoginInfoDTO dto) {
        return new LoginInfoDocument(
                null, // ID (MongoDB tarafından oluşturulur)
                dto.getUserId(),
                dto.getIpAddress(),
                dto.getLoginTime()
        );
    }
}
