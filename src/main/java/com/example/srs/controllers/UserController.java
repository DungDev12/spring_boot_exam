package com.example.srs.controllers;

import com.example.srs.models.entities.dto.request.user.*;
import com.example.srs.models.entities.dto.response.ApiResponse;
import com.example.srs.models.entities.dto.response.user.UserInfoResponse;
import com.example.srs.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserInfoResponse>>> getAllUsers(
            @ModelAttribute UserFilterRequest filter,
            @PageableDefault(
                    page = 0,
                    size = 10,
                    sort = "id",
                    direction = Sort.Direction.ASC
            )
            Pageable pageable
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        ApiResponse.pageSuccess(
                                userService.getAllUsers(filter, pageable),
                                "Get Users Success")
                );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getUserById(
            @PathVariable Long userId
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse
                        .success(userService.getUserById(userId),
                                "Lấy dữ liệu user có id là " + userId + " thành công")
                );
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ApiResponse<UserInfoResponse>> createdUser(
            @Valid @RequestPart("user") UserCreatedRequest dto,
            @RequestPart(value = "avatar", required = false)
            MultipartFile fileAvatar
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        ApiResponse.success(
                                userService.createUser(dto, fileAvatar),
                                "Created User Successfully"
                        )
                );
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<ApiResponse<UserInfoResponse>> updateUserRole(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRoleRequest dto
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse
                        .success(userService.updateUserRoleById(userId,dto),
                                "Cập nhật user có id là " + userId + " thành công")
                );
    }

    @PutMapping("/{userId}/status")
    public ResponseEntity<ApiResponse<UserInfoResponse>> updateUserActiveStatus(
            @PathVariable Long userId,
            @RequestBody UpdateUserActiveRequest dto
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse
                        .success(userService.updateUserActiveStatusById(userId, dto),
                                "Cập nhật trạng thái người dùng " + userId + " thành công"));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> deleteUserById(
            @PathVariable Long userId
    ){
        userService.deleteUserById(userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse
                        .success("Xoá tài khoản "+userId+" thành công"));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserInfoResponse>> updateInfoUser(
            @PathVariable Long userId,
            @Valid @RequestPart("user") UpdateUserRequest request,
            @RequestPart(value = "avatar", required = false) MultipartFile fileAvatar
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        userService.updateUserById(userId, request, fileAvatar),
                        "Cập nhật thành công"
                ));
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<ApiResponse<UserInfoResponse>> updatePasswordUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdatePasswordUserRequest request
            ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(
                        userService.updatePasswordUserById(userId,request),
                        "Cập nhật mật khẩu thành công"
                ));
    }
}
