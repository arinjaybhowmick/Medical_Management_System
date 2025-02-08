package com.project.medicalmanagementsystem.service;

import java.util.Optional;

import javax.management.InvalidAttributeValueException;

import com.project.medicalmanagementsystem.dto.PatientDTO;
import com.project.medicalmanagementsystem.model.Patient;
import com.project.medicalmanagementsystem.model.Users;
import com.project.medicalmanagementsystem.utility.enums.BloodGroup;
import com.project.medicalmanagementsystem.utility.enums.Gender;

public interface PatientService {
    Patient create(PatientDTO patientDTO) throws InvalidAttributeValueException;

    Patient read(Long id);

    Patient update(Long id, PatientDTO patientDTO) throws InvalidAttributeValueException;

    Patient validatePatientId(Long id);

    Users validateUsersId(Long id);

    void validatePatientDetails(PatientDTO patientDTO) throws InvalidAttributeValueException;

    boolean validateBirthYear(String birthYear);

    boolean validateGender(Gender gender);

    boolean validateBloodGroup(BloodGroup bloodGroup);

    boolean validateEmail(String email);

    boolean validateContactNumber(String contactNumber);

    Optional<Patient> getPatientById(Long patientId);
}
