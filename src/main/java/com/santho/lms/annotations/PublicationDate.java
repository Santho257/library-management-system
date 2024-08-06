package com.santho.lms.annotations;

import com.santho.lms.validators.PubDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PubDateValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PublicationDate {
    public String message() default "Publication Date cannot be in Future";

    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}
