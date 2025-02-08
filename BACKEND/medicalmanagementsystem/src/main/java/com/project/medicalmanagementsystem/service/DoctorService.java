package com.project.medicalmanagementsystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.project.medicalmanagementsystem.dto.DashboardDataDTO;
import com.project.medicalmanagementsystem.dto.DoctorDTO;
import com.project.medicalmanagementsystem.dto.SpecializationDTO;
import com.project.medicalmanagementsystem.exception.DoctorNotFoundException;
import com.project.medicalmanagementsystem.model.Doctor;
import com.project.medicalmanagementsystem.model.Specialization;
import com.project.medicalmanagementsystem.utility.enums.Status;

import jakarta.mail.MessagingException;

public interface DoctorService {

    Optional<Doctor> getActiveDoctorById(Long doctorId);

    Doctor addDoctor(DoctorDTO doctor) throws MessagingException;

    Doctor updateDoctor(Long id, DoctorDTO updatedDoctorDto);

    Page<DoctorDTO> searchDoctors(String query, Pageable pageable, String sortBy, String sortDir);

    Page<DoctorDTO> getAllDoctors(Pageable pageable);

    Page<DoctorDTO> findByStatus(Status status, Pageable pageable);

    Page<DoctorDTO> findDoctorsBySpecialization(String specialization, Pageable pageable);

    public void updateDoctorStatus();

    public void updateDoctorRating(Integer newRating, Long appointmentId);

    Page<DoctorDTO> searchAndFilterDoctors(String searchQuery, Status status, Pageable pageable);

    Page<DoctorDTO> searchDoctorsAdmin(String query, Pageable pageable);

    Doctor getDoctorById(Long id) throws DoctorNotFoundException;

    List<LocalDate> getUnavailableDoctorDatesById(Long id) throws DoctorNotFoundException;

    Specialization addSpecialization(SpecializationDTO specializationDTO);

    Specialization updateSpecialization(Long id, SpecializationDTO specializationDTO);

    Map<String, Long> getDoctorSpecializationCounts();

    DashboardDataDTO getDashboardData();
}
