package com.project.medicalmanagementsystem.controller;

import javax.management.InvalidAttributeValueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.medicalmanagementsystem.dto.AppointmentBookingRequestDTO;
import com.project.medicalmanagementsystem.dto.AppointmentSearchRequestDTO;
import com.project.medicalmanagementsystem.service.AppointmentService;
import com.project.medicalmanagementsystem.service.GenerateResponseService;
import com.project.medicalmanagementsystem.service.ReportGenerationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("appointment")
public class AppointmentController {

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    GenerateResponseService generateResponseService;

    @Autowired
    ReportGenerationService reportGenerationService;

    @PostMapping("/book")
    public ResponseEntity<Object> bookAppointment(
            @Valid @RequestBody AppointmentBookingRequestDTO appointmentBookingRequestDTO) {
        
        System.out.println(appointmentBookingRequestDTO);

        return generateResponseService.generateResponse(
                "Appointment successfully booked",
                HttpStatus.OK,
                appointmentService.bookAppointment(appointmentBookingRequestDTO));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Object> cancelAppointment(@PathVariable Long id) throws InvalidAttributeValueException {
        return generateResponseService.generateResponse(
                "Appointment successfully cancelled",
                HttpStatus.OK,
                appointmentService.cancelAppointment(id));
    }

    @PostMapping("/searchAppointments")
    public ResponseEntity<Object> searchAppointments(@Valid @RequestBody AppointmentSearchRequestDTO appointmentSearchRequestDTO)
    {
        System.out.println("Request data = ");
        System.out.println(appointmentSearchRequestDTO);
        return generateResponseService.generateResponse(
            "Appointment List fetched",
            HttpStatus.OK,
            appointmentService.searchAppointments(appointmentSearchRequestDTO));
    }

}
