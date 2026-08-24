package com.example.srs.models.entities.dto.response.user;

import com.example.srs.models.entities.Role;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserLoginResponse(
        String username,
        String fullName,
        String token,
        Set<Role> roles
) {
}
