package com.sefaunal.resumebuilder.Repository;

import com.sefaunal.resumebuilder.Model.Experience;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author github.com/sefaunal
 * @since 2024-01-23
 */
@Repository
public interface ExperienceRepository extends MongoRepository<Experience, String> {
    Optional<Experience> findByID(String ID);

    Collection<Experience> findAllByUserID(String userID);
}
