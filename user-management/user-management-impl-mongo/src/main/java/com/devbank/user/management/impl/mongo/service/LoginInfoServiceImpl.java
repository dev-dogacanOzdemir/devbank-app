package com.devbank.user.management.impl.mongo.service;

import com.devbank.user.management.api.DTO.LoginInfoDTO;
import com.devbank.user.management.api.service.LoginInfoService;
import com.devbank.user.management.impl.mongo.mapper.LoginInfoMapper;
import com.devbank.user.management.impl.mongo.model.LoginInfo;
import com.devbank.user.management.impl.mongo.repository.LoginInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {

    private final LoginInfoRepository loginInfoRepository;
    private final LoginInfoMapper loginInfoMapper;

    @Autowired
    public LoginInfoServiceImpl(LoginInfoRepository loginInfoRepository, LoginInfoMapper loginInfoMapper) {
        this.loginInfoRepository = loginInfoRepository;
        this.loginInfoMapper = loginInfoMapper;
    }

    @Override
    public void saveLoginInfo(LoginInfoDTO loginInfoDTO) {
        LoginInfo loginInfo = loginInfoMapper.toEntity(loginInfoDTO);
        loginInfoRepository.save(loginInfo);
    }

    @Override
    public List<LoginInfoDTO> getLoginInfoByUserId(Long userId) {
        Objects.requireNonNull(userId, "UserId cannot be null");
        List<LoginInfo> loginInfos = loginInfoRepository.findByUserId(userId);
        return loginInfos.stream()
                .map(loginInfo -> new LoginInfoDTO(loginInfo.getIpAddress(), loginInfo.getLoginTime()))
                .collect(Collectors.toList());
    }
}
