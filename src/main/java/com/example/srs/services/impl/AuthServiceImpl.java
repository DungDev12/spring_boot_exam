package com.example.srs.services.impl;

import com.example.srs.configs.security.jwt.JwtProvider;
import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.ResourceNotFoundException;
import com.example.srs.models.entities.User;
import com.example.srs.models.entities.dto.request.user.UserLoginRequest;
import com.example.srs.models.entities.dto.response.user.UserInfoResponse;
import com.example.srs.models.entities.dto.response.user.UserLoginResponse;
import com.example.srs.models.mapper.UserMapper;
import com.example.srs.repositories.UserRepository;
import com.example.srs.securities.CurrentUserService;
import com.example.srs.securities.UserPrinciple;
import com.example.srs.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final AuthenticationProvider authenticationProvider;
    private final UserMapper userMapper;

    @Override
    public UserLoginResponse login(UserLoginRequest dto){
        Authentication authentication;
        authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.username(),
                        dto.password()
                )
        );
        assert authentication != null;
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        assert userPrinciple != null;

        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + dto.username()));

        String token = jwtProvider.generateToken(userPrinciple);
        return new UserLoginResponse(
                user.getUsername(),
                user.getPerson().getFullName(),
                token,
                user.getRoles()
        );
    }

    @Override
    public UserInfoResponse getCurrentUser(){
        User user = userRepository.findByUsername(currentUserService.getCurrentUser().getUsername()).orElseThrow(() ->
                new ResourceNotFoundException("User", "username", currentUserService.getCurrentUser().getUsername(), ERRORCODE.USER_NOTFOUND)
        );
        return userMapper.toUserInfoResponse(user);
    }
}
