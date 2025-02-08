package com.project.medicalmanagementsystem.exception;

public class DoctorNotFoundException extends Exception {
    public DoctorNotFoundException() {
        super("Doctor not found!!!");
    }
}
