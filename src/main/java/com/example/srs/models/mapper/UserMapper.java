package com.example.srs.models.mapper;

import com.example.srs.models.entities.User;
import com.example.srs.models.entities.dto.request.UserCreatedRequest;
import com.example.srs.models.entities.dto.request.UserLoginRequest;
import com.example.srs.models.entities.dto.response.user.UserLoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserLoginResponse toUserLoginResponse(User user);
    User toUser(UserCreatedRequest  userCreatedDTO);

}
