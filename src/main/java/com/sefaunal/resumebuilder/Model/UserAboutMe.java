package com.sefaunal.resumebuilder.Model;

import lombok.Data;

/**
 * @author github.com/sefaunal
 * @since 2024-01-25
 */
@Data
public class UserAboutMe {
    private Integer totalExperience;

    private Integer happyClientsCount;

    private Integer projectsCount;

    private String briefIntroduction;

    private String aboutMeSection;
}
