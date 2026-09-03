package com.example.srs.services.impl;


import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.ResourceNotFoundException;
import com.example.srs.models.entities.Role;
import com.example.srs.models.entities.User;
import com.example.srs.models.entities.dto.request.user.*;
import com.example.srs.models.entities.dto.response.user.UserInfoResponse;
import com.example.srs.models.mapper.UserMapper;
import com.example.srs.repositories.RoleRepository;
import com.example.srs.repositories.UserRepository;

import com.example.srs.securities.UserPrinciple;
import com.example.srs.services.UserService;
import com.example.srs.validations.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UploadServiceImpl uploadService;
    private final UserValidator userValidator;

    @Transactional
    public UserInfoResponse createUser(UserCreatedRequest dto, MultipartFile fileAvatar){
        userValidator.validateForCreate(dto);
        Set<Role> roles = new HashSet<>();

        Role role = roleRepository.findByName("STUDENT").orElseThrow(() ->
                new ResourceNotFoundException("Role", "name STUDENT", ERRORCODE.SYSTEM_ERROR));
        roles.add(role);

        User user = userMapper.toUser(dto);
        user.setRoles(roles);
        user.setPasswordHash(passwordEncoder.encode(dto.password()));
        user.setActive(true);
        user.setPerson(userMapper.toPerson(dto));
        if(fileAvatar != null && !fileAvatar.isEmpty()){
            String avatarUrl = uploadService.upload(fileAvatar);
            user.getPerson().setAvatarURL(avatarUrl);
        }
        return userMapper.toUserInfoResponse(userRepository.save(user));
    }

    @Override
    public Page<UserInfoResponse> getAllUsers(UserFilterRequest filer, Pageable pageable) {
        return userRepository.findAllUsers(filer,pageable)
                .map(userMapper::toUserInfoResponse);
    }

    @Override
    public UserInfoResponse getUserById(Long id) {
        return userMapper.toUserInfoResponse(getById(id));
    }

    @Override
    public UserInfoResponse updateUserRoleById(Long id, UpdateUserRoleRequest dto) {
        User targetUser = getById(id);
        userValidator.validateCanChangeRole(targetUser);
        Role newRole = roleRepository.findByName(dto.role()).orElseThrow(() ->
                new ResourceNotFoundException("Role", "name ",dto.role(), ERRORCODE.SYSTEM_ERROR));

        targetUser.setRoles(new HashSet<>(Set.of(newRole)));
        return userMapper.toUserInfoResponse(userRepository.save(targetUser));
    }

    @Override
    public UserInfoResponse updateUserActiveStatusById(Long id, UpdateUserActiveRequest dto) {
        User targetUser = getById(id);
        targetUser.setActive(dto.isActive());
        return userMapper.toUserInfoResponse(userRepository.save(targetUser));
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User user = getById(id);
        userValidator.validateCanDelete(user);
        uploadService.delete(user.getPerson().getAvatarURL());
        userRepository.delete(user);
    }

    @Override
    @PreAuthorize("""
        hasRole('ADMIN') or
        @userPermission.isOwner(#id)
    """)
    public UserInfoResponse updateUserById(Long id, UpdateUserRequest dto, MultipartFile fileAvatar) {
        User user = getById(id);
        userMapper.updateUserFromDto(dto, user);
        if(fileAvatar != null && !fileAvatar.isEmpty()){
            uploadService.delete(user.getPerson().getAvatarURL());
            String avatarUrl = uploadService.upload(fileAvatar);
            user.getPerson().setAvatarURL(avatarUrl);
        }
        return userMapper.toUserInfoResponse(userRepository.save(user));
    }

    @Override
    @PreAuthorize("""
        hasRole('ADMIN') or
        @userPermission.isOwner(#id)
    """)
    public UserInfoResponse updatePasswordUserById(Long id, UpdatePasswordUserRequest request) {
        User user = getById(id);
        userValidator.validateForUpdatePassword(user, request.password());
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        return userMapper.toUserInfoResponse(userRepository.save(user));
    }

    public User getById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id, ERRORCODE.USER_NOTFOUND));
    }
}
