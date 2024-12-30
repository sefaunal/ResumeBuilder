package com.sefaunal.resumebuilder.Repository;

import com.sefaunal.resumebuilder.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    Page<User> findByRole(String role, Pageable pageable);

    void deleteByUsername(String username);
}