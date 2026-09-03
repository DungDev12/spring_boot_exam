package com.example.srs.controllers;

import com.example.srs.models.entities.Role;
import com.example.srs.models.entities.dto.request.role.CreateRoleRequest;
import com.example.srs.models.entities.dto.request.role.UpdateRoleRequest;
import com.example.srs.models.entities.dto.response.ApiResponse;
import com.example.srs.services.impl.RoleServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleServiceImpl roleService;

    @PostMapping
    public ResponseEntity<ApiResponse<Role>> createdRole(
            @Valid @RequestBody CreateRoleRequest dto
            ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse
                        .success(roleService.create(dto),
                        "Tạo vai trò thành công"));
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String search,
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
                .body(ApiResponse
                        .pageSuccess(roleService.getAll(search,pageable),
                        "Lấy dữ liệu thành công"));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponse<String>> deleteById(
            @PathVariable Long roleId
    ){
        roleService.delete(roleId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(ApiResponse.success("Xoá role thành công"));
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<?> updateById(
            @PathVariable Long roleId,
            @Valid @RequestBody UpdateRoleRequest dto
            ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleService.update(roleId,dto));
    }
}
