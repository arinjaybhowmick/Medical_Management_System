package com.project.medicalmanagementsystem.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.medicalmanagementsystem.dto.AppointmentStatusDistribution;
import com.project.medicalmanagementsystem.dto.AppointmentStatusDistributionWithSpecialization;
import com.project.medicalmanagementsystem.dto.DoctorDistribution;
import com.project.medicalmanagementsystem.dto.PatientDistribution;
import com.project.medicalmanagementsystem.dto.TopDepartmentAndDoctor;
import com.project.medicalmanagementsystem.dto.TopTimeSlotsWithHighestNumberOfAppointments;
import com.project.medicalmanagementsystem.repository.AppointmentRepository;

@Service
public class ReportGenerationServiceImpl implements ReportGenerationService {
    @Autowired
    AppointmentRepository appointmentRepository;

    public List<AppointmentStatusDistribution> getAppointmentStatusDistribution(LocalDate startDate,
            LocalDate endDate) {
        return appointmentRepository.findAppointmentStatusDistribution(startDate, endDate);
    }

    public List<AppointmentStatusDistributionWithSpecialization> getAppointmentStatusDistributionWithSpecialization(
            LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findAppointmentStatusDistributionWithSpecialization(startDate, endDate);
    }

    public List<TopTimeSlotsWithHighestNumberOfAppointments> getTopTimeSlotsWithHighestNumberOfAppointments(
            LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findTopTimeSlotsWithHighestNumberOfAppointments(startDate, endDate);
    }

    public List<TopDepartmentAndDoctor> getTopDepartmentAndDoctor(LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findTopDepartmentAndDoctor(startDate, endDate);

    }

    public List<PatientDistribution> getPatientDistribution(LocalDate startDate, LocalDate endDate) {

        return appointmentRepository.findPatientDistribution(startDate, endDate);
    }

    public List<DoctorDistribution> getDoctorDistribution(LocalDate startDate, LocalDate endDate) {

        return appointmentRepository.findDoctorDistribution(startDate, endDate);
    }
}