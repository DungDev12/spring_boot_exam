package com.example.srs.models.mapper;

import com.example.srs.models.entities.Role;
import com.example.srs.models.entities.dto.request.role.CreateRoleRequest;
import com.example.srs.models.entities.dto.request.role.UpdateRoleRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toRole(CreateRoleRequest dto);
    Role toRole(UpdateRoleRequest dto);
}
