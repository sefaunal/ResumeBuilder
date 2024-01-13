package com.sefaunal.resumebuilder.Validator;

import com.sefaunal.resumebuilder.Annotation.UniqueEmail;
import com.sefaunal.resumebuilder.Service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private final UserService userService;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return userService.isEmailFree(email);
    }
}