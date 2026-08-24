package com.example.srs.controllers;

import com.example.srs.models.entities.User;
import com.example.srs.models.entities.dto.request.UserCreatedRequest;
import com.example.srs.repositories.UserRepository;
import com.example.srs.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserServiceImpl userService;
    @GetMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping
    public User createdUser(
            @RequestBody UserCreatedRequest dto
            ){
        return userService.createUser(dto);
    }
}
