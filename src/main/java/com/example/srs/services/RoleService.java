package com.example.srs.services;

import com.example.srs.commons.services.CrudService;
import com.example.srs.models.entities.Role;
import com.example.srs.models.entities.dto.request.role.CreateRoleRequest;
import com.example.srs.models.entities.dto.request.role.UpdateRoleRequest;

public interface RoleService extends CrudService<Long, Role, CreateRoleRequest, UpdateRoleRequest> {
}
