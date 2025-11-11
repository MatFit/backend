package com.aftermath.backend.repository;

import com.aftermath.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

// Where Spring Data JPA interface will be
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Spring Data JPA kinda automatically does this with @Repository if naming conventions are clean
    // It'll generate it's sql queries for these searches
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID uuid);
}
