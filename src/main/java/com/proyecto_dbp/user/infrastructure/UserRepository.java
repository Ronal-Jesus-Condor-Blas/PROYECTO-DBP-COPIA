// src/main/java/com/proyecto_dbp/user/infrastructure/UserRepository.java
package com.proyecto_dbp.user.infrastructure;

import com.proyecto_dbp.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends  JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

