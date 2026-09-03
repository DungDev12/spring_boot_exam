package com.example.srs.services;

import com.example.srs.models.entities.dto.request.user.UserLoginRequest;
import com.example.srs.models.entities.dto.response.user.UserInfoResponse;
import com.example.srs.models.entities.dto.response.user.UserLoginResponse;

public interface AuthService {
    UserInfoResponse getCurrentUser();
    UserLoginResponse login(UserLoginRequest dto);
}
