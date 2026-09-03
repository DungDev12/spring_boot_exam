package com.example.srs.validations;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.AccessDeniedException;
import com.example.srs.exceptions.BadRequestException;
import com.example.srs.exceptions.UserAlreadyExistsException;
import com.example.srs.models.entities.User;
import com.example.srs.models.entities.dto.request.user.UserCreatedRequest;
import com.example.srs.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void validateForCreate(
            UserCreatedRequest dto
    ) {
        validateUsernameAvailable(dto.username());
        validateEmailAvailable(dto.email());
    }

    public void validateCanChangeRole(
            User targetUser
    ){
        boolean targetIsAdmin = targetUser.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals("ADMIN"));
        if(targetIsAdmin) {
            throw new AccessDeniedException("Không có quyền thay đổi vai trò của ADMIN", ERRORCODE.FORBIDDEN);
        }
    }

    public void validateCanDelete(
            User targetUser
    ){
        boolean targetIsAdmin = targetUser.getRoles()
                .stream()
                .anyMatch(role -> role.getName().equals("ADMIN"));
        if(targetIsAdmin) {
            throw new AccessDeniedException("Không được phép xoá tài khoản là ADMIN",ERRORCODE.FORBIDDEN);
        }
    }

    public void validateForUpdatePassword(User user, String newPassword){
        duplicationPassword(user,newPassword);
    }

    public void duplicationPassword(User user, String newPassword){
        if(passwordEncoder.matches(newPassword, user.getPasswordHash())){
            throw new BadRequestException(
                    "Mật khẩu mới không được giống mật khẩu hiện tại",
                    ERRORCODE.INVALID_INPUT_DATA
            );
        }
    }

    public void validateUsernameAvailable(String username) {

        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(
                    "Username đã tồn tại"
            );
        }
    }

    public void validateEmailAvailable(String email) {

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(
                    "Email đã tồn tại"
            );
        }
    }
}
