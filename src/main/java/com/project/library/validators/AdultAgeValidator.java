package com.project.library.validators;

import com.project.library.annotations.AdultAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AdultAgeValidator implements ConstraintValidator<AdultAge, LocalDate> {

    private static final int ADULT_AGE = 18;

    @Override
    public void initialize(AdultAge constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return false;
        }
        return Period.between(localDate, LocalDate.now()).getYears() >= ADULT_AGE;
    }
}
