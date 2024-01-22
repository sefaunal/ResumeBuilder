package com.sefaunal.resumebuilder.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

/**
 * @author github.com/sefaunal
 * @since 2024-01-23
 */
@Data
@Document
public class Project {
    @Id
    private String ID;

    private String title;

    private String description;

    private Instant additionDate;

    private String imageURI;

    private String userID;
}
