package com.devbank.user.management.api.service;


import com.devbank.user.management.api.DTO.LoginInfoDTO;

import java.util.List;

public interface LoginInfoService {
    void saveLoginInfo(LoginInfoDTO loginInfo);
    List<LoginInfoDTO> getLoginInfoByUserId(String userId);
}
