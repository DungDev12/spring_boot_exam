package com.example.srs.services.impl;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.UserAlreadyExistsException;
import com.example.srs.models.entities.Role;
import com.example.srs.models.entities.User;
import com.example.srs.models.entities.dto.request.UserCreatedRequest;
import com.example.srs.models.mapper.UserMapper;
import com.example.srs.repositories.RoleRepository;
import com.example.srs.repositories.UserRepository;
import com.example.srs.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public User createUser(UserCreatedRequest dto){
        existByUsername(dto.getUsername());
        Set<Role> roles = new HashSet<>();

        Role role = roleRepository.getRoleByName("STUDENT");

        roles.add(role);
        User user = userMapper.toUser(dto);
        user.setRoles(roles);
        user.setPasswordHash(dto.getPassword());
        user.setActive(true);
        return userRepository.save(user);
    }

    private void existByUsername(String username){
        if(userRepository.existsUserByUsername(username)){
            throw new UserAlreadyExistsException("User đã tồn tại");
        }
    }
}
