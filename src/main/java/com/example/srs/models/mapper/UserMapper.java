package com.example.srs.models.mapper;

import com.example.srs.commons.entities.Person;
import com.example.srs.models.entities.Role;
import com.example.srs.models.entities.User;
import com.example.srs.models.entities.dto.request.user.UpdateUserRequest;
import com.example.srs.models.entities.dto.request.user.UserCreatedRequest;
import com.example.srs.models.entities.dto.response.user.UserInfoResponse;
import com.example.srs.models.entities.dto.response.user.UserLoginResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserLoginResponse toUserLoginResponse(User user);

    @Mapping(source = "person", target = ".")
    UserInfoResponse toUserInfoResponse(User user);
    default String map(Role role) {
        return role != null ? role.getName() : null;
    }

    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "person", ignore = true)
    User toUser(UserCreatedRequest  userCreatedDTO);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(source = ".", target = "person")
    @Mapping(target = "person.avatarURL", ignore = true)
    void updateUserFromDto(
            UpdateUserRequest request,
            @MappingTarget User user
    );

    Person toPerson(UserCreatedRequest  userCreatedDTO);
}
