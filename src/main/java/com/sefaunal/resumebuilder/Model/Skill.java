package com.sefaunal.resumebuilder.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * @author github.com/sefaunal
 * @since 2024-01-22
 */
@Document
@Data
public class Skill {
    @Id
    private String ID;

    private String skillName;

    private String skillType;

    private Instant additionDate;

    private String userID;
}
