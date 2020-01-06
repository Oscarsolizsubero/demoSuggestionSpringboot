package com.example.demo.mapper;

import com.example.demo.model.DTO.user.UserDTO;
import com.example.demo.model.DTO.user.UserResponse;
import com.example.demo.model.DTO.jwt.JwtRequest;
import com.example.demo.model.User;

public class UserMapper {
    private UserMapper() {
    }

    public static UserResponse toResponse(User user) {
        return UserResponse.builder().name(user.getName()).id(user.getId()).build();
    }

    public static User toModel(UserDTO authorizationRequest) {
        return User.builder().name(authorizationRequest.getUsername()).password(authorizationRequest.getPassword())
                .build();
    }

}
