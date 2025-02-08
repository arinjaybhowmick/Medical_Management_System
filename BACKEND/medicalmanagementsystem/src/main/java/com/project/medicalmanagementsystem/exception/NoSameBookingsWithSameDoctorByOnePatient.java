package com.project.medicalmanagementsystem.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NoSameBookingsWithSameDoctorByOnePatient extends RuntimeException {
    public NoSameBookingsWithSameDoctorByOnePatient(String message) {
        super(message);
    }
} 
