package com.sefaunal.resumebuilder.Model;

import lombok.Data;

/**
 * @author github.com/sefaunal
 * @since 2024-01-25
 */
@Data
public class UserAboutMe {
    private String jobPosition;

    private String phoneNumber;

    private String briefIntroduction;

    private Integer experienceYearsCount;

    private Integer projectsCount;

    private Integer happyClientsCount;

    private String aboutMeSection;

    public Boolean isEmpty() {
        return briefIntroduction == null
                || experienceYearsCount == null
                || projectsCount == null
                || happyClientsCount == null
                || aboutMeSection == null
                || jobPosition == null
                || phoneNumber == null;
    }
}