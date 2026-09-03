package com.example.srs.services.impl;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.ResourceNotFoundException;
import com.example.srs.models.entities.Role;
import com.example.srs.models.entities.dto.request.role.CreateRoleRequest;
import com.example.srs.models.entities.dto.request.role.UpdateRoleRequest;
import com.example.srs.models.mapper.RoleMapper;
import com.example.srs.repositories.RoleRepository;
import com.example.srs.services.RoleService;
import com.example.srs.validations.RoleValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final RoleValidator roleValidator;

    @Override
    @Transactional
    public Role create(CreateRoleRequest dto) {
        roleValidator.validateForCreate(dto.name());
        Role role = roleMapper.toRole(dto);
        role.setName(dto.name().toUpperCase());
        return roleRepository.save(role);
    }

    @Override
    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id, ERRORCODE.ROLE_NOTFOUND));
    }

    @Override
    public Page<Role> getAll(String search ,Pageable pageable) {
        return roleRepository.findAllSearchName(search,pageable);
    }

    @Override
    public Role update(Long id, UpdateRoleRequest dto) {
        Role role = getById(id);
        if(dto.name() != null && !dto.name().isBlank()){
            roleValidator.validateForUpdate(dto.name());
            role.setName(dto.name().toUpperCase());
        }
        if (dto.description() != null) {
            role.setDescription(dto.description());
        }
        return roleRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        Role role = getById(id);
        roleRepository.delete(role);
    }
}
