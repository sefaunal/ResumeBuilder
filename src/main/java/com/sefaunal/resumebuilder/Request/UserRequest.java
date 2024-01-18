package com.sefaunal.resumebuilder.Request;

import com.sefaunal.resumebuilder.Annotation.EmailUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author github.com/sefaunal
 * @since 2024-01-19
 */
@Data
public class UserRequest {
    @NotNull
    @Size(min = 1, max = 75)
    private String firstName;

    @NotNull
    @Size(min = 1, max = 75)
    private String lastName;

    @NotNull
    @EmailUpdate
    @Size(min = 2, max = 75)
    private String email;

    @NotNull
    @Size(min = 8, max = 75)
    private String password;
}
