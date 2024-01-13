package com.sefaunal.resumebuilder.Annotation;

import com.sefaunal.resumebuilder.Validator.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;

/**
 * @author github.com/sefaunal
 * @since 2024-01-14
 */
@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "Email is already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
