package com.example.srs.securities;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CurrentUserService {

    public UserPrinciple getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        return (UserPrinciple) authentication.getPrincipal();
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public boolean isOwner(Long userId){
       return getCurrentUserId().equals(userId);
    }

    public boolean isAdmin(){
        return getCurrentUser().getAuthorities().stream().anyMatch(role ->
                Objects.equals(role.getAuthority(), "ROLE_ADMIN"));
    }
}
