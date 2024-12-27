package com.devbank.user.management.impl.mongo.service;

import com.devbank.user.management.api.DTO.LoginInfoDTO;
import com.devbank.user.management.api.service.LoginInfoService;
import com.devbank.user.management.impl.mongo.mapper.LoginInfoMapper;
import com.devbank.user.management.impl.mongo.document.LoginInfoDocument;
import com.devbank.user.management.impl.mongo.repository.LoginInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class LoginInfoServiceImpl implements LoginInfoService {

    private final LoginInfoRepository loginInfoRepository;
    private final LoginInfoMapper loginInfoMapper;

    public LoginInfoServiceImpl(LoginInfoRepository loginInfoRepository, LoginInfoMapper loginInfoMapper) {
        this.loginInfoRepository = loginInfoRepository;
        this.loginInfoMapper = loginInfoMapper;
    }

    @Override
    public void saveLoginInfo(LoginInfoDTO loginInfo) {
        LoginInfoDocument document = loginInfoMapper.toDocument(loginInfo);
        loginInfoRepository.save(document);
    }

    @Override
    public List<LoginInfoDTO> getLoginInfoByUserId(String userId) {
        List<LoginInfoDocument> documents = loginInfoRepository.findAllByUserId(userId);

        return documents.stream()
                .map(loginInfoMapper::toDto)
                .collect(Collectors.toList());
    }




}
