package com.sefaunal.resumebuilder.Request;

import lombok.Data;

/**
 * @author github.com/sefaunal
 * @since 2024-01-25
 */
@Data
public class ProjectRequest {
    private String ID;

    private String title;

    private String description;

    private String password;
}