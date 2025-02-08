package com.project.medicalmanagementsystem.service;

import java.time.LocalDate;
import java.util.List;

import com.project.medicalmanagementsystem.dto.AppointmentStatusDistribution;
import com.project.medicalmanagementsystem.dto.AppointmentStatusDistributionWithSpecialization;
import com.project.medicalmanagementsystem.dto.DoctorDistribution;
import com.project.medicalmanagementsystem.dto.PatientDistribution;
import com.project.medicalmanagementsystem.dto.TopDepartmentAndDoctor;
import com.project.medicalmanagementsystem.dto.TopTimeSlotsWithHighestNumberOfAppointments;

public interface ReportGenerationService {
    public List<AppointmentStatusDistribution> getAppointmentStatusDistribution(LocalDate startDate, LocalDate endDate);

    public List<AppointmentStatusDistributionWithSpecialization> getAppointmentStatusDistributionWithSpecialization(
            LocalDate startDate, LocalDate endDate);

    public List<TopTimeSlotsWithHighestNumberOfAppointments> getTopTimeSlotsWithHighestNumberOfAppointments(
            LocalDate startDate, LocalDate endDate);

    public List<TopDepartmentAndDoctor> getTopDepartmentAndDoctor(LocalDate startDate, LocalDate endDate);

    public List<PatientDistribution> getPatientDistribution(LocalDate startDate, LocalDate endDate);

    public List<DoctorDistribution> getDoctorDistribution(LocalDate startDate, LocalDate endDate);
}