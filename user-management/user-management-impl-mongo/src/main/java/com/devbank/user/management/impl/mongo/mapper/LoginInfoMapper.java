package com.devbank.user.management.impl.mongo.mapper;

import com.devbank.user.management.api.DTO.LoginInfoDTO;
import com.devbank.user.management.impl.mongo.model.LoginInfo;
import org.springframework.stereotype.Component;

@Component
public class LoginInfoMapper {

    public LoginInfoDTO toDto(LoginInfo loginInfo) {
        LoginInfoDTO dto = new LoginInfoDTO();
        dto.setUserId(loginInfo.getUserId());
        dto.setIpAddress(loginInfo.getIpAddress());
        dto.setLoginTime(loginInfo.getLoginTime());
        return dto;
    }

    public LoginInfo toEntity(LoginInfoDTO dto) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserId(dto.getUserId());
        loginInfo.setIpAddress(dto.getIpAddress());
        loginInfo.setLoginTime(dto.getLoginTime());
        return loginInfo;
    }
}
