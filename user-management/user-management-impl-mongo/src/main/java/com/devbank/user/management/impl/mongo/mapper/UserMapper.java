package com.devbank.user.management.impl.mongo.mapper;

import com.devbank.user.management.api.DTO.UserDTO;
import com.devbank.user.management.impl.mongo.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserDTO toDto(User user) {
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

    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }

}
