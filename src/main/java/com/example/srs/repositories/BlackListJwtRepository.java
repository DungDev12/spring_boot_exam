package com.example.srs.repositories;

import com.example.srs.models.entities.BlackListJwt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListJwtRepository extends JpaRepository<BlackListJwt, String> {
}
