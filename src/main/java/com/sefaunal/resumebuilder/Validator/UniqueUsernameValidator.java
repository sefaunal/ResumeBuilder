package com.sefaunal.resumebuilder.Validator;

import com.sefaunal.resumebuilder.Annotation.UniqueUsername;
import com.sefaunal.resumebuilder.Service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final UserService userService;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {}

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return userService.isUsernameFree(username);
    }
}