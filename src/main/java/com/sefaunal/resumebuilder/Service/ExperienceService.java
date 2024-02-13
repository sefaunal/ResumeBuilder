package com.sefaunal.resumebuilder.Service;

import com.sefaunal.resumebuilder.Exception.PasswordException;
import com.sefaunal.resumebuilder.Model.Experience;
import com.sefaunal.resumebuilder.Repository.ExperienceRepository;
import com.sefaunal.resumebuilder.Request.ExperienceRequest;
import com.sefaunal.resumebuilder.Utils.CommonUtils;
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

    public Experience findRecordByID(String ID) {
        return experienceRepository.findByID(ID).orElseThrow();
    }

    public Collection<Experience> findAllExperiencesByUserID(String userID) {
        return experienceRepository.findAllByUserID(userID);
    }

    public void deleteRecordByID(String experienceID, String userID) {
        Experience experience = experienceRepository.findByID(experienceID).orElseThrow();

        if (!experience.getUserID().equals(userID)) {
            throw new AccessDeniedException("IDs Don't Match. You Are Not Authorized!");
        }

        experienceRepository.deleteById(experienceID);
    }

    public void updateRecordByID(ExperienceRequest experienceRequest, String userPassword, String userID) {
        Experience experience = experienceRepository.findByID(experienceRequest.getID()).orElseThrow();

        if (!experience.getUserID().equals(userID)) {
            throw new AccessDeniedException("IDs Don't Match. You Are Not Authorized!");
        }

        if (!CommonUtils.checkPasswordsMatch(experienceRequest.getPassword(), userPassword)) {
            throw new PasswordException("Passwords Does Not Match");
        }

        experience.setCompany(experienceRequest.getCompany());
        experience.setLocation(experienceRequest.getLocation());
        experience.setJobTitle(experienceRequest.getJobTitle());
        experience.setStartDate(experienceRequest.getStartDate());
        experience.formatStartDate(experienceRequest.getStartDate());
        experience.setDescription(experienceRequest.getDescription());

        if (experienceRequest.getEndDate() != null) {
            experience.setEndDate(experienceRequest.getEndDate());
            experience.formatEndDate(experienceRequest.getEndDate());
        }

        if (experienceRequest.getEndDate() == null) {
            experience.setEndDate(null);
            experience.setFormattedEndDate(null);
        }

        experienceRepository.save(experience);
    }
}
