package com.sefaunal.resumebuilder.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author github.com/sefaunal
 * @since 2024-01-24
 */
@Data
public class SkillRequest {
    @NotNull
    private String ID;

    @NotNull
    private String skillName;

    @NotNull
    private String skillType;

    @NotNull
    @Size(min = 8, max = 75)
    private String password;
}
