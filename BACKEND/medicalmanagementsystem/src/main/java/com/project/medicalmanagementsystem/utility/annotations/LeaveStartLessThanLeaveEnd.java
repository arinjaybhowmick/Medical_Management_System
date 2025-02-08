package com.project.medicalmanagementsystem.utility.annotations;

import java.lang.annotation.Target;

import com.project.medicalmanagementsystem.utility.validators.LeaveStartLessThanLeaveEndValidation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { LeaveStartLessThanLeaveEndValidation.class })
public @interface LeaveStartLessThanLeaveEnd {

}
