package com.example.srs.validations;

import com.example.srs.enums.ERRORCODE;
import com.example.srs.exceptions.AlreadyExistsException;
import com.example.srs.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleValidator {
    private final RoleRepository roleRepository;

    public void validateForCreate(String name){
        validateNameAvailable(name);
    }

    public void validateForUpdate(String name){
        validateNameAvailable(name);
    }

    public void validateNameAvailable(String name){
        if(roleRepository.existsByName(name.toUpperCase())){
            throw new AlreadyExistsException("Role", "name", name.toUpperCase(), ERRORCODE.ROLE_ALREADY_EXISTS);
        }
    }
}
