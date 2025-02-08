package com.project.medicalmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.medicalmanagementsystem.model.Patient;
import com.project.medicalmanagementsystem.model.Users;

public interface PatientRepository extends JpaRepository<Patient, Long> {

   Patient findByUser(Users user);

   @Query(value = "SELECT count(*) FROM patient", nativeQuery = true)
   Integer countPatient();
}
