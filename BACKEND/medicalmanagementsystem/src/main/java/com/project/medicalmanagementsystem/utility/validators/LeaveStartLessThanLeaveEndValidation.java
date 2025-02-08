package com.project.medicalmanagementsystem.utility.validators;

import com.project.medicalmanagementsystem.model.Doctor;
import com.project.medicalmanagementsystem.utility.annotations.LeaveStartLessThanLeaveEnd;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LeaveStartLessThanLeaveEndValidation  implements ConstraintValidator<LeaveStartLessThanLeaveEnd, Doctor>{
    
    @Override
    public void initialize(LeaveStartLessThanLeaveEnd constraintAnnotation)
    {
        
    }

    @Override
    public boolean isValid(Doctor value, ConstraintValidatorContext context) {
        if( value.getLeaveEnd().isBefore(value.getLeaveStart()))
        return false;
        return true;
    }

}