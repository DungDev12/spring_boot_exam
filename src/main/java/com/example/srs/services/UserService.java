package com.example.srs.services;

import com.example.srs.models.entities.dto.request.user.*;
import com.example.srs.models.entities.dto.response.user.UserInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    Page<UserInfoResponse> getAllUsers(
            UserFilterRequest filter,
            Pageable pageable
    );

    UserInfoResponse getUserById(Long id);

    UserInfoResponse updateUserRoleById(Long id, UpdateUserRoleRequest dto);

    UserInfoResponse updateUserActiveStatusById(Long id, UpdateUserActiveRequest dto);

    void deleteUserById(Long id);

    UserInfoResponse updateUserById(Long id, UpdateUserRequest dto, MultipartFile fileAvatar);

    UserInfoResponse updatePasswordUserById(Long id, UpdatePasswordUserRequest request);
}
