package com.sefaunal.resumebuilder.Service;

import com.sefaunal.resumebuilder.Model.Experience;
import com.sefaunal.resumebuilder.Repository.ExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

/**
 * @author github.com/sefaunal
 * @since 2024-01-23
 */
@Service
@RequiredArgsConstructor
public class ExperienceService {
    private final ExperienceRepository experienceRepository;

    public void addExperience(Experience experience, String userID) {
        experience.setAdditionDate(Instant.now());
        experience.setUserID(userID);
        experience.formatStartDate(experience.getStartDate());

        if (experience.getEndDate() != null) {
            experience.formatEndDate(experience.getEndDate());
        }

        experienceRepository.save(experience);
    }

    public Collection<Experience> findAllExperiencesByUserID(String userID) {
        return experienceRepository.findAllByUserID(userID);
    }

    public void deleteRecordByID(String experienceID, String userID) {
        Experience experience = experienceRepository.findById(experienceID).orElseThrow();

        if (!experience.getUserID().equals(userID)) {
            throw new AccessDeniedException("IDs Don't Match. You Are Not Authorized!");
        }

        experienceRepository.deleteById(experienceID);
    }
}
