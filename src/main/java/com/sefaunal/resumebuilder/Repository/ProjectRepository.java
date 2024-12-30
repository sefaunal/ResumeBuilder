package com.sefaunal.resumebuilder.Repository;

import com.sefaunal.resumebuilder.Model.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author github.com/sefaunal
 * @since 2024-01-23
 */
@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {
    Optional<Project> findByID(String ID);

    Collection<Project> findAllByUserID(String userID);
}
