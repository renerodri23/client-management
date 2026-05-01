package com.renerodriguez.core_client_management_system.adapters.out.db.spring;

import com.renerodriguez.core_client_management_system.adapters.out.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.direcciones LEFT JOIN FETCH u.documentos WHERE u.email = :email")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.direcciones LEFT JOIN FETCH u.documentos WHERE u.userId = :userId")
    Optional<UserEntity> findById(@Param("userId") UUID userId);

    @Query("SELECT DISTINCT u FROM UserEntity u LEFT JOIN FETCH u.direcciones LEFT JOIN FETCH u.documentos")
    List<UserEntity> findAll();

    boolean existsByEmail(String email);
}