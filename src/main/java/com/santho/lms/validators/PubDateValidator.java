package com.santho.lms.validators;

import com.santho.lms.annotations.PublicationDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class PubDateValidator implements ConstraintValidator<PublicationDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if(localDate == null)   return false;
        LocalDate today = LocalDate.now();
        return localDate.isBefore(today.plusDays(1));
    }
}
