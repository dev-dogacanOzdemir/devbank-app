package com.devbank.user.management.impl.mongo.mapper;

import com.devbank.user.management.api.DTO.UserDTO;
import com.devbank.user.management.impl.mongo.document.UserDocument;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserDTO toDto(UserDocument user) {
        if (user == null) {
            return null;
        }
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setPhoneNumber(user.getPhoneNumber());
        return dto;
    }

    public UserDocument toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        UserDocument user = new UserDocument();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }

}
