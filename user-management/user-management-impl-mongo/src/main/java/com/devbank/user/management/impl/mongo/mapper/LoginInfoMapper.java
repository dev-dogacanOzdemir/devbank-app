package com.devbank.user.management.impl.mongo.mapper;

import com.devbank.user.management.api.DTO.LoginInfoDTO;
import com.devbank.user.management.impl.mongo.document.LoginInfoDocument;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LoginInfoMapper {


    public LoginInfoDocument toDocument(LoginInfoDTO dto) {
        return new LoginInfoDocument(
                dto.getId(),
                dto.getUserId(),
                dto.getIpAddress(),
                dto.getLoginTime() != null ? dto.getLoginTime() : java.time.LocalDateTime.now() // Eğer DTO'da zaman yoksa yeni bir zaman oluştur
        );
    }

    public LoginInfoDTO toDTO(LoginInfoDocument document) {
        return new LoginInfoDTO(
                document.getId(),
                document.getUserId(),
                document.getIpAddress(),
                document.getLoginTime()
        );
    }
}
