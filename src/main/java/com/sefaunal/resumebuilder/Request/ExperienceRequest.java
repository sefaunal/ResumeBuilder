package com.sefaunal.resumebuilder.Request;

import lombok.Data;

import java.util.Date;

/**
 * @author github.com/sefaunal
 * @since 2024-01-24
 */
@Data
public class ExperienceRequest {
    private String ID;

    private String company;

    private String location;

    private String jobTitle;

    private Date startDate;

    private Date endDate;

    private String description;

    private String password;
}
