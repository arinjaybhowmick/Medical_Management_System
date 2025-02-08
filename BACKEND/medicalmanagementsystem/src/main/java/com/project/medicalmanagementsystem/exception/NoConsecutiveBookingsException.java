package com.project.medicalmanagementsystem.exception;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NoConsecutiveBookingsException extends RuntimeException {
    public NoConsecutiveBookingsException(String message) {
        super(message);
    }
}