package com.sefaunal.resumebuilder.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author github.com/sefaunal
 * @since 2024-01-25
 */
@Data
@AllArgsConstructor
public class UserVisibilitySettings {
    private boolean showBriefIntroduction;

    private boolean showExperienceYearCount;

    private boolean showProjectsCount;

    private boolean showHappyClientsCount;

    private boolean showAboutMeSection;

    private boolean showSkillsSection;

    private boolean showExperiencesSection;

    private boolean showLatestWorksSection;
}
