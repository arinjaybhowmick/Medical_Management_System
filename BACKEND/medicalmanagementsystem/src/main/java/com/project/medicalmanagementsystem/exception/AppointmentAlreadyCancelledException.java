package com.project.medicalmanagementsystem.exception;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AppointmentAlreadyCancelledException extends RuntimeException {
    public AppointmentAlreadyCancelledException(String message) {
        super(message);
    }
}