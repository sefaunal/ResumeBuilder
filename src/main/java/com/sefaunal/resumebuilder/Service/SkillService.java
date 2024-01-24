package com.sefaunal.resumebuilder.Service;

import com.sefaunal.resumebuilder.Exception.PasswordException;
import com.sefaunal.resumebuilder.Model.Skill;
import com.sefaunal.resumebuilder.Model.User;
import com.sefaunal.resumebuilder.Repository.SkillRepository;
import com.sefaunal.resumebuilder.Request.SkillRequest;
import com.sefaunal.resumebuilder.Utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public Skill findRecordByID(String ID) {
        return skillRepository.findByID(ID).orElseThrow();
    }

    public Collection<Skill> findAllCoreSkillsByUserID(String userID) {
        return skillRepository.findAllByUserIDAndSkillType(userID, "CORE");
    }

    public Collection<Skill> findAllOtherSkillsByUserID(String userID) {
        return skillRepository.findAllByUserIDAndSkillType(userID, "OTHER");
    }

    public void deleteRecordByID(String skillID, String userID) {
        Skill skill = skillRepository.findByID(skillID).orElseThrow();

        if (!skill.getUserID().equals(userID)) {
            throw new AccessDeniedException("IDs Don't Match. You Are Not Authorized!");
        }

        skillRepository.deleteById(skillID);
    }

    public void updateRecordByID(SkillRequest skillRequest, String userPassword, String userID) {
        Skill skill = skillRepository.findByID(skillRequest.getID()).orElseThrow();

        if (!skill.getUserID().equals(userID)) {
            throw new AccessDeniedException("IDs Don't Match. You Are Not Authorized!");
        }

        if (!CommonUtils.checkPasswordsMatch(skillRequest.getPassword(), userPassword)) {
            throw new PasswordException("Passwords Does Not Match");
        }

        skill.setSkillName(skillRequest.getSkillName());
        skill.setSkillType(skillRequest.getSkillType());

        skillRepository.save(skill);
    }
}
