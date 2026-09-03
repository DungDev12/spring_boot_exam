package com.example.srs.permissions;

import com.example.srs.securities.CurrentUserService;
import com.example.srs.securities.UserPrinciple;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPermission {

    private final CurrentUserService currentUserService;

    public boolean isOwner(
            Long userId
    ) {
        return currentUserService.getCurrentUserId().equals(userId);
    }

    public boolean IsAdmin(){
        return currentUserService.isAdmin();
    }
}
