package com.sefaunal.resumebuilder.Repository;

import com.sefaunal.resumebuilder.Model.Skill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author github.com/sefaunal
 * @since 2024-01-22
 */
@Repository
public interface SkillRepository extends MongoRepository<Skill, String> {
    Optional<Skill> findByID(String ID);

    Collection<Skill> findAllByUserIDAndSkillType(String userID, String skillType);
}
