package com.sefaunal.resumebuilder.Validator;

import com.sefaunal.resumebuilder.Annotation.EmailUpdate;
import com.sefaunal.resumebuilder.Service.UserService;
import com.sefaunal.resumebuilder.Utils.CommonUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

/**
 * @author github.com/sefaunal
 * @since 2024-01-19
 */
@RequiredArgsConstructor
public class EmailUpdateValidator implements ConstraintValidator<EmailUpdate, String> {
    private final UserService userService;

    @Override
    public void initialize(EmailUpdate constraintAnnotation) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email.equals(userService.findUserByUsername(CommonUtils.getUserInfo()).getEmail())) {
            return true;
        }
        return userService.isEmailFree(email);
    }
}
