package com.example.srs.models.entities.dto.request.user;

public record UserFilterRequest(
        String role,
        boolean status
) {
}
