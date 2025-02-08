package com.project.medicalmanagementsystem.utility.validators;

import java.time.LocalDate;
import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import com.project.medicalmanagementsystem.utility.annotations.PastDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PastDateValidation implements ConstraintValidator<PastDate, Object> {

    @Autowired
    private LocalDate startDate;

    @Override
    public void initialize(PastDate constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        if (value instanceof String string)
            return Integer.parseInt(string) <= Year.now().getValue();
        // return startDate.getYear()<=value && value<=Year.now().getValue();

        if (value instanceof LocalDate date) {
            // Integer year=Integer.parseInt(((String) value).substring(0,4));
            // Integer year=((LocalDate)value).isBefore(startDate);
            // return startDate.getYear()<=year && year<=Year.now().getValue();
            return date.isAfter(startDate);
        }
        return false;
    }

}
