package com.example.srs.commons.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<ID, RESPONSE, CREATE, UPDATE> {
   RESPONSE create(CREATE dto);

   RESPONSE getById(ID id);

   Page<RESPONSE> getAll(String search ,Pageable pageable);

   RESPONSE update(ID id, UPDATE dto);

   void delete(ID id);
}
