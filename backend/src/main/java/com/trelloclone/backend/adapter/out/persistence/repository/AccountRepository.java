package com.trelloclone.backend.adapter.out.persistence.repository;

import com.trelloclone.backend.adapter.out.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<AccountEntity> findByEmail(String email);

    Optional<AccountEntity> findByUsername(String username);

    void deleteById(UUID id);

}
