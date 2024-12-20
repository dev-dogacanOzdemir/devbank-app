package com.devbank.user.management.impl.mongo.mapper;

import com.devbank.user.management.api.DTO.LoginInfoDTO;
import com.devbank.user.management.impl.mongo.document.LoginInfoDocument;
import org.springframework.stereotype.Component;

@Component
public class LoginInfoMapper {

    public LoginInfoDTO toDto(LoginInfoDocument loginInfo) {
        LoginInfoDTO dto = new LoginInfoDTO();
        dto.setUserId(loginInfo.getUserId());
        dto.setIpAddress(loginInfo.getIpAddress());
        dto.setLoginTime(loginInfo.getLoginTime());
        return dto;
    }

    public LoginInfoDocument toEntity(LoginInfoDTO dto) {
        LoginInfoDocument loginInfo = new LoginInfoDocument();
        loginInfo.setUserId(dto.getUserId());
        loginInfo.setIpAddress(dto.getIpAddress());
        loginInfo.setLoginTime(dto.getLoginTime());
        return loginInfo;
    }
}
