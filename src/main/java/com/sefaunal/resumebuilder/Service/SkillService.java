package com.sefaunal.resumebuilder.Service;

import com.sefaunal.resumebuilder.Model.Skill;
import com.sefaunal.resumebuilder.Repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;

/**
 * @author github.com/sefaunal
 * @since 2024-01-22
 */
@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    public void addSkill(String skillName, String skillType, String userID) {
        Skill skill = new Skill();
        skill.setSkillName(skillName);
        skill.setSkillType(skillType);
        skill.setAdditionDate(Instant.now());
        skill.setUserID(userID);

        skillRepository.save(skill);
    }

    public Collection<Skill> findAllCoreSkillsByUserID(String userID) {
        return skillRepository.findAllByUserIDAndSkillType(userID, "CORE");
    }

    public Collection<Skill> findAllOtherSkillsByUserID(String userID) {
        return skillRepository.findAllByUserIDAndSkillType(userID, "OTHER");
    }

    public void deleteRecordByID(String skillID, String userID) {
        Skill skill = skillRepository.findById(skillID).orElseThrow();

        if (!skill.getUserID().equals(userID)) {
            throw new AccessDeniedException("IDs Don't Match. You Are Not Authorized!");
        }

        skillRepository.deleteById(skillID);
    }
}
