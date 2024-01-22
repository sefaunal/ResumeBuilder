package com.sefaunal.resumebuilder.Repository;

import com.sefaunal.resumebuilder.Model.Experience;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author github.com/sefaunal
 * @since 2024-01-23
 */
@Repository
public interface ExperienceRepository extends MongoRepository<Experience, String> {
    Collection<Experience> findAllByUserID(String userID);
}
