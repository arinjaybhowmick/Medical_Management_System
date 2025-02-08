package com.project.medicalmanagementsystem.service;

import java.util.NoSuchElementException;

import javax.management.InvalidAttributeValueException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;

import com.project.medicalmanagementsystem.dto.AppointmentBookingRequestDTO;
import com.project.medicalmanagementsystem.dto.AppointmentBookingResponseDTO;
import com.project.medicalmanagementsystem.dto.AppointmentSearchRequestDTO;
import com.project.medicalmanagementsystem.dto.AppointmentSearchResponseDTO;
import com.project.medicalmanagementsystem.exception.AppointmentAlreadyBookedException;
import com.project.medicalmanagementsystem.exception.AppointmentAlreadyCancelledException;
import com.project.medicalmanagementsystem.model.Appointment;

import jakarta.validation.ConstraintViolationException;

public interface AppointmentService {
    public AppointmentBookingResponseDTO bookAppointment(AppointmentBookingRequestDTO appointmentBookingRequestDTO) 
    throws AppointmentAlreadyBookedException, ConstraintViolationException, NoSuchElementException, DataIntegrityViolationException;

    public String cancelAppointment(Long id) throws AppointmentAlreadyCancelledException,InvalidAttributeValueException;

    public Appointment getAppointmentById(Long appointment_id) throws InvalidAttributeValueException; 
    public Page<AppointmentSearchResponseDTO> searchAppointments(AppointmentSearchRequestDTO appointmentSearchRequestDTO);
    public void updateAppointmentStatus();
    
}