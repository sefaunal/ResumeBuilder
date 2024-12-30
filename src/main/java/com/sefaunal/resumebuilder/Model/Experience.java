package com.sefaunal.resumebuilder.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * @author github.com/sefaunal
 * @since 2024-01-23
 */
@Data
@Document
public class Experience {
    @Id
    private String ID;

    private String company;

    private String location;

    private String jobTitle;

    private Date startDate;

    private Date endDate;

    private String description;

    private String userID;

    private Instant additionDate;

    private String formattedStartDate;

    private String formattedEndDate;

    public void formatStartDate(Date date) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM yyyy");
        this.formattedStartDate = outputFormat.format(date);
    }

    public void formatEndDate(Date date) {
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM yyyy");
        this.formattedEndDate = outputFormat.format(date);
    }
}
