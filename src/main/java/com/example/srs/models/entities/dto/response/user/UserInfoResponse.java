package com.example.srs.models.entities.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserInfoResponse(
         Long id,
         String username,
         String email,
         String avatarURL,
         String firstName,
         String lastName,
         Set<String> roles,
         boolean active
) {

}
